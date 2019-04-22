
public class WalkingBird extends Moveable {
	public final int BASE_SPEED = 5;
	int destinationX;
	int destinationY;
	int speed;
	
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

	public void update() {
		if (distanceTo(destinationX, destinationY) <= speed) {
			velocity.setXY(destinationX - x, destinationY - y);
		} else {
			velocity.setPolar(speed, angleTo(destinationX, destinationY));
			//double theta = angleTo(destinationX, destinationY);
			//xVelocity = (int) Math.round(Math.cos(theta) * speed);
			//yVelocity = (int) Math.round(Math.sin(theta) * speed);
		}
		move();
	}
	
	public void setDestination(int tox, int toy) {
		destinationX = tox;
		destinationY = toy;
	}
	
	public int getDestinationX() {
		return this.destinationX;
	}
	
	public int getDestinationY() {
		return this.destinationY;
	}
	
	public String getImageName() {
		return velocity.getR() > 0 ? "walkingbird" : "standingbird";
	}
}
