
public abstract class Enemy extends FlyingObject {

	Enemy(int xP, int yP, int r, int xV, int yV) {
		super(xP, yP, r, xV, yV);
	}
	
	
	void update() {
		move();
	}
	
}
