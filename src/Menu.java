import java.util.ArrayList;
import java.util.Collection;


public class Menu extends Model {
	
	Menu(int w, int h) {

		frameHeight = w;
		frameWidth = h;
		score = 0;
	}

	@Override
	void update() {
		
	}

	@Override
	Collection<Moveable> getMoveables() {
		return new ArrayList<Moveable>();
	}

	@Override
	void mousePressed(int mouseX, int mouseY, int actualX, int actualY, boolean leftClick, boolean rightClick) {
		// TODO Auto-generated method stub
		
	}

	@Override
	void mouseReleased() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void mouseDragged(int mouseX, int mouseY, int actualX, int actualY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	void mouseMoved(int mouseX, int mouseY) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean endGame() {
		return false;
	}

	@Override
	int[] getHUDargs() {
		int[] toret = {};
		return toret;
	}
	
	@Override
	public Model nextModel(int frameWidth, int frameHeight, boolean isMigrating) {
			return new EatingModel(frameWidth, frameHeight);
	}


}
