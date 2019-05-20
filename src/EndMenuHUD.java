import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

/*
 * display end score
 * 
 * @author Kelly
 */

public class EndMenuHUD implements HUD, ImageObserver {
	int frameHeight;
	int frameWidth;
	BufferedImage background;
	static String ANNOUNCE_FONT_NAME = "maiandra";
	static float ANNOUNCE_FONT_SIZE = 36.0f;
	static String SCORE_FONT_NAME = "arial_rounded";
	static float SCORE_FONT_SIZE = 64.0f;
	Font scoreFont;
	Font announceFont;
	
	public EndMenuHUD(int w, int h) {
		frameWidth = w;
		frameHeight = h;
		background = View.createImage(View.IMAGE_PATH+"background_ending_blank.png");
		announceFont = View.loadFont(ANNOUNCE_FONT_NAME, ANNOUNCE_FONT_SIZE);
		scoreFont = View.loadFont(SCORE_FONT_NAME, SCORE_FONT_SIZE);
	}
	
	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		return false;
	}
	
	@Override
	public void paint(Graphics g, int[] args) {
		g.setFont(scoreFont);
		g.drawString("blah", 500, 500);
	}
	
	@Override
	public void paintBack(Graphics g, int[] args, int cameraX, int cameraY) {
		int drawWidth = frameHeight*background.getWidth()/background.getHeight();
		g.drawImage(background, frameWidth/2-drawWidth/2, 0, drawWidth, frameHeight, this);
		
	}
}
