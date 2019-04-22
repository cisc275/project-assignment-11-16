
public class Grasshopper extends Food {
	private int jumpCooldown;
	private int jumpTimer;
	
	private static final int JUMP_TRIGGER_RADIUS = 100;
	private static final int JUMP_COOLDOWN = 30;
	private static final int JUMP_TIMER = 10;
	private static final int JUMP_SPEED = 30;
	
	public Grasshopper(int inx, int iny) {
		super(inx, iny, 20, 0, 0);
		scoreValue = 40;
		jumpTimer = 0;
		jumpCooldown = (int) (Math.random()*(JUMP_COOLDOWN+JUMP_TIMER));
	}
	
	/**
	 * For testing purposes.
	 * @param inx
	 * @param iny
	 * @param r
	 * @param xV
	 * @param yV
	 */
	public Grasshopper(int inx, int iny, int r, int xV, int yV) {
		super(inx, iny, r, xV, yV);
		scoreValue = 40;
	}
	
	
	@Override
	void update() {
		if (jumpTimer > 0) {
			jumpTimer--;
		} else {
			velocity.setXY(0, 0);
			if (jumpCooldown > 0) {
				jumpCooldown--;
			} else { //TODO I actually want to have this trigger when the player gets close, but that would require changing a lot of things.
				jumpTimer = JUMP_TIMER;
				jumpCooldown = JUMP_COOLDOWN;
				velocity.setPolar(JUMP_SPEED, Math.random()*Math.PI*2);
			}
		}
		move();
	}

	@Override
	public String getImageName() {
		return "grasshopper";
	}
}
