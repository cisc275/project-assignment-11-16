/**
 * 
 * @author Prescott
 *
 */
public class Beetle extends Food {
	public final double BASE_SPEED = 2.0;
	
	public Beetle(int inx, int iny) {
		super(inx, iny, 20, 0, 0);
		velocity.setPolar(BASE_SPEED, VVector.randomTheta());
		scoreValue = 20;
	}
	
	/**
	 * For testing purposes.
	 * @param inx
	 * @param iny
	 * @param r
	 * @param xV
	 * @param yV
	 */
	public Beetle(int inx, int iny, int r, int xV, int yV) {
		super(inx, iny, r, xV, yV);
		scoreValue = 20;
	}
	
	@Override
	void update(EatingBird birb) {
		move();
	}

	@Override
	public String getImageName() {
		return "beetle";
	}
}
