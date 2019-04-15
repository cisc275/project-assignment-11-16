
public class BreedingBird extends Moveable{

	int gravity;
	int xScreenCenter;
	int yScreenCenter;
	
	BreedingBird(int xP, int yP, int r, int xV, int yV){
		x = xP;
		y = yP;
		radius = r;
		xVelocity = xV;
		yVelocity = yV;
		gravity = 3;
		xScreenCenter = 130;
		yScreenCenter = 130;
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
	
	void mouseDownUpdate(int xPos, int yPos) {
		yVelocity = (yPos - this.y)/this.gravity;
		xVelocity = (xPos - this.x)/this.gravity;
	}
	
	void mouseUpUpdate() {
		yVelocity = (yScreenCenter - this.y)/this.gravity;
		xVelocity = (xScreenCenter - this.x)/this.gravity;
	}

}
