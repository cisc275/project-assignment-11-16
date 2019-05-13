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
	//public HUD nextHUD(int fw, int fh);
	
	/**
	 * Paint the background image for each view
	 * @param g
	 * @param args
	 * @author wenkiliang
	 */
	public void paintBack(Graphics g, int[] args);
}
