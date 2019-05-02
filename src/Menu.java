import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;


public class Menu extends Model {
	
	ArrayList<Moveable> emptyList = new ArrayList<Moveable>();
	
	Menu(int w, int h) {

		frameHeight = w;
		frameWidth = h;
		score = 0;
	}

	@Override
	void update() {
		
	}

	@Override
	boolean endGame() {
		return false;
	}

	@Override
	Collection<Moveable> getMoveables() {
		return emptyList;
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


}
