/*Safely synchronized shared data class*/
public class SafeSyncSharedData extends SharedData {
	private boolean canMove;

	/* Constructor */
	public SafeSyncSharedData(int x, int y) {
		super(x, y);
		canMove = true;
	}

	/* gets dx and dy, moves the shared data */
	@Override
	public synchronized void move(int dx, int dy) {
		/* wait until the data has been read and the shared data can be moved */
		while (!canMove) {
			waitForThreads();
		}
		super.move(dx, dy);
	}

	/* returns the shared data */
	@Override
	public synchronized SharedData get() {
		/* wait until the data has been moved and the shared data can be get */
		while (canMove) {
			waitForThreads();
		}
		return super.get();
	}
/*Wait until notified*/
	public synchronized void waitForThreads() {
		try {
			wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
/*get a boolean flag, set canMove to the flag*/
	public synchronized void changeMoveFlag(boolean flag) {
		canMove = flag;

	}
}
