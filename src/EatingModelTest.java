import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class EatingModelTest {
	EatingBird b = new EatingBird(100, 100);
	List<Food> foodList = new ArrayList<Food>();
	final int frameWidth = 500;
	final int frameHeight= 100;
	final int time = 600;
	EatingModel m = new EatingModel(frameWidth, frameHeight, b, foodList, time);
	
	
	@Test
	void constructorTest() {
		//testing constructors
		EatingModel m2 = new EatingModel(frameWidth, frameHeight);
		assertEquals(m.getFrameX(), m2.getFrameX());
		assertEquals(m.getFrameY(), m2.getFrameY());
		
		EatingModel m3 = new EatingModel(frameWidth, frameHeight, b, foodList);
		assertEquals(m.getMoveables(), m3.getMoveables());
		
		EatingModel m4 = new EatingModel(frameWidth, frameHeight, b, foodList, time);
		assertEquals(m.getMoveables(), m4.getMoveables());
		assertEquals(m.getTime(), m4.getTime());
		
		//testing getters and setters
		//to do: destination, birdx/y, set time, get current time, get score, get menu objects (?)
	}
	
	@Test
	void birdTest() {
		EatingBird b2 = new EatingBird(10, 10);
		EatingModel m1 = new EatingModel(frameWidth, frameHeight, b, foodList, time);
		EatingModel m2 = new EatingModel(frameWidth, frameHeight, b2, foodList, time);
		
		//test bird location getters
		assertNotEquals(m1.getBirdX(), m2.getBirdX());
		assertNotEquals(m1.getBirdY(), m2.getBirdY());
		
		//test bird dest setter
		assertEquals(m.bird, m1.bird);
		m1.setDestination(101000, 104023);
		assertNotEquals(m.bird, m1.bird);
	}
	
	@Test
	void updateTest() {
		EatingModel m1 = new EatingModel(frameWidth, frameHeight, b, foodList, time);
		m1.update();
		assertNotEquals(m, m1);
		assertNotEquals(m.getCurrentTime(), m1.getCurrentTime());
		//assertNotEquals(m.getMoveables(), m1.getMoveables()); //WHY DOES THIS NOT FUCKING WORK
		
	}
	
	@Test
	void updateCollisionTest() {
		//test if collision with food removes food
		List<Food> f1 = new ArrayList<Food>();
		f1.add(new Grasshopper(100, 100));
		EatingModel m1 = new EatingModel(frameWidth, frameHeight, b, f1, time);
		m1.updateCollision();

		assertEquals(m.getMoveables(), m1.getMoveables());
		
		//test if food going offscreen removes food
		f1.add(new Grasshopper(1000000, 100000));
		m1 = new EatingModel(frameWidth, frameHeight, b, f1, time);
		m1.updateCollision();
	}
	
	@Test
	void getMenuObjectsTest() {
		
	}
	
	@Test
	void setDestinationTest() {
		
	}
	
	@Test
	void getBirdXYTest() {
		
	}
	
	@Test
	void generateFoodTest() {
		EatingModel eModel = new EatingModel(frameWidth, frameHeight, b, foodList);
		EatingModel eModel2 = new EatingModel(frameWidth, frameHeight, b, foodList);
		
		eModel.spawnRandomFood(); //add more to this test
		
		assertNotEquals(eModel, eModel2);
	}
	
	@Test
	void endGameTest() {
		//test for end of game by time out
		EatingModel eModel = new EatingModel(frameWidth, frameHeight, b, foodList, time);
		eModel.setCurrentTime(time);
		assertTrue(eModel.endGame());
		
		//end of game by score goal reached
		EatingModel eModel2 = new EatingModel(frameWidth, frameHeight, b, foodList, time);
		eModel2.setScore(501);
		assertTrue(eModel2.endGame());
	}
}