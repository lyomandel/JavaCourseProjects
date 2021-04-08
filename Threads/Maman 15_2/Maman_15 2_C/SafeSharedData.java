/*Safely shared data*/
public class SafeSharedData extends SharedData {

	private boolean getFlag;// true when there is a get operation
	private boolean moveFlag;// true when there is a move operation
	/* Constructor */

	public SafeSharedData(int x, int y) {
		super(x, y);
		getFlag = false;
		moveFlag = false;
	}

	/* wait until notified */
	public synchronized void waitForThreads() {
		try {
			wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* get a flag, change getFlag to the flag */
	public synchronized void changeGetFlage(boolean flag) {
		getFlag = flag;
		notifyAll();
	}

	/* get a flag, change moveFlag to the flag */
	public synchronized void changeMoveFlag(boolean flag) {
		moveFlag = flag;
		notifyAll();
	}

	/*
	 * Synchronized move, moves only when all other threads are not using move or
	 * get operations
	 */
	@Override
	public synchronized void move(int dx, int dy) {
		while (moveFlag || getFlag) {
			waitForThreads();
		}
		moveFlag = true;
		super.move(dx, dy);
		moveFlag = false;
		notifyAll();
	}

	/*
	 * Synchronized get, returns a value only when all other threads are not using
	 * move
	 */
	@Override
	public synchronized SharedData get() {
		while (moveFlag) {
			waitForThreads();
		}
		getFlag = true;
		SharedData temp = super.get();
		getFlag = false;
		notifyAll();
		return temp;
	}

	/*
	 * Synchronized move for debugging multiple treads, gets also the thread id.
	 * prints in between move operations to make sure only move is operating, moves
	 * only when all other threads are not using move or get operations
	 */
	public synchronized void syncMoveDebug(int dx, int dy, int id) {
		while (moveFlag || getFlag) {
			waitForThreads();
		}
		moveFlag = true;
		System.out.println(id + " is moving by : dx - " + dx + " dy - " + dy + "...");
		super.move(dx, dy);
		System.out.println(id + " has finised moving.");
		moveFlag = false;
		notifyAll();
	}

	/*
	 * Synchronized get for debugging multiple treads, gets also the thread id.
	 * prints the SharedData about to be returned to make sure get operates only
	 * when move is done. returns a value only when all other threads are not using
	 * move
	 */
	public synchronized SharedData syncGetDebug(int id) {
		while (moveFlag) {
			waitForThreads();
		}
		getFlag = true;
		SharedData temp = super.get();
		System.out.println(id + " id is printing: " + temp);
		getFlag = false;
		notifyAll();
		return temp;
	}

}
