import java.util.*;

class MigratingModel extends Model{

	MigratingBird bird;
	List<Enemy> enemies;
	List <Gust> gusts;
	
	//pass frame height/width from view to create models
	MigratingModel(int w, int h){
		frameHeight = w;
		frameWidth = h;
		bird = new MigratingBird(0, 0, 200, 0, 0);
		enemies = new ArrayList<>();
		enemies.add(new Enemy(300, 300, 200, 10, 10));
		gusts = new ArrayList<>();
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
		
	//adds new Moveable objects when they disappear or at time intervals
	void generateEnemies() {}
	void generateGusts() {}
	void despawn() {}
	
	//if bird collides with Moveable, update lists and bird
	void updateEnemyCollision() {}
	void updateGustCollision() {}
	
}