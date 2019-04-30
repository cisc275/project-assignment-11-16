import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Test;


class MigratingModelTest {
//	Enemy(int xP, int yP, int r, int xV, int yV)
	
	int frameWidth = 500;
	int frameHeight= 500;
	ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	ArrayList<Gust> gusts = new ArrayList<Gust>();
	MigratingBird bird = new MigratingBird(frameWidth/2,frameHeight/2);
	MigratingModel m = new MigratingModel(frameWidth, frameHeight, bird, enemies, gusts);
	
	@Test
	void constructor() {
		enemies.add(new Hawk(frameWidth-50, 150));
		MigratingModel m1 = new MigratingModel(frameWidth, frameHeight, bird, enemies, gusts);
		MigratingModel m2 = new MigratingModel(frameWidth, frameHeight);
		
		//should be true. Possible that failed due to they are different object w/ same properties
		assertEquals(m2,m1);
	}
	
	@Test 
	void endGame(){
		assertFalse(m.endGame());
	}
	
	
	@Test
	void getMoveables() {
		MigratingModel mModel = new MigratingModel(frameWidth, frameHeight, bird, enemies, gusts);
		Collection<Moveable> c1 = new ArrayList<>();
		Collection<Moveable> c2 = mModel.getMoveables();
		assertEquals(c1.size(), c2.size());
		assertEquals(c1, c2);
	}
	
	
	@Test 
	void setDest() {
		MigratingModel mModel1 = new MigratingModel(frameWidth, frameHeight, bird, enemies, gusts);
		mModel1.setDestination(0, 4);
		bird.setDestination(0, 4);
		MigratingModel mModel2 = new MigratingModel(frameWidth, frameHeight, bird, enemies, gusts);
		
		assertEquals(mModel1, mModel2);
		
		
	}
	
	
	@Test
	void test() {

		enemies.add(new Hawk(10, 10, 10, 1, 1));
		enemies.add(new Hawk(100, 100, 10, 1, 1));
		gusts.add(new Gust(100, 100, 10, 1, 1));

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
	void test2() { //test if collision w/ hawk
		enemies.add(new Hawk(10, 10, 10, 1, 1));
		enemies.add(new Hawk(100, 100, 10, 1, 1));
		MigratingModel mModel = new MigratingModel(frameWidth, frameHeight, new MigratingBird(10,10,10,1,1), enemies, gusts);
		
		for(Enemy e : enemies) {
			e.move();
		}
		MigratingModel mModel2 = new MigratingModel(frameWidth, frameHeight, new MigratingBird(10,10,10,1,1), enemies, gusts);
		
		mModel.update();
		assertNotEquals(mModel, mModel2);
	}
	
	@Test
	void test3() { //test if collision w/ gust
		gusts.add(new Gust(10, 10, 10, 1, 1));
		gusts.add(new Gust(100, 100, 10, 1, 1));
		MigratingModel mModel = new MigratingModel(frameWidth, frameHeight, new MigratingBird(10,10,10,1,1), enemies, gusts);
		for(Gust g : gusts) {
			g.move();
		}
		MigratingModel mModel2 = new MigratingModel(frameWidth, frameHeight, new MigratingBird(10,10,10,1,1), enemies, gusts);
		
		mModel.update();
		assertNotEquals(mModel, mModel2);
	}
	
	@Test
	void test4() { //test if gust outside the frame
		
		MigratingModel mModel = new MigratingModel(frameWidth, frameHeight, bird, enemies, gusts);
		
		gusts.add(new Gust(1000, 1000, 10, 1000, 1000));
		MigratingModel mModel2 = new MigratingModel(frameWidth, frameHeight, bird, enemies, gusts);
		mModel.update();
		mModel2.update();
		assertEquals(mModel, mModel2);
	}
	@Test
	void despawnTest() {
		//no enemies or gust
		MigratingModel mModel = new MigratingModel(frameWidth, frameHeight, bird, enemies, gusts);
		
		enemies.add(new Hawk(10, 10, 10, 1000, 1000));
		MigratingModel mModel2 = new MigratingModel(frameWidth, frameHeight, bird, enemies, gusts);
		
		mModel2.update();
		assertEquals(mModel, mModel2);
	}
	
	/*
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
	*/
	
	@Test
	void spawnTest() {
		//no enemies or gust
		MigratingModel mModel = new MigratingModel(frameWidth, frameHeight, bird, enemies, gusts);
		
		MigratingModel mModel2 = new MigratingModel(frameWidth, frameHeight, bird, enemies, gusts);
		MigratingModel mModel3 = new MigratingModel(frameWidth, frameHeight, bird, enemies, gusts);

		mModel2.generateEnemy();
		assertNotEquals(mModel, mModel2);
		
		mModel3.generateGust();
		assertNotEquals(mModel,mModel3);
	}
	

}
