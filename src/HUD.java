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
	 * Paint the background image for each view
	 * @param g
	 * @param args
	 * @param cameraX
	 * @param cameraY
	 * @author wenkiliang
	 */
	public void paintBack(Graphics g, int[] args, int cameraX, int cameraY);
}
 