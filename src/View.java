import java.io.*;
import java.util.ArrayList;
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
	public static final String IMAGE_PATH = "./images/";
	
	public static final boolean NO_IMAGES = false;
	public static final boolean SPRITE_INFO = true;
	
	JFrame frame;
	static Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize(); //for setting window

	static int frameWidth = (int) windowSize.getWidth();
	static int frameHeight = (int) windowSize.getHeight();
	BufferedImage bird;
	//final String[] IMAGE_NAMES_STATIC = {"nest", "rock1", "rock2", "grass1", "grass2", "grass3", "grass4", "grass5"};
	static final String[] IMAGE_NAMES_ANIMATED = {"walkingbird", "standingbird", "brokenwingbird", "migratingbird", "earthworm", "beetle", "grasshopper", 
												"hawk", "raccoon", "pointerarea", "gust", "bag", "nest", "cloud1", "cloud2", "cloud3", "migratingbird-powerup", "migratingbird-powerdown"};
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
	Collection <Moveable> moveables = new ArrayList<Moveable>();

	int cameraOffX = 0;
	int cameraOffY = 0;
	

	static Dimension answerSize = new Dimension(frameWidth,frameHeight/3); //gotta figure out a good size
	static Dimension buttonSize = new Dimension(frameWidth*2/5, frameHeight-50);
	JPanel subpanel;
	JButton migrateButton;
	JButton stayButton;
	JButton qA1Button;
	JButton qA2Button;
	JButton qA3Button;
	boolean migrate = false;
	boolean endMenu  = false;
	boolean quizTime = false;
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
	 * @author Anna and Zach
	 */
	private void buildFrame() {
		frame = new JFrame();
		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Killdeer Simulator");
		Insets insets1 = this.getInsets();
	

		migrateButton = new JButton("MIGRATE"); 
		stayButton = new JButton("STAY"); 
		migrateButton.addActionListener(someactionevent -> {removeMenu(); migrate = true; endMenu = true;});
		stayButton.addActionListener(someactionevent -> {removeMenu(); endMenu = true;});
		migrateButton.setPreferredSize(buttonSize);
		stayButton.setPreferredSize(buttonSize); //must be pref size
		qA1Button = new JButton("Answer A");
		qA2Button = new JButton("Answer B");
		qA3Button = new JButton("Answer C");
		qA1Button.addActionListener(someactionevent -> {System.out.print("fuck");removeMenu(); endMenu = true; quizTime = false;});
		qA2Button.addActionListener(someactionevent -> {removeMenu(); endMenu = true; quizTime = false;});
		qA3Button.addActionListener(someactionevent -> {removeMenu(); endMenu = true; quizTime = false;});
		//qA1Button.setPreferredSize(answerSize);
		//qA2Button.setPreferredSize(answerSize);
		//qA3Button.setPreferredSize(answerSize);
		frame.setVisible(true); //NOTE: must put all in frame before setVisible
		stayButton.setBounds(150 + insets1.left, 15 + insets1.top, buttonSize.width + 50, buttonSize.height + 20);
		stayButton.setOpaque(false);
		migrateButton.setOpaque(false);
		Insets insets = frame.getInsets();
		//set the frame size to fit the panel
		frame.setSize(frameWidth + insets.left + insets.right, frameHeight + insets.top + insets.bottom);
		
		frame.setVisible(true); //NOTE: must put all in frame before setVisible
		this.setFocusable(true);
	}
	
	void addControllerToButton(Controller c){
		qA1Button.addActionListener(c);
		qA2Button.addActionListener(c);
		qA3Button.addActionListener(c);
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
		subpanel.add(stayButton);
		subpanel.add(migrateButton);
		subpanel.setOpaque(false);
		this.add(subpanel);
		frame.setVisible(true);
	}
	/**
	 * called from outside (Controller) to add/show quiz at breeding game
	 *
	 */
	public void buildQuiz() {
		subpanel = new JPanel();
		subpanel.add(qA1Button);
		subpanel.add(qA2Button);
		subpanel.add(qA3Button);
		this.add(subpanel);
		frame.setVisible(true);	
	}

	public void paint(Graphics g) {
		
		if(subpanel != null) {
			subpanel.paint(g);
		}
		if (hud != null) {
			 hud.paintBack(g,hudargs);
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
		if (hud != null)
			hud.paint(g, hudargs);
	}
	
	void update(Collection<Moveable> moveables, int[] hudargs) {
		this.moveables = (moveables != null) ? moveables : new ArrayList<Moveable>();
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
	 * Pulls from IMAGE_NAMES.
	 * IMPORTANT: You do not need to specify how many sprites are in each animation loop!
	 * The code uses the image file's height and width to determine automatically. For example, if the image is 100x400, it will make 4 sprites of size 100x100.
	 * <ul><li>Each sprite on the sheet must be square.</li>
	 * <li>Each sprite must be arranged on the sheet in a single row from left to right.</li></ul>
	 * @author Prescott
	 */
	private void createImages() {
		images = new HashMap<String, BufferedImage[][]>();
		for (String nom : IMAGE_NAMES_ANIMATED) {
			BufferedImage[][] currmatrix = new BufferedImage[DIRECTION_NAMES.length][];
			for (int i = 0; i < DIRECTION_NAMES.length; i++) {
				BufferedImage sheet = createImage(IMAGE_PATH+nom+"-"+DIRECTION_NAMES[i]+".png");
				if (sheet != null) {
					int subsize = sheet.getHeight(); //consider splitting this into another method
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
		//for (String nom : IMAGE_NAMES_STATIC) { //idk how to do this, but make a nondirectional spritesheet (kelly)
			//BufferedImage myImg = createImage("./images/" + nom + ".png");
		//}
	}
	
	/**
	 * This does animation cycles and facing automatically, so don't worry about that.
	 * @param m The moveable that needs its sprite retrieved.
	 * @return A BufferedImage representing that moveable.
	 * @author Prescott
	 */
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