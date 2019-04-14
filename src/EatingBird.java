
public class EatingBird extends Moveable {
	public final int BASE_SPEED = 5;
	public final int BASE_RADIUS = 20;
	
	int destinationX;
	int destinationY;
	int speed;
	
	EatingBird(int xP, int yP){
		super(xP, yP, 10, 0, 0);
		destinationX = xP;
		destinationY = yP;
		speed = BASE_SPEED;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		if (x != destinationX || y != destinationY) {
			double theta = Math.atan2(destinationY - this.y, destinationX - this.x);
			xVelocity = (int) Math.round(Math.cos(theta) * speed);
			yVelocity = (int) Math.round(Math.sin(theta) * speed);
			move();
		}
	}
	
	public void setDestination(int tox, int toy) {
		destinationX = tox;
		destinationY = toy;
	}

}
