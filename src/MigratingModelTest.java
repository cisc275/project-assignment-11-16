import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
	MigratingModel m = new MigratingModel(frameWidth, frameHeight, bird, enemies, gusts,true);
	
	@Test
	void constructor() {
		enemies.add(new Hawk(frameWidth-50, 150));
		MigratingModel m1 = new MigratingModel(frameWidth, frameHeight, bird, enemies, gusts,true);
		MigratingModel m2 = new MigratingModel(frameWidth, frameHeight, true);
		MigratingModel m3 = new MigratingModel(frameWidth, frameHeight, false);
		
		//should be true. Possible that failed due to they are different object w/ same properties
		assertEquals(m2,m1);
		assertNotEquals(m3,m1);
	}
	
	@Test 
	void endGame(){
		MigratingModel mModel = new MigratingModel(frameWidth, frameHeight, false);
		mModel.distance = 1000;
		assertFalse(mModel.endGame());
		MigratingModel mModel2 = new MigratingModel(frameWidth, frameHeight,true);
		mModel2.distance = 8000;
		assertTrue(mModel2.endGame());
	}
	
	@Test
	void getMoveables() {
		MigratingModel mModel = new MigratingModel(frameWidth, frameHeight, bird, enemies, gusts,true);
		Collection<Moveable> c1 = new ArrayList<Moveable>();
		Collection<Moveable> c2 = mModel.getMoveables();
		assertEquals(c1.size(), c2.size());
		assertEquals(c1, c2);
	}
	
	
	@Test 
	void setDest() {
		MigratingModel mModel1 = new MigratingModel(frameWidth, frameHeight, bird, enemies, gusts,true);
		mModel1.bird.setDestination(0, 4);
		MigratingBird bird2 = new MigratingBird(frameWidth/2,frameHeight/2);
		bird2.setDestination(0, 4);
		MigratingModel mModel2 = new MigratingModel(frameWidth, frameHeight, bird2, enemies, gusts,true);
		
		assertEquals(mModel1.bird.getDestinationX(), mModel2.bird.getDestinationX());
		assertEquals(mModel1.bird.getDestinationY(), mModel2.bird.getDestinationY());
		
		
	}
	
	/**
	 * test for update: if the poweron is true and the powerTimer is on
	 * @author wenkiliang
	 */
	@Test
	void test() {

		enemies.add(new Hawk(10, 10, 10, 1, 1));
		enemies.add(new Hawk(100, 100, 10, 1, 1));
		gusts.add(new Gust(100, 100, 10, 1, 1));

		MigratingModel mModel = new MigratingModel(frameWidth, frameHeight, bird, enemies, gusts,true);
		for(Enemy e : enemies) {
			e.move();
		}
		mModel.bird.powerUp();
		//mModel.powerTimer = 30;
		MigratingModel mModel2 = new MigratingModel(frameWidth, frameHeight, bird, enemies, gusts,true);
		
		assertNotEquals(mModel, mModel2);
		mModel.update();
		assertEquals(mModel, mModel2);
		
	}
	
	@Test
	void test2() { //test if collision w/ hawk
		enemies.add(new Hawk(10, 10, 10, 1, 1));
		enemies.add(new Hawk(100, 100, 10, 1, 1));
		MigratingModel mModel = new MigratingModel(frameWidth, frameHeight, new MigratingBird(10,10,10,1,1), enemies, gusts,true);
		
		for(Enemy e : enemies) {
			e.move();
		}
		MigratingModel mModel2 = new MigratingModel(frameWidth, frameHeight, new MigratingBird(10,10,10,1,1), enemies, gusts,true);
		
		mModel.update();
		assertNotEquals(mModel, mModel2);
	}
	
	@Test
	void CollideWithBag() { //test if collision w/ bag
		enemies.add(new Bag(10, 10, 10, 1, 1));
		enemies.add(new Bag(100, 100, 10, 1, 1));
		MigratingModel mModel = new MigratingModel(frameWidth, frameHeight, new MigratingBird(10,10,10,1,1), enemies, gusts,true);
		
		for(Enemy e : enemies) {
			e.move();
		}
		MigratingModel mModel2 = new MigratingModel(frameWidth, frameHeight, new MigratingBird(10,10,10,1,1), enemies, gusts,true);
		
		mModel.update();
		assertNotEquals(mModel, mModel2);
	}
	@Test
	void test3() { //test if collision w/ gust
		gusts.add(new Gust(10, 10, 10, 1, 1));
		gusts.add(new Gust(100, 100, 10, 1, 1));
		MigratingModel mModel = new MigratingModel(frameWidth, frameHeight, new MigratingBird(10,10,10,1,1), enemies, gusts,true);
		for(Gust g : gusts) {
			g.move();
		}
		MigratingModel mModel2 = new MigratingModel(frameWidth, frameHeight, new MigratingBird(10,10,10,1,1), enemies, gusts,true);
		
		mModel.update();
		assertNotEquals(mModel, mModel2);
	}
	
	
	@Test
	void test4() { //test if gust outside the frame
		
		MigratingModel mModel = new MigratingModel(frameWidth, frameHeight, bird, enemies, gusts,true);
		
		gusts.add(new Gust(1000, 1000, 10, 1000, 1000));
		MigratingModel mModel2 = new MigratingModel(frameWidth, frameHeight, bird, enemies, gusts,true);
		mModel.update();
		mModel2.update();
		assertEquals(mModel, mModel2);
	}
	
	
	/**
	 * test for update: if the powerOn is true and the PowerTimer is zero
	 * @author wenkiliang
	 */
	@Test
	void test5() {

		enemies.add(new Hawk(10, 10, 10, 1, 1));
		enemies.add(new Hawk(100, 100, 10, 1, 1));
		gusts.add(new Gust(100, 100, 10, 1, 1));

		MigratingModel mModel = new MigratingModel(frameWidth, frameHeight, bird, enemies, gusts,true);
		for(Enemy e : enemies) {
			e.move();
		}
		mModel.bird.getPowerUp();
		MigratingModel mModel2 = new MigratingModel(frameWidth, frameHeight, bird, enemies, gusts,true);
		
		assertNotEquals(mModel, mModel2);
		mModel.update();
		assertEquals(mModel, mModel2);
		
	}
	@Test
	void despawnTest() {
		//no enemies or gust
		MigratingModel mModel = new MigratingModel(frameWidth, frameHeight, bird, enemies, gusts,true);
		
		enemies.add(new Hawk(10, 10, 10, 1000, 1000));
		MigratingModel mModel2 = new MigratingModel(frameWidth, frameHeight, bird, enemies, gusts,true);
		
		mModel2.update();
		assertNotEquals(mModel, mModel2);
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
		MigratingModel mModel = new MigratingModel(frameWidth, frameHeight, bird, enemies, gusts,true);
		
		MigratingModel mModel2 = new MigratingModel(frameWidth, frameHeight, bird, enemies, gusts,true);
		MigratingModel mModel3 = new MigratingModel(frameWidth, frameHeight, bird, enemies, gusts,true);

		mModel2.generateObjects();
		assertNotEquals(mModel, mModel2);
	}
	
	@Test
	void mouseMoved() {
		MigratingModel mModel = new MigratingModel(frameWidth, frameHeight, bird, enemies, gusts,true);
		mModel.mouseMoved(15, 15);
	}
	
	@Test
	void getHUDargs() {
		MigratingModel mModel = new MigratingModel(frameWidth, frameHeight, bird, enemies, gusts,true);
		int[] test = new int[3];
		test[0] = 1;
		test[1] = 0;
		test[2] = 5000;
		assertEquals(mModel.getHUDargs()[0],test[0]);
		assertEquals(mModel.getHUDargs()[1],test[1]);
		assertEquals(mModel.getHUDargs()[2],test[2]);
		
		MigratingModel mModel2 = new MigratingModel(frameWidth, frameHeight, bird, enemies, gusts,false);
		int[] test2 = new int[3];
		test2[0] = 0;
		test2[1] = 0;
		test2[2] = 2000;
		assertEquals(mModel2.getHUDargs()[0],test2[0]);
		assertEquals(mModel2.getHUDargs()[1],test2[1]);
		assertEquals(mModel2.getHUDargs()[2],test2[2]);
	}
	

	
}
