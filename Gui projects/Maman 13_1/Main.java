public class Main {
/*
 * A main program that runs hang man game
 * */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HangGameView hangView = new HangGameView();
		HangManModel hangModel= new HangManModel();
		HangGameController gg = new HangGameController(hangView,hangModel);
	}

}
