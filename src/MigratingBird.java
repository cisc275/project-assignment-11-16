/**
 * 
 * @author Prescott
 *
 */
public class MigratingBird extends Moveable {

	final static double followDistanceCoefficientX = .05;
	final static double followDistanceCoefficientY = .4;
	private int destinationX;
	private int destinationY;
	private boolean powerUp;
	private boolean powerDown;
	
	MigratingBird(int xP, int yP){
		super(xP, yP, 60, 0, 0);
		destinationX = xP;
		destinationY = yP;
		powerUp = false;
		powerDown = false;
	}
	
	MigratingBird(int xP, int yP, int r, int xV, int yV){
		super(xP, yP, r, xV, yV);
	}

	@Override
	void update() {
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
	
	public boolean getPowerUp() {
		return powerUp;
	}
	public boolean getPowerDown() {
		return powerDown;
	}
	
	public void powerUp() {
		powerUp = true;
		powerDown = false;
	}
	
	public void powerDown() {
		powerUp = false;
		powerDown = true;
	}
	
	public void powerReset() {
		powerUp = false;
		powerDown = false;
	}
	


	@Override
	public String getImageName() {
		if(powerUp) {
			return "migratingbird-powerup";
		}else if (powerDown) {
			return "migratingbird-powerdown";
		}else{
			return "migratingbird";
		}
	}
}
