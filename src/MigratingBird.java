
public class MigratingBird extends Moveable{

	MigratingBird(int xP, int yP){
		x = xP;
		y = yP;
		radius = 50;
		xVelocity = 0;
		yVelocity = 0;
	}
	
	MigratingBird(int xP, int yP, int r, int xV, int yV){
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
	
	void mouseUpdate(int xPos, int yPos) {
		yVelocity = yPos - this.y;
		// xVelocity = xPos - this.x;
	}

	@Override
	void move() {
		// TODO Auto-generated method stub
	}

}
