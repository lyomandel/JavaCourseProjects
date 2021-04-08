public class SharedData {
	private int x = 0;
	private int y = 0;

	public SharedData(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public SharedData get() {
		return (new SharedData(x, y));
	}

	public void move(int dx, int dy) {
		x = x + dx;
		y = y + dy;
	}
	@Override
	public String toString() {
		return "x: "+x+" y:" + y;
	}
}
