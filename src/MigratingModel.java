import java.util.*;


class MigratingModel extends Model{

	MigratingBird bird;
	List<Enemy> enemies;
	List<Gust> gusts;
	List<Cloud> backgroundObjects; 
	protected int maxEnemies = 3;
	protected int maxGusts = 2;
	
	protected double velocityScale = 1;
	protected static double POWERUP_SCALE = 2.5; 
	protected static double POWERDOWN_SCALE = .5;
	protected static int POWER_DURATION = 40;
	protected int powerTimer = 0;

	protected int distance = 0; //how far bird has travelled
	protected int maxDistance;  //how much it needs to travel varies on migrate or not. 
	protected boolean isMigrating;
	protected static int MIGRATE_DISTANCE = 10000;
	protected static int STAY_DISTANCE = 2000;
	
	protected int avoidOverlap = 3;
	protected int birdVelocity = 10; 
	
	protected static int BIRD_STARTING_X = 300;
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
		enemies = new ArrayList<Enemy>();
		gusts = new ArrayList<Gust>();
		backgroundObjects = new ArrayList<Cloud>();
		isMigrating = isMigrate;
		if(isMigrate) {
			maxDistance =  MIGRATE_DISTANCE;
		} else {
			maxDistance = STAY_DISTANCE;
		}
		BIRD_STARTING_Y = frameHeight/2;
	}
	
	//for testing ease
	MigratingModel(int w, int h, MigratingBird b, List<Enemy> e, List<Gust> g, boolean isMigrate){
		frameHeight = w;
		frameWidth = h;
		bird = b;
		enemies = e;
		gusts = g;
		backgroundObjects = new ArrayList<Cloud>();
		isMigrating = isMigrate;
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
		}else if(exitFrame()) {
			bird.setDestination(frameWidth+BIRD_STARTING_X, bird.getDestinationY());
		}
		
		bird.update();
		updateMoveableLists();
		updatePower();
		distance += birdVelocity*velocityScale; 
	}
	
	
	void updateMoveableLists() {
		while(enemies.size() < maxEnemies) {
			generateEnemy();
		}
		while(gusts.size() < maxGusts) {
			generateGust();
		}
		for (Moveable o : enemies) {
			o.update();
		}
		for (Moveable o : gusts) {
			o.update();
		}
		for(Moveable o : backgroundObjects) {
			o.update();
		}
		updateEnemyCollision();
		updateGustCollision();
		updateBackgroundObjects();
		
	}
	
	/**
	 * Speed or slow everything when power is turned on
	 * Decrements powertimer
	 * @author Anna
	 */
	void updatePower(){
		if(powerTimer == POWER_DURATION) {
			scaleVelocities(velocityScale);
			powerTimer--;
		}else if(powerTimer > 0) {
			powerTimer--;
		}else if(powerTimer == 0) {
			bird.powerReset();
			velocityScale = 1;
			scaleVelocities(velocityScale);
		}
	}
	
	/**
	 * End when bird exits frame
	 */
	boolean endGame() {
		//return (distance >= maxDistance);
		return bird.exitsFrame(frameWidth, frameHeight);
	}
	
	/**
	 * check when to move bird out of frame/initiate end sequence
	 */
	boolean exitFrame() {
		return (distance >= maxDistance);
	}
	
	Collection<Moveable> getMoveables(){
		Collection<Moveable> m = new ArrayList<Moveable>();
		m.addAll(enemies);
		m.add(bird);
		m.addAll(gusts);
		m.addAll(backgroundObjects); 
		return m;
	}
	
	/**
	 * get all the moveable object except bird
	 * use for avoid overlapping
	 * @author Wenki
	 */
	Collection<Moveable> getOtherMoveables(){
		Collection<Moveable> m = new ArrayList<Moveable>();
		m.addAll(enemies);
		m.addAll(gusts);
		m.addAll(backgroundObjects);
		return m;
	}


	/**
	 * Use random number as switch to generate sub enemies - Hawk or Plastic Bags.
	 * If switch is on (equals to one), generate Hawk;
	 * if switch if off (equals to zero), generate Plastic bag.
	 * Check the y location with other enemies before generate to avoid overlap with other object.
	 * @author Wenki
	 */
	void generateEnemy() {
		Random r =  new Random();
        int switchE =  r.nextInt(2);
        int yloc = (int) (Math.random()*frameHeight); 
        Enemy newEnemy = null;
  
        for(Moveable m: getOtherMoveables()) { //check the exist enemies
        	if(m.getX() == frameWidth && (yloc == m.getY() ||(yloc < m.getY() && yloc >= m.getY()-m.getR())
        									||(yloc>m.getY() &&yloc <=m.getY()+m.getR())))
        	{//if both at start point, and Y-location is overlapping with the area existing enemy
        		yloc += avoidOverlap*m.getRadius(); //change the y-location 
        	}
        }
        switch(switchE){
	        case 0:
	        	newEnemy = new Bag(frameWidth,yloc);
	        	removeInBagRange(yloc);
	        	break;
	        case 1:
	        	newEnemy = new Hawk(frameWidth, yloc);
	        	break;
        }
		if(bird.getPowerUp() || bird.getPowerDown()) {
			newEnemy.scaleVelocity(velocityScale);
		}
		enemies.add(newEnemy);
	}
	
	/**
	 * generate gust
	 */
	void generateGust() {
		Gust g = new Gust(frameWidth, (int) (Math.random()*frameHeight));
		if(bird.getPowerUp() || bird.getPowerDown()) {
			g.scaleVelocity(velocityScale);
		}
		gusts.add(g);
	}
	
	/**
	 * Since there the bag is bouncing, if there is object in the bounce range during the generating
	 * remove the object.
	 * @author Wenki
	 * @param yloc
	 */
	void removeInBagRange(int yloc) {
		Iterator <Moveable> movableIterator = getOtherMoveables().iterator();
		while(movableIterator.hasNext()) {
			Moveable m = movableIterator.next();
    		if(m.getX() == frameWidth && (m.getY()>= yloc || m.getY()<= yloc+120)){
    			movableIterator.remove();
    		}
    		}
	}

	/**
	 * If enemy collides with bird or exits frame, remove enemy, deduct hp
	 * @author Anna
	 */
	void updateEnemyCollision() {
		Iterator <Enemy> enemiesIterator = enemies.iterator();
		while(enemiesIterator.hasNext()) {
			Enemy e = enemiesIterator.next();
			if (bird.collidesWith(e)) {
				enemiesIterator.remove();
				if(bird.getPowerDown() == false) {
					powerTimer = POWER_DURATION;
					velocityScale = POWERDOWN_SCALE;
					bird.powerDown();
				}
			} else if (e.exitsFrame(frameWidth, frameHeight)) {
				enemiesIterator.remove();
			}
		}
	}
	
	
	/**
	 * If gusts collide with bird or exits frame, removes gust, and sets up powerUp logic
	 * @author Anna
	 */
	void updateGustCollision() {
		Iterator <Gust> gustIterator = gusts.iterator();
		while(gustIterator.hasNext()) {
			Gust g = gustIterator.next();
			if(bird.collidesWith(g)) {
				gustIterator.remove();
				powerTimer = POWER_DURATION; //outside to enable continuous powerup
				if(bird.getPowerUp() == false) {
					velocityScale = POWERUP_SCALE;
					bird.powerUp();
				}

			}else if(g.exitsFrame(frameWidth, frameHeight)) {
				gustIterator.remove();
			}
		}
	}

	
	/**
	 * If bgObjects exit frame, they loop back around. 
	 * For clouds and other things that don't affect bird
	 * @author Anna
	 */
	void updateBackgroundObjects() {
		Iterator <Cloud> bgIterator = backgroundObjects.iterator();
		while(bgIterator.hasNext()) {
			Moveable o = bgIterator.next();
			if(o.exitsFrame(frameWidth, frameHeight)) {
				o.setLocation(frameWidth+o.getR(), o.getY());
			}
		}
	}
	
	/**
	 * Speeds up or down all objects besides bird according to input
	 * @author Anna
	 */
	void scaleVelocities(double scale) {
		for(Gust gust : gusts) {
			gust.scaleVelocity(scale);
		}
		for(Enemy enemy : enemies) {
			enemy.scaleVelocity(scale);
		}
		for(Cloud cloud : backgroundObjects) {
			cloud.scaleVelocity(scale);
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