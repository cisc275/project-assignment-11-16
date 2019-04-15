import java.util.ArrayList;

public class MainMenu extends Menu {
	public MainMenu(int w, int h, final Controller cont) {
		menuObjects = new ArrayList<MenuObject>();
		menuObjects.add(new Button(50, 50, 200, 40, "Eating", new Runnable() {
			public void run() {
				cont.startEating();
			}
		}));
		menuObjects.add(new Button(50, 100, 200, 40, "Migrating", new Runnable() {
			public void run() {
				cont.startMigrating();
			}
		}));
		menuObjects.add(new Button(50, 150, 200, 40, "Breeding", new Runnable() {
			public void run() {
				cont.startBreeding();
			}
		}));
	}

	
}
