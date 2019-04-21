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
	static int frameWidth = 2040;
	static int frameHeight = 1080;
	static Dimension windowSize  = new Dimension(frameWidth, frameHeight);  //for setting window
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
	Collection <MenuObject> menuObjects;
	int cameraOffX = 0;
	int cameraOffY = 0;
	boolean Migrate = false;
	
	
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
	
	@SuppressWarnings("unused")
	public void paint(Graphics g) {

		if(Migrate) {
			BufferedImage background = createImageBG() ;
			g.drawImage(background,0,0,this);
			
		}
		for(Moveable m : moveables) {
			if(Migrate) {
				int sx = m.getX() - cameraOffX;
				int sy = m.getY() - cameraOffY;
				BufferedImage imgEnemy = createImageEnemy();
				BufferedImage imgGust = createImageGust();
				BufferedImage imgBird = createImageBird();
				//System.out.println(img);
				if(m instanceof Enemy) {
				if (imgEnemy == null || NO_IMAGES) {
					g.fillOval(sx-m.getRadius(), sy-m.getRadius(), m.getRadius()*2, m.getRadius()*2);
				} else {
					g.drawImage(imgEnemy, sx-imgEnemy.getWidth()/2, sy-imgEnemy.getHeight()/2, this);
				}
				if (SPRITE_INFO) {
					g.drawString(m.getImageName(), sx+m.getRadius()+3, sy);
				}
				}
				else if(m instanceof MigratingBird) {
					if (imgBird == null || NO_IMAGES) {
						g.fillOval(sx-m.getRadius(), sy-m.getRadius(), m.getRadius()*2, m.getRadius()*2);
					} else {
						g.drawImage(imgBird, sx-imgBird.getWidth()/2, sy-imgBird.getHeight()/2, this);
					}
					if (SPRITE_INFO) {
						g.drawString(m.getImageName(), sx+m.getRadius()+3, sy);
					}
					}
				else if(m instanceof Gust) {
					if (imgGust == null || NO_IMAGES) {
						g.fillOval(sx-m.getRadius(), sy-m.getRadius(), m.getRadius()*2, m.getRadius()*2);
					} else {
						g.drawImage(imgGust, sx-imgGust.getWidth()/2, sy-imgGust.getHeight()/2, this);
					}
					if (SPRITE_INFO) {
						g.drawString(m.getImageName(), sx+m.getRadius()+3, sy);
					}
					}
			}
			else {
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
		for(MenuObject m : menuObjects) {
			g.drawRect(m.getX(), m.getY(), m.getWidth(), m.getHeight());
			g.drawString(m.getText(), m.getX(), m.getY()+m.getHeight()/2);
		}
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
	private BufferedImage createImageBG() {
		BufferedImage bufferedImage;
		try {
			bufferedImage = ImageIO.read(new File("src/images/migrationbackground.png"));
			return bufferedImage;
		} catch (IOException e) {
			System.out.println("background could not be found");
			//e.printStackTrace();
		}
		return null;
	}
	private BufferedImage createImageEnemy() {
		BufferedImage bufferedImage;
		try {
			bufferedImage = ImageIO.read(new File("src/images/enemy.png"));
			return bufferedImage;
		} catch (IOException e) {
			System.out.println("enemy could not be found");
			//e.printStackTrace();
		}
		return null;
	}
	private BufferedImage createImageGust() {
		BufferedImage bufferedImage;
		try {
			bufferedImage = ImageIO.read(new File("src/images/gust.png"));
			return bufferedImage;
		} catch (IOException e) {
			System.out.println("gust could not be found");
			//e.printStackTrace();
		}
		return null;
	}
	
	private BufferedImage createImageBird() {
		BufferedImage bufferedImage;
		try {
			bufferedImage = ImageIO.read(new File("src/images/bird.png"));
			return bufferedImage;
		} catch (IOException e) {
			System.out.println("bird could not be found");
			//e.printStackTrace();
		}
		return null;
	}
	private void createImages() {
		images = new HashMap<String, BufferedImage[][]>();
		for (String nom : IMAGE_NAMES) {
			BufferedImage[][] currmatrix = new BufferedImage[DIRECTION_NAMES.length][];
			for (int i = 0; i < DIRECTION_NAMES.length; i++) {
				BufferedImage sheet = createImage("src/images/"+nom+"-"+DIRECTION_NAMES[i]+".png");
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