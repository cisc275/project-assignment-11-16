
public abstract class Enemy extends Moveable {

	Enemy(int xP, int yP, int r, int xV, int yV) {
		super(xP, yP, r, xV, yV);
	}
	
	/*Enemy(int xP, int yP){
		x = xP;
		y = yP;
		radius = 20;
		xVelocity = -5;
		yVelocity = 0;
	}*/
	
	void update() {
		x -= velocity.getX();
		y -= velocity.getY();
	}
	
}
