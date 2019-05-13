import java.awt.event.*;
//import java.util.Collection;


public class Controller implements MouseMotionListener, MouseListener, ActionListener {
	// if i update this it updates that
	private Model model;
	private View view;
	private GameSequence sequence;
	
	public Controller(){
		view = new View();
		view.addControllerToMouse(this);
		view.addControllerToButton(this);
		
		sequence = new GameSequence(view.getFrameWidth(), view.getFrameHeight(),view.migrate);
		
	}

	//change to build differently depending on boolean migrate in View
	public void startMainMenu() {
		model = new Menu(view.getFrameWidth(), view.getFrameHeight());
		view.hud = new MenuHUD(view.getFrameWidth(), view.getFrameHeight());
		view.buildMenu();
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
			if (model instanceof BreedingModel) {
				BreedingModel bMode1 = (BreedingModel) model;
				view.quizTime = bMode1.quizTime;
				while (view.quizTime) {
					view.buildQuiz();
					//System.out.println("While loop");
					view.quizTime = bMode1.quizTime;
				}
				//System.out.println("Outside the loop");
				
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

	@Override
	public void actionPerformed(ActionEvent e) {
		view.endMenu = true;
		System.out.println("button was pressed!");
		int answer = -1;
		if (e.getSource() == view.qA1Button) {
			answer = 0;
		}
		if (e.getSource() == view.qA1Button) {
			answer = 1;
		}
		if (e.getSource() == view.qA1Button) {
			answer = 2;
		}
		view.quizTime = false;
		view.removeMenu();
		model.actionPerformed(e, answer);
	}
}