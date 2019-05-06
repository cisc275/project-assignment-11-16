import java.awt.Graphics;

public class BreedingHUD implements HUD {
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

}
