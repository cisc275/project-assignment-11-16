import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.RasterFormatException;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


/**
 * 
 * @author Prescott
 *
 */
public class EatingHUD implements HUD, ImageObserver {
	final int SCORE_SIZE = 30;
	int frameHeight;
	int frameWidth;
	BufferedImage background;
	BufferedImage[] timer;
	int currentTime;
	Font myFont;
	final String FONT_NAME = "arial_rounded";
	final int TIMER_OFFSET = 30;
	final int SCORE_OFFSET = 50;
	
	public EatingHUD(int w, int h) {
		frameWidth = w;
		frameHeight = h;
		background = View.createImage(View.IMAGE_PATH+"background_eating.png");
		timer = loadTimerAnimation();
		loadFont();
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
	 * </br> note: scoregoal deprecated do not use
	 * @author - kelly
	 */
	public void paint(Graphics g, int[] args) {
		g.setFont(myFont);
		g.drawString(Integer.toString(args[0]) + " points. Eat as many bugs as you can before migration time!", 
				frameWidth*2/5, frameHeight-SCORE_OFFSET);
		BufferedImage currentTimer= timer[getFrame(args[2], args[3])];
		g.drawImage(currentTimer, frameWidth - currentTimer.getWidth() - TIMER_OFFSET, frameHeight - currentTimer.getHeight() - TIMER_OFFSET, this);
	}
	
	private void loadFont() {
		String fontFileName = "src/fonts/" + FONT_NAME + ".ttf";
		try {
			InputStream is = new FileInputStream(fontFileName);
			Font tempFont = Font.createFont(Font.TRUETYPE_FONT, is);
			myFont = tempFont.deriveFont(24.0f);
		} catch (IOException | FontFormatException e) {
			myFont = new Font ("Zapfino", Font.BOLD, 24);
			System.out.println("font " + FONT_NAME + " not found");
		} 
		
		
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
	public void paintBack(Graphics g, int[] args, int cameraX, int cameraY) {
		//TODO moving camera
		g.drawImage(background, -cameraX, -cameraY, this);
	}
	
	@Override
	public boolean imageUpdate(Image arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
		// TODO Auto-generated method stub
		return false;
	}
}


