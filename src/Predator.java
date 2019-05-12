
public abstract class Predator extends Moveable {
	
	//static final int gravity = 100;
	//int xB;
	//int yB;
	int speed;

	Predator(int xP, int yP, int r, int xV, int yV) {
		super(xP, yP, r, xV, yV);
	}
	
	Predator(int xP, int yP, int r, int xV, int yV, int s) {
		super(xP, yP, r, xV, yV);
		speed = s;
	}

	@Override
	void update() {
		move();
	}
	
	/**
	 * Sets velocity to be moving towards the provided location.
	 * @param birdX
	 * @param birdY
	 */
	void updateBirdLoc(int birdX, int birdY) {
		velocity.setPolar(speed, angleTo(birdX, birdY));
		//yVelocity = (yBird - this.y)/Predator.gravity;
		//xVelocity = (xBird - this.x)/Predator.gravity;
	}
	
}
