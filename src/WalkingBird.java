
public class WalkingBird extends Moveable {
	public final int BASE_SPEED = 5;
	int destinationX;
	int destinationY;
	int speed;
	
	WalkingBird(int xP, int yP){
		super(xP, yP, 70, 0, 0);
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
		move();
	}
	
	public void move() {
		if (x != destinationX || y != destinationY) {
			if (distanceTo(destinationX, destinationY) <= speed) {
				x = destinationX;
				y = destinationY;
			} else {
				double theta = angleTo(destinationX, destinationY);
				xVelocity = (int) Math.round(Math.cos(theta) * speed);
				yVelocity = (int) Math.round(Math.sin(theta) * speed);
				super.move();
			}
		}
	}
	
	public void setDestination(int tox, int toy) {
		destinationX = tox;
		destinationY = toy;
	}
}
