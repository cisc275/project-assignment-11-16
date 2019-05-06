import java.awt.Graphics;

public class MenuHUD implements HUD {
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

	@Override
	public HUD nextHUD(int fw, int fh) {
		return new EatingHUD(fw,fh);
	}

}
