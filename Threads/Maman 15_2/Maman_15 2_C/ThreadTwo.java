public class ThreadTwo extends Thread {
	private SafeSharedData sharedData;
	private final int REQUESTS_NUMBER = 10;
	private int id;
	/* Constructor, gets thread id for debug purposes */
	public ThreadTwo(SafeSharedData sharedData, int id) {
		this.sharedData = sharedData;
		this.id = id;
	}

	@Override
	public void run() {
		/* gets the shared data 10 times */
		for (int i = 0; i < REQUESTS_NUMBER; i++) {
			SharedData temp = sharedData.syncGetDebug(id);
			/*
			 * sleep operation for debugging, to make sure one thread doesn't always "catch"
			 * the synchronization mechanism, and check if other threads interrupt while it
			 * operates
			 */
//			try {
//				sleep(1);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}

	}
}
