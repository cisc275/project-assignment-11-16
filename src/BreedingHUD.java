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
	
	public BreedingHUD(int w, int h) {
		frameWidth = w;
		frameHeight = h;
		
	}
	
	public void paint(Graphics g, int[] args) {
		//Zach: okay my idea is to make an exclamation mark over the raccoon's head that fills up
		BufferedImage distract = View.createImage("./images/distract.png");
		BufferedImage distractB = View.createImage("./images/distractB.png");
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
		BufferedImage background = null;
		BufferedImage bushes = View.createImage("./images/background_breeding_bushes.png");
		if(args[4]==0) {
			background = View.createImage("./images/background_breeding_parkinglot.png");
		}
		if(args[4] ==1) {
			background = View.createImage("./images/background_breeding.png");
		}
		g.drawImage(background,0,0,this);
		//g.drawImage(bushes,0,0,this); currently not transparent :(
		
	}

	@Override
	public HUD nextHUD(int fw, int fh) {
		// TODO Auto-generated method stub
		return new MenuHUD(fw,fh);
	}


	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		return false;
	}

}
