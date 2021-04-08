import java.awt.event.*; 
import javax.swing.*; 
import java.awt.*; 

/*Main class that runs calculator program- combines the MVC elements*/
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CalcView calcView = new CalcView();
		CalcModel calcModel = new CalcModel();
		CalcController calcController = new CalcController(calcView, calcModel);
	}

}
