import java.awt.Graphics;

public interface HUD {
	public void paint(Graphics g, int[] args);
	public HUD nextHUD(int fw, int fh);
}
