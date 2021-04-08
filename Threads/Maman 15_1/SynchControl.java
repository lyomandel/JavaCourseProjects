/*A control class to synchronize between the threads, counts the rounds and finished threads*/
public class SynchControl {
	private int currentRound, threadsFinished, rounds;

	/* Constructor initiates rounds, finished threads and current round */
	public SynchControl() {
		currentRound = 0;
		threadsFinished = 0;
		this.rounds = 0;
	}

	/* Gets the number of rounds the threads have to run */
	public synchronized void setRounds(int rounds) {
		this.rounds = rounds;
	}

	/* Returns the current round's number */
	public synchronized int getCurrentRound() {
		return currentRound;
	}

	/* Returns the total amount of rounds the threads have to run */
	public synchronized int getRounds() {
		return rounds;
	}

	/*
	 * Counts the number of finished threads, notifies all threads that a thread
	 * finished
	 */
	public synchronized void threadFinishedRound() {
		threadsFinished++;
		notifyAll();
	}

	/* Returns the number of finished threads */
	public synchronized int getThreadsFinished() {
		return threadsFinished;
	}

	/* Waiting until notified */
	public synchronized void waitForThreads() {
		try {
			wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* Starts the next round and notifies all threads */
	public synchronized void nextRound() {
		threadsFinished = 0;
		currentRound++;
		notifyAll();
	}

	/* Resets the current round and finished threads to 0 */
	public void reset() {
		currentRound = 0;
		threadsFinished = 0;
	}
}
