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
		distract = View.createImage(View.IMAGE_PATH+"images/distract.png");
		distractB = View.createImage(View.IMAGE_PATH+"images/distractB.png");
	}
	
	public void paint(Graphics g, int[] args) {
		//Zach: okay my idea is to make an exclamation mark over the raccoon's head that fills up
		if (args[2] == 1) {
			g.drawImage(distractB, args[0]+30, y, 20, 180, this);
			g.drawImage(distract, args[0], args[1] - 80, 80, 90, this);
			refreshY(args[1],args[3]);
		}
	}
	
	//fills in the exclamation point
	//doesn't currently work
	public void refreshY(int yloc ,int distract) {
		y = yloc + distract;
		System.out.println("y =" + y);
	}
	
	@Override
	public void paintBack(Graphics g,int[] args) {
		g.drawImage(background,0,0,this);
		//g.drawImage(bushes,0,0,this); currently not transparent :(
		
	}
	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		return false;
	}

}
