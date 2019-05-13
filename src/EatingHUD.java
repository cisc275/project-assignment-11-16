import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;




/**
 * 
 * @author Prescott
 *
 */

public class EatingHUD implements HUD, ImageObserver {
	int frameHeight;
	int frameWidth;
	BufferedImage background;
	public EatingHUD(int w, int h) {
		frameWidth = w;
		frameHeight = h;
		background = View.createImage("./images/background_eating.png");
	}
	
	/**
	 * args: score, scoregooal, time, maxtime
	 */
	public void paint(Graphics g, int[] args) {
		g.drawString(Integer.toString(args[0]), 0, 0);
	}

//	@Override
//	public HUD nextHUD(int fw, int fh) {
//		return new MigratingHUD(fw, fh);
//	}

	@Override
	
	/**
	 * generate the background image.
	 * 
	 * @Author Wenki
	 */
	public void paintBack(Graphics g,int[]args) {
		g.drawImage(background,0,0,this);
	}

	@Override
	public boolean imageUpdate(Image arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
		// TODO Auto-generated method stub
		return false;
	}
}


