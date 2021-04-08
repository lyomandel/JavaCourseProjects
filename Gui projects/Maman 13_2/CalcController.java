import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/*Calculator controller class,
 * connects between the logic layer "CalcModel" and the view layer "CalcView"*/
public class CalcController {
	private CalcView calcView;
	private CalcModel calcModel;

	/*
	 * Constructor that gets instances of CalcView and CalcModel and initiates
	 * action listeners
	 */
	public CalcController(CalcView calcView, CalcModel calcModel) {
		this.calcView = calcView;
		this.calcModel = calcModel;
		initActionListeners();

	}

	/* Adds Action Listeners to the view */
	private void initActionListeners() {
		this.calcView.addNumbersActionListener(new NumbersListener());
		this.calcView.addOperandActionListener(new OperandListener());
		this.calcView.addDotActionListener(new DotListener());
		this.calcView.addMinusActionListener(new MinusListener());
		this.calcView.addEqualsActionListener(new EqualsListener());
		this.calcView.addClearActionListener(new ClearListener());
	}

	/* A listener to number inputs */
	private class NumbersListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			calcModel.addDigit(e.getActionCommand());
			calcView.setText(calcModel.getEquation());
		}
	}

	/* A listener to dot input */
	private class DotListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			calcModel.addDot();
			calcView.setText(calcModel.getEquation());
		}
	}

	/* A listener to operand input */
	private class OperandListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			calcModel.addOperand(e.getActionCommand());
			calcView.setText(calcModel.getEquation());

		}
	}

	/* A listener to minus input */
	private class MinusListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			calcModel.addMinus();
			calcView.setText(calcModel.getEquation());
		}
	}

	/* A listener to equals input */
	private class EqualsListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			/*
			 * Tries to calculate the equation, if an exception is caught a corresponding
			 * message will be shown to the user, otherwise the result will be shown
			 */
			try {
				calcModel.equationOutcome();
				calcView.setText(calcModel.getEquation());
			} catch (Exception ex) {
				calcModel.resetEquation();
				calcView.setText(calcModel.getEquation());
				calcView.displayErrorMessage(ex.toString());
			}
		}
	}

	/* A listener to clear input - resets the text field */
	private class ClearListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			calcModel.resetEquation();
			calcView.setText(calcModel.getEquation());
		}
	}
}
