import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import javax.imageio.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;


@SuppressWarnings("serial")
class View extends JPanel {
	public static final boolean NO_IMAGES = true;
	public static final boolean SPRITE_INFO = true;
	
	JFrame frame;
	static int frameWidth = 1080;
	static int frameHeight = 720;
	static Dimension windowSize  = new Dimension(frameWidth, frameHeight);  //for setting window
	static Dimension buttonSize = new Dimension(frameWidth*2/5, frameHeight);
	BufferedImage bird;
	static final String[] IMAGE_NAMES = {"walkingbird", "standingbird", "migratingbird", "earthworm", "grasshopper", "hawk"};
	static final String[] DIRECTION_NAMES = {"right", "up", "left", "down"};
	/**
	 * I'll leave it up to you if you want to do 4 or 8 directions.
	 * images.get("name")[direction][cycle num] 
	 */
	Map<String, BufferedImage[][]> images;
	Map<Moveable, Integer> picCycles;
	Collection <Moveable> moveables;
	int cameraOffX = 0;
	int cameraOffY = 0;
	boolean Migrate = false;
	
	
	JPanel subpanel;
	JButton b1;
	JButton b2;
	boolean migrate = false;
	boolean endMenu  = false;

	
	View(){
		if (!NO_IMAGES) {
			this.createImages();
		}
		picCycles = new WeakHashMap<Moveable, Integer>();
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
		

		//frame.setBackground(Color.RED);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Killdeer Simulator");
		frame.setSize(frameWidth, frameHeight);
		
		b1 = new JButton("PUSH ME"); 
		b2 = new JButton("PUSH ME2"); 
		b1.addActionListener(someactionevent -> {removeMenu(); migrate = true; endMenu = true;});
		b2.addActionListener(someactionevent -> {removeMenu(); endMenu = true;});
		b1.setPreferredSize(buttonSize);
		b2.setPreferredSize(buttonSize); //must be pref size
		
		frame.getContentPane().add(this);
		frame.setVisible(true); //NOTE: must put all in frame before setVisible
			 	
		frame.setSize(windowSize);
		frame.setMinimumSize(windowSize);
		frame.setMaximumSize(windowSize);
		this.setFocusable(true);
	}
	
	public void removeMenu() {
		if (subpanel != null) {
			this.remove(subpanel);
			subpanel = null;
		}
	}
	
	public void buildMenu() {
		removeMenu();
		subpanel = new JPanel();
		subpanel.add(b1);
		subpanel.add(b2);
		this.add(subpanel);

		//panel.setOpaque(true);
		//panel.setBackground(Color.GREEN);
	}
	
	@SuppressWarnings("unused")
	public void paint(Graphics g) {
		
		if (subpanel != null) {
			subpanel.paint(g); //needed to show panels within view
		}

		for(Moveable m : moveables) {
			int sx = m.getX() - cameraOffX;
			int sy = m.getY() - cameraOffY;
			BufferedImage img = getImage(m);
			//System.out.println(img);
			if (img == null || NO_IMAGES) {
				g.fillOval(sx-m.getRadius(), sy-m.getRadius(), m.getRadius()*2, m.getRadius()*2);
			} else {
				g.drawImage(img, sx-img.getWidth()/2, sy-img.getHeight()/2, this);
			}
			if (SPRITE_INFO) {
				g.drawString(m.getImageName(), sx+m.getRadius()+3, sy);
			}
		}

	}
	
	void update(Collection<Moveable> moveables) {

		frame.repaint();
		this.moveables = moveables;

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
	
	private BufferedImage createImage(String sauce) {
		BufferedImage bufferedImage;
		try {
			bufferedImage = ImageIO.read(new File(sauce));
			return bufferedImage;
		} catch (IOException e) {
			System.out.println(sauce + " could not be found");
			//e.printStackTrace();
		}
		return null;
	}
 
	private BufferedImage createImages(String type){//create image for the background
		BufferedImage bufferedImage;
		try {
			bufferedImage = ImageIO.read(new File("/src/images/"+type+".png"));
			return bufferedImage;
		} catch (IOException e) {
			System.out.println(type+" could not be found");
			//e.printStackTrace();
		}
		return null;
	}
	private void createImages() {
		images = new HashMap<String, BufferedImage[][]>();
		for (String nom : IMAGE_NAMES) {
			BufferedImage[][] currmatrix = new BufferedImage[DIRECTION_NAMES.length][];
			for (int i = 0; i < DIRECTION_NAMES.length; i++) {
				BufferedImage sheet = createImage("/src/images/"+nom+"-"+DIRECTION_NAMES[i]+".png");
				if (sheet != null) {
					int subsize = sheet.getHeight();
					int numSprites = sheet.getWidth() / subsize;
					currmatrix[i] = new BufferedImage[numSprites];
					for (int j = 0; j < numSprites; j++) {
						//System.out.println(nom+"-"+DIRECTION_NAMES[i]+" "+j+" ("+subsize*j+"+"+subsize+")/"+sheet.getWidth());
						try {
							currmatrix[i][j] = sheet.getSubimage(subsize*j, 0, subsize-1, subsize);
							//System.out.println(nom+"-"+DIRECTION_NAMES[i]+" "+j+" ("+subsize*j+"+"+subsize+")/"+sheet.getWidth() + " completed");
						} catch (RasterFormatException e) {
							currmatrix[i][j] = null;
							System.out.println(nom+"-"+DIRECTION_NAMES[i]+" "+j+" ("+subsize*j+"+"+subsize+")/"+sheet.getWidth() + " failed");
							System.out.println(e);
						}
					}
				} else {
					currmatrix[i] = null;
				}
			}
			images.put(nom, currmatrix);
		}
	}
	
	BufferedImage getImage(Moveable m) {
		try {
			BufferedImage[] row = images.get(m.getImageName())[angleToFaceIndex(m.getFacing())];
			Integer Dex = picCycles.get(m);
			int dex;
			if (Dex != null) {
				dex = Dex.intValue();
				dex = dex % row.length;
			} else {
				dex = 0;
			}
			picCycles.put(m, dex+1);
			return row[dex];
		} catch (NullPointerException e) {
			//System.out.println("nullpointer");
			return null;
		} catch (IndexOutOfBoundsException e) {
			//System.out.println("index");
			return null;
		}
	}
	
	public void addControllerToMouse(Controller controller) {
		this.addMouseListener(controller);
		this.addMouseMotionListener(controller);
	}
	
	/**
	 * 
	 * @param theta The angle in radians
	 * @return An integer representing the index of the facing (0=right, 1=up, 2=left, 3=down)
	 */
	static int angleToFaceIndex(double theta) {
		theta = (theta + Math.PI*2) % (Math.PI*2);
		if (theta <= Math.PI/4)
			return 0;
		else if (theta < Math.PI*3/4)
			return 3;
		else if (theta <= Math.PI*5/4)
			return 2;
		else if (theta < Math.PI*7/4)
			return 1;
		else
			return 0;
	}
}