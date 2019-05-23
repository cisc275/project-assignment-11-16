import java.util.ArrayList;
import java.util.Collection;

/**
 * a tutorial for EatingModel
 * @author Prescott
 *
 */
public class EatingTutorial extends EatingModel {
	PointerArea pointer;
	Food tutTarget;
	
	EatingTutorial(int w, int h, boolean mig) {
		super(w, h, mig);
		food = new ArrayList<Food>();
		tutTarget = new Earthworm(bird.getX()+w/3, bird.getY()-h/8);
		food.add(tutTarget);
		pointer = new PointerArea(0,0,20);
		maxFood = 1;
	}
	
	@Override
	public void update() {
		if (pointer != null) {
			pointer.moveTo(tutTarget);
			pointer.update();
		}
		super.update();
	}
	
	boolean endGame() {
		return food.isEmpty() || food.get(0) != tutTarget;
	}
	
	@Override
	Collection<Moveable> getMoveables() {
		Collection<Moveable> m = super.getMoveables();
		if (pointer != null)
			m.add(pointer);
		return m;
	}
}
