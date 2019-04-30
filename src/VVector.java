/**
 * A vector that uses both polar and rectangular coordinates.
 * @author pedz
 *
 */
public class VVector {
	private double x;
	private double y;
	private double r;
	private double theta;
	@SuppressWarnings("unused")
	private boolean usedPolar;	
	
	/**
	 * Constructs a new VVector with coordinates (0, 0).
	 */
	public VVector() {
		setXY(0, 0);
	}
	
	/**
	 * Constructs a new VVector with the given rectangular coordinates.
	 * @param x
	 * @param y
	 */
	public VVector(int x, int y) {
		setXY(x, y);
	}
	
	/**
	 * Sets rectangular coordinates (and then applies it to polar).
	 * @param x The vector's new X coordinate.
	 * @param y The vector's new Y coordinate.
	 */
	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
		echoRectToPolar();
	}
	
	/**
	 * Sets polar coordinates (and then applies it to rectangular).
	 * @param r The vector's new R coordinate (speed or distance).
	 * @param theta The vector's new Theta coordinate (angle; 0=right, 1/2*pi=down, pi=left, 3/2*pi=up)
	 */
	public void setPolar(double r, double theta) {
		this.r = r;
		this.theta = theta;
		echoPolarToRect();
	}
	
	
	/**
	 * Reduces to X, Y, and R to 0 while still keeping the same theta.
	 */
	public void zero() {
		this.r = 0;
		echoPolarToRect();
	}
	
	/**
	 * Uses rectangular coordinates to update polar coordinates.
	 */
	private void echoRectToPolar() {
		usedPolar = false;
		r =  Math.sqrt(x*x + y*y);
		if (r > 0)
			theta = Math.atan2(y, x);
	}
	

	/**
	 * Uses polar coordinates to update rectangular coordinates.
	 */
	private void echoPolarToRect() {
		usedPolar = true;
		x = r * Math.cos(theta);
		y = r * Math.sin(theta);
	}
	
	/**
	 * 
	 * @return The vector's X coordinate.
	 */
	public int getX() {
		return pround(x);
	}
	
	/**
	 * 
	 * @return The vector's Y coordinate (note that Y is down in this project).
	 */
	public int getY() {
		return pround(y);
	}
	
	/**
	 * 
	 * @return The vector's R coordinate (speed or distance).
	 */
	public double getR() {
		return r;
	}
	
	/**
	 * 
	 * @return The vector's Theta coordinate (angle; 0=right, 1/2*pi=down, pi=left, 3/2*pi=up)
	 */
	public double getTheta() {
		return theta;
	}
	
	/**
	 * Short for "Probability Round". 
	 * For example: If you call this with 3.2 as input, there will be a 20% chance of returning 4 and an 80% chance of returning 3.
	 * @param num
	 * @return
	 */
	public static int pround(double num) {
		int iPart = (int) Math.floor(num);
		double fPart = num - iPart;
		if (fPart > Math.random())
			return iPart+1;
		else
			return iPart;
	}
	
	/**
	 * Get a random polar theta.
	 * @return A random value uniformly distributed between 0 and 2pi.
	 */
	public static double randomTheta() {
		return Math.random()*2*Math.PI;
	}
}
