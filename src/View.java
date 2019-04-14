import java.io.*;
import java.util.Collection;

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
	BufferedImage[] images; 
	Collection <Moveable> moveables; 

	
	View(){
		this.createImages();
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
	
	public void paint(Graphics g) {
		try {
			for(Moveable m : moveables) {
				g.fillOval(m.getX()-m.getRadius()/2, m.getY()-m.getRadius()/2, m.getRadius(), m.getRadius());
				//g.drawImage(this.getImage(m), m.getX(), m.getY(), this);
			}

		} catch (NullPointerException e) {}
	}
	void addControllerToMouse(MouseMotionListener m) {}
	
	void update(Collection <Moveable> moveables) {
		this.moveables = moveables;
		frame.repaint();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private BufferedImage createImage(String str) {
		BufferedImage bufferedImage;
		try {
			bufferedImage = ImageIO.read(new File(str));
			return bufferedImage;
		} catch (IOException e) {
			System.out.println(str);
			e.printStackTrace();
		}
		return null;
	}
	private void createImages() {
		images = new BufferedImage[5];
		images[0] = createImage("/images/bird.png");
		images[1] = createImage("/images/enemy.png");
		images[2] = createImage("/images/gust.png");
		images[3] = createImage("/images/food.png");
		images[4] = createImage("/images/food.png");
	}
	
	//make an id or smth idk
	BufferedImage getImage(Moveable m) {return images[0];}
/*
	void update(EatingBird b, List food) {}
	void update(MigratingBird b, List enemies, List gusts) {}
	void update(BreedingBird b, List predators, Nest n) {}
*/
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