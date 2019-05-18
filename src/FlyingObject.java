
public abstract class FlyingObject extends Moveable{
	
	int xVelocityStorage;
	
	FlyingObject(int xP, int yP, int r, int xV, int yV) {
		super(xP, yP, r, xV, yV);
		xVelocityStorage = xV;
	}
	void scaleVelocity(double scale) {
		velocity.setXY((int)(xVelocityStorage*scale), velocity.getY());
	}
	void resetVelocity() {
		velocity.setXY(xVelocityStorage,velocity.getY());
	}
	
	abstract void updateBirdPower(MigratingBird b);


}
