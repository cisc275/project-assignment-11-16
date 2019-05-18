/**
 * 
 * @author Prescott - Anna
 *
 */
public class MigratingBird extends Moveable {
	private enum State{ DEFAULT, POWERUP, POWERDOWN} 
	
	final static double followDistanceCoefficientX = .05;
	final static double followDistanceCoefficientY = .4;
	private int destinationX;
	private int destinationY;
	private State birdState = State.DEFAULT;
	
	protected double velocityScale = 1;
	protected static double POWERUP_SCALE = 2; 
	protected static double POWERDOWN_SCALE = .5;
	protected static int POWER_DURATION = 40;
	protected int powerTimer = 0;
	
	MigratingBird(int xP, int yP){
		super(xP, yP, 60, 0, 0);
		destinationX = xP;
		destinationY = yP;
	}
	
	MigratingBird(int xP, int yP, int r, int xV, int yV){
		super(xP, yP, r, xV, yV);
	}

	@Override
	void update() {
		
		if(powerTimer == POWER_DURATION) {
			powerTimer--;
		}else if(powerTimer < POWER_DURATION && powerTimer > 0) {
			powerTimer--;
		}else if(powerTimer == 0) {
			powerReset();
		}
		
		velocity.setXY( (int) Math.ceil((destinationX - this.x) * followDistanceCoefficientX), (int) Math.ceil((destinationY - this.y) * followDistanceCoefficientY)  );
		move();
	}
	/**
	 * change the destination of the bird.
	 * 
`	 */
	void setDestination(int xPos, int yPos) {
		destinationX = xPos;//Mute the destination change in X location so that the bird fly vertically
		destinationY = yPos;
	}
	
	public int getDestinationX() {
		return destinationX;
	}
	public int getDestinationY() {
		return destinationY;
	}
	
	public double getVelocityScale() {
		return velocityScale;
	}
	
	public boolean getPowerUp() {
		return (birdState == State.POWERUP);
	}
	public boolean getPowerDown() {
		return (birdState == State.POWERDOWN);
	}
	
	public void powerUp() {
		birdState = State.POWERUP;
		velocityScale = POWERUP_SCALE;
		powerTimer = POWER_DURATION;
	}
	
	public void powerDown() {
		birdState = State.POWERDOWN;
		velocityScale = POWERDOWN_SCALE;
		powerTimer = POWER_DURATION;
	}
	
	public void powerReset() {
		birdState = State.DEFAULT;
		velocityScale = 1;
		powerTimer = 0;
	}
	
	public void test(Enemy e) {
		System.out.println("enemy");
	}
	public void test(Gust g) {
		System.out.println("gust");
	}

	@Override
	public String getImageName() {
		switch(birdState) {
			case POWERUP:
				return "migratingbird-powerup";
			case POWERDOWN:
				return "migratingbird-powerdown";
			default:
				return "migratingbird";
		}
	}
}
