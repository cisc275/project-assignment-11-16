import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collection;
//import java.util.Collection;


public class Controller implements MouseMotionListener, MouseListener {
	// if i update this it updates that
	private Model model;
	private View view;
	
	
	public Controller(){
		view = new View();
		view.addControllerToMouse(this);
			
		//startMainMenu();
		startMigrating();
		//startBreeding();

	}

	//change to build differently depending on boolean migrate in View
	public void startMainMenu() {
		model = new Menu(view.getFrameWidth(), view.getFrameHeight());
		view.buildMenu();
	}
	
	public void startEating() {
		model = new EatingModel(view.getFrameWidth(), view.getFrameHeight());
	}
	
	public void startMigrating() {
		model = new MigratingModel(view.getFrameWidth(), view.getFrameHeight());
	}
	
	public void startBreeding() {
		model = new BreedingModel(view.getFrameWidth(), view.getFrameHeight());
	}


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
	 */
	public void start() {
		while (true) {
			
			if(model instanceof Menu && view.endMenu == true) {
				startEating();
			}

			if (model instanceof EatingModel) {
				EatingModel eModel = (EatingModel) model;
				view.moveCamera(eModel.getBirdX(), eModel.getBirdY());
			} else {
				view.resetCamera();
			}
			
			view.update(model.getMoveables());
			model.update();
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void mouseClicked(MouseEvent e) {

	}
	
	public void mousePressed(MouseEvent e) {
		if (model instanceof EatingModel) {
			EatingModel eModel = (EatingModel) model;
			eModel.setDestination(view.actualX(e.getX()), view.actualY(e.getY()));
			//System.out.println(e.getX() + ", " + e.getY());
		} else if (model instanceof BreedingModel) {
			BreedingModel bModel = (BreedingModel) model;
			if (e.getButton() == MouseEvent.BUTTON1) {
				//if left click, move
				bModel.setDestination(e.getX(), e.getY());
			}
			else if (e.getButton() == MouseEvent.BUTTON3) {
				// if right click, show broken wing
				bModel.startBrokenWing();
			}
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		if (model instanceof BreedingModel) {
			BreedingModel bModel = (BreedingModel) model;
			bModel.stopBrokenWing();
			//should reset the bird
		}
	}
	
	public void mouseEntered(MouseEvent e) {
		
	}
	
	public void mouseExited(MouseEvent e) {
		
	}
	
	public void mouseDragged(MouseEvent e) {
		if (model instanceof EatingModel) {
			EatingModel eModel = (EatingModel) model;
			eModel.setDestination(view.actualX(e.getX()), view.actualY(e.getY()));
		} else if (model instanceof BreedingModel) {
			BreedingModel bModel = (BreedingModel) model;
			bModel.setDestination(e.getX(), e.getY());
		}
	}
	
	public void mouseMoved(MouseEvent e) {
		if (model instanceof MigratingModel) {
			MigratingModel mModel = (MigratingModel) model;
			mModel.setDestination(e.getX(), e.getY());
		}
	}
}