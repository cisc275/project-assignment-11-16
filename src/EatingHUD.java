import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

<<<<<<< HEAD
public class EatingHUD implements HUD, ImageObserver {
=======
/**
 * 
 * @author Prescott
 *
 */
public class EatingHUD implements HUD {
>>>>>>> 93f7cfd637c8951213f3f1ce045ab285e13f0686
	int frameHeight;
	int frameWidth;
	public EatingHUD(int w, int h) {
		frameWidth = w;
		frameHeight = h;
	}
	
	/**
	 * args: score, scoregooal, time, maxtime
	 */
	public void paint(Graphics g, int[] args) {
		g.drawString(Integer.toString(args[0]), 0, 0);
	}

	@Override
	public HUD nextHUD(int fw, int fh) {
		return new MigratingHUD(fw, fh);
	}

	@Override
	public void paint2(Graphics g) {
		// TODO Auto-generated method stub
		BufferedImage background = View.createImage("src/images/background_eating.gif");
		g.drawImage(background,0,0,this);
	}

	@Override
	public boolean imageUpdate(Image arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
		// TODO Auto-generated method stub
		return false;
	}
}
