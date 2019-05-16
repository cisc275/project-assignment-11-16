/**
 * If bird hits hawk, deduct bird hp by 10 pts
 * @author Wenki
 */
public class Hawk extends Enemy {

	Hawk(int xP, int yP) {
		super(xP, yP, 60, -10, 0);
	}
	Hawk(int xP, int yP, int velocity) {
		super(xP, yP, 60, velocity, 0);
	}
	Hawk(int xP, int yP, int r, int xV, int yV) {
		super(xP, yP, r, xV, yV);
	}
	
	public String getImageName() {
		return "hawk";
	}

}
