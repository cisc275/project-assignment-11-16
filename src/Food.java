/**
 * Things that can be eaten during the Eating game.
 * @author pedz
 *
 */
public abstract class Food extends Moveable{
	
	int scoreValue;
	
	Food(int xP, int yP, int r, int xV, int yV){
		super(xP, yP, r, xV, yV);
	}
	
	int getScoreValue() {
		return scoreValue;
	}
	
	abstract void update(EatingBird birb);
	
	@Override
	void update() {
		move();
	}
}
