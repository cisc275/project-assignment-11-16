
public class Earthworm extends Food {
	
	public Earthworm(int inx, int iny) {
		super(inx, iny, 20, 0, 0);
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
	public Earthworm(int inx, int iny, int r, int xV, int yV) {
		super(inx, iny, r, xV, yV);
		scoreValue = 20;
	}
	
	@Override
	void update() {
		move();
	}

	@Override
	public String getImageName() {
		return "earthworm";
	}
	
}
