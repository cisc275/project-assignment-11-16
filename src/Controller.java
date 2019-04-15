import java.awt.event.*;
import java.util.Collection;


public class Controller implements MouseMotionListener,MouseListener{
	// if i update this it updates that
	private Model model;
	private EatingModel eModel;
	private MigratingModel mModel;
	private BreedingModel bModel;
	private View view;
	
	public Controller(){
		view = new View();
		view.addControllerToMouse(this);
		int frameWidth = view.getFrameWidth();
		int frameHeight = view.getFrameHeight();
				
		eModel = new EatingModel(frameWidth, frameHeight);
		mModel = new MigratingModel(frameWidth, frameHeight);
		bModel = new BreedingModel(frameWidth, frameHeight);
		
		//can switch models later
		model = eModel;		
	}
	
	//for testing
	public String checkModel() {
		String string = "? not e, b, or m model ?";
		if(model.equals(eModel)) {
			string = "currently eModel";
		}else if(model.equals(bModel)) {
			string = "currently bModel";
		}else if(model.equals(mModel)) {
			string = "currently mModel";
		}
		System.out.println(string);
		return string;
	}

	//handles ticking: update model, update view 
	public void start() {
		for(int i = 0; i < 5000; i++) {
			//increment the x and y coordinates, alter direction if necessary
			model.update();
			view.update(model.getMoveables());

		}
	}

	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		if (model == mModel) {
			mModel.mouseUpdate(e.getX(), e.getY());
			System.out.println(e.getX() + " " + e.getY());
		}
		
	}
}