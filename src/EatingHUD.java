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
	BufferedImage[] timer;
	
	public EatingHUD(int w, int h) {
		frameWidth = w;
		frameHeight = h;
		background = View.createImage(View.IMAGE_PATH+"background_eating.png");
		timer = loadTimerAnimation();
	}
	
	/**
	 * 
	 * loads animation from timer image
	 * @author Kelly
	 */
	private BufferedImage[] loadTimerAnimation() {
		BufferedImage sheet = View.createImage(View.IMAGE_PATH + "eating-timer.png"); 
		BufferedImage[] imgs;
		
		if (sheet != null) {
			int subsize = sheet.getHeight();
			int numSprites = sheet.getWidth()/subsize;
			imgs = new BufferedImage[numSprites];
			for (int i = 0; i < numSprites; i++) {
				
			}
			return imgs;
		} else {
			return null;
		}
		
	}
	
	/**
	 * args: score, scoregoal, time, maxtime
	 */
	public void paint(Graphics g, int[] args) {
		//TODO do it better
		g.drawString(Integer.toString(args[0]), 0, 0);
	}
	
	/**
	 * draw the background image.
	 * 
	 */
	public void paintBack(Graphics g, int[]args) {
		//TODO moving camera
		g.drawImage(background,0,0,this);
	}

	@Override
	public boolean imageUpdate(Image arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
		// TODO Auto-generated method stub
		return false;
	}
}


