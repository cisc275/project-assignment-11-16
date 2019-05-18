import java.util.*;

/**
 * Prompts player to collide with a gust and a hawk to show what happens
 * @author Anna
 *
 */
public class MigratingTutorial extends MigratingModel {

	
	int stage;
	int DELAY_TIME = 50;
	int delay = DELAY_TIME;
	List<Moveable> tutorialObjects = new ArrayList<Moveable>(); //not moving
	Gust gust;
	PointerArea pointer;
	Mouse mouse ;
	

	
	MigratingTutorial(int w, int h, boolean isMigrate) {
		super(w, h, isMigrate);
		//gusts.add(new Gust(frameWidth-10,150));
		//PointerArea pointer = new PointerArea(500, 150, 50);
		//tutorialObjects.add(pointer);
		
		gust = new Gust(frameWidth-10,150);
		//pointer = new PointerArea(h, h, h);
		//backgroundObjects.add(new Cloud(frameWidth-100, 100));
		//backgroundObjects.add(new Cloud(600, 600));
		//backgroundObjects.add(new Cloud(300, 300)); 
		stage = 0;
		//maxEnemies = 0;
		//maxGusts = 0;
		mouse = new Mouse(frameWidth/2, frameHeight/2, 200, 0);
		mouse.enableUpDown();
		tutorialObjects.add(mouse);
	}
	
	@Override
	public Collection<Moveable> getMoveables() {
		Collection<Moveable> r = super.getMoveables();
		r.addAll(tutorialObjects);
		return r;
	}
	
	@Override
	public void update() {
		
		if(delay > 0){
			delay--;
		}
		
		/*
		 * if stage 0, delay 0 -> stage 1, remove mouse, maxGust = smth, restart delay
		 * if 1 and bird collide with gust -> stage 2, maxGust = 0, restart delay
		 * 
		 * have predators in line??
		 */
		
		if(stage == 0 && delay == 0) {
			stage++;
			tutorialObjects.remove(mouse);
			//maxGusts = 3;
		}else if(stage == 1  && bird.getPowerUp()) {
			stage++;
			//maxGusts = 0;
			//maxEnemies = 9;
		}else if(stage == 2 && bird.getPowerUp()) {
			stage++;
			delay = DELAY_TIME;
		}
		super.update();
		//System.out.println(stage);
	}
	
	@Override
	public void updateMoveableLists() {
		super.updateMoveableLists();
		for (Moveable m : tutorialObjects) {
			m.update();
		}
	}
	
	//note to change this later
	@Override
	public boolean endSequence() {
		return super.endSequence();
	}
	@Override
	public boolean endGame() {
		return false;
	}
	
	
	double distanceTo(int x, int y, int otherx, int othery) {
		return Math.sqrt(Math.pow(x - otherx, 2) + Math.pow(y - othery, 2));
	}
	


}
