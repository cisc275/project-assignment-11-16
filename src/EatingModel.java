import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * The first model of the game, in which the player runs around and scores points by eating food (insects).
 * @author Prescott
 *
 */
class EatingModel extends Model {
	
	EatingBird bird;
	List<Food> food;
	int scoreGoal = 500;
	int timeLimit;
	int timeTaken;
	int foodSpawnTimer;
	final int maxFood = 20;
	final int worldWidth = 2000;
	final int worldHeight = 1200;
	
	/**
	 * pass frame height/width from view to create models
	 * @param w
	 * @param h
	 */
	EatingModel(int w, int h) {
		timeLimit = 600;
		timeTaken = 0;
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
		timeTaken++;
		if (food.size() < maxFood)
			spawnRandomFood();
		for (Food f : food) {
			f.update(bird);
		}
		bird.update();
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
			} else if (f.exitsFrame(worldWidth, worldHeight)) {
				foodIt.remove();
			}
		}
	}
	
	void spawnRandomFood() {
		double rand = Math.random();
		Food toAdd;
		if (rand < .5) {
			toAdd = new Earthworm((int) (Math.random()*worldWidth), (int) (Math.random()*worldHeight));
		} else if (rand < 1) {
			toAdd = new Grasshopper((int) (Math.random()*worldWidth), (int) (Math.random()*worldHeight));
		} else {
			return;
		}
		food.add(toAdd);
	}
	
	boolean endGame() {
		return timeTaken >= timeLimit;
	}
	
	Collection<Moveable> getMoveables() {
		Collection<Moveable> m = new ArrayList<Moveable>();
		m.addAll(food);
		m.add(bird);
		return m;
	}
	
	/**
	 * @deprecated
	 */
	Collection<MenuObject> getMenuObjects() {
		List<MenuObject> jex = new ArrayList<MenuObject>();
		jex.add(new Label(0, 0, 200, 40, this.score + "/" + this.scoreGoal));
		jex.add(new Label(400, 0, 200, 40, this.timeTaken + "/" + this.timeLimit));
		return jex;
	}
	
	void setDestination(int x, int y) {
		this.bird.setDestination(x, y);
	}
	
	public int getBirdX() {
		return bird.getX();
	}
	
	public int getBirdY() {
		return bird.getY();
	}
}