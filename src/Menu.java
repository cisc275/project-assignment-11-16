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
	boolean endGame() {
		return false;
	}

	@Override
	Collection<Moveable> getMoveables() {
		return new ArrayList<Moveable>();
	}
}
