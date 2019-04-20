
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
		super(xP, yP, r, xV, yV);
	}

	@Override
	void update() {
		velocity.setXY((int) Math.ceil((destinationX - this.x) * this.followDistanceCoefficient), (int) Math.ceil((destinationY - this.y) * this.followDistanceCoefficient));
		move();
	}
	
	void setDestination(int xPos, int yPos) {
		//destinationX = xPos; mute to make the bird in an fixed X position
		destinationY = yPos;
	}
	

	@Override
	public String getImageName() {
		return "migratingbird";
	}
}
