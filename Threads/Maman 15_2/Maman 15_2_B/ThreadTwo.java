/*Thread two - gets and prints the shared data*/
public class ThreadTwo extends Thread {
	private SafeSyncSharedData sharedData;
	private final int REQUESTS_NUMBER = 10;

	/* Constructor */
	public ThreadTwo(SafeSyncSharedData sharedData) {
		this.sharedData = sharedData;
	}

	@Override
	public void run() {
		/* print the shared data 10 times */
		for (int i = 0; i < REQUESTS_NUMBER; i++) {
			synchronized (sharedData) {
				System.out.println(sharedData.get());
				/*
				 * change flag to true - the shared data was read and can move now, then notify
				 * all waiting threads
				 */
				sharedData.changeMoveFlag(true);
				sharedData.notifyAll();
			}

		}
	}
}
