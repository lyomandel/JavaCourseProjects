
/*Thread two - gets and prints the shared data*/
public class ThreadTwo extends Thread {
	private SharedData sharedData;
	private final int REQUESTS_NUMBER = 10;

	/*Constructor*/
	public ThreadTwo(SharedData sharedData) {
		this.sharedData = sharedData;
	}

	@Override
	public void run() {
		/*print the shared data 10 times and sleep in between iterations*/
		for (int i = 0; i < REQUESTS_NUMBER; i++) {
			System.out.println(sharedData.get());
			try {
				sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
