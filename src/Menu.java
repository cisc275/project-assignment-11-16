import java.util.ArrayList;
import java.util.Collection;

public abstract class Menu extends Model {
	Collection<MenuObject> menuObjects;
	
	public void click(int x, int y) {
		for (MenuObject oj : menuObjects) {
			oj.checkClick(x, y);
		}
	}
	
	public Collection<Moveable> getMoveables() {
		return new ArrayList<Moveable>();
	}
	
	@Override
	public Collection<MenuObject> getMenuObjects() {
		return menuObjects;
	}
	
	@Override
	void update() {
		
	}

	@Override
	boolean endGame() {
		return false;
	}
}
