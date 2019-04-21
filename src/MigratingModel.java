import java.util.*;


class MigratingModel extends Model{

	MigratingBird bird;
	List<Enemy> enemies;
	List<Gust> gusts;
	
	private static int enemyScore = -10; 
	private static int gustScore = 20;
	
	/**
	 * pass frame height/width from view to create models
	 * @param w
	 * @param h
	 */
	MigratingModel(int w, int h ){
		frameHeight = w;
		frameWidth = h;
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
		Enemy e = new Hawk((int) Math.random()*frameWidth , (int) Math.random()*frameHeight);
		enemies.add(e);
	}
	void generateGusts() {
		Gust g = new Gust((int) Math.random()*frameWidth, (int) Math.random()*frameHeight);
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
				this.setScore(this.getScore() + gustScore);	
			}else if(g.exitsFrame(frameWidth, frameHeight)) {
				gustIterator.remove();
			}
		}
	}
	
	
}