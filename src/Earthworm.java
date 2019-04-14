
public class Earthworm extends Food {
	
	public Earthworm(int inx, int iny) {
		super(inx, iny, 30, 1, 0);
		scoreValue = 20;
	}
	
	@Override
	void update() {
		move();
	}
}
