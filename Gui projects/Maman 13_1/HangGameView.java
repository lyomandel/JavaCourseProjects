import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HangGameView extends JFrame {
	private final int NO_BODY_PARTS = 0;
	private HangManView hangMan;
	private JTextField inputLetter;
	private JLabel letterBank;
	private JLabel hiddenWord;

	public String getInput() {
		return inputLetter.getText();
	}

	/* Constructor - sets the game's size and view components inside the frame */
	public HangGameView() {
		super();
		setSize(320, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel inputPanel = new JPanel();
		inputLetter = new JTextField(10);
		letterBank = new JLabel();
		hiddenWord = new JLabel();
		inputPanel.add(inputLetter);
		inputPanel.add(letterBank);
		inputPanel.add(hiddenWord);
		hangMan = new HangManView();
		setLayout(new GridLayout(2, 1));
		GridBagConstraints gridBag = new GridBagConstraints();
		gridBag.gridx = 1;
		gridBag.gridy = 1;
		add(hangMan);
		gridBag.gridx = 2;
		gridBag.gridy = 2;
		this.add(inputPanel); // add(label1);
		setVisible(true);
	}

	/* sets the hidden word to show the user */
	public void setHiddenWord(String word) {
		hiddenWord.setText(word);
	}

	/* sets the letter banks to show the user */
	public void setLetterBank(String bank) {
		letterBank.setText(bank);
	}

	/* clears the input text field */
	public void clearInputTextField() {
		inputLetter.setText("");
	}

	/*
	 * gets a number of mistakes, if the number is valid the body parts
	 * corresponding to the mistakes number will be painted
	 */
	public void drawBodyParts(int mistakeNumber) {
		if (mistakeNumber <= BodyPart.values().length)
			hangMan.setBodyParts(mistakeNumber);
	}

	/* resets the hang man's body parts */
	public void resetHangMan() {
		hangMan.setBodyParts(NO_BODY_PARTS);
	}

	/* gets an action listener and adds it to inputLetter component */
	public void addInputListener(ActionListener listener) {
		inputLetter.addActionListener(listener);
	}

	/* gets an error string and displays it as an error message to the user */
	public void displayErrorMessage(String errorMsg) {
		JOptionPane.showMessageDialog(this, errorMsg);
	}

	/*
	 * gets a string, displays the ending screen with the string's message and
	 * returns true if pressed yes, and false otherwise
	 */
	public boolean displayEndScreen(String msg) {
		int userChoice = JOptionPane.showConfirmDialog(null, msg, "Ending Screen", JOptionPane.YES_NO_OPTION);
		if (userChoice == 0)
			return true;
		if (userChoice == 1)
			return false;
		else
			return false;
	}

}
