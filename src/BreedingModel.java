
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class BreedingModel extends Model {
	
	BreedingBird bird;
	//List<Predator> predators;
	Predator p;
	Nest nest;
	boolean quizTime = false;
	int distractCountdown = 50;
	int randX = (int) (Math.random()*frameHeight); //random isn't working like id like
	int randY = (int) (Math.random()*frameWidth);
	
	//pass frame height/width from view to create models
	BreedingModel(int w, int h){
		frameHeight = w;
		frameWidth = h;
		bird = new BreedingBird(400, 400, 30, 0, 0, 8);
		//predators = new ArrayList<Predator>();
		//predators.add(new Raccoon(40, 40, 10, 5, 0));
		p = new Raccoon(300, 800, 35, 0, 0);
		nest = new Nest(frameHeight/2,frameWidth/2,50);
	}
	
	//for testing 
	BreedingModel(int w, int h, BreedingBird b, List<Predator> p){
		frameHeight = w;
		frameWidth = h;
		bird = b;
		//predators = p;
		nest = new Nest(0, 0, 0);
	}	
	
	//for testing 
	BreedingModel(int w, int h, BreedingBird b, List<Predator> p, Nest n){
		frameHeight = w;
		frameWidth = h;
		bird = b;
		//predators = p;
		nest = n;
	}	
	
	void setDestination(int x, int y) {
		this.bird.setDestination(x, y);
	}
	
	void updateBird(int xB, int yB) {
		/*
		 * for (Predator p : this.predators) { p.updateBirdLoc(xB, yB); }
		 */
		if (this.bird.getBrokenWing() == true) {
			//p.velocity.setPolar(p.velocity.getR(), -(p.velocity.getTheta()));
			p.updateBirdLoc(xB, yB);
			distractCountdown--;
			System.out.println(distractCountdown);
		}
		else p.updateBirdLoc(nest.x, nest.y);
		//predator goes towards nest
	}
	
	void update() {
		bird.update();
		//for (Predator p : this.predators) {
			p.update();
			this.updateBird(this.bird.getX(), this.bird.getY());
			updateCollision();
			despawnPredators();
		
	}
	
	void updateCollision() {
		if (bird.collidesWith(p)) {
			System.out.println("bird collided with p");
		}
		if (p.collidesWith(nest)) {
			nest.numEggs -= 1;
			System.out.println("bird collided with n");
			byeByePredator();
	}
	}
	boolean endGame() {
		if (nest.numEggs == 0) {
			return true;
		}
		else return false;
	}
	
	Collection<Moveable> getMoveables(){
		Collection<Moveable> m = new ArrayList<Moveable>();
		//m.addAll(predators);
		m.add(p);
		m.add(bird);
		m.add(nest);
		return m;
	}
		
	/**
	 * checks when to create pop-up quiz
	 * @return
	 */
	boolean isQuizTime() {
		return quizTime;
	}
	
	void generatePredators() {
		
	}
	
	/* should make the predator run off the screen to the left, but uhhh
	 * @author Zach */
	void byeByePredator() {
		//int randX = (int) (Math.random()*frameHeight);
		int randYY = (int) (Math.random()*frameWidth);
		p.updateBirdLoc(-100, randYY);
	}
	
	void despawnPredators() {
		//predators in the view should run away
		if (distractCountdown < 0) {
			byeByePredator();
		}
		if (p.exitsFrame(frameWidth, frameHeight)) {
			p = new Raccoon (randX, randY, 35, 0, 0);
			quizTime = true;
			distractCountdown = 200;
		}
	
		
	}
	
	void updatePredatorCollision() {
		
	}
	void startBrokenWing() {
		this.bird.brokenWing = true;
	}
	
	void stopBrokenWing() {
		this.bird.brokenWing = false;
	}

	@Override
	void mousePressed(int mouseX, int mouseY, int actualX, int actualY, boolean leftClick, boolean rightClick) {
		if (leftClick == true) {
			this.setDestination(mouseX, mouseY);
		}else if(rightClick == true) {
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
		// TODO Auto-generated method stub
		
	}

	@Override
	int[] getHUDargs() {
		int[] toret = {
				0,
				0
		};
		return toret;
	}
	
	@Override
	public Model nextModel(int frameWidth, int frameHeight, boolean isMigrating) {
			return new Menu(frameWidth, frameHeight);
	}
	
}