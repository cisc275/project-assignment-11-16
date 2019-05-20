import java.util.Collection;

public class EndMenu extends Model {
	boolean restart = false;
	
	EndMenu(int w, int h) {
		frameHeight = w;
		frameWidth = h;
		score = 0;
	}

	@Override
	void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	boolean endGame() {
		return restart;
	}

	@Override
	Collection<Moveable> getMoveables() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	int[] getHUDargs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	void mousePressed(int mouseX, int mouseY, int actualX, int actualY, boolean leftClick, boolean rightClick) {
		if (leftClick) {
			this.restart = true;
		}
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
	void buttonClicked(int answer) {
		// TODO Auto-generated method stub
		
	}
}
