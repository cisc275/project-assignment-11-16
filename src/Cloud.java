
public class Cloud extends Moveable {
	String nameArr

	Cloud(int xP, int yP) {
		super(xP, yP, 30, -8, 0);
	}
	Cloud(int xP, int yP, int velocity) {
		super(xP, yP, 30, velocity, 0);
	}
	Cloud(int xP, int yP, int r, int xV, int yV) {
		super(xP, yP, r, xV, yV);
	}

	
	@Override
	void update() {
		move();
	}

	@Override
	public String getImageName() {
		return "cloud";
	}

}
