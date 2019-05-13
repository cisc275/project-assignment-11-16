import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

/*
 * Unit tests for EatingModel
 * @author Kelly
 * 
 */

class EatingModelTest {
	
	EatingBird testBird = new EatingBird(0, 0);
	List<Food> foodList = new ArrayList<Food>();
	int frameWidth = 500;
	int frameHeight= 500;
	
	@Test
	void eatingTest() {
		foodList.add(new Earthworm(0, 0, 0, 0, 0));
		//EatingModel eModel = new EatingModel(frameWidth, frameHeight, testBird, foodList);
		
	}
	
	@Test
	void generateFoodTest() {
		EatingModel eModel = new EatingModel(frameWidth, frameHeight, testBird, new ArrayList<Food>());
		EatingModel eModel2 = new EatingModel(frameWidth, frameHeight, testBird, new ArrayList<Food>());
		
		eModel.spawnRandomFood();
		
		assertNotEquals(eModel.food, eModel2.food);
	}
	
	@Test 
	void endGameTest() {
		EatingModel eModel = new EatingModel(frameWidth, frameHeight, testBird, foodList, 5);
		eModel.timeTaken = 5;
		assertTrue(eModel.endGame());
	}
	
	@Test
	void constructTest() {
		EatingModel eModel = new EatingModel(frameWidth, frameHeight);
		assertEquals(eModel.frameHeight, frameHeight);
		assertEquals(eModel.frameWidth, frameWidth);
		assertEquals(eModel.timeLimit, 200);
		assertEquals(eModel.timeTaken, 0);
		assertNotEquals(eModel.food, foodList);
		
	}
	
	@Test
	void updateTest() {
		EatingModel eModel = new EatingModel(frameWidth, frameHeight, testBird, foodList, 5);
		eModel.food.add(new Grasshopper(1000, 1000, 10, 0, 0));
		EatingModel eModel1 = new EatingModel(frameWidth, frameHeight, testBird, foodList, 5);
		eModel.update();
		assertNotEquals(eModel.timeTaken, eModel1.timeTaken);
		
		//test collision
		eModel.food.add(new Grasshopper(eModel.getBirdX(), eModel.getBirdY(), 10, 0, 0));
		eModel.update();
		assertNotEquals(eModel.getScore(), 0);
		
		eModel.food = new ArrayList<Food>();
		eModel.food.add(new Grasshopper(10000, 100000, 10, 0, 0));
		eModel.update();
		assertEquals(eModel.getMoveables().size(), eModel1.getMoveables().size()); 
	}
	
	//test different get methods
	@Test
	void getSetTest() {
		EatingModel eModel = new EatingModel(frameWidth, frameHeight, testBird, foodList, 5);
		assertNotNull(eModel.getBirdX());
		assertNotNull(eModel.getMoveables());
		assertNotNull(eModel.getBirdY());
		assertNotNull(eModel.getHUDargs());
		assertNotNull(eModel.nextModel(eModel.frameWidth, eModel.frameHeight, false));
		eModel.setDestination(10, 20);
		assertEquals(eModel.bird.destinationY, 20);
		assertEquals(eModel.bird.destinationX, 10);
		eModel.setScore(100);
		assertEquals(eModel.score, 100);
	}
	
	//test different mouse listener methods
	@Test
	void mouseTest() {
		EatingModel eModel = new EatingModel(frameWidth, frameHeight, new EatingBird(0, 0), foodList, 5);
		EatingModel eModel0 = new EatingModel(frameWidth, frameHeight, new EatingBird(0, 0), foodList, 5);
		EatingModel eModel1 = new EatingModel(frameWidth, frameHeight, new EatingBird(0, 0), foodList, 5);
		eModel1.mouseDragged(100, 200, 1000, 2000);
		eModel.update();
		eModel1.update();
		assertNotEquals(eModel.getBirdX(), eModel1.getBirdX());
		assertNotEquals(eModel.getBirdY(), eModel1.getBirdY());
		
		eModel0.mousePressed(1110, 10, 100, 100, true, false);
		eModel.update();
		eModel0.update();
		assertNotEquals(eModel.getBirdX(), eModel0.getBirdX());
		assertNotEquals(eModel.getBirdY(), eModel0.getBirdY());
		
	}
}