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
	final int TIME_LIMIT_STAYING = 600;
	final int TIME_LIMIT_MIGRATING = 600;
	EatingBird bird;
	List<Food> food;
	int scoreGoal;// = 500;
	int timeLimit;
	int timeTaken;
	int foodSpawnTimer;
	int maxFood = 30;
	final int worldWidth = 2160;
	final int worldHeight = 1440;
	
	EatingModel(int w, int h) {
		this(w, h, false);
	}
	
	/**
	 * pass frame height/width from view to create models
	 * @param w
	 * @param h
	 */
	EatingModel(int w, int h, boolean mig) {
		timeLimit = mig ? TIME_LIMIT_MIGRATING : TIME_LIMIT_STAYING;
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
		int rand = (int) (Math.random()*3);
		Food toAdd;
		switch (rand) {
			case 0: toAdd = new Earthworm((int) (Math.random()*worldWidth), (int) (Math.random()*worldHeight)); break;
			case 1: toAdd = new Grasshopper((int) (Math.random()*worldWidth), (int) (Math.random()*worldHeight)); break;
			case 2: toAdd = new Beetle((int) (Math.random()*worldWidth), (int) (Math.random()*worldHeight)); break;
			default : return;
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
	
	void setDestination(int x, int y) {
		this.bird.setDestination(x, y);
	}
	
	public int getBirdX() {
		return bird.getX();
	}
	
	public int getBirdY() {
		return bird.getY();
	}
	
	@Override
	public int getWidth() {
		return worldWidth;
	}

	@Override
	public int getHeight() {
		return worldHeight;
	}
	
	@Override
	void mousePressed(int mouseX, int mouseY, int actualX, int actualY, boolean leftClick, boolean rightClick) {
		this.setDestination(actualX, actualY);
	}

	@Override
	void mouseReleased() {
		
	}

	@Override
	void mouseDragged(int mouseX, int mouseY, int actualX, int actualY) {
		this.setDestination(actualX, actualY);
	}

	@Override
	void mouseMoved(int mouseX, int mouseY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	int[] getHUDargs() {
		int[] toret = {score, scoreGoal, timeTaken, timeLimit};
		return toret;
	}
	
	@Override
	void buttonClicked(int answer) {
		// TODO Auto-generated method stub
		
	}


		
}