import java.util.*;


class MigratingModel extends Model{

	MigratingBird bird;
	List<Enemy> enemies;
	List<Gust> gusts;
	
	private static int enemyScore = -10; 
	private static int gustScore = 20;
	private static int maxEnemies = 3;
	private static int maxGusts = 2;
	
	boolean powerOn = false;
	private int powerTimer = 0;
	private static int powerDuration = 50;
	private static int velocityChange = 10;
	
	
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
		
	//also update score based on time completion
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
		System.out.println(powerTimer);
		for (Moveable o : enemies) {
			o.update();
		}
		for (Moveable o : gusts) {
			o.update();
		}
		updateCollision(); // ????
	}
	void updateCollision() {
		updateEnemyCollision();
		updateGustCollision();
	}
	
	boolean endGame() {return false;}
	
	Collection<Moveable> getMoveables(){
		Collection<Moveable> m = new ArrayList<>();
		m.addAll(enemies);
		m.add(bird);
		m.addAll(gusts);
		return m;
	}
	
	void setDestination(int x, int y) {
		this.bird.setDestination(x, y);
	}
		
	//check to make sure enemies don't overlap later
	void generateEnemy() {
		Enemy e = new Hawk(frameWidth, (int) (Math.random()*frameHeight));
		if(powerTimer != 0) {
			e.setVelocity(e.getXVelocity()-velocityChange, e.getYVelocity());
		}
		enemies.add(e);
	}
	void generateGust() {
		Gust g = new Gust(frameWidth, (int) (Math.random()*frameHeight));
		if(powerTimer != 0) {
			g.setVelocity(g.getXVelocity()-velocityChange, g.getYVelocity());
		}
		gusts.add(g);
	}
	

	/**
	 * if bird collides with Moveable, update lists and bird
	 */
	void updateEnemyCollision() {
		//if bird collides with enemy for all enemy 
		//remove enemy from list
		//score.setscore(decrement);
		Iterator <Enemy> enemiesIterator = enemies.iterator();
		while(enemiesIterator.hasNext()) {
			Enemy e = enemiesIterator.next();
			if (bird.collidesWith(e)) {
				enemiesIterator.remove();
				this.setScore(this.getScore() + enemyScore);
			} else if (e.exitsFrame(frameWidth, frameHeight)) {
				enemiesIterator.remove();
			}
		}
	}
	
	
	//add something to up bird velocity for a certain period
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
	
	void accelerateMoveables(int velocity) {
		for(Gust gust : gusts) {
			gust.setVelocity(gust.getXVelocity()+velocity, gust.getYVelocity());
		}
		for(Enemy enemy : enemies) {
			enemy.setVelocity(enemy.getXVelocity()+velocity, enemy.getYVelocity());
		}
	}
	
	
	
	
}