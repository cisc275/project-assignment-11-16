import java.util.ArrayList;
import java.util.Collection;

public class EmptyModel extends Model {

	@Override
	void update() {
		// TODO Auto-generated method stub

	}

	@Override
	boolean endGame() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	Collection<Moveable> getMoveables() {
		// TODO Auto-generated method stub
		return new ArrayList<Moveable>();
	}

}
