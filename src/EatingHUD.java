import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.RasterFormatException;

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
	int currentTime;
	
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
				try {
					imgs[i] = sheet.getSubimage(subsize*i, 0, subsize-1, subsize);
				} catch (RasterFormatException e) {
					imgs[i] = null;
					System.out.println("loading frame " + (i+1) + " failed");
					System.out.println(e);
				}
			}
			return imgs;
		} else {
			return null;
		}
		
	}
	
	/**
	 * args: score, scoregoal, time elapsed, maxtime
	 * @author - kelly
	 */
	public void paint(Graphics g, int[] args) {
		g.drawString(Integer.toString(args[0]), 0, 0);
		BufferedImage currentTimer= timer[getFrame(args[2], args[3])];
		g.drawImage(currentTimer, frameWidth - currentTimer.getWidth(), frameHeight - currentTimer.getHeight(), this);
		
	}
	
	/**
	 * pass in time elapsed and time limit to find current timer frame </br>
	 * returns int
	 * @author - kelly
	 */
	private int getFrame(int timeTaken, int maxTime) {
		int a = timer.length;
		float percent = (float) timeTaken/maxTime;
		int frame = (int) (a * percent);
		return frame; //int conversion truncates so that it won't go beyond array range
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


