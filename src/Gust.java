/**
 * If bird hits gust, should speed up migration
 * @author Wenki
 *
 */
public class Gust extends FlyingObject{

	Gust(int xP, int yP) {
		super(xP, yP, 30, -10, 0);
	}
	
	Gust(int xP, int yP, int v) {
		super(xP, yP, 30, v, 0);
	}
	
	Gust(int xP, int yP, int r, int xV, int yV){
		super(xP, yP, r, xV, yV);
	}

	@Override
	void update() {
		// TODO Auto-generated method stub
		move();
	}
	
	public String getImageName() {
		return "gust";
	}

	@Override
	void updateBirdPower(MigratingBird b) {
		b.powerUp();
		
	}



}
