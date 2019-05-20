import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import javax.imageio.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.*;


@SuppressWarnings("serial")
class View extends JPanel {
	public static final String IMAGE_PATH = "./images/";
	
	public static final boolean NO_IMAGES = false;
	public static final boolean SPRITE_INFO = false;
	
	JFrame frame;
	JFrame endFrame;
	static Dimension computerScreen = Toolkit.getDefaultToolkit().getScreenSize(); //for setting window
	static int taskBarSize = 40;
	static int frameWidth = (int) computerScreen.getWidth();
	static int frameHeight = (int) computerScreen.getHeight()- taskBarSize;
	static Dimension windowSize = new Dimension(frameWidth, frameHeight);
			
	BufferedImage bird;
	//final String[] IMAGE_NAMES_STATIC = {"nest", "rock1", "rock2", "grass1", "grass2", "grass3", "grass4", "grass5"};
	static final String[] IMAGE_NAMES_ANIMATED = {"walkingbird", "standingbird", "brokenwingbird", "migratingbird", "earthworm", "beetle", "grasshopper2", "grasshopper", 
												"hawk", "raccoon", "pointerarea", "gust", "bag", "nest", "cloud1", "cloud2", "cloud3", "migratingbird-powerup", "migratingbird-powerdown",
												"mousedefault", "mouserightclick", "mouseleftclick","mouserighthold", "arrow"};
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
	
	static Dimension buttonSize = new Dimension(frameWidth*2/5, frameHeight-50);
	JPanel subpanel;
	JPanel endSubpanel;
	JButton migrateButton;
	JButton stayButton;
	JButton restartButton;
	boolean migrate = false;
	boolean endMenu  = false;
	boolean restart = false;
	//boolean quizTime = false;

	int quizInput;
	

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
		Insets insets1 = this.getInsets();
	
		ImageIcon migrateIcon = new ImageIcon("./images/button-migrate.png");
		ImageIcon stayIcon = new ImageIcon("./images/button-stay.png");

		migrateButton = buildButton("MIGRATE", someactionevent -> {removeMenu(); migrate = true; endMenu = true;}); 
		stayButton = buildButton("STAY", someactionevent -> {removeMenu(); endMenu = true;}); 

		frame.setVisible(true); //NOTE: must put all in frame before setVisible
		
		stayButton.setOpaque(false);
		migrateButton.setOpaque(false);
		
		Insets insets = frame.getInsets();
		//set the frame size to fit the panel
		
		frame.setSize(frameWidth + insets.left + insets.right, frameHeight + insets.top + insets.bottom);
		
		frame.setVisible(true); //NOTE: must put all in frame before setVisible
		this.setFocusable(true);
	}
	
	private JButton buildButton(String buttonStyle, ActionListener al) {
		ImageIcon myIcon = new ImageIcon(".images/button-" + buttonStyle + ".png");
		JButton myButton = new JButton(myIcon);
		
		//JButton myButton = new JButton(buttonStyle);
		
		myButton.addActionListener(al);
		myButton.setPreferredSize(buttonSize);
		myButton.setOpaque(false);
		
		return myButton;
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
	 *@author ZachC
	 */
	public void buildQuiz(Quiz quiz) {

		Object[] quizAnswers = quiz.getQuizAnswers();
		int answer = JOptionPane.showOptionDialog(null, quiz.getQuestion(), "Quiz Time!", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, quizAnswers, quiz.getCorrectAnswer());
		if(answer == -1) {
			JOptionPane.showOptionDialog(null, "Can't exit quiz", "Answer the question", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);
			buildQuiz(quiz);
		}else if(answer == quiz.getCorrectAnswer()) {
			JOptionPane.showOptionDialog(null, "CORRECT!", "Good Job", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
		}else {
			JOptionPane.showOptionDialog(null, "SORRY, INCORRECT.", "Try Again", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
			buildQuiz(quiz);
		}

	}
	
	/**
	 * called to open popup stating lost game, try again.
	 * specifically says lost all eggs, but can make more general later
	 * @author Anna
	 */
	public void restartGamePrompt() {
		JOptionPane.showOptionDialog(null, "Lost all eggs \nRetry game?", "Lost Game", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
	}

	public void paint(Graphics g) {
		
		if(subpanel != null) {
			subpanel.paint(g);
		}
		if (hud != null) {
			 hud.paintBack(g, hudargs, cameraOffX, cameraOffY);
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
				g.drawString(m.getImageName() + angleToFaceIndex(m.getFacing()), sx+m.getRadius()+3, sy);
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
	
	static Font loadFont(String fontName, float size) {
		String fontFileName = "./fonts/" + fontName + ".ttf";
		try {
			InputStream is = new FileInputStream(fontFileName);
			Font tempFont = Font.createFont(Font.TRUETYPE_FONT, is);
			return tempFont.deriveFont(size);
		} catch (IOException | FontFormatException e) {
			System.out.println("font " + fontName + " not found");
			return null;
		} 
	}
	
	void resetCamera() {
		cameraOffX = 0;
		cameraOffY = 0;
	}
	
	void moveCamera(int centerX, int centerY, int maxWidth, int maxHeight) {
		cameraOffX = Math.max(Math.min(centerX - frameWidth/2, maxWidth - frameWidth), 0);
		cameraOffY = Math.max(Math.min(centerY - frameHeight/2, maxHeight - frameHeight), 0);
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
			//TODO bluh
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
	/**
	 * pass in time elapsed and time limit to find current frame of sprite </br>
	 * returns int
	 * @author - kelly
	 */
	static int getFrame(BufferedImage[] imgs, int over, int under) {
		int a = imgs.length;
		float percent = (float) over/under;
		int frame = (int) (a * percent);
		return frame; //int conversion truncates so that it won't go beyond array range

	}
/*
	public void addPropertyChangeListener(JOptionPane j, Controller c) {
		quizPane.addPropertyChangeListener(c);
	}
	*/
}