
public class MapBird extends WalkingBird{
	public final int BASE_RADIUS = 5;
	
	MapBird(int xP, int yP) {
		super(xP, yP, 5, 0, 0);
	}
	MapBird(int xP, int yP, int r, int xV, int yV){
		super(xP, yP, r, xV, yV);
	}
	
	@Override
	public String getImageName() {
		return "MapBird";
	}
	void move() {
		x += velocity.getX();
		y += velocity.getY();
	}
	public void update() {
		super.update();
	}
}
