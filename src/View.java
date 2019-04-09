import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseMotionListener;
import java.awt.image.*;




class View extends JPanel{
	JFrame frame;
	static int frameWidth = 300;
	static int frameHeight = 300;
	static Dimension windowSize  = new Dimension(frameWidth, frameHeight);  //for setting window
	BufferedImage bird;
	
	View(){
		this.buildFrame();
	}

	public int getFrameWidth() {
		return frameWidth;
	}
	public int getFrameHeight() {
		return frameHeight;
	}
	
	private void buildFrame() {
		frame = new JFrame();
		frame.getContentPane().add(this);
		//frame.setBackground(Color.gray);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(frameWidth, frameHeight);
		
		frame.setVisible(true); //NOTE: must put all in frame before setVisible
			 	
		frame.setSize(windowSize);
		frame.setMinimumSize(windowSize);
		frame.setMaximumSize(windowSize);
		this.setFocusable(true);
	}
	
	public void paint(Graphics g) {}
	void addControllerToMouse(MouseMotionListener m) {}
	void update(EatingBird b, List food) {}
	void update(MigratingBird b, List enemies, List gusts) {}
	void update(BreedingBird b, List predators, Nest n) {}
	JPanel generateQuiz() {
		JPanel panel = new JPanel();
		return panel;
	}
	void displayScore(int eScore, int mScore, int bScore) {}
	
	
	public void addControllerToMouse(Controller controller) {
		// TODO Auto-generated method stub
		this.addMouseListener(controller);
		this.addMouseMotionListener(controller);
	}

	
	
}