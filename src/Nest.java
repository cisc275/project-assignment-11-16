
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
	
	@Override
	public double getFacing() {
		switch(numEggs) {
			case 3: 
				return 0; //right direction
			case 2:
				return 1; //down
			case 1:
				return 3; //left
			default:
				return 4; //up
		}
	}
	
	public String getImageName() {
		return "nest";
	}
	
}
