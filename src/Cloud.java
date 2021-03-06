import java.util.Random;

public class Cloud extends FlyingObject {
	private Random nameInt = new Random();
	private String name;
	
	Cloud(int xP, int yP) {
		super(xP, yP, 30, -8, 0);
		name = "cloud" + (nameInt.nextInt(3) + 1);
	}
	Cloud(int xP, int yP, int velocity) {
		super(xP, yP, 30, velocity, 0);
		name = "cloud" + (nameInt.nextInt(3) + 1);
	}
	Cloud(int xP, int yP, int r, int xV, int yV) {
		super(xP, yP, r, xV, yV);
		name = "cloud" + (nameInt.nextInt(3) + 1);
	}

	
	@Override
	void update() {
		move();
	}

	@Override
	public String getImageName() {
		return name;
	}
	@Override
	void updateBirdPower(MigratingBird b) {
		// TODO Auto-generated method stub
		
	}


}
