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
	BufferedImage[] chicks = new BufferedImage[3];
	static String ANNOUNCE_FONT_NAME = "maiandra";
	static float ANNOUNCE_FONT_SIZE = 64.0f;
	static String SCORE_FONT_NAME = "maiandra";
	static float SCORE_FONT_SIZE = 85.0f;
	Font scoreFont;
	Font announceFont;
	int score;
	
	public EndMenuHUD(int w, int h) {
		frameWidth = w;
		frameHeight = h;
		background = View.createImage(View.IMAGE_PATH+"background_ending_blank.png");
		announceFont = View.loadFont(ANNOUNCE_FONT_NAME, ANNOUNCE_FONT_SIZE);
		scoreFont = View.loadFont(SCORE_FONT_NAME, SCORE_FONT_SIZE);

		for (int i = 0; i < chicks.length; i++) {
			chicks[i] = View.createImage(View.IMAGE_PATH + "score-" + (i+1) + ".png");
		}
	}
	
	
	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		return false;
	}
	
	@Override
	public void paint(Graphics g, int[] args) {
		g.drawImage(chicks[View.getFrame(chicks, score, 1000)], 
				0, frameHeight - chicks[0].getHeight(), this);
		score = Controller.overallScore;
		//score = 580;
		g.setFont(announceFont);
		g.drawString("Congratulations!", 500, 300);
		g.setFont(scoreFont);
		g.drawString("You earned " + score + " points!" , 500, 370);
		g.setFont(announceFont);
		g.drawString("Click to restart", 500, 600);
	}
	
	@Override
	public void paintBack(Graphics g, int[] args, int cameraX, int cameraY) {
		g.drawImage(background, 0, 0, frameWidth, frameHeight, this);
		
	}
}
