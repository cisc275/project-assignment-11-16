import java.awt.Graphics;

public class MigratingHUD implements HUD {
	int frameHeight;
	int frameWidth;
	public MigratingHUD(int w, int h) {
		frameWidth = w;
		frameHeight = h;
	}
	
	/**
	 * args: <br>
	 * 	0. is migrating (1 = migrating, 0 = not migrating);<br>
	 *  1. current distance<br>
	 *  2. max distance
	 */
	public void paint(Graphics g, int[] args) {
		g.drawRect(frameWidth-200,frameHeight-200, 200, 200);  
	}

}
