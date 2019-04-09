import static org.junit.Assert.*;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;


class MigratingModelTest {
//	Enemy(int xP, int yP, int r, int xV, int yV)
	ArrayList<Enemy> enemies = new ArrayList<>();
	ArrayList<Gust> gusts = new ArrayList<>();
	MigratingBird bird = new MigratingBird(0,0,0,0,0);
	int frameWidth = 500;
	int frameHeight= 500;
	
	
	@Test
	void test() {

		enemies.add(new Enemy(10, 10, 10, 1, 1));
		enemies.add(new Enemy(100, 100, 10, 1, 1));

		MigratingModel mModel = new MigratingModel(frameWidth, frameHeight, bird, enemies, gusts);
		for(Enemy e : enemies) {
			e.move();
		}
		
		MigratingModel mModel2 = new MigratingModel(frameWidth, frameHeight, bird, enemies, gusts);
		
		assertNotEquals(mModel, mModel2);
		mModel.update();
		assertEquals(mModel, mModel2);
		
	}
	
	@Test
	void test2() {
		enemies.add(new Enemy(10, 10, 10, 1, 1));
		enemies.add(new Enemy(100, 100, 10, 1, 1));
		MigratingModel mModel = new MigratingModel(frameWidth, frameHeight, new MigratingBird(10,10,10,1,1), enemies, gusts);
		
		for(Enemy e : enemies) {
			e.move();
		}
		MigratingModel mModel2 = new MigratingModel(frameWidth, frameHeight, new MigratingBird(10,10,10,1,1), enemies, gusts);
		
		mModel.update();
		assertNotEquals(mModel, mModel2);
	}
	
	@Test
	void despawnTest() {
		//no enemies or gust
		MigratingModel mModel = new MigratingModel(frameWidth, frameHeight, bird, enemies, gusts);
		
		enemies.add(new Enemy(10, 10, 10, 1000, 1000));
		MigratingModel mModel2 = new MigratingModel(frameWidth, frameHeight, bird, enemies, gusts);
		
		mModel2.update();
		assertEquals(mModel, mModel2);
	}
	
	@Test
	void despawnTest2() {
		//no enemies or gust
		MigratingModel mModel = new MigratingModel(frameWidth, frameHeight, bird, enemies, gusts);

		enemies.add(new Enemy(1000, 1000, 10, 1000, 1000));
		gusts.add(new Gust(600, 600, 10, 1000, 1000));
		MigratingModel mModel2 = new MigratingModel(frameWidth, frameHeight, bird, enemies, gusts);
		
		mModel2.despawn();
		assertEquals(mModel, mModel2);
	}
	
	@Test
	void spawnTest() {
		//no enemies or gust
		MigratingModel mModel = new MigratingModel(frameWidth, frameHeight, bird, enemies, gusts);
		
		MigratingModel mModel2 = new MigratingModel(frameWidth, frameHeight, bird, enemies, gusts);
		MigratingModel mModel3 = new MigratingModel(frameWidth, frameHeight, bird, enemies, gusts);

		mModel2.generateEnemies();
		assertNotEquals(mModel, mModel2);
		
		mModel3.generateGusts();
		assertNotEquals(mModel,mModel3);
	}
	

}
