/**
 * 
 * @author Prescott
 *
 */
public class MigratingBird extends Moveable {

	final static double followDistanceCoefficientX = .05;
	final static double followDistanceCoefficientY = .4;
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
		velocity.setXY( (int) Math.ceil((destinationX - this.x) * followDistanceCoefficientX), (int) Math.ceil((destinationY - this.y) * followDistanceCoefficientY)  );
		move();
	}
	/**
	 * change the destination of the bird.
	 * 
`	 */
	void setDestination(int xPos, int yPos) {
		destinationX = xPos;//Mute the destination change in X location so that the bird fly vertically
		destinationY = yPos;
	}
	

 /*
   //testing
	public boolean collidesWith(Enemy e){
		boolean eh = super.collidesWith(e);
		if(eh) {
			System.out.println(this.radius);
			this.radius += 100;
		}
		return eh;
	}
	*/

	@Override
	public String getImageName() {
		return "migratingbird";
	}
}
