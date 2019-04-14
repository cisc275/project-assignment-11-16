import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

class EatingModel extends Model{
	
	EatingBird bird;
	List<Food> food;
	int scoreGoal;
	int timeLimit;
	int timeTaken;
	int foodSpawnTimer;
	final int maxFood = 20;
	final int worldWidth = 1000;
	final int worldHeight = 800;
	
	/**
	 * pass frame height/width from view to create models
	 * @param w
	 * @param h
	 */
	EatingModel(int w, int h) {
		frameHeight = w;
		frameWidth = h;
		bird = new EatingBird(worldWidth/2, worldHeight/2);
		food = new ArrayList<Food>();
		timeTaken = 0;
		for (int i = 0; i < maxFood; i++) {
			spawnRandomFood();
		}
	}
	
	//for testing ease
	EatingModel(int w, int h, EatingBird b, List<Food> f) {
		frameHeight = w;
		frameWidth = h;
		bird = b;
		food = f;
	}
	
	EatingModel(int w, int h, EatingBird b, List<Food> f, int foodNum, int time){
		frameHeight = w;
		frameWidth = h;
		bird = b;
		food = f;
		timeLimit = time;
	}

	
	public void update() {
		if (food.size() < maxFood)
			spawnRandomFood();
		bird.update();
		for (Moveable o : food) {
			o.update();
		}
		updateCollision();
	}
	
	
	public void updateCollision() {
		Iterator<Food> foodIt = food.iterator();
		while(foodIt.hasNext()) {
			Food f = foodIt.next();
			//System.out.println(bird.distanceTo(f));
			if (bird.collidesWith(f)) {
				foodIt.remove();
				this.score += f.getScoreValue();
			}
		}
	}
	
	void spawnRandomFood() {
		food.add(new Earthworm((int) (Math.random()*worldWidth), (int) (Math.random()*worldHeight)));
	}
	
	boolean endGame() {
		return timeTaken >= timeLimit;
	}
	
	Collection<Moveable> getMoveables(){
		Collection<Moveable> m = new ArrayList<Moveable>();
		m.addAll(food);
		m.add(bird);
		return m;
	}
	
	//for randomly generating food
	void generateFood() {}
	void despawnFood() {}
	
	//if bird collides with food, update list, bird, and score
	void updateFoodCollision() {}
	
}