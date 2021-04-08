
import java.util.*;

/*A model class that handles the logic of number threads*/
public class Model {
	private ArrayList<NumThread> numThreads;
	private SynchControl control;
	private int threadsNum, rounds;
	private boolean isRunning;
	View view;

	/* Constructor that gets the view, in order to print output each round */
	public Model(View view) {
		numThreads = new ArrayList<NumThread>(); // An array list in which the threads are organized
		control = new SynchControl();
		rounds = 0;
		threadsNum = 0;
		isRunning = false;
		this.view = view;
	}

	/* Gets the number of rounds the threads need to run */
	public void setRounds(int rounds) {
		control.setRounds(rounds);
		this.rounds = rounds;
	}

	/* Gets the number of threads that have to run */
	public void setNumberOfThreads(int num) {
		threadsNum = num;
	}

	/*
	 * Create thread in which there's a random number, sets the "neighbors" of the
	 * threads and prints their output each round
	 */
	public void Start() {
		isRunning = true;
		resetThreads();
		/* start threads */
		Iterator<NumThread> itr = numThreads.iterator();
		view.addNumbersLine("Starting numbers: " + getNumbers());
		while (itr.hasNext()) {
			itr.next().start();
		}
		while (control.getCurrentRound() < rounds) {
			/*
			 * if all threads are finished - print them and move to the next round,
			 * otherwise wait for threads to finish
			 */
			if (control.getThreadsFinished() >= threadsNum) {
				view.addNumbersLine("round " + (control.getCurrentRound() + 1) + ": " + getNumbers());
				control.nextRound();
			} else {
				control.waitForThreads();
			}
		}
		isRunning = false;
	}

	/* returns true if the rounds are not finished, else otherwise */
	public boolean getIsRunning() {
		return isRunning;
	}

	/* returns a string with all the current numbers */
	public String getNumbers() {
		String numStr = "";
		Iterator<NumThread> itr = numThreads.iterator();
		while (itr.hasNext()) {
			NumThread numThread = itr.next();
			numStr += " " + numThread.toString();
		}
		return numStr;
	}

	/*
	 * resets the threads by deleting the old ones, creating new threads, and
	 * setting their "neighbors" the first thread has the following thread and the
	 * last thread as neighbors the last thread has the previous thread and the
	 * first thread as neighbors
	 */
	public void resetThreads() {
		control.reset();
		numThreads.clear();
		for (int i = 0; i < threadsNum; i++) {
			numThreads.add(new NumThread(control));
		}
		if (threadsNum > 1) {
			for (int i = 0; i < threadsNum; i++) {
				if (i == 0) {
					numThreads.get(i).setNeighbors(numThreads.get(threadsNum - 1), numThreads.get(i + 1));
				} else if (i == threadsNum - 1) {
					numThreads.get(i).setNeighbors(numThreads.get(i - 1), numThreads.get(0));
				} else {
					numThreads.get(i).setNeighbors(numThreads.get(i - 1), numThreads.get(i + 1));
				}
			}
		} else {
			numThreads.get(0).setNeighbors(numThreads.get(0), numThreads.get(0));
		}
	}
}
