import java.awt.Graphics;

/**
 * 
 * @author Prescott
 *
 */
public interface HUD {
	/**
	 * 
	 * @param g The graphics object on which to be painted.
	 * @param args The arguments to be passed in from the Model. Varies from HUD to HUD.
	 */
	public void paint(Graphics g, int[] args);

	/**
	 * i'm gonna fix that spaghetti -Prescott
	 * @param frameWidth
	 * @param frameHeight
	 * @return
	 * @deprecated
	 */
	public HUD nextHUD(int fw, int fh);
	public void paint2(Graphics g);
}
