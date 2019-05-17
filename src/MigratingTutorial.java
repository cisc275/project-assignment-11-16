import java.util.*;

/**
 * Prompts player to collide with a gust and a hawk to show what happens
 * @author Anna
 *
 */
public class MigratingTutorial extends MigratingModel {

	PointerArea pointer;
	int stage;
	List<Moveable> tutorialObjects = new ArrayList<Moveable>(); //not moving
	
	

	
	MigratingTutorial(int w, int h, boolean isMigrate) {
		super(w, h, isMigrate);
		gusts.add(new Gust(frameWidth-10,150));
		pointer = new PointerArea(500, 150, 50);
		tutorialObjects.add(pointer);
		backgroundObjects.add(new Cloud(frameWidth-100, 100));
		backgroundObjects.add(new Cloud(600, 600));
		backgroundObjects.add(new Cloud(300, 300)); 
		stage = 0;
		maxEnemies = 0;
		maxGusts = 0;

	}
	
	@Override
	public Collection<Moveable> getMoveables() {
		Collection<Moveable> r = super.getMoveables();
		r.addAll(tutorialObjects);
		return r;
	}
	
	@Override
	public boolean exitFrame() {
		return true;
	}
	
	
	double distanceTo(int x, int y, int otherx, int othery) {
		return Math.sqrt(Math.pow(x - otherx, 2) + Math.pow(y - othery, 2));
	}
	


}
