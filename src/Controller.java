import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class Controller implements MouseMotionListener, MouseListener{
	// if i update this it updates that
	private Model model;
	private View view;
	private GameSequence sequence;
	static int overallScore = 0;
	
	public Controller(){
		view = new View();
		view.addControllerToMouse(this);
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
				view.moveCamera(eModel.getBirdX(), eModel.getBirdY(), eModel.getWidth(), eModel.getHeight());
			} else {
				view.resetCamera();
			}
			if (model instanceof BreedingModel) {
				//starts quiz when necessary
				BreedingModel bModel = (BreedingModel) model;
				if (bModel.quizTime == true) {
					Quiz quiz = bModel.getQuiz();
					view.buildQuiz(quiz);
					bModel.setQuizTime(false);
				}else if(bModel.loseGame()) {
					view.restartGamePrompt();
					bModel.retryGame();
				}
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
		overallScore += model.getScore();
		sequence.advance();
		model = sequence.getModel();
		view.hud = sequence.getHUD();
	}
	
	/**
	 * initiates end screen and restart logic. 
	 * also writes current model (and score) to save.ser file
	 */
	private void winGame(){
		EndMenu end = new EndMenu(view.getFrameWidth(), view.getFrameHeight());
		model = end;
		view.setHUD(new EndMenuHUD(view.getFrameWidth(), view.getFrameHeight()));
		
		while (end.endGame() == false ) {
			view.update(model.getMoveables(), model.getHUDargs());
			//System.out.print(model.getLoadScore());
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}  
		}
		
		saveModel();
		view.endMenu = false;
		start();
	}
	
	
	/**
	 * serializable implementation
	 * writes current model to save file (save.ser)
	 */
	public void saveModel() {
		try {
			FileOutputStream fos = new FileOutputStream("save.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(model);
			oos.close();
			
		} catch (IOException e) {
			System.out.println("something to do with the file WRITE");
			e.printStackTrace();
		}
	}
	
	/**
	 * method for loading a model in from a save file
	 */
	public void loadSavedModel() {
		try {
			 FileInputStream fis = new FileInputStream("save.ser");
		     ObjectInputStream ois = new ObjectInputStream(fis);
		     model = (Model) ois.readObject();
		     ois.close();
			
		} catch (IOException e) {
			System.out.println("something to do with the file READ");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
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