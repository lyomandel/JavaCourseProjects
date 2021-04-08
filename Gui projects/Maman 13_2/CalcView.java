import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.*;

/*The view layer representing a calculator with numbers 0-9 and operations: / * - + and clear */
public class CalcView extends JFrame {
	JButton ba, bs, bd, bm, beq, bdot, bc; // operations
	ArrayList<JButton> numbers; // numbers
	JPanel buttonPanel; // buttons panel
	JTextField text; // text field where the equation will be shown
	/* constructor */

	public CalcView() {
		buttonPanel = new JPanel();
		initButtons();
		text = new JTextField();
		this.add(text, BorderLayout.NORTH);
		this.add(buttonPanel, BorderLayout.CENTER);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	/* sets all the buttons on the button panel */
	private void initButtons() {
		numbers = new ArrayList<JButton>();
		for (int i = 0; i < 10; i++) {
			numbers.add(new JButton(String.valueOf(i)));
		}
		GridBagConstraints gbc = new GridBagConstraints();
		buttonPanel.setLayout(new GridBagLayout());
		gbc.fill = GridBagConstraints.HORIZONTAL;
		/*
		 * arrange the numbers on the grid from top to bottom - first line 7,8,9 then
		 * 4,5,6 and so on
		 */
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				gbc.gridx = j;
				gbc.gridy = i;
				buttonPanel.add(numbers.get(10 - 3 * (i + 1) + j), gbc);
			}
		/* sets the rest of the buttons on the gird */
		gbc.gridx = 0;
		gbc.gridy = 3;
		buttonPanel.add(numbers.get(0), gbc);
		gbc.gridwidth = 1;
		ba = new JButton("+");
		bs = new JButton("-");
		bd = new JButton("*");
		bm = new JButton("/");
		beq = new JButton("=");
		bdot = new JButton(".");
		bc = new JButton("Clear");
		gbc.gridx = 3;
		gbc.gridy = 0;
		buttonPanel.add(ba, gbc);
		gbc.gridx = 3;
		gbc.gridy = 1;
		buttonPanel.add(bs, gbc);
		gbc.gridx = 3;
		gbc.gridy = 2;
		buttonPanel.add(bd, gbc);
		gbc.gridx = 3;
		gbc.gridy = 3;
		buttonPanel.add(bm, gbc);
		gbc.gridx = 2;
		gbc.gridy = 3;
		buttonPanel.add(beq, gbc);
		gbc.gridx = 1;
		gbc.gridy = 3;
		buttonPanel.add(bdot, gbc);
		gbc.gridwidth = 4;
		gbc.gridx = 0;
		gbc.gridy = 4;
		buttonPanel.add(bc, gbc);
	}

	/* gets text to show on text field */
	public void setText(String text) {
		this.text.setText(text);
	}

	/* gets a listener for numbers and adds it to all number buttons */
	public void addNumbersActionListener(ActionListener listener) {
		numbers.forEach((number) -> number.addActionListener(listener));
	}

	/* adds an action listener to the operations add,divide and multiply */
	public void addOperandActionListener(ActionListener listener) {
		ba.addActionListener(listener);
		bd.addActionListener(listener);
		bm.addActionListener(listener);
	}

	/* adds an action listener listener to dot button */
	public void addDotActionListener(ActionListener listener) {
		bdot.addActionListener(listener);
	}

	/* adds an action listener to minus operation */
	public void addMinusActionListener(ActionListener listener) {
		bs.addActionListener(listener);
	}

	/* adds an action listener to equals operation */
	public void addEqualsActionListener(ActionListener listener) {
		beq.addActionListener(listener);
	}

	/* adds an action listener to clear button */
	public void addClearActionListener(ActionListener listener) {
		bc.addActionListener(listener);
	}

	/* gets an error message and displays it in a message dialog */
	public void displayErrorMessage(String msg) {
		JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
	}
}