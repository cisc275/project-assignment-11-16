import java.util.*;


class MigratingModel extends Model{

	MigratingBird bird;
	List<Enemy> enemies;
	List<Gust> gusts;
	List<Moveable> backgroundObjects; 
	
	private static int enemyScore = -10; 
	private static int gustScore = 20;
	protected static int maxEnemies = 3;
	protected static int maxGusts = 2;
	protected static int powerDuration = 50;
	protected static int velocityChange = 10;
	
	protected boolean powerOn = false;
	protected int powerTimer = 0;
	

	protected int distance = 0; //how far bird has travelled
	protected int maxDistance;  //how much it needs to travel varies on migrate or not. 
	protected boolean isMigrating;
	protected int migrateDistance = 5000;
	protected int stayDistance = 2000;
	
	protected int avoidOverlap = 3;
	protected int birdVelocity = 10; 
	
	Hawk h = new Hawk(frameWidth, 400);
	/**
	 * pass frame height/width from view to create models
	 * @param w
	 * @param h
	 */
	MigratingModel(int w, int h,boolean isMigrate){
		frameHeight = h;
		frameWidth = w;
		bird = new MigratingBird(frameWidth/2, frameHeight/2); //bird is still
		enemies = new ArrayList<Enemy>();
		gusts = new ArrayList<Gust>();
		backgroundObjects = new ArrayList<Moveable>();
		isMigrating = isMigrate;
		if(isMigrate) {
			maxDistance =  migrateDistance;
		} else {
			maxDistance = stayDistance;
		}
	}
	
	//for testing ease
	MigratingModel(int w, int h, MigratingBird b, List<Enemy> e, List<Gust> g,boolean isMigrate){
		frameHeight = w;
		frameWidth = h;
		bird = b;
		enemies = e;
		gusts = g;
		isMigrating = isMigrate;
		backgroundObjects = new ArrayList<Moveable>();
	}
		
	/**
	 * Moveable generation here, moving moveables here
	 * TO DO: update score based on time completion?
	 */
	void update() {
		bird.update();
		while(enemies.size() < maxEnemies) {
			generateEnemy();
		}
		while(gusts.size() < maxGusts) {
			generateGust();
		}
		if( powerOn && powerTimer > 0) {
			powerTimer--;
		}else if (powerOn == true && powerTimer == 0){
			accelerateMoveables(velocityChange);
			powerOn = false;
		}
		
		updateMoveableLists();
		
		if(powerOn == false) {
			distance += birdVelocity;
		}else {
			distance += birdVelocity+velocityChange; 
		}
		System.out.println(this.endGame());
	}
	
	
	void updateMoveableLists() {
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
	 * TO-DO: if overall timer ends? or if traveled certain distance, end game
	 */
	boolean endGame() {
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
	
	void setDestination(int x, int y) {
		this.bird.setDestination(x, y);
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
        if(switchE == 1){
        	newEnemy = new Hawk(frameWidth, yloc);
        }
        if(switchE == 0) { 
        	newEnemy = new Bag(frameWidth,yloc);
        	removeInBagRange(yloc);
        	}
		if(powerTimer != 0) {
			newEnemy.setVelocity(newEnemy.getXVelocity()-velocityChange, newEnemy.getYVelocity());
		}
		enemies.add(newEnemy);
	}
	
	/**
	 * generate gust
	 */
	void generateGust() {
		Gust g = new Gust(frameWidth, (int) (Math.random()*frameHeight));
		if(powerTimer != 0) {
			g.setVelocity(g.getXVelocity()-velocityChange, g.getYVelocity());
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
				if(e.getImageName()=="Hawk") { /**if collide with hawk, deduct hp by 10 pts*/
					this.setScore(this.getScore() + enemyScore); 
				}
				else { /**if collide with plastic bag, deduct hp by 10 pts*/
					this.setScore(this.getScore() + (int)0.5*enemyScore);
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
				powerTimer = powerDuration;
				if(powerOn == false) {
					powerOn = true;
					accelerateMoveables(-velocityChange);
				}
				this.setScore(this.getScore() + gustScore);	
			}else if(g.exitsFrame(frameWidth, frameHeight)) {
				gustIterator.remove();
			}
		}
	}
	
	
	/**
	 * If bgObjects exit frame, they loop back around. 
	 * Mostly for clouds, used to also hold pointer in tutorial
	 * @author Anna
	 */
	void updateBackgroundObjects() {
		Iterator <Moveable> bgIterator = backgroundObjects.iterator();
		while(bgIterator.hasNext()) {
			Moveable o = bgIterator.next();
			if(o.exitsFrame(frameWidth, frameHeight)) {
				o.setLocation(frameWidth, o.getY());
			}
		}
	}
	
	/**
	 * Speeds up or down all objects besides bird according to input
	 * @author Anna
	 */
	void accelerateMoveables(int velocity) {
		for(Gust gust : gusts) {
			gust.setVelocity(gust.getXVelocity()+velocity, gust.getYVelocity());
		}
		for(Enemy enemy : enemies) {
			enemy.setVelocity(enemy.getXVelocity()+velocity, enemy.getYVelocity());
		}
		for(Moveable cloud : backgroundObjects) {
			cloud.setVelocity(cloud.getXVelocity()+velocity, cloud.getYVelocity());
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
		this.setDestination(mouseX, mouseY);	
	}

	/**
	 * return the HuD argument list for the Hud to generate image.
	 */
	@Override
	int[] getHUDargs() {
		int[] toret = {
				isMigrating ? 1 : 0,
				distance,
				isMigrating ? migrateDistance : stayDistance
		};
		return toret;
	}
	
	void buttonClicked(int answer) {
		
	}
}