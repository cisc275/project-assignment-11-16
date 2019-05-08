import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

/**
 * generate the background image by the paintBack method.
 * 
 * @Author Wenki
 */
public class BreedingHUD implements HUD, ImageObserver {
	int frameHeight;
	int frameWidth;
	public BreedingHUD(int w, int h) {
		frameWidth = w;
		frameHeight = h;
	}
	
	public void paint(Graphics g, int[] args) {
		
	}

	@Override
	public HUD nextHUD(int fw, int fh) {
		// TODO Auto-generated method stub
		return new MenuHUD(fw,fh);
	}

	@Override
	public void paintBack(Graphics g) {
		BufferedImage background = View.createImage("src/images/background_breeding.png");
		g.drawImage(background,0,0,this);
		
	}

	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		return false;
	}

}
