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
	 * checks if game related to the model is ended
	 * @return
	 */
	abstract boolean endGame();
	
	/**
	 * 
	 * @return A Collection of every Moveable intended to be drawn by the View.
	 */
	abstract Collection<Moveable> getMoveables();
	
	abstract int[] getHUDargs();
	
	/**
	 * @param actualX @param actualY for the moving camera
	 * @author Anna
	 */
	abstract void mousePressed(int mouseX, int mouseY, int actualX, int actualY, boolean leftClick, boolean rightClick);
	
	abstract void mouseReleased();
	
	abstract void mouseDragged(int mouseX, int mouseY, int actualX, int actualY);
	
	abstract void mouseMoved(int mouseX, int mouseY);
	
	/**
	 * i'm gonna fix that spaghetti -Prescott
	 * @param frameWidth
	 * @param frameHeight
	 * @param isMigrating
	 * @return
	 * @deprecated
	 */
	abstract Model nextModel(int frameWidth, int frameHeight, boolean isMigrating);

	
}