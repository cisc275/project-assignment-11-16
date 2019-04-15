
public class MigratingBird extends Moveable{

	int followDistanceCoefficient;
	
	MigratingBird(int xP, int yP){
		x = xP;
		y = yP;
		followDistanceCoefficient = 2;
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
		if (this.xVelocity > 0) {
			xVelocity -= 1;
		} else if (this.xVelocity < 0) {
			xVelocity += 1;
		}

		if (this.yVelocity > 0) {
			yVelocity -= 1;
		} else if (this.yVelocity < 0) {
			yVelocity += 1;
		}
	}
	
	void mouseUpdate(int xPos, int yPos) {
		yVelocity = (yPos - this.y)/this.followDistanceCoefficient;
		xVelocity = (xPos - this.x)/this.followDistanceCoefficient;
	}

	@Override
	void move() {
		// TODO Auto-generated method stub
	}

}
