/** Migrating HUD
 * Use paint method to create moving minimap for the migrating game since it is the most above layer.
 * Generate background image for the game with paintBack method.
 * @author Wenki
 */
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
public class MigratingHUD implements HUD, ImageObserver {
	static int frameWidth;
	static int frameHeight;//1080;

	BufferedImage background;
	BufferedImage map;
	BufferedImage mapBird;
	BufferedImage centralAmerica;
	BufferedImage nestingGrounds;
	BufferedImage readyToFly;
	int destinationX;
	int destinationY;
	int initialX;
	int initialY;
	
	Font font = new Font("TimesRoman", Font.PLAIN, 150);
	
	/**
	 * @deprecated
	 */
	int x;
	/**
	 * @deprecated
	 */
	int y;
	int currentDistance;
	int maxDistance;
	
	public MigratingHUD(int w, int h, boolean mig) {
		frameWidth = w;
		frameHeight = h;
		if(mig) {
			map = View.createImage(View.IMAGE_PATH+"migrateMinimap.png");
		}else {
			map = View.createImage(View.IMAGE_PATH+"nonMigrateMinimap.png");
		}
		mapBird = View.createImage(View.IMAGE_PATH+"mapBird.png");
		background = View.createImage(View.IMAGE_PATH+"background_migrating.png");
		destinationX = frameWidth - map.getWidth()/3;
		destinationY = frameHeight - map.getHeight()/3;
		if(mig) {
			initialX = frameWidth - map.getWidth()*1/3;
			initialY = frameHeight - map.getHeight()*6/8;
		}else {
			initialX = frameWidth - map.getWidth()*2/3;
			initialY = frameHeight - map.getHeight()*7/8;
		}
		centralAmerica = View.createImage(View.IMAGE_PATH+"textCentralAmerica.png");
		nestingGrounds = View.createImage(View.IMAGE_PATH+"textNestingGrounds.png");
		readyToFly =View.createImage(View.IMAGE_PATH+"textFly.png");
		
	}

	public void paintBack(Graphics g, int[] args, int cameraX, int cameraY) {	
		g.drawImage(background, 0, 0, frameWidth, frameHeight, this);
		if(args[3] == 1 ) {//if transition to endSequence
			if(args[4] == 1) { //tutorial
				g.drawImage(readyToFly, 0, 0, this);
			}else if(args[4] == 0 && args[0] == 1) { //migrating
				g.drawImage(centralAmerica, 0, 0, this);
			}else if(args[4] == 0 && args[0] == 0) { // non
				g.drawImage(nestingGrounds, 0, 0, this);
			}
		}
	}
	/**
	 * args: <br>
	 * 	index 0. is migrating (1 = migrating, 0 = not migrating);<br>
	 *  index 1. current distance<br>
	 *  index 2. max distance<br>
	 * This method draw the minimap at the right corner of the frame.
	 *
	 * @author -Wenki- Prescott
	 */
	public void paint(Graphics g, int[] args) {
		if(args[4] == 0) {
			currentDistance = args[1];
			maxDistance = args[2];
			int birdX = (int) (initialX + (destinationX-initialX) * (1.0*currentDistance/maxDistance));
			int birdY = (int) (initialY + (destinationY-initialY) * (1.0*currentDistance/maxDistance));
			g.drawImage(map, frameWidth-map.getWidth(), frameHeight-map.getHeight(), this);	
			g.drawImage(mapBird, birdX, birdY, this);
		}
	}
	
	/**Refresh X,Y location so that it could update same pace with the game.
	 *The X location update by convert the scale of the game to the scale of the map.
	 * 
	 * For Y position, used a line function that update Y location with the X location.
	 * 
	 * @author Wenki
	 * @deprecated
	 */
	public void refreshXY() {
		if(x != destinationX) {
			x  = -(maxDistance - currentDistance)*(destinationX-initialX)/maxDistance + destinationX;

			y = (x-initialX)*(destinationY - initialY)/(destinationX - initialX) + initialY;
			
		}
		//System.out.println(x); //for test
	}
	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		return false;
	}


}
