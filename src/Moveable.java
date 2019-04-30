import java.lang.Math;

public abstract class Moveable {
	
	protected int x;
	protected int y;
	protected int radius;
	protected VVector velocity;

	int getX() {return x;}
	int getY() {return y;}
	int getR() {return radius;}
	int getRadius() {return radius;}
	public void setVelocity(int x, int y){velocity.setXY(x, y);}
	public int getXVelocity(){return velocity.getX();}
	public int getYVelocity(){return velocity.getY();}
	
	public double getFacing() {
		return velocity.getTheta();
	}
	
	public Moveable() {
		
	}
	
	public Moveable(int inx, int iny, int inr, int indx, int indy) {
		this.x = inx;
		this.y = iny;
		this.radius = inr;
		this.velocity = new VVector(indx, indy);
	}
	
	
	/**
	 * Updates the Moveable.
	 */
	abstract void update();
	
	public abstract String getImageName();
	
	void setLocation(int xP, int yP) {
		x = xP;
		y = yP;
	}
	
	/**
	 * Moves the object, generally according to its own velocity.
	 */
	void move() {
		x += velocity.getX();
		y += velocity.getY();
	}
	
	/*void move(double delta) {
		x += xVelocity*delta;
		y += yVelocity*delta;
	}*/

	double angleTo(int otherx, int othery) {
		return Math.atan2(othery - this.y, otherx - this.x);
	}
	
	double angleTo(Moveable other) {
		return angleTo(other.getX(), other.getY());
	}
	
	double distanceTo(int otherx, int othery) {
		return Math.sqrt(Math.pow(this.x - otherx, 2) + Math.pow(this.y - othery, 2));
	}
	
	double distanceTo(Moveable other) {
		return distanceTo(other.getX(), other.getY());
	}
	
	boolean collidesWith(Moveable other) {
		return distanceTo(other) <= (this.radius + other.getRadius());
	}
	
	/**
	 * 
	 * @param frameWidth
	 * @param frameHeight
	 * @return Whether this is entirely out of frame.
	 */
	boolean exitsFrame(int frameWidth, int frameHeight) {
		return x + radius < 0 || y + radius < 0 || x - radius > frameWidth || y - radius > frameHeight;
	}
	
}
