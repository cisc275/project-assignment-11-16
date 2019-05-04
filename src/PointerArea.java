/**
 * click here
 * @author Prescott
 *
 */
public class PointerArea extends Moveable {
	public PointerArea(int xP, int yP, int r) {
		super(xP, yP, r, 0, 0);
	}
	
	public void moveTo(int tox, int toy) {
		x = tox;
		y = toy;
	}
	
	public void moveTo(Moveable m) {
		if (m == null) {
			
		} else {
			moveTo(m.getX(), m.getY());
		}
	}
	
	@Override
	void update() {
		
	}
	
	public String getImageName() {
		return "pointerarea";
	}
}
