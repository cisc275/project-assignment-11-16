
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
				return 0;
			case 2:
				return 1;
			case 1:
				return 3;
			default:
				return 4;
		}
	}
	
	public String getImageName() {
		return "nest";
	}
	
}
