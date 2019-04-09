
public class Nest extends Moveable{
	
	int numEggs; 
	
	//nest does not move
	Nest(int xP, int yP, int r){
		x = xP;
		y = yP;
		radius = r;
		xVelocity = 0;
		yVelocity = 0;	
		numEggs = 3;
	}

	Nest(int xP, int yP, int r, int e){
		x = xP;
		y = yP;
		radius = r;
		xVelocity = 0;
		yVelocity = 0;	
		numEggs = e;
	}


	@Override
	void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void move() {
		// TODO Auto-generated method stub
		
	}

}
