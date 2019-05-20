
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BreedingModel extends Model {
	static final int DISTRACT_DURATION = 60;
	BreedingBird bird;
	Predator p;
	Nest nest;
	boolean quizTime = false;
	int distractCountdown = DISTRACT_DURATION;
	int tutorialSpeed = 0;
	int normalSpeed = 10;
	int runawaySpeed = 12;
	int switchDir;
	int correctAnswer;
	boolean isMigrating;
	int numPredators = 4; //including tutorial one, when restart don't play tutorial
	boolean tutorialMove = true;
	boolean tutorial = true;
	Mouse mouse;
	int questionNumber = -1;
	/**
	 * pass frame height/width from view to create models
	 */
	BreedingModel(int w, int h, boolean mig){
		frameHeight = h;
		frameWidth = w;
		bird = new BreedingBird(frameWidth/2, frameHeight/2, 30, 0, 0, 8);
		p = new Raccoon(frameWidth/2, frameHeight-100, 35, 0, 0, tutorialSpeed);
		nest = new Nest(frameWidth/2,(frameHeight/2)-100,50);
		isMigrating = mig;
		mouse = new Mouse(frameWidth*2/3,frameHeight*2/3,300,2,8);
		mouse.enableLeftRight();
	}
	
	/**
	 * for testing 
	 * @param w
	 * @param h
	 * @param b
	 * @param p
	 */
	BreedingModel(int w, int h, BreedingBird b, Raccoon r){
		frameHeight = w;
		frameWidth = h;
		bird = b;
		p = r;
		nest = new Nest(frameWidth/2,(frameHeight/2)-100,50);
		isMigrating = true;
	}	
	
	/**
	 * for testing 
	 * @param w
	 * @param h
	 * @param b
	 * @param p
	 * @param n
	 */
	BreedingModel(int w, int h, BreedingBird b, Raccoon r, Nest n){
		frameHeight = w;
		frameWidth = h;
		bird = b;
		nest = n;
		isMigrating = true;
	}	
	
	/**
	 * called from Controller if eggs drops to 0, 
	 * start game again without tutorial
	 */
	void retryGame() {
		quizTime = false;
		distractCountdown = DISTRACT_DURATION;
		numPredators = 3;
		questionNumber = -1;
		nest.numEggs = 3;
		generatePredators();
	}
	
	void setDestination(int x, int y) {
		this.bird.setDestination(x, y);
	}
	
	void updateBird(int xB, int yB) {
		if (this.bird.getBrokenWing() == true) {
			p.updateBirdLoc(xB, yB);
			distractCountdown--;
			//System.out.println(distractCountdown);
			//predator chases bird
		}
		else p.updateBirdLoc(nest.x, nest.y);
		//predator goes towards nest
	}
	
	void update() {
		if(!tutorialMove) {
			mouse.setState(3);
			mouse.resetMouse();
		}
		mouse.update();
		bird.update();
		p.update();
		this.updateBird(this.bird.getX(), this.bird.getY());
		updateCollision();
		despawnPredators();
	}
	
	void updateCollision() {
		/*
		 * if (bird.collidesWith(p)) { System.out.println("bird collided with p"); }
		 */
		if (p.collidesWith(nest)) {
			p.setCollidedWithNest(true); //turns collision off so it can leave nest smoothly 
			nest.numEggs -= 1;
			System.out.println(nest.numEggs);
		}
	}
	
	boolean endGame() {
		if (numPredators <= 0) {
			return true;
		}
		else return false;
	}
	
	Collection<Moveable> getMoveables(){
		Collection<Moveable> m = new ArrayList<Moveable>();
		m.add(nest);
		m.add(bird);
		m.add(p);
		if(tutorial) {
			m.add(mouse);
		}
		return m;
	}
		
	/**
	 * checks when to create pop-up quiz
	 * @return
	 */
	boolean isQuizTime() {
		return quizTime;
	}

	void setQuizTime(boolean b) {
		quizTime = b;
	}
	
	void generatePredators() {
		switch ((int) Math.random()*2) {
			//start raccoon at left of screen
			case 0: p = new Raccoon(0, frameHeight/2, 35, 0, 0, normalSpeed); break;
			//start raccoon at right of screen
			case 1: p = new Raccoon(frameWidth, frameHeight/2,35,0,0, normalSpeed); break;
		}
		distractCountdown = DISTRACT_DURATION;
	}
	
	/** checks which quadrant raccoon is in and runs off screen
	 * @author Zach
	 */
	void byeByePredator() {
		p.setSpeed(runawaySpeed);
		if(p.getX() >= (frameWidth/2) && p.getY() >= (frameHeight/2)) {
			//if in bottom right quadrant, go to that corner
			p.updateBirdLoc(frameWidth+p.radius*2, frameHeight+p.radius*2);
		}else if(p.getX() <= (frameWidth/2) && p.getY() >= (frameHeight/2)) {
			//if in bottom left quadrant, go to that corner
			p.updateBirdLoc(-p.radius*2, frameHeight+p.radius*2);
		}else if(p.getX() >= (frameWidth/2) && p.getY() <= (frameHeight/2)) {
			//if in top right quadrant, go to that corner
			p.updateBirdLoc(frameWidth+p.radius*3, -p.radius*2);
		}else if(p.getX() <= (frameWidth/2) && p.getY() <= (frameHeight/2)) {
			//if in top left quadrant, go to that corner
			p.updateBirdLoc(-p.radius*2, -p.radius*2);
		}
		tutorial = false;
	}
	
	void despawnPredators() {
		//predators in the view should run away
		if (distractCountdown < 0 || p.getCollidedWithNest()){
			byeByePredator();
		}
		//stop generating predators after eggcount hits 0
		if (p.exitsFrame(frameWidth, frameHeight) && numPredators > 0) {
			generatePredators();
			numPredators--;
			quizTime = true; 
		}
		
	}
	
	void isCorrect(int ans) {
		if (ans == correctAnswer) {
			score += 100;
		}
		else score -= 50;
	}
	
	void startBrokenWing() {
		this.bird.brokenWing = true;
	}
	
	void stopBrokenWing() {
		this.bird.brokenWing = false;
	}

	@Override
	void mousePressed(int mouseX, int mouseY, int actualX, int actualY, boolean leftClick, boolean rightClick) {
		if (leftClick) {
			this.setDestination(mouseX, mouseY);
			tutorialMove = false;
		} else if (rightClick) {
			this.startBrokenWing();
		}
	}

	@Override
	void mouseReleased() {
		this.stopBrokenWing();
		
	}

	@Override
	void mouseDragged(int mouseX, int mouseY, int actualX, int actualY) {
		this.setDestination(mouseX, mouseY);
	}

	@Override
	void mouseMoved(int mouseX, int mouseY) {
		
	}

	@Override
	int[] getHUDargs() {
		int[] toret = {
				p.getX(),
				p.getY(),
				bird.brokenWing ? 1 : 0,
				distractCountdown,
				DISTRACT_DURATION,
				isMigrating ? 1 : 0,
				numPredators,
				nest.numEggs
		};
		return toret;
	}
	
	@Override
	void buttonClicked(int answer) {
		isCorrect(answer);
		this.quizTime = false;
	}
	
	Quiz getQuiz() {
		questionNumber++;
		return new Quiz(questionNumber);
	}
	public boolean loseGame() {
		return (nest.numEggs <= 0);
	}
}