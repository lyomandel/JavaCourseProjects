/*Main class that creates threads and makes them run to test the program*/
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SharedData sharedData = new SharedData(0, 0);
		ThreadOne t1 = new ThreadOne(sharedData);
		ThreadTwo t2 = new ThreadTwo(sharedData);
		t1.start();
		t2.start();
	}

}
