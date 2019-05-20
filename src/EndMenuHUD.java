import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

/*
 * display end score
 * 
 * @author Kelly
 */

public class EndMenuHUD implements HUD, ImageObserver {
	int frameHeight;
	int frameWidth;
	BufferedImage background;
	
	public EndMenuHUD(int w, int h) {
		frameWidth = w;
		frameHeight = h;
		background = View.createImage(View.IMAGE_PATH+"background_ending_blank.png");
	}
	
	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		return false;
	}
	
	@Override
	public void paint(Graphics g, int[] args) {}
	
	@Override
	public void paintBack(Graphics g, int[] args, int cameraX, int cameraY) {
		int drawWidth = frameHeight*background.getWidth()/background.getHeight();
		g.drawImage(background, frameWidth/2-drawWidth/2, 0, drawWidth, frameHeight, this);
		
	}
}
