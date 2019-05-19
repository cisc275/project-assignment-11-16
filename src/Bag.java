/**
 * Alternating velocity, so bag will float up and down
 * @author Anna
 *
 */
public class Bag extends Enemy{
	static int RADIUS = 30;

	int velocityDuration = 30;
	int velocityTimer = velocityDuration;
	int accelDuration = (int)velocityDuration/10;

	Bag(int xP, int yP) {
		super(xP, yP, RADIUS, -8, 1);
	}
	Bag(int xP, int yP, int velocity) {
		super(xP, yP, RADIUS, velocity, 1);
	}
	Bag(int xP, int yP, int r, int xV, int yV) {
		super(xP, yP, r, xV, yV);
	}

	public String getImageName() {
		return "bag";
	}
	
	@Override
	public void update() {
		if(velocityTimer == 0) {
			velocity.setXY(velocity.getX(), -velocity.getY());
			velocityTimer = velocityDuration;	
		} else if(velocityTimer <= accelDuration || velocityTimer > (velocityDuration-accelDuration)) {
			velocity.setXY(velocity.getX(), velocity.getY()+1);
			velocityTimer--;
		}
		else {
			velocityTimer--;
		}
		move();
	}

	
	
}
