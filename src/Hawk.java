
public class Hawk extends Enemy {

	Hawk(int xP, int yP) {
		super(xP, yP, 30, 5, 0);
	}
	
	Hawk(int xP, int yP, int r, int xV, int yV) {
		super(xP, yP, r, xV, yV);
	}
	
	public String getImageName() {
		return "hawk";
	}

}
