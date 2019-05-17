/**
 * A Food that hops around periodically.
 * @author Prescott
 * 
 */
public class Grasshopper extends Food {
	private int jumpCooldown;
	private int jumpTimer;
	
	private static final int JUMP_TRIGGER_RADIUS = 120;
	private static final int JUMP_COOLDOWN = 40;
	private static final int JUMP_TIMER = 5;
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
	void update(EatingBird birb) {
		if (jumpTimer > 0) {
			jumpTimer--;
		} else {
			velocity.zero();
			if (jumpCooldown > 0) {
				jumpCooldown--;
			} else if (distanceTo(birb) <= JUMP_TRIGGER_RADIUS) {
				jumpTimer = JUMP_TIMER;
				jumpCooldown = JUMP_COOLDOWN;
				velocity.setPolar(JUMP_SPEED, angleTo(birb) + Math.PI*(.5+Math.random()));
			}
		}
		move();
	}

	@Override
	public String getImageName() {
		return (jumpTimer > 0) ? "grasshopper" : "grasssitter";
	}
}
