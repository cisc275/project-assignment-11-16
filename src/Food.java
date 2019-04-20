
public abstract class Food extends Moveable{
	
	int scoreValue;
	
	Food(int xP, int yP, int r, int xV, int yV){
		super(xP, yP, r, xV, yV);
	}
	
	int getScoreValue() {
		return scoreValue;
	}

	@Override
	void update() {
		move();
	}
}
