import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*A controller class that connects the view and model*/
public class Controller {
	Model model;
	View view;

	/* Constructor that gets the model and view */
	public Controller(Model model, View view) {
		this.view = view;
		this.model = model;
		this.view.addStartButtonActionListener(new inputListener());
	}

	/* An action listener to the start button */
	private class inputListener implements ActionListener {
		/*
		 * Action performed that gets the number of threads and number of runs from the
		 * view, sets the model and starts the operations
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (model.getIsRunning()) //don't initiate another run if the the model is already running
				return;
			if ((view.getNumberOfRuns() < 1) || view.getNumberOfThreads() < 1) //start only if the number of threads and runs are above 0
				return;
			view.resetNumbersText();
			model.setNumberOfThreads(view.getNumberOfThreads());
			model.setRounds(view.getNumberOfRuns());
			model.Start();
			while (model.getIsRunning()) {
				try {
					wait();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
}
