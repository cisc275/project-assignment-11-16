import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Iterator;
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
		
		m2.setTime(1000000);
		assertNotEquals(m.getTime(), m2.getTime());
		
	}
	
	@Test
	void birdLocTest() {
		EatingBird b2 = new EatingBird(10, 10);
		EatingModel m1 = new EatingModel(frameWidth, frameHeight, b, foodList, time);
		EatingModel m2 = new EatingModel(frameWidth, frameHeight, b2, foodList, time);
		
		//test bird location getters
		assertNotEquals(m1.getBirdX(), m2.getBirdX());
		assertNotEquals(m1.getBirdY(), m2.getBirdY());
		
	}
	
	@Test
	void birdDestTest() {
		EatingModel m1 = new EatingModel(frameWidth, frameHeight, b, foodList, time);
		final int destX = 23;
		final int destY = 33;
		m1.setDestination(destX, destY);
		
		assertEquals(m1.bird.getDestinationX(), destX);
		assertEquals(m1.bird.getDestinationY(), destY);
	}
	
	@Test
	void updateTest() {
		EatingModel m1 = new EatingModel(frameWidth, frameHeight, b, foodList, time);
		m1.update();
		assertNotEquals(m, m1);
		assertNotEquals(m.getCurrentTime(), m1.getCurrentTime());
		
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
	void generateFoodTest() {
		EatingModel eModel = new EatingModel(frameWidth, frameHeight, b, foodList);
		EatingModel eModel2 = new EatingModel(frameWidth, frameHeight, b, foodList);
		
		eModel.spawnRandomFood();
		
		assertNotEquals(eModel, eModel2);
	}
	
	@Test
	void endGameTest() {
		//test for end of game by time out
		EatingModel eModel = new EatingModel(frameWidth, frameHeight, b, foodList, time);
		
		//time == limit
		eModel.setCurrentTime(time);
		assertTrue(eModel.endGame());
		
		//time > limit
		eModel.setCurrentTime(time + 10);
		assertTrue(eModel.endGame());
		
		//end of game by score goal reached
		EatingModel eModel2 = new EatingModel(frameWidth, frameHeight, b, foodList, time);
		int newScore1 = 501;
		int newScore = 500;
		
		//for score == goal
		eModel2.setScore(newScore);
		assertTrue(eModel2.endGame());
		assertEquals(eModel2.getScore(), newScore);
		
		//for score > goal
		eModel2.setScore(newScore1);
		assertTrue(eModel2.endGame());
		
		eModel2.setCurrentTime(time-10);
		eModel2.setScore(newScore-10);
		assertFalse(eModel2.endGame());
	}
	
	@Test
	void mouseTest() {
		EatingModel eModel = new EatingModel(frameWidth, frameHeight, new EatingBird(100, 100), foodList, time);
		EatingModel eModel1 = new EatingModel(frameWidth, frameHeight, new EatingBird(100, 100), foodList, time);
		EatingModel eModel2 = new EatingModel(frameWidth, frameHeight, new EatingBird(100, 100), foodList, time);

		eModel1.mouseDragged(100, 100, 1000, 1000);
		eModel1.update();
		assertNotEquals(eModel.bird.destinationX, eModel1.bird.destinationX);
		assertNotEquals(eModel.bird.destinationY, eModel1.bird.destinationY);
		
		eModel2.mousePressed(130, 700, 1200, 10, true, false);
		eModel2.update();
		assertNotEquals(eModel.bird.destinationX, eModel2.bird.destinationX);
		assertNotEquals(eModel.bird.destinationY, eModel2.bird.destinationY);
	}
}