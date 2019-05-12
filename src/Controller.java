import java.awt.event.*;
//import java.util.Collection;


public class Controller implements MouseMotionListener, MouseListener {
	// if i update this it updates that
	private Model model;
	private View view;
	private GameSequence sequence;
	
	public Controller(){
		view = new View();
		view.addControllerToMouse(this);
		
		sequence = new GameSequence(view.getFrameWidth(), view.getFrameHeight(),view.migrate);
		//startEating();
		//startMigrating();
		//startBreeding();
		
	}

	//change to build differently depending on boolean migrate in View
	public void startMainMenu() {
		model = new Menu(view.getFrameWidth(), view.getFrameHeight());
		view.hud = new MenuHUD(view.getFrameWidth(), view.getFrameHeight());
		view.buildMenu();
	}
/*	
	public void startEating() {
		model = new EatingModel(view.getFrameWidth(), view.getFrameHeight());
		view.setHUD(new EatingHUD(view.getFrameWidth(), view.getFrameHeight()));
	}
	
	public void startEatingTutorial() {
		model = new EatingTutorial(view.getFrameWidth(), view.getFrameHeight());
		view.setHUD(null);
	}
	
	*/
	/*public void startMigrating() {
		model = new MigratingModel(view.getFrameWidth(), view.getFrameHeight(),view.migrate);
		view.setHUD(new MigratingHUD(view.getFrameWidth(), view.getFrameHeight()));
	}
	*/
	/*
	public void startBreeding() {
		model = new BreedingModel(view.getFrameWidth(), view.getFrameHeight());
		view.setHUD(new BreedingHUD(view.getFrameWidth(), view.getFrameHeight()));
	}
	*/
	
	/**
	 * for testing
	 * @return
	 */
	public String checkModel() {
		String string = "? not e, b, or m model ?";

		if (model instanceof EatingModel) {
			string = "currently EatingModel";
		} else if(model instanceof BreedingModel) {
			string = "currently BreedingModel";
		} else if(model instanceof MigratingModel) {
			string = "currently MigratingModel";
		}
		System.out.println(string);
		return string;
	}
	
	/**
	 * handles ticking: update model, update view 
	 * note to get rid of instance of later
	 */
	public void start() {
		startMainMenu();
		
		while (!view.endMenu) {
			view.update(model.getMoveables(), model.getHUDargs());
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		view.endMenu = false;
		sequence = new GameSequence(view.getFrameWidth(), view.getFrameHeight(), view.migrate);
		loadNextGame();
		boolean ended = false;
		while (!ended) {
			if (model instanceof EatingModel) {
				EatingModel eModel = (EatingModel) model;
				view.moveCamera(eModel.getBirdX(), eModel.getBirdY());
			} else {
				view.resetCamera();
			}
			
			view.update(model.getMoveables(), model.getHUDargs());
			model.update();
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if(model.endGame()) {
				if (sequence.atEnd()) {
					winGame();
					ended = true;
				} else {
					loadNextGame();
				}
			}
		}
		winGame();
	}
	
	public void loadNextGame() {
		sequence.advance();
		model = sequence.getModel();
		view.hud = sequence.getHUD();
		//model = model.nextModel(view.getFrameWidth(), view.getFrameHeight(), view.migrate);
		//view.hud = view.hud.nextHUD(view.getFrameWidth(), view.getFrameHeight());
	}
	
	private void winGame() {
		System.out.println("you won.");
	}
	
	public void mouseClicked(MouseEvent e) {

	}
	
	//mouse press and released in each of the sub models 
	public void mousePressed(MouseEvent e) {
		boolean rightClick = (e.getButton() == MouseEvent.BUTTON3);
		boolean leftClick = (e.getButton() == MouseEvent.BUTTON1);
		model.mousePressed(e.getX(), e.getY(), view.actualX(e.getX()), view.actualY(e.getY()), leftClick, rightClick);
	}
	
	public void mouseReleased(MouseEvent e) {
		model.mouseReleased();
	}
	
	public void mouseEntered(MouseEvent e) {
		
	}
	
	public void mouseExited(MouseEvent e) {
		
	}
	
	public void mouseDragged(MouseEvent e) {
		model.mouseDragged(e.getX(), e.getY(), view.actualX(e.getX()), view.actualY(e.getY()));
	}
	
	public void mouseMoved(MouseEvent e) {
			model.mouseMoved(e.getX(), e.getY());
	}
}