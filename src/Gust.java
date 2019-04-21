
public class Gust extends Moveable{

	Gust(int xP, int yP) {
		super(xP, yP, 30, 5, 0);
	}
	
	Gust(int xP, int yP, int r, int xV, int yV){
		super(xP, yP, r, xV, yV);
	}

	@Override
	void update() {
		// TODO Auto-generated method stub
		x -= velocity.getX();
		y -= velocity.getY();
	}
	
	public String getImageName() {
		return "gust";
	}
	


}
