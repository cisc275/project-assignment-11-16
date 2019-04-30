/**
 * 
 * @author Prescott
 *
 */
public class MigratingBird extends Moveable {

	final static double followDistanceCoefficient = .4;
	final static int distanceFromFrame = 350;
	int destinationX;
	int destinationY;
	
	MigratingBird(int xP, int yP){
		super(distanceFromFrame, yP, 50, 0, 0);
		destinationX = xP;
		destinationY = yP;
	}
	
	MigratingBird(int xP, int yP, int r, int xV, int yV){
		super(xP, yP, r, xV, yV);
	}

	@Override
	void update() {
		velocity.setXY( 0, (int) Math.ceil((destinationY - this.y) * this.followDistanceCoefficient));
		move();
	}
	
	void setDestination(int xPos, int yPos) {
		//destinationX = xPos;// mute to make the bird in an fixed X position //no
		destinationY = yPos;
	}
	

	@Override
	public String getImageName() {
		return "migratingbird";
	}
}
