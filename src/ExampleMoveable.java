
/**
 * 
 * @author Prescott
 *
 */
public class ExampleMoveable extends Moveable {
	public ExampleMoveable(int x, int y, int radius, int dx, int dy) {
		super(x, y, radius, dx, dy);
	}

	@Override
	void update() {
		move();
	}
	
	public String getImageName() {
		// TODO Auto-generated method stub
		return null;
	}
}
