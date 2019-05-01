import java.util.*;


class MigratingModel extends Model{

	MigratingBird bird;
	List<Enemy> enemies;
	List<Gust> gusts;
	
	private static int enemyScore = -10; 
	private static int gustScore = 20;
	private final static int maxEnemies = 3;
	private final static int maxGusts = 2;
	private final static int powerDuration = 50;
	private final static int velocityChange = 10;
	
	boolean powerOn = false;
	private int powerTimer = 0;
	
	
	/**
	 * pass frame height/width from view to create models
	 * @param w
	 * @param h
	 */
	MigratingModel(int w, int h){
		frameHeight = h;
		frameWidth = w;
		bird = new MigratingBird(frameWidth/2, frameHeight/2); //bird is still
		enemies = new ArrayList<Enemy>();
		enemies.add(new Hawk(frameWidth, 400));
		gusts = new ArrayList<Gust>();
		gusts.add(new Gust(frameWidth-100,150));
	}
	
	//for testing ease
	MigratingModel(int w, int h, MigratingBird b, List<Enemy> e, List<Gust> g){
		frameHeight = w;
		frameWidth = h;
		bird = b;
		enemies = e;
		gusts = g;
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
		//System.out.println(powerTimer);
		for (Moveable o : enemies) {
			o.update();
		}
		for (Moveable o : gusts) {
			o.update();
		}
		updateCollision(); 
	}
	void updateCollision() {
		updateEnemyCollision();
		updateGustCollision();
	}
	
	/**
	 * TO-DO: if overall timer ends? or if traveled certain distance, end game
	 */
	boolean endGame() {return false;}
	
	Collection<Moveable> getMoveables(){
		Collection<Moveable> m = new ArrayList<Moveable>();
		m.addAll(enemies);
		m.add(bird);
		m.addAll(gusts);
		return m;
	}
	
	void setDestination(int x, int y) {
		this.bird.setDestination(x, y);
	}
		
	/**
	 * Use random number as switch to generate sub enemies - Hawk or Plastic Bags.
	 * If switch is on (equals to one), generate Hawk;
	 * if switch if off (equals to zero), generate Plastic bag.
	 * Check the y location with other enemies before generate to avoid overlap.
	 * @author Wenki
	 */
	void generateEnemy() {
		Random r =  new Random();
        int switchE =  r.nextInt(2);
        int yloc = (int) (Math.random()*frameHeight); 
        Enemy newEnemy = null;
        for(Enemy e: enemies) { //check the exist enemies
        	if(e.getX() == frameWidth && (yloc == e.getY() ||(yloc < e.getY() && yloc >= e.getY()-e.getR())
        									||(yloc>e.getY() &&yloc <=e.getY()+e.getR())))
        	{//if both at start point, and Y-location is overlapping with the area existing enemy
        		yloc += 3*e.getRadius(); //change the y-location 
        	}
        }
        if(switchE == 1){
        	newEnemy = new Hawk(frameWidth, yloc);
        }
        if(switchE == 0) { 
        	newEnemy = new Bag(frameWidth,yloc);
        	}
		if(powerTimer != 0) {
			newEnemy.setVelocity(newEnemy.getXVelocity()-velocityChange, newEnemy.getYVelocity());
		}
		enemies.add(newEnemy);
	}
	
	
	void generateGust() {
		Gust g = new Gust(frameWidth, (int) (Math.random()*frameHeight));
		if(powerTimer != 0) {
			g.setVelocity(g.getXVelocity()-velocityChange, g.getYVelocity());
		}
		gusts.add(g);
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
	}
	
	
	
	
}