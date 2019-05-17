import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

/**
 * generate the background image by the paintBack method.
 * 
 * @Author Wenki
 * 
 */
public class MenuHUD implements HUD, ImageObserver {
	int frameHeight;
	int frameWidth;
	BufferedImage background1;
	BufferedImage background;
	
	public MenuHUD(int w, int h) {
		frameWidth = w;
		frameHeight = h;
		background = View.createImage(View.IMAGE_PATH+"background_start_menu.png");
	}
	@Override
	public void paint(Graphics g, int[] args) {
		// TODO Auto-generated method stub

	}
	@Override
	public void paintBack(Graphics g,int[] args, int cameraX, int cameraY) {
		int drawWidth = frameHeight*background.getWidth()/background.getHeight();
		g.drawImage(background, frameWidth/2-drawWidth/2, 0, drawWidth, frameHeight, this);
	}
	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		return false;
	}

}
