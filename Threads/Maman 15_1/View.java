import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.*;
import java.awt.*;

/*A view class that handles the GUI*/
public class View extends JFrame {
	JButton startButton;
	JTextField threadNumberTxt, runsNumberTxt;
	JLabel runsNumberLabel, threadNumberLabel;
	TextArea outputTextArea;
	String numberOutput;

	/* Constructor, sets the GUI elements */
	public View() {
		super();
		this.setLayout(new GridLayout(3, 1));
		/* user input view */
		threadNumberTxt = new JTextField(30);
		runsNumberTxt = new JTextField(30);
		threadNumberTxt.setText("1");
		runsNumberTxt.setText("1");
		runsNumberLabel = new JLabel();
		threadNumberLabel = new JLabel();
		runsNumberLabel.setText("Number of Runs: ");
		threadNumberLabel.setText("Number of Processes: ");
		JPanel userInputPanel = new JPanel();
		userInputPanel.setLayout(new GridLayout(2, 2));
		userInputPanel.add(threadNumberLabel);
		userInputPanel.add(threadNumberTxt);
		userInputPanel.add(runsNumberLabel);
		userInputPanel.add(runsNumberTxt);
		userInputPanel.revalidate();
		this.add(userInputPanel);
		/* numbers array view */
		outputTextArea = new TextArea();
		outputTextArea.setBounds(10, 30, 300, 300);
		this.add(outputTextArea);
		/* Start Button */
		startButton = new JButton();
		startButton.setText("Start!");
		this.add(startButton);
		numberOutput = "";
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
/*Gets an action listener and adds it to start button*/
	public void addStartButtonActionListener(ActionListener listener) {
		startButton.addActionListener(listener);
	}
/*Gets a String and adds it to the text shown in output text area*/
	public void addNumbersLine(String txt) {
		numberOutput +=  txt + "\n";
		outputTextArea.setText(numberOutput);
	}
/*Reset the text shown in the output text area*/
	public void resetNumbersText() {
		numberOutput = "";
		outputTextArea.setText("");
	}
/*Returns the number of threads input*/
	public int getNumberOfThreads() {
		return Integer.valueOf(threadNumberTxt.getText());
	}
	/*Returns the number of rounds input*/
	public int getNumberOfRuns() {
		return Integer.valueOf(runsNumberTxt.getText());
	}

}
