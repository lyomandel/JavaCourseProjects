import java.util.Random;
/*Thread one - moves the shared data*/

public class ThreadOne extends Thread {
	private int[][] numArray;
	private final int NUM_ARRAY_SIZE = 10;
	private final int PAIR = 2;
	private Random random;
	private SafeSharedData sharedData;
	private int id;

	/* Constructor, gets thread id for debug purposes */
	public ThreadOne(SafeSharedData sharedData, int id) {
		this.id = id;
		numArray = new int[NUM_ARRAY_SIZE][PAIR];
		this.sharedData = sharedData;
		random = new Random();
		for (int i = 0; i < NUM_ARRAY_SIZE; i++) {
			for (int j = 0; j < PAIR; j++) {
				numArray[i][j] = random.nextInt(10);
			}
		}
	}

	@Override
	public void run() {
		/* move the shared data 10 times */
		for (int i = 0; i < numArray.length; i++) {
			sharedData.syncMoveDebug(numArray[i][0], numArray[i][1], id);
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
