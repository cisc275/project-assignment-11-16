
public class Food extends Moveable{

	//different for different subclasses of food, maybe make abstract later
	static int scoreValue;
	
	Food(int xP, int yP, int r, int xV, int yV){
		x = xP;
		y = yP;
		radius = r;
		xVelocity = xV;
		yVelocity = yV;
	}
	
	int getScoreValue() {
		return scoreValue;
	}

	@Override
	void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void move() {
		// TODO Auto-generated method stub
		
	}

}
