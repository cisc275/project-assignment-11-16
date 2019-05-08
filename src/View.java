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
	public static final boolean NO_IMAGES = false;
	public static final boolean SPRITE_INFO = true;
	
	JFrame frame;
	static int frameWidth = 1080;
	static int frameHeight = 720;//1080;
	static Dimension windowSize = new Dimension(frameWidth, frameHeight);  //for setting window
	BufferedImage bird;
	static final String[] IMAGE_NAMES_2 = {};
	static final String[] IMAGE_NAMES = {"walkingbird", "standingbird", "brokenwingbird", "migratingbird", "earthworm", "grasshopper", "hawk", "raccoon", "pointerarea"};
	static final String[] DIRECTION_NAMES = {"right", "down", "left", "up"};
	HUD hud;
	int[] hudargs;
	/**
	 * Contains all of the actual images.
	 * images.get("Moveable.getImageName()")[direction][cycle num] 
	 */
	Map<String, BufferedImage[][]> images;
	/**
	 * Keeps track of the animation cycles of every Moveable.
	 * It uses weak references, so don't worry about memory leaks.
	 */
	Map<Moveable, Integer> picCycles;
	Collection <Moveable> moveables;

	int cameraOffX = 0;
	int cameraOffY = 0;
	
	static Dimension buttonSize = new Dimension(frameWidth*2/5, frameHeight-40);
	JPanel subpanel;
	JButton migrateButton;
	JButton stayButton;
	boolean migrate = false;
	boolean endMenu  = false;
	
	View() {
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
	
	/**
	 * sets up frame and button styles
	 * @author Anna
	 */
	private void buildFrame() {
		frame = new JFrame();
		frame.add(this);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Killdeer Simulator");
	
		
		
		ImageIcon migrateIcon = new ImageIcon("./images/bird.png");
		ImageIcon  stayIcon = new ImageIcon("./images/bird.png");
		migrateButton = new JButton("MIGRATE", migrateIcon); 
		stayButton = new JButton("STAY", stayIcon); 
		migrateButton.addActionListener(someactionevent -> {removeMenu(); migrate = true; endMenu = true;});
		stayButton.addActionListener(someactionevent -> {removeMenu(); endMenu = true;});
		migrateButton.setPreferredSize(buttonSize);
		stayButton.setPreferredSize(buttonSize); //must be pref size
		Insets insets1 = this.getInsets();
		stayButton.setBounds(150 + insets1.left, 15 + insets1.top,
				buttonSize.width + 50, buttonSize.height + 20);
		
		frame.setVisible(true); //NOTE: must put all in frame before setVisible
		Insets insets = frame.getInsets();
		//set the frame size to fit the panel
		frame.setSize(frameWidth + insets.left + insets.right, frameHeight + insets.top + insets.bottom);
		System.out.println( insets.left+ "+"+ insets.right+ "+" +insets.top+ "+" +insets.bottom);
		
		this.setFocusable(true);
	}
	
	public void removeMenu() {
		if (subpanel != null) {
			this.remove(subpanel);
			subpanel = null;
		}
	}
	
	public void setHUD(HUD inhud) {
		hud = inhud;
	}
	
	/**
	 * called from outside (Controller) to add/show buttons at start
	 */
	public void buildMenu() {
		subpanel = new JPanel();
		subpanel.add(migrateButton);
		subpanel.add(stayButton);
		this.add(subpanel);
		frame.setVisible(true);
	}

	public void paint(Graphics g) {
		if (hud != null)
			 hud.paintBack(g);
		
		if(subpanel != null) {
			subpanel.paint(g);
		}
		
		for(Moveable m : moveables) {
			int sx = m.getX() - cameraOffX;
			int sy = m.getY() - cameraOffY;
			BufferedImage img = getImage(m);
			if (img == null || NO_IMAGES) {
				g.fillOval(sx-m.getRadius(), sy-m.getRadius(), m.getRadius()*2, m.getRadius()*2);
			} else {
				g.drawImage(img, sx-img.getWidth()/2, sy-img.getHeight()/2, this);
			}
			if (SPRITE_INFO) {
				g.drawString(m.getImageName(), sx+m.getRadius()+3, sy);
			}
		}
		//System.out.println(hud);
		if (hud != null)
			hud.paint(g, hudargs);
	}
	
	void update(Collection<Moveable> moveables, int[] hudargs) {
		this.moveables = moveables;
		this.hudargs = hudargs;
		frame.repaint();
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
	
	public static BufferedImage createImage(String sauce) {
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
	
	/**
	 * create image for the background
	 * @param type
	 * @return
	 */
	/*private BufferedImage createImages(String type) {
		BufferedImage bufferedImage;
		try {
			bufferedImage = ImageIO.read(new File("/src/images/"+type+".png"));
			return bufferedImage;
		} catch (IOException e) {
			System.out.println(type+" could not be found");
			//e.printStackTrace();
		}
		return null;
	}*/
	
	/**
	 * Pulls from IMAGE_NAMES.
	 * IMPORTANT: You do not need to specify how many sprites are in each animation loop!
	 * The code uses the image file's height and width to determine automatically. For example, if the image is 100x400, it will make 4 sprites of size 100x100.
	 * <ul><li>Each sprite on the sheet must be square.</li>
	 * <li>Each sprite must be arranged on the sheet in a single row from left to right.</li></ul>
	 * @author Prescott
	 */
	private void createImages() {
		images = new HashMap<String, BufferedImage[][]>();
		for (String nom : IMAGE_NAMES) {
			BufferedImage[][] currmatrix = new BufferedImage[DIRECTION_NAMES.length][];
			for (int i = 0; i < DIRECTION_NAMES.length; i++) {
				BufferedImage sheet = createImage("./images/"+nom+"-"+DIRECTION_NAMES[i]+".png");
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
	
	/**
	 * This does animation cycles and facing automatically, so don't worry about that.
	 * @param m The moveable that needs its sprite retrieved.
	 * @return A BufferedImage representing that moveable.
	 * @author Prescott
	 */
	BufferedImage getImage(Moveable m) {
		//if (m instanceof WalkingBird)
			//System.out.println((m.getFacing() + Math.PI*2) % (Math.PI*2));
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
	 * @param theta The angle in radians.
	 * @return An integer representing the index of the facing (0=right, 1=down, 2=left, 3=up)
	 */
	static int angleToFaceIndex(double theta) {
		theta = (theta + Math.PI*2) % (Math.PI*2);
		if (theta <= Math.PI/4)
			return 0;
		else if (theta < Math.PI*3/4)
			return 1;
		else if (theta <= Math.PI*5/4)
			return 2;
		else if (theta < Math.PI*7/4)
			return 3;
		else
			return 0;
	}
}