
public class BreedingBird extends Moveable{

	public final int gravity = 3;
	public final int xScreenCenter = 130;
	public final int yScreenCenter = 130;
	
	BreedingBird(int xP, int yP, int r, int xV, int yV){
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
	
	void mouseDownUpdate(int xPos, int yPos) {
		yVelocity = (yPos - this.y)/this.gravity;
		xVelocity = (xPos - this.x)/this.gravity;
	}
	
	void mouseUpUpdate() {
		yVelocity = (yScreenCenter - this.y)/this.gravity;
		xVelocity = (xScreenCenter - this.x)/this.gravity;
	}

}
