import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class BreedingModel extends Model {
	
	BreedingBird bird;
	//List<Predator> predators;
	Predator p;
	Nest nest;
	
	//pass frame height/width from view to create models
	BreedingModel(int w, int h){
		frameHeight = w;
		frameWidth = h;
		bird = new BreedingBird(130, 130, 30, 0, 0);
		//predators = new ArrayList<Predator>();
		//predators.add(new Raccoon(40, 40, 10, 5, 0));
		p = new Raccoon(600, 600, 35, 1, 0);
		nest = new Nest(400,400,50);
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
			p.updateBirdLoc(xB, yB);;
			System.out.println("wing is broken");
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
		
	}
	
	void updateCollision() {
		if (bird.collidesWith(p)) {
			System.out.println("bird collided with p");
		}
		if (p.collidesWith(nest)) {
			nest.numEggs -= 1;
	}
	}
	boolean endGame() {
		if (nest.numEggs == 0) {
			return true;
		}
		else return false;
	}
	
	Collection<Moveable> getMoveables(){
		Collection<Moveable> m = new ArrayList<>();
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
	boolean isQuizTime() {return false;}
	
	void generatePredators() {
		
	}
	
	void despawnPredators() {
		//predators in the view should run away
		
		
	}
	
	void updatePredatorCollision() {
		
	}
	void startBrokenWing() {
		this.bird.showBrokenWing = true;
	}
	
	void stopBrokenWing() {
		this.bird.showBrokenWing = false;
	}
	
}