import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;


@SuppressWarnings("serial")
class View extends JPanel {
	public static final boolean NO_IMAGES = true;
	
	JFrame frame;
	static int frameWidth = 500;
	static int frameHeight = 400;
	static Dimension windowSize  = new Dimension(frameWidth, frameHeight);  //for setting window
	BufferedImage bird;
	static final String[] IMAGE_NAMES = {"bird", "enemy", "gust", "food"};
	Map<String, BufferedImage> images; 
	Collection <Moveable> moveables;
	Collection <MenuObject> menuObjects;
	int cameraOffX = 0;
	int cameraOffY = 0;
	
	
	View(){
		if (!NO_IMAGES)
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
		frame.setTitle("Killdeer Simulator");
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
				int sx = m.getX() - cameraOffX;
				int sy = m.getY() - cameraOffY;
				if (NO_IMAGES)
					g.fillOval(sx-m.getRadius(), sy-m.getRadius(), m.getRadius()*2, m.getRadius()*2);
				else
					g.drawImage(this.getImage(m), sx, sy, this);
			}
			for(MenuObject m : menuObjects) {
				g.drawRect(m.getX(), m.getY(), m.getWidth(), m.getHeight());
				g.drawString(m.getText(), m.getX(), m.getY()+m.getHeight()/2);
			}

		} catch (NullPointerException e) {}
	}
	
	void update(Collection<Moveable> moveables, Collection<MenuObject> menuObjects) {
		this.moveables = moveables;
		this.menuObjects = menuObjects;
		frame.repaint();
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	void resetCamera() {
		cameraOffX = 0;
		cameraOffY = 0;
	}
	
	void moveCamera(int centerX, int centerY) {
		cameraOffX = centerX - frameWidth/2;
		cameraOffY = centerY - frameHeight/2;
	}
	
	int actualX(int clickx) {
		return clickx + cameraOffX;
	}
	
	int actualY(int clicky) {
		return clicky + cameraOffY;
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
		images = new HashMap<String, BufferedImage>();
		for (String nom : IMAGE_NAMES) {
			images.put(nom, createImage("/images/"+nom+".png"));
		}
	}
	
	//make an id or something idk
	BufferedImage getImage(Moveable m) {
		return images.get("bird");
	}
	
	public void addControllerToMouse(Controller controller) {
		this.addMouseListener(controller);
		this.addMouseMotionListener(controller);
	}
}