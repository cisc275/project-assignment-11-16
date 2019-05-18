import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

/**
 * generate the background image by the paintBack method.
 * 
 * @Author Wenki
 * 
 * paint the exclamation mark and fill in as timer goes down
 * @author ZachC
 */
public class BreedingHUD implements HUD, ImageObserver {
	static final int ABOVE_PREDATOR = 40;
	
	int frameHeight;
	int frameWidth;
	//int shifter;
	int y;
	BufferedImage bushes;
	BufferedImage background;
	BufferedImage distract;
	BufferedImage distractB;
	
	public BreedingHUD(int w, int h, boolean migrating) {
		frameWidth = w;
		frameHeight = h;
		bushes = View.createImage(View.IMAGE_PATH+"background_breeding_bushes.png");
		if (migrating) {
			background = View.createImage(View.IMAGE_PATH+"background_breeding.png");
		} else {
			background = View.createImage(View.IMAGE_PATH+"background_breeding_parkinglot.png");
		}
		distract = View.createImage(View.IMAGE_PATH+"distract.png");
		distractB = View.createImage(View.IMAGE_PATH+"distractB.png");
	}
	
	/**
	 * 0: predator's x
	 * 1: predator's y
	 * 2: broken wing
	 * 3: distract countdown
	 * 4: distract duration
	 * 5: is migrating
	 */
	public void paint(Graphics g, int[] args) {
		//Zach: okay my idea is to make an exclamation mark over the raccoon's head that fills up
		if (args[2] == 1) {
			int filled = Math.min(Math.max(1, (int) (distract.getHeight() * (1.0 - (1.0 * args[3] / args[4])))), distract.getHeight());
			BufferedImage filledImage = distract.getSubimage(0, distract.getHeight()-filled, distract.getWidth(), filled);
			g.drawImage(filledImage, args[0], args[1]-ABOVE_PREDATOR-filled, this);
			//g.drawImage(distractB, args[0]+30, y, 20, 180, this);
			//g.drawImage(distract, args[0], args[1]-ABOVE_PREDATOR-distract.getHeight(), this);
			//refreshY(args[1],args[3]);
		}
	}
	
	@Override
	public void paintBack(Graphics g, int[] args, int cameraX, int cameraY) {
		g.drawImage(background, 0, 0, frameWidth, frameHeight, this);
		//g.drawImage(bushes,0,0,this); currently not transparent :(
		
	}
	
	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		return false;
	}

}
