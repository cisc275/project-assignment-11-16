import java.awt.event.*;
//import java.util.Collection;


public class Controller implements MouseMotionListener,MouseListener{
	// if i update this it updates that
	private Model model;
	private View view;
	
	public Controller(){
		view = new View();
		view.addControllerToMouse(this);
		
		//model = bModel;		
		startMainMenu();
	}
	
	public void startMainMenu() {
		model = new MainMenu(view.getFrameWidth(), view.getFrameHeight(), this);
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
		if (model instanceof Menu) {
			string = "currently a menu";
		} else if (model instanceof EatingModel) {
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
			//increment the x and y coordinates, alter direction if necessary
			model.update();
			if (model instanceof EatingModel) {
				EatingModel eModel = (EatingModel) model;
				view.moveCamera(eModel.getBirdX(), eModel.getBirdY());
			} else {
				view.resetCamera();
			}
			view.update(model.getMoveables(), model.getMenuObjects());

		}
	}

	
	@Override
	public void mouseClicked(MouseEvent e) {
		//checkModel();
		//System.out.println("click " + e.getX() + ", " + e.getY());
		if (model instanceof Menu) {
			Menu meModel = (Menu) model;
			meModel.click(e.getX(), e.getY());
			//System.out.println(e.getX() + ", " + e.getY());
		}
	}



	@Override
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
				bModel.showBrokenWing();
			}
		}
	}
		



	@Override
	public void mouseReleased(MouseEvent e) {
		if (model instanceof BreedingModel) {
			BreedingModel bModel = (BreedingModel) model;
			bModel.stopBrokenWing();
			//should reset the bird
		}
		
	}



	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseDragged(MouseEvent e) {
		if (model instanceof EatingModel) {
			EatingModel eModel = (EatingModel) model;
			eModel.setDestination(view.actualX(e.getX()), view.actualY(e.getY()));
		} else if (model instanceof BreedingModel) {
			BreedingModel bModel = (BreedingModel) model;
			bModel.setDestination(e.getX(), e.getY());
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		if (model instanceof MigratingModel) {
			MigratingModel mModel = (MigratingModel) model;
			mModel.setDestination(e.getX(), e.getY());
		}
	}
}