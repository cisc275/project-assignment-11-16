
public class Nest extends Moveable{
	
	int numEggs; 
	
	//nest does not move
	Nest(int xP, int yP, int r){
		super(xP, yP, r, 0, 0);	
		numEggs = 3;
	}

	Nest(int xP, int yP, int r, int e){
		super(xP, yP, r, 0, 0);	
		numEggs = e;
	}
	
	
	void update() {
		
	}
	
	public String getImageName() {
		return "nest";
	}
	
}
