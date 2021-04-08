import java.util.regex.Pattern;

/*The logic layer representing a Calculator*/
public class CalcModel {

	private String equation; // the main equation
	private boolean isReset; // a flag turns true if the next input will reset the equation
	private boolean dotAdded; // true when there's a dot in a number
	/* constructor */

	public CalcModel() {
		equation = "";
		isReset = false;
		dotAdded = false;
	}

	/* resets the equation */
	public void resetEquation() {
		equation = "";
		isReset = false;
		dotAdded = false;
	}

	/* adds a single digit to the equation */
	public void addDigit(String digit) {
		if (isReset)
			resetEquation();
		if (isNumber(digit.charAt(0))) {
			equation += digit;
		}
	}

	/* Adds an operand\symbol to the equation */
	public void addOperand(String digit) {
		isReset = false;
		dotAdded = false;
		if (equation.length() == 0)
			return;
		if (Character.isDigit(equation.charAt(equation.length() - 1)))
			equation += digit;
	}

	public void addDot() {
		if (equation.length() == 0)
			return;
		if (Character.isDigit(equation.charAt(equation.length() - 1)))
			if (!dotAdded) {
				dotAdded = true;
				equation += '.';
			}
	}

	/* Adds a minus symbol to the equation */
	public void addMinus() {
		isReset = false;
		dotAdded = false;

		if (equation.length() == 0) {
			equation += '-';
			return;
		}
		if (equation.charAt(equation.length() - 1) != '-' && equation.charAt(equation.length() - 1) != '.')
			equation += '-';
	}

	/*
	 * calculates the equation outcome, throws an exception if the calculation has
	 * failed
	 */
	public void equationOutcome() throws Exception {
		/*Only if there's a number in the end of the equation it is finished*/
		if (!isNumber(equation.charAt(equation.length()-1)))
			return;
		priorityOperations();
		secondaryOperations();
		isReset = true;
		dotAdded = true;

	}

	/*
	 * Searches for all priority operations and calculates them. Priority operations
	 * are Multiplication and Division operators. throws an exception if the
	 * calculation has failed
	 */
	private void priorityOperations() throws Exception {
		for (int i = 0; i < equation.length(); i++) {
			if (equation.charAt(i) == '*' || equation.charAt(i) == '/') {
				performOperation(i, equation.charAt(i));
				i = 0;
			}
		}
	}

	/*
	 * Searches for all secondary operations and calculates them. Secondary
	 * operations are Addition and Subtraction operators. throws an exception if the
	 * calculation has failed
	 */
	private void secondaryOperations() throws Exception {
		for (int i = 0; i < equation.length(); i++) {
			if (equation.charAt(i) == '+' || (equation.charAt(i) == '-' && i > 0)) {
				performOperation(i, equation.charAt(i));
				i = 0;
			}
		}
	}

	/*
	 * gets an operation to perform and an index of where the operation is located
	 * in the equation's string. Calculates the reuslt and puts it in the equation
	 * in the corresponding position, throws an exception if the calculation has
	 * failed or if the equation is unreadable.
	 */
	private void performOperation(int index, char op) throws Exception {
		int startIndex, stopIndex, beginOpNumber = 0;
		double num1, num2, result = 0;
		/* Find first number */
		stopIndex = index;
		int i = stopIndex - 1;
		while ((i > 0) && (isNumber(equation.charAt(i)) || equation.charAt(i) == '.'
				|| (equation.charAt(i) == '-' && !isNumber(equation.charAt(i - 1))))) {
			i--;
		}
		if (equation.charAt(i) == '+' || equation.charAt(i) == '/' || equation.charAt(i) == '*')
			i++;
		beginOpNumber = i;
		num1 = Double.parseDouble(equation.substring(i, stopIndex)); // throws an excpetion if failed to create number
		/* Find second number */
		startIndex = index + 1;
		i = startIndex;
		while ((i < equation.length()) && (isNumber(equation.charAt(i)) || equation.charAt(i) == '.'
				|| ((equation.charAt(i) == '-') && !isNumber(equation.charAt(i - 1))))) {
			i++;
		}
		num2 = Double.parseDouble(equation.substring(startIndex, i));
		switch (op) {
		case '+':
			result = num1 + num2;
			break;
		case '-':
			result = num1 - num2;
			break;
		case '*':
			result = num1 * num2;
			break;
		case '/':
			if (num2 == 0)
				throw new ArithmeticException("Can't Divide By 0!");
			result = num1 / num2;
		default:
			break;
		}
		equation = equation.substring(0, beginOpNumber) + String.valueOf(result)
				+ equation.substring(i, equation.length());
	}

	private boolean isNumber(char ch) {
		if ((ch >= '0' && ch <= '9'))
			return true;
		return false;
	}

	public String getEquation() {
		return equation;
	}
}
