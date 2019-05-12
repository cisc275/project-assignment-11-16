import java.util.Collection;

public class MigratingTutorial extends MigratingModel {

	PointerArea pointer;
	int stage;

	
	MigratingTutorial(int w, int h, boolean isMigrate) {
		super(w, h, isMigrate);
		gusts.add(new Gust(frameWidth-10,150));
		pointer = new PointerArea(500, 150, 50);
		backgroundObjects.add(pointer);
		stage = 0;
	}
	
	void update() {
		bird.update();
		for (Moveable o : enemies) {
			o.update();
		}
		for (Moveable o : gusts) {
			o.update();
		}
		updateEnemyCollision();
		updateGustCollision();
	
		if(stage == 0 && gusts.isEmpty()) {
			enemies.add(new Hawk(frameWidth-10, 500));
			pointer = new PointerArea(500, 500, 50);
			backgroundObjects.add(pointer);
			stage++;
		}else if(stage == 1 && enemies.isEmpty()) {
			stage++;
		}
	}
	
	@Override
	public boolean endGame() {
		return stage == 2;
	}
	
	double distanceTo(int x, int y, int otherx, int othery) {
		return Math.sqrt(Math.pow(x - otherx, 2) + Math.pow(y - othery, 2));
	}
	
	void mouseMoved(int mouseX, int mouseY) {
		if( this.distanceTo(pointer.getX(), pointer.getY(), mouseX, mouseY) < pointer.getR()/2){
			this.setDestination(mouseX, mouseY);
			backgroundObjects.remove(pointer);
		}
	}
	
	/*
	@Override
	Collection<Moveable> getMoveables() {
		Collection<Moveable> m = super.getMoveables();
		if (pointer != null)
			m.add(pointer);
		return m;
	}
	*/

}
