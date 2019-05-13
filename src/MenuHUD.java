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
	
	public MenuHUD(int w, int h) {
		frameWidth = w;
		frameHeight = h;
	}
	@Override
	public void paint(Graphics g, int[] args) {
		// TODO Auto-generated method stub

	}

//	@Override
//	public HUD nextHUD(int fw, int fh) {
//		return new EatingHUD(fw,fh);
//	}
	@Override
	public void paintBack(Graphics g,int[] args) {
		// TODO Auto-generated method stub
		BufferedImage background = View.createImage("./images/background_start_menu.png");
		g.drawImage(background,0,0,this);
	}
	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		return false;
	}

}
