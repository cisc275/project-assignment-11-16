import java.util.*;

/**
 * Prompts player to move, collide with a gust, and avoid hawks to show what happens
 * @author Anna
 *
 */
public class MigratingTutorial extends MigratingModel {

	private int stage = 0;
	private int DELAY_TIME = 50;
	private int delay = DELAY_TIME;
	private boolean hitEnemy = true;
	private List<Cloud> clouds = new ArrayList<Cloud>();
	private List<Moveable> tutorialObjects = new ArrayList<Moveable>(); //not moving
	PointerArea pointer;
	private Mouse mouse;

	private int tutorialGustVelocity = -12;
	private int tutorialEnemyVelocity = -12;
	
	private Arrow arrow1 = new Arrow(frameWidth*2/5, frameHeight*1/4);
	private Arrow arrow2 = new Arrow(frameWidth*2/5, frameHeight*2/4);
	private Arrow arrow3 = new Arrow(frameWidth*2/5, frameHeight*3/4);

	
	MigratingTutorial(int w, int h, boolean isMigrate) {
		super(w, h, isMigrate);
		MAX_OBJECTS = 0;
		clouds.add(new Cloud(frameWidth-100, 100));
		clouds.add(new Cloud(600, 600));
		clouds.add(new Cloud(300, 300)); 
		
		mouse = new Mouse(frameWidth/2, frameHeight/2, 200, 0);
		mouse.enableUpDown();
		tutorialObjects.add(mouse);
		tutorial = true;
	}
	
	private void generateGustLine() {
		flyingObjects.add(new Gust(frameWidth, frameHeight*1/4, tutorialGustVelocity));
		flyingObjects.add(new Gust(frameWidth, frameHeight*2/4, tutorialGustVelocity));
		flyingObjects.add(new Gust(frameWidth, frameHeight*3/4, tutorialGustVelocity));
		tutorialObjects.add(arrow1);
		tutorialObjects.add(arrow2);
		tutorialObjects.add(arrow3);
	}
	
	private void generateEnemyLine() {
		flyingObjects.add(new Hawk(frameWidth, frameHeight*0/4, tutorialEnemyVelocity));
		flyingObjects.add(new Hawk(frameWidth, frameHeight*2/4, tutorialEnemyVelocity));
		flyingObjects.add(new Hawk(frameWidth, frameHeight*3/4, tutorialEnemyVelocity));
		flyingObjects.add(new Hawk(frameWidth, frameHeight, tutorialEnemyVelocity));
		tutorialObjects.remove(arrow2);
		tutorialObjects.remove(arrow3);
	}
	
	@Override
	public Collection<Moveable> getMoveables() {
		Collection<Moveable> r = new ArrayList<>();
		r.addAll(clouds);
		r.addAll(tutorialObjects);
		r.addAll(super.getMoveables());
		return r;
	}
	
	
	/**
	 * handles transition of stages. 
	 * stage 0 is initial, 1 is powerup tutorial, 2 is avoid enemy tutorial, 3 is end sequence
	 */
	@Override
	public void update() {
		super.update();
		if(delay > 0){
			delay--;
		}
	
		if(stage == 0 && delay == 0) {
			stage=1;
			tutorialObjects.remove(mouse);
		}else if(stage == 1) {
			if(bird.getPowerUp()) {
				stage=2;
			}else if(flyingObjects.isEmpty()){
				generateGustLine();
			}
		}else if(stage == 2) {
			if(!hitEnemy && flyingObjects.isEmpty()) {
				stage=3;
				delay = DELAY_TIME;
			}else if(flyingObjects.isEmpty()){
					generateEnemyLine();
					hitEnemy = false;
			}
		}else {
			tutorialObjects.remove(arrow1);
		}
	}
	


	
	@Override
	public void updateMoveableLists() {
		super.updateMoveableLists();
		for (Moveable m : tutorialObjects) {
			m.update();
		}
		for(FlyingObject f: clouds) {
			if(f.exitsFrame(frameWidth, frameHeight)) {
				f.setLocation(frameWidth-f.getR(), f.getY());
			}else {
				f.scaleVelocity(bird.getVelocityScale());
				f.update();
			}
		}
	}
	
	@Override
	void updateCollision() {
		Iterator <FlyingObject> mIterator = flyingObjects.iterator();
		while(mIterator.hasNext()) {
			FlyingObject m = mIterator.next();
			if (bird.collidesWith(m)) {
				if(stage == 2) {
					hitEnemy = true;
				}
				m.updateBirdPower(bird); 
				mIterator.remove();		
			} else if (m.exitsFrame(frameWidth, frameHeight)) {
				mIterator.remove();
			}
		}
	}
	
	
	@Override
	public boolean endSequence() {
		return (stage == 3 && flyingObjects.isEmpty());
	}
	@Override
	public boolean endGame() {
		return super.endGame();
	}
	
	
	double distanceTo(int x, int y, int otherx, int othery) {
		return Math.sqrt(Math.pow(x - otherx, 2) + Math.pow(y - othery, 2));
	}
	


}
