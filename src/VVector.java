
public class VVector {
	private double x;
	private double y;
	private double r;
	private double theta;
	@SuppressWarnings("unused")
	private boolean usedPolar;	
	
	public VVector(int x, int y) {
		setXY(x, y);
	}
	
	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
		echoRectToPolar();
	}
	
	public void setPolar(double r, double theta) {
		this.r = r;
		this.theta = theta;
		echoPolarToRect();
	}
	
	/**
	 * Reduces to 0 while still maintaining directions.
	 */
	public void zero() {
		this.r = 0;
		echoPolarToRect();
	}
	
	private void echoRectToPolar() {
		usedPolar = false;
		r =  Math.sqrt(x*x + y*y);
		if (r > 0)
			theta = Math.atan2(y, x);
	}
	
	private void echoPolarToRect() {
		usedPolar = true;
		x = r * Math.cos(theta);
		y = r * Math.sin(theta);
	}
	
	public int getX() {
		return pround(x);
	}
	
	public int getY() {
		return pround(y);
	}
	
	public double getR() {
		return r;
	}
	
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
}
