public class HangManModel {
	private static final int HIDDEN_LETTER_INDEX_FACTOR = 2; // each hidden letter is shown to the user as 2 letters -
	// "_ "
	private final String abcd = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z";
	private String revealedWord; // revealed word to the user
	private String hiddenWord; // hidden word
	private String letterBank;
	private int hiddenLettersCounter; // the number of letters that were not revealed

	/* empty constructor */
	public HangManModel() {
		setWord("");
	}

	/* constructor that sets an hidden word */
	public HangManModel(String word) {
		setWord(word);
	}

	/*
	 * Gets a letter. if the letter is in the hidden word, it moves is to the
	 * revealed word returns false if the guess was mistaken returns true if the
	 * letter was found or was guessed already
	 */
	public boolean letterGuess(char guess) {
		if (!letterBankUpdate(guess))
			return true; // if the letter was guessed already return 'true' because it was not a mistake
		char[] hiddenWordArray = hiddenWord.toCharArray();
		char[] revealedWordArray = revealedWord.toCharArray();
		boolean isFound = false;
		/* go over the hidden word and check for matching letters to 'guess' */
		for (int i = 0; i < hiddenWord.length(); i++)
			if (guess == hiddenWordArray[i] || Character.toUpperCase(guess) == hiddenWordArray[i]) {
				revealedWordArray[i * HIDDEN_LETTER_INDEX_FACTOR] = hiddenWordArray[i];// if the letter was found reveal
																						// // it
				hiddenWordArray[i] = '_';// set the hidden letter to 'found'.
				revealedWord = String.valueOf(revealedWordArray); // update the revealed word
				hiddenWord = String.valueOf(hiddenWordArray); // update the revealed hidden word
				hiddenLettersCounter--; // reduce the number of hidden letters
				isFound = true;
			}
		return isFound;
	}

	/*
	 * gets a letter and updates the letter bank with the chosen letter, returns
	 * true if it was updated, false otherwise
	 */
	public boolean letterBankUpdate(char letter) {
		char[] abcLettersArray = letterBank.toCharArray();
		boolean isFound = false;
		for (int i = 0; i < abcLettersArray.length; i++)
			if (letter == abcLettersArray[i] || Character.toUpperCase(letter) == abcLettersArray[i]) {
				abcLettersArray[i] = '_';
				isFound = true;
			}
		letterBank = String.valueOf(abcLettersArray);
		return isFound;
	}

	/* returns the revealed word */
	public String getWord() {
		return revealedWord;
	}

	/* sets a new hidden word */
	public void setWord(String word) {
		hiddenWord = word;
		resetLetterBank();
		resetRevealedWord();
	}

	/* resets the revealed word */
	private void resetRevealedWord() {
		revealedWord = "";
		hiddenLettersCounter = hiddenWord.length();
		char[] hiddenWordArray = hiddenWord.toCharArray();
		for (int i = 0; i < hiddenWordArray.length; i++) {
			revealedWord += "_ "; // each hidden letter is revealed for the user as 2 letters for easier
									// readability
		}
	}

	/* resets the letter bank */
	public void resetLetterBank() {
		this.letterBank = abcd;
	}

	/* Returns the number of hidden letters */
	public int getHiddenLettersNumber() {
		return hiddenLettersCounter;
	}

	/* returns the word's length */
	public int getWordLength() {
		return revealedWord.length();
	}

	/* returns the letter bank as it is shown */
	public String getLetterBank() {
		return letterBank;
	}

}
