/** Migrating HUD
 * Use paint method to create moving minimap for the migrating game since it is the most above layer.
 * Generate background image for the game with paintBack method.
 * @author Wenki
 */
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
public class MigratingHUD implements HUD, ImageObserver {
	static int frameWidth = 1080;
	static int frameHeight = 720;//1080;
	
	BufferedImage map = View.createImage("./images/migrateMinimap.png");
	BufferedImage mapBird = View.createImage("./images/mapBird.png");
	int destinationX = frameWidth - map.getWidth()/3;
	int destinationY = frameHeight - map.getHeight()/3;
	int initialX = frameWidth - map.getWidth()*2/3;
	int initialY = frameHeight - map.getHeight()*7/8;
	int x = initialX;
	int y = initialY;
	int currentDistance;
	int maxDistance;
	BufferedImage background;
	
	public MigratingHUD(int w, int h) {
		frameWidth = w;
		frameHeight = h;
	}

	public void paintBack(Graphics g, int[]args) {
		background = View.createImage("./images/background_migrating.png");
		g.drawImage(background,0,0,this);
	}
	/**
	 * args: <br>
	 * 	index 0. is migrating (1 = migrating, 0 = not migrating);<br>
	 *  index 1. current distance<br>
	 *  index 2. max distance<br>
	 *This method draw the minimap at the right corner of the frame.
	 *
	 * @author Wenki
	 */
	public void paint(Graphics g, int[] args) {
		currentDistance = args[1];
		maxDistance = args[2];
		if(args[0]==0) {
			map = View.createImage("./images/nonMigrateMinimap.png");
		}
		if(args[0] ==1) {
			map = View.createImage("./images/migrateMinimap.png");
		}
		g.drawImage(map, frameWidth-map.getWidth(), frameHeight-map.getHeight(), this);	
		g.drawImage(mapBird, x,y , this);
		refreshXY();
	}
	
	/**Refresh X,Y location so that it could update same pace with the game.
	 *The X location update by convert the scale of the game to the scale of the map.
	 * 
	 * For Y position, used a line function that update Y location with the X location.
	 * 
	 * @author Wenki
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
//	@Override
//	public HUD nextHUD(int fw, int fh) {
//		return new BreedingHUD(fw, fh);
//	}

}
