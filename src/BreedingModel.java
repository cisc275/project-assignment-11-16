import java.util.ArrayList;
import java.util.List;

class BreedingModel extends Model{
	
	BreedingBird bird;
	List<Predator> predators;
	Nest nest;
	
	//pass frame height/width from view to create models
	BreedingModel(int w, int h){
		frameHeight = w;
		frameWidth = h;
		bird = new BreedingBird(0, 0, 0, 0, 0);
		predators = new ArrayList<>();
	}
	
	//for testing 
	BreedingModel(int w, int h, BreedingBird b, List<Predator> p){
		frameHeight = w;
		frameWidth = h;
		bird = b;
		predators = p;
		nest = new Nest(0, 0, 0);
	}	
	
	//for testing 
	BreedingModel(int w, int h, BreedingBird b, List<Predator> p, Nest n){
		frameHeight = w;
		frameWidth = h;
		bird = b;
		predators = p;
		nest = n;
	}	
	
	void update() {}
	void updateCollision() {}
	boolean endGame() {return false;}
		
	//checks when to create pop-up quiz
	boolean isQuizTime() {return false;}
	
	//
	void generatePredators() {}
	void updatePredatorCollision() {}
	
}