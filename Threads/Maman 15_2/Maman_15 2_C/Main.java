/*Main class that creates threads and makes them run to test the program*/
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SafeSharedData sharedData = new SafeSharedData(0, 0);
		for (int i = 0; i < 4; i++) {
			ThreadOne t1 = new ThreadOne(sharedData, 2 * i + 1);
			ThreadTwo t2 = new ThreadTwo(sharedData, 2 * i);
			t1.start();
			t2.start();
		}

	}

}
