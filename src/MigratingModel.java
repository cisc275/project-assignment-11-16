import java.util.*;


class MigratingModel extends Model{

	MigratingBird bird;
	List<FlyingObject> flyingObjects;
	protected int MAX_OBJECTS = 8;
	protected static int GENERATE_TIME = 12; //delay so objects aren't made all at once
	protected int generateTimer = 0;

	protected int distance = 0; //how far bird has travelled
	protected int maxDistance;  //how much it needs to travel varies on migrate or not. 
	protected boolean isMigrating;
	protected static int MIGRATE_DISTANCE = 10000;
	protected static int STAY_DISTANCE = 6000;
	
	protected int avoidObjectOverlap = 3;
	protected int avoidBagOverlap = 120;
	protected int birdVelocity = 10; 
	
	protected static int BIRD_STARTING_X;
	protected static int BIRD_STARTING_Y;
	
	
	/**
	 * pass frame height/width from view to create models
	 * @param w
	 * @param h
	 */
	MigratingModel(int w, int h,boolean isMigrate){
		frameHeight = h;
		frameWidth = w;
		bird = new MigratingBird(0, 0); //bird is still
		flyingObjects = new ArrayList<FlyingObject>();
		isMigrating = isMigrate;
		if(isMigrate) {
			maxDistance =  MIGRATE_DISTANCE;
		} else {
			maxDistance = STAY_DISTANCE;
		}
		BIRD_STARTING_X = frameWidth/4;
		BIRD_STARTING_Y = frameHeight/2;
	}
	
	//for testing ease
	MigratingModel(int w, int h, MigratingBird b, List<Enemy> e, List<Gust> g, boolean isMigrate){
		frameHeight = w;
		frameWidth = h;
		bird = b;
		flyingObjects = new ArrayList<FlyingObject>();
		flyingObjects.addAll(e);
		flyingObjects.addAll(g);
		isMigrating = isMigrate;
		BIRD_STARTING_X = frameWidth/4;
		BIRD_STARTING_Y = frameHeight/2;
	}
		
	/**
	 * Object generation and position updates
	 * Bird transitions here at start and end game
	 * Adding to distance per tick
	 * @author Anna
	 */
	void update() {
		if(bird.getDestinationX() < BIRD_STARTING_X) {
			bird.setDestination(BIRD_STARTING_X, frameHeight/2);
		}else if(endSequence()) {
			bird.setDestination(frameWidth+BIRD_STARTING_X, bird.getDestinationY());
		}
		if(generateTimer > 0) {
			generateTimer--;
		}
		bird.update();
		updateMoveableLists();
		distance += birdVelocity*bird.getVelocityScale(); 
	}
	
	
	void updateMoveableLists() {
		while(flyingObjects.size() < MAX_OBJECTS && generateTimer <= 0) {
			generateObjects();
		}
		for(FlyingObject f: flyingObjects) {
			f.scaleVelocity(bird.getVelocityScale());
			f.update();
		}
		updateCollision();	
	}
	
	/**
	 * End when bird exits frame
	 */
	boolean endGame() {
		//return (distance >= maxDistance);
		return bird.exitsFrame(frameWidth, frameHeight);
	}
	
	/**
	 * check if time to initiate end sequence/ move bird out of frame
	 */
	boolean endSequence() {
		return (distance >= maxDistance);
	}
	
	Collection<Moveable> getMoveables(){
		Collection<Moveable> m = new ArrayList<Moveable>();
		m.addAll(flyingObjects);
		m.add(bird);
		return m;
	}	 


	/**
	 * Use random number as switch to generate FlyingObjects
	 * Probability now is 1/3: switch = 0 generate bag, switch = 1 generate Hawk, else generate gust;
	 * Check the y location with other enemies before generate to avoid overlap with other object.
	 * @author Wenki
	 */
	void generateObjects() {
		Random r =  new Random();
        int switchObj =  r.nextInt(3);//r.nextInt(2);
        int yloc = (int) (Math.random()*frameHeight); 
        FlyingObject newObject = null;
  
        for(FlyingObject f: flyingObjects) { //check the exist enemies
        	if(f.getX() == frameWidth && (yloc == f.getY() ||(yloc < f.getY() && yloc >= f.getY()-f.getR())
        									||(yloc>f.getY() && yloc <= f.getY() + f.getR())))
        	{//if both at start point, and Y-location is overlapping with the area existing enemy
        		yloc += avoidObjectOverlap*f.getRadius(); //change the y-location 
        	}
        }
        switch(switchObj){
	        case 0:
	        	newObject = new Bag(frameWidth,yloc);
	        	removeInBagRange(yloc);
	        	break;
	        case 1:
	        	newObject = new Hawk(frameWidth, yloc);
	        	break;
	        default:
	        	newObject = new Gust(frameWidth, yloc);
        }
		if(bird.getPowerUp() || bird.getPowerDown()) {
			 newObject.scaleVelocity(bird.getVelocityScale());
		}
		flyingObjects.add(newObject);
		generateTimer = GENERATE_TIME;
	}
	
	
	/**
	 * Since there the bag is bouncing, if there is object in the bounce range during the generating
	 * remove the object.
	 * @author Wenki
	 * @param yloc
	 */
	void removeInBagRange(int yloc) {
		Iterator <FlyingObject> fIterator = flyingObjects.iterator();
		while(fIterator.hasNext()) {
			FlyingObject m = fIterator.next();
    		if(m.getX() == frameWidth && (m.getY()>= yloc || m.getY()<= yloc+avoidBagOverlap)){
    			fIterator.remove();
    		}
    	}
	}
	
	//generalized
	void updateCollision() {
		Iterator <FlyingObject> mIterator = flyingObjects.iterator();
		while(mIterator.hasNext()) {
			FlyingObject m = mIterator.next();
			if (bird.collidesWith(m)) {
				m.updateBirdPower(bird); //call powerUp methods
				mIterator.remove();		
			} else if (m.exitsFrame(frameWidth, frameHeight)) {
				mIterator.remove();
			}
		}
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
		if(bird.getDestinationX() == BIRD_STARTING_X) {
			bird.setDestination(bird.getDestinationX(), mouseY);
		}
	}

	/**
	 * return the HuD argument list for the Hud to generate image.
	 */
	@Override
	int[] getHUDargs() {
		int[] toret = {
				isMigrating ? 1 : 0,
				distance,
				isMigrating ? MIGRATE_DISTANCE : STAY_DISTANCE,
		};
		return toret;
	}
	
	void buttonClicked(int answer) {
		
	}
}