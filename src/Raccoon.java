
public class Raccoon extends Predator {

	Raccoon(int xP, int yP) {
		super(xP, yP, 40, 0, 0);
		speed = 8;
	}
	
	Raccoon(int xP, int yP, int r, int xV, int yV) {
		super(xP, yP, r, xV, yV);
		speed = 8;
	}

	public String getImageName() {
		return "raccoon";
	}
}
