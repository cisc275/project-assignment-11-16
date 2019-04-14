import java.util.*;

class MigratingModel extends Model{

	MigratingBird bird;
	List<Enemy> enemies;
	List <Gust> gusts;
	
	Iterator <Enemy> enemiesIterator;
	Iterator <Gust> gustIterator;
	
	private static int enemyScore = -10; 
	private static int gustScore = 20;
	
	//pass frame height/width from view to create models
	MigratingModel(int w, int h){
		frameHeight = w;
		frameWidth = h;
		bird = new MigratingBird(0, 20, 200, 0, 0); //bird is still
		enemies = new ArrayList<>();
		enemies.add(new Enemy(300, 300, 200, 10, 10));
		gusts = new ArrayList<>();
		enemiesIterator = enemies.iterator();
		gustIterator = gusts.iterator();
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
	void update() {}
	void updateCollision() {}
	boolean endGame() {return false;}
	
	Collection<Moveable> getMoveables(){
		Collection<Moveable> m = new ArrayList<>();
		m.addAll(enemies);
		m.add(bird);
		m.addAll(gusts);
		return m;
	}
		
	//check to make sure enemies don't overlap later
	void generateEnemy() {
		Enemy e = new Enemy( frameWidth-2 , (int) Math.random()*frameHeight);
		enemies.add(e);
	}
	void generateGusts() {
		
	}
	

	//if bird collides with Moveable, update lists and bird
	void updateEnemyCollision() {
		//if bird collideswith enemy for all enemy 
		//remove enemy from list
		//score.setscore(decrement);
		
		while(enemiesIterator.hasNext()) {
			Enemy e = enemiesIterator.next();
			if(e.collidesWith(bird)) {
				enemiesIterator.remove();
				this.setScore(this.getScore() + enemyScore);
			}else if(e.exitsFrame(frameWidth, frameHeight)) {
				enemiesIterator.remove();
			}
		}
	}
	
	
	//add something to up bird velocity for a certain peroid
	void updateGustCollision() {
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