import java.util.Random;

public class Raccoon extends Predator {
	String myName;

	Raccoon(int xP, int yP) {
		super(xP, yP, 40, 0, 0);
		speed = 8;
		setName();
	}
	
	Raccoon(int xP, int yP, int r, int xV, int yV) {
		super(xP, yP, r, xV, yV);
		speed = 5;
		setName();
	}
	
	Raccoon(int xP, int yP, int r, int xV, int yV, int s) {
		super(xP, yP, r, xV, yV, s);
		setName();
	}

	private void setName() {

		Random r =  new Random();
        int switchObj =  r.nextInt(2);
        
        switch(switchObj){
        case 0:
        	myName = "raccoon";
        	break;
        case 1:
        	myName = "fox";
        	break;
        default:
        	myName = "raccoon";
        }
	}
	
	public String getImageName() {
		return myName;
	}
}
