
public class EatingBird extends Moveable {
	public final int BASE_SPEED = 5;
	public final int BASE_RADIUS = 20;
	
	int destinationX;
	int destinationY;
	int speed;
	
	EatingBird(int xP, int yP){
		super(xP, yP, 70, 0, 0);
		destinationX = xP;
		destinationY = yP;
		speed = BASE_SPEED;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		if (x != destinationX || y != destinationY) {
			if (distanceTo(destinationX, destinationY) <= speed) {
				x = destinationX;
				y = destinationY;
			} else {
				double theta = angleTo(destinationX, destinationY);
				xVelocity = (int) Math.round(Math.cos(theta) * speed);
				yVelocity = (int) Math.round(Math.sin(theta) * speed);
				move();
			}
		}
	}
	
	public void setDestination(int tox, int toy) {
		destinationX = tox;
		destinationY = toy;
	}
	
	
}
