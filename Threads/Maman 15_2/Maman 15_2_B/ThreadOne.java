import java.util.Random;

/*Thread one - moves the shared data*/
public class ThreadOne extends Thread {
	private int[][] numArray;// matrix of random pairs (dx,dy) of numbers
	private final int NUM_ARRAY_SIZE = 10;
	private final int PAIR = 2;
	private Random random;
	private SafeSyncSharedData sharedData;

	/* Constructor */
	public ThreadOne(SafeSyncSharedData sharedData) {
		numArray = new int[NUM_ARRAY_SIZE][PAIR];
		this.sharedData = sharedData;
		random = new Random();
		/* set the array of random pairs - dx, dy */
		for (int i = 0; i < NUM_ARRAY_SIZE; i++) {
			for (int j = 0; j < PAIR; j++) {
				numArray[i][j] = random.nextInt(10) + 1;
			}
		}
	}

	@Override
	public void run() {
		/* move the shared data 10 times */
		for (int i = 0; i < numArray.length; i++) {
			synchronized (sharedData) {
				sharedData.move(numArray[i][0], numArray[i][1]);
				/* set flag to false - the shared data has moved and notify waiting threads */
				sharedData.changeMoveFlag(false);
				sharedData.notifyAll();
			}
		}
	}
}