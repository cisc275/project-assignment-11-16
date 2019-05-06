import java.awt.Graphics;

/**
 * 
 * @author Prescott
 *
 */
public class EatingHUD implements HUD {
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
}
