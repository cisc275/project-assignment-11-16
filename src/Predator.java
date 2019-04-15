
public class Predator extends Moveable{
	
	static final int gravity = 100;
	int xB;
	int yB;

	Predator(int xP, int yP, int r, int xV, int yV){
		x = xP;
		y = yP;
		radius = r;
		xVelocity = xV;
		yVelocity = yV;
	}

	@Override
	void update() {
		// TODO Auto-generated method stub
		this.x += this.xVelocity;
		this.y += this.yVelocity;
	}
	
	void updateBirdLoc(int xBird, int yBird) {
		yVelocity = (yBird - this.y)/Predator.gravity;
		xVelocity = (xBird - this.x)/Predator.gravity;
	}

	@Override
	void move() {
		// TODO Auto-generated method stub
		
	}

}
