
public class Mouse extends Moveable{

	int mouseState;
	boolean updown = false;
	boolean leftright = false;
	int distance;
	int distanceCount;
	int moveVelocity = 15;
	
	
	
	public Mouse(int xP, int yP, int dist) {
		super(xP, yP, 50, 0, 0);
		mouseState = 0;
		distance = dist;
		distanceCount = dist;	
	}
	
	/**
	 * 
	 * @param xP
	 * @param yP
	 * @param dist distance of travel for up/down left/right movement
	 * @param ms mouseState: 0 for default, 1 for rightclick, 2 for leftclick, 3 for righthold
	 */
	public Mouse(int xP, int yP, int dist, int ms) {
		super(xP, yP, 50, 0, 0);
		mouseState = ms;
		distance = dist;
		distanceCount = dist;
	}
	
	public void enableUpDown() {
		updown = true;
		leftright = false;
		
	}
	public void enableLeftRight() {
		leftright = true;
		updown = false;
	}
	public void resetMouse() {
		leftright = false;
		updown = false;
	}
	
	@Override
	void update() {
		if(updown||leftright) {
			if(distanceCount > 0){
				distanceCount -= Math.abs(moveVelocity);
			}else if(distanceCount <= 0) {
				distanceCount = distance;
				moveVelocity = -moveVelocity;
			}
		}
		if(updown) {
			this.setVelocity(0,moveVelocity);
		}else if(leftright) {
			this.setVelocity(moveVelocity, 0);
		}else {
			this.setVelocity(0,0);
		}
		move();
	}

	@Override
	public String getImageName() {
		switch(mouseState) {
		case 1:
			return "mouserightclick";
		case 2:
			return "mouseleftclick";
		case 3:
			return "mouserighthold";
		default:
			return "mousedefault";
		}
		
	}

}
