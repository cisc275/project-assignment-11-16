
public class BreedingBird extends WalkingBird {

	public final int gravity = 3;
	public final int xScreenCenter = 130;
	public final int yScreenCenter = 130;
	boolean brokenWing = false;
	
	BreedingBird(int xP, int yP) {
		super(xP, yP, 70, 0, 0);
	}
	
	BreedingBird(int xP, int yP, int r, int xV, int yV){
		super(xP, yP, r, xV, yV);
	}
	
	public void update() {
		if (brokenWing) {
			velocity.zero();
		} else {
			super.update();
		}
	}
	
	boolean getBrokenWing() {
		return brokenWing;
	}
	
	public void showBrokenWing() {
		brokenWing = true;
	}

	public void stopBrokenWing() {
		brokenWing = false;
	}
	
	public String getImageName() {
		return brokenWing ? "brokenwingbird" : super.getImageName();
	}
}
