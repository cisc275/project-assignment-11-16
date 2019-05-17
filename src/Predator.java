
public abstract class Predator extends Moveable {
	
	//static final int gravity = 100;
	//int xB;
	//int yB;
	int speed;
	boolean collidedWithNest = false;

	Predator(int xP, int yP, int r, int xV, int yV) {
		super(xP, yP, r, xV, yV);
	}
	
	Predator(int xP, int yP, int r, int xV, int yV, int s) {
		super(xP, yP, r, xV, yV);
		speed = s;
	}
	
	void setSpeed(int s) {
		speed = s;
	}
	
	void setCollidedWithNest(boolean b) {
		collidedWithNest = b;
	}
	boolean getCollidedWithNest() {
		return collidedWithNest;
	}

	@Override
	void update() {
		move();
	}
	
	//turns off collision after collides w/ nest once 
	@Override
	boolean collidesWith(Moveable other){
		if(collidedWithNest) {
			return false;
		}else {
			return super.collidesWith(other);
		}
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
