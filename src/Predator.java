
public class Predator extends Moveable{

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

	@Override
	void move() {
		// TODO Auto-generated method stub
		
	}

}
