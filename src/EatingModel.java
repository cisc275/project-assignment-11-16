import java.util.ArrayList;
import java.util.List;

class EatingModel extends Model{
	
	EatingBird bird;
	List<Food> food;
	int foodGoal; //target amount of food to eat
	int timeLimit;
	int foodScore = 0;
	
	//pass frame height/width from view to create models
	EatingModel(int w, int h){
		frameHeight = w;
		frameWidth = h;
		bird = new EatingBird(0,0,0,0,0);
		food = new ArrayList<>();
	}
	
	//for testing ease
	EatingModel(int w, int h, EatingBird b, List<Food> f){
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
		foodGoal = foodNum;
		timeLimit = time;
	}

	
	void update() {}
	void updateCollision() {}
	boolean endGame() {return false;}
	
	boolean foodEaten() {
		return false;
	}
	
	//for randomly generating food
	void generateFood() {}
	void despawnFood() {}
	
	//if bird collides with food, update list, bird, and score
	void updateFoodCollision() {}
	
	
	
	
	
}