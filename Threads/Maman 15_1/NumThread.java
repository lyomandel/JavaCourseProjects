
import java.util.Random;

/*A Thread holding a random number and its neighbors, and doing a calculation accordingly*/
public class NumThread extends Thread {
	private int currentRound, num, getCount;
	private Random random;
	private NumThread leftThread, rightThread;
	private SynchControl control;
	private final int RANDOM_HIGH = 100;
	private final int RANDOM_LOW = 1;
	private final int GET_NUMBER = 2;

	/* Constructor that gets the SynchControl object and sets a random number */
	public NumThread(SynchControl control) {
		super();
		random = new Random();
		this.control = control;
		currentRound = 0;
		getCount = 0;
		this.leftThread = null;
		this.rightThread = null;
		num = random.nextInt(RANDOM_HIGH) + RANDOM_LOW;
	}

	/* gets 2 NumThreads and sets them as neighbors */
	public void setNeighbors(NumThread leftThread, NumThread rightThread) {
		this.leftThread = leftThread;
		this.rightThread = rightThread;
	}

	/*
	 * returns the number the thread holds, notifies all other threads that it
	 * returned a number
	 */
	public int getNumber() {
		synchronized (control) {
			this.getCount++;
			control.notifyAll();
		}
		return num;
	}

	/* A string representation of the thread is equal to the number it holds */
	@Override
	public synchronized String toString() {
		return Integer.toString(num);
	}

	/*
	 * A run method that performs the operation according to the neighbors, then
	 * waits for all its neighbors to use getNumber before updating
	 * if a round is finished the thread waits for the round number to be updated to the next round
	 */
	@Override
	public void run() {
		while (currentRound < control.getRounds()) {
			if (control.getCurrentRound() == currentRound) {
				int tempNum = num;
				int leftNum = leftThread.getNumber();
				int rightNum = rightThread.getNumber();
				if ((tempNum > leftNum) && (tempNum > rightNum))
					tempNum--;
				if ((tempNum < leftNum) && (tempNum < rightNum))
					tempNum++;
				while (getCount < GET_NUMBER) {
					control.waitForThreads();
				}
				control.threadFinishedRound();
				num = tempNum;
				getCount = 0;
				currentRound++;
			} else {
				control.waitForThreads();
			}
		}
	}
}
