/**
 * A superclass for both EatingBird and Breeding bird, due to their shared movement.
 * @author pedz
 *
 */
public class WalkingBird extends Moveable {
	public final int BASE_SPEED = 5;
	int destinationX;
	int destinationY;
	double speed;
	
	WalkingBird(int xP, int yP){
		super(xP, yP, 60, 0, 0);
		destinationX = xP;
		destinationY = yP;
		speed = BASE_SPEED;
	}
	
	WalkingBird(int xP, int yP, int r, int xV, int yV){
		super(xP, yP, r, xV, yV);
		destinationX = xP;
		destinationY = yP;
		speed = BASE_SPEED;
	}
	
	WalkingBird(int xP, int yP, int r, int xV, int yV, double s){
		super(xP, yP, r, xV, yV);
		destinationX = xP;
		destinationY = yP;
		speed = s;
	}

	public void update() {
		faceDestination(speed);
		move();
	}
	
	public void setDestination(int tox, int toy) {
		destinationX = tox;
		destinationY = toy;
	}
	
	public void faceDestination(double speed) {
		if (distanceTo(destinationX, destinationY) <= speed) {
			velocity.setXY(destinationX - x, destinationY - y);
		} else {
			velocity.setPolar(speed, angleTo(destinationX, destinationY));
		}
	}
	
	public void faceDestination() {
		faceDestination(speed);
	}
	
	public String getImageName() {
		return velocity.getR() > 0 ? "walkingbird" : "standingbird";
	}
}
