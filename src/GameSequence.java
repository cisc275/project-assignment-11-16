import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Supplier;

/**
 * Keeps track of the sequence of models and HUDs within the game.
 * If you need to, add more.
 * @author Prescott
 *
 */
public class GameSequence {
	LinkedList<Supplier<GameSequenceElement>> elements;
	GameSequenceElement current;
	Iterator<Supplier<GameSequenceElement>> itty;
	
	public GameSequence(int w, int h, boolean mig) {
		elements = new LinkedList<Supplier<GameSequenceElement>>();
		//elements.add(new GameSequenceElement(new Menu(w, h), new MenuHUD(w, h)));
		elements.add(() -> new GameSequenceElement(new EatingTutorial(w, h), new EatingHUD(w, h)));
		elements.add(() -> new GameSequenceElement(new EatingModel(w, h), new EatingHUD(w, h)));
		elements.add(() -> new GameSequenceElement(new MigratingModel(w, h, mig), new MigratingHUD(w, h)));
		//elements.add(() -> new GameSequenceElement(new MigratingTutorial(w, h, mig), new MigratingHUD(w, h)));
		
		elements.add(() -> new GameSequenceElement(new BreedingModel(w, h, mig), new BreedingHUD(w, h)));
		itty = elements.iterator();
	}
	
	public void advance() {
		current = itty.next().get();
	}
	
	public Model getModel() {
		return current.getModel();
	}
	
	public HUD getHUD() {
		return current.getHud();
	}

	public boolean atEnd() {
		return !itty.hasNext();
	}
}

class GameSequenceElement {
	Model model;
	HUD hud;
	
	public GameSequenceElement(Model model, HUD hud) {
		this.model = model;
		this.hud = hud;
	}
	
	public Model getModel() {
		return model;
	}
	
	public HUD getHud() {
		return hud;
	}
}