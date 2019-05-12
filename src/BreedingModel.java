
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

class BreedingModel extends Model {
	
	BreedingBird bird;
	//List<Predator> predators;
	Predator p;
	Nest nest;
	boolean quizTime = false;
	int distractCountdown = 50;
	int tutorialSpeed = 1;
	int normalSpeed = 5;
	int switchDir;
	
	//pass frame height/width from view to create models
	BreedingModel(int w, int h){
		frameHeight = h;
		frameWidth = w;
		bird = new BreedingBird(frameWidth/2, frameHeight/2, 30, 0, 0, 8);
		//predators = new ArrayList<Predator>();
		//predators.add(new Raccoon(40, 40, 10, 5, 0));
		p = new Raccoon(frameWidth/2, frameHeight-100, 35, 0, 0, tutorialSpeed);
		nest = new Nest(frameWidth/2,(frameHeight/2)-100,50);
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
			p.speed = normalSpeed;
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
		Random r =  new Random();
        switchDir =  r.nextInt(2);
        if(switchDir == 1){
        	//start raccoon at left of screen
        	p = new Raccoon (0, frameHeight/2, 35, 0, 0, normalSpeed);
        }
        if(switchDir == 0) { 
        	//start raccoon at right of screen
        	p = new Raccoon (frameWidth, frameHeight/2,35,0,0, normalSpeed);
        	}
		distractCountdown = 60;
	}
	
	/* should make the predator run off the screen to the left, but uhhh
	 * @author Zach */
	void byeByePredator() {
		if(p.getX() >= (frameWidth/2) && p.getY() >= (frameHeight/2)) {
			//if in bottom right quadrant, go to that corner
			p.updateBirdLoc(frameWidth+p.radius*2, frameHeight+p.radius*2);
			System.out.print("You should be going bottom right");
		}
		if(p.getX() <= (frameWidth/2) && p.getY() >= (frameHeight/2)) {
			//if in bottom left quadrant, go to that corner
			p.updateBirdLoc(-p.radius*2, frameHeight+p.radius*2);
			System.out.print("You should be going bottom left");
			//System.out.print(p.getY() + ">" + frameHeight/2);
		}
		if(p.getX() >= (frameWidth/2) && p.getY() <= (frameHeight/2)) {
			//if in top right quadrant, go to that corner
			p.updateBirdLoc(frameWidth+p.radius*2, -p.radius*2);
			System.out.print("You should be going top right");
		}
		if(p.getX() <= (frameWidth/2) && p.getY() <= (frameHeight/2)) {
			//if in top left quadrant, go to that corner
			p.updateBirdLoc(-p.radius*2, -p.radius*2);
			System.out.print("You should be going top left");
		}
	}
	
	void despawnPredators() {
		//predators in the view should run away
		if (distractCountdown < 0) {
			byeByePredator();
		}
		if (p.exitsFrame(frameWidth, frameHeight)) {
			quizTime = true;
			generatePredators();
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