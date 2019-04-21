
public class BreedingBird extends WalkingBird {

	public final int gravity = 3;
	public final int xScreenCenter = 130;
	public final int yScreenCenter = 130;
	boolean showBrokenWing = false;
	
	BreedingBird(int xP, int yP) {
		super(xP, yP, 70, 0, 0);
	}
	
	BreedingBird(int xP, int yP, int r, int xV, int yV){
		super(xP, yP, r, xV, yV);
	}
	boolean getBrokenWing() {
		return showBrokenWing;
	}

}
