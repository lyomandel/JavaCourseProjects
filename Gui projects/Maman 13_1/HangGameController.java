import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Scanner;

import javax.swing.JOptionPane;

import java.io.*;

/*
 * A controller that connects between the logics of hang man model and the game's view
 * */
public class HangGameController {
	private final int MAX_MISTAKES = 7; // max mistakes allowed for the player
	private final String FILE_WORDS_NAME = "words.txt"; // the file's name has to be words.txt, the file has to be in
														// the same folder that "src" folder is located in -
														// workspace\Maman13_1
	private HangManModel hangModel;
	private HangGameView hangView;
	private Scanner input;
	private File file;
	private String word;
	private int mistakes;

	/* Constructor that gets a hang game view and hang man model */
	public HangGameController(HangGameView hangView, HangManModel hangModel) {
		/*
		 * try to read a file of the words to guess, the file has to be in the same
		 * folder that "src" folder is located in - workspace\Maman13_1
		 */
		file = new File(FILE_WORDS_NAME);
		try {
			input = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println("No words.txt File!!");
		}
		word = "";
		this.hangView = hangView;
		this.hangModel = hangModel;
		this.hangView.addInputListener(new InputListener());
		mistakes = 1;
		playGame();
	}

	/*
	 * resets tha game round - sets a new word, reset mistakes, guessed letters and
	 * hang man
	 */
	public void playGame() {
		hangView.resetHangMan();
		readWord();
		mistakes = 1;
		hangModel.setWord(word);
		this.hangView.setHiddenWord(this.hangModel.getWord());
		this.hangView.setLetterBank(this.hangModel.getLetterBank());
		this.hangView.repaint();

	}

	/* gets the next word in the file */
	private void readWord() {
		if (input.hasNextLine()) {
			word = input.nextLine();
		}
	}

	/* adds body parts to the drawing */
	private void addBodyPart() {
		hangView.drawBodyParts(mistakes);
	}

	/*
	 * ending screen - lets the user choose between another round or closing the
	 * program
	 */
	private void endScreen() {
		String msg = "";
		if (mistakes >= MAX_MISTAKES)
			msg = String.format(":( You Lost, but you can always try again!");
		if (hangModel.getHiddenLettersNumber() <= 0)
			msg = String.format("Congratulations :D!! the word was '%s', \n" + "Do you want to play another round?",
					hangModel.getWord());
		if (hangView.displayEndScreen(msg))
			playGame();
		else
			hangView.dispatchEvent(new WindowEvent(hangView, WindowEvent.WINDOW_CLOSING));
	}

	/*
	 * Input listener passed to the view, on user events the listener will handle
	 * the user's guess
	 */
	private class InputListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String input = "";

			try {
				input = hangView.getInput();
				hangView.clearInputTextField();
				/*check for valid input*/
				if ((input.length() != 1) || (!(Character.isLetter(input.charAt(0))))) {
					hangView.displayErrorMessage("Invalid input! please enter a single letter");
					return;
				}
				/*check the guess and act accordingly*/
				if (!hangModel.letterGuess(input.charAt(0))) {
					mistakes++;
					addBodyPart();
				}
				hangView.setLetterBank(hangModel.getLetterBank());
				hangView.setHiddenWord(hangModel.getWord());
				hangView.repaint();
				/*check if the game has reached its end - too many mistakes or guessed word*/
				if ((mistakes >= MAX_MISTAKES) || (hangModel.getHiddenLettersNumber() <= 0))
					endScreen();
			} catch (Exception ex) {
				System.out.println(ex);
			}
		}
	}

}
