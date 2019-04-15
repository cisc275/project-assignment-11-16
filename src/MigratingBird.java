
public class MigratingBird extends Moveable{

	final double followDistanceCoefficient = .4;
	int destinationX;
	int destinationY;
	
	MigratingBird(int xP, int yP){
		super(xP, yP, 50, 0, 0);
		destinationX = xP;
		destinationY = yP;
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
		xVelocity = (int) Math.ceil((destinationX - this.x) * this.followDistanceCoefficient);
		yVelocity = (int) Math.ceil((destinationY - this.y) * this.followDistanceCoefficient);
		move();
	}
	
	void setDestination(int xPos, int yPos) {
		destinationX = xPos;
		destinationY = yPos;
	}

}
