
public class Enemy extends Moveable {

	Enemy(int xP, int yP, int r, int xV, int yV){
		x = xP;
		y = yP;
		radius = r;
		xVelocity = xV;
		yVelocity = yV;
	}
	Enemy(int xP, int yP ){
		x = xP;
		y = yP;
		radius = 20;
		xVelocity = -5;
		yVelocity = 0;
	}

	@Override
	void update() {
		move();
	}

}
