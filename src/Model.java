import java.util.ArrayList;
import java.util.Collection;

abstract class Model {
	int score;
	int frameHeight;
	int frameWidth;
	
	//may need timer/countdown for models
	
	int getScore() {return score;}
	void setScore(int s) {score = s;}	

	/**
	 * contains logic of what to do each tick, call smaller updates within
	 */
	abstract void update();
	
	/**
	 * checks if game related to the model is ended based on time
	 * @return
	 */
	abstract boolean endGame();
	
	abstract Collection<Moveable> getMoveables();

}