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
		EatingModel eModel = new EatingModel(frameWidth, frameHeight, testBird, foodList);
		EatingModel eModel2 = new EatingModel(frameWidth, frameHeight, testBird, foodList);
		
		eModel.spawnRandomFood();
		
		assertNotEquals(eModel, eModel2);
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
	}
	
	@Test
	void foodSpawnTest() {
		
	}
	
	//test different get methods
	@Test
	void getsTest() {
		EatingModel eModel = new EatingModel(frameWidth, frameHeight, testBird, foodList, 5);
		assertNotNull(eModel.getBirdX());
		assertNotNull(eModel.getMoveables());
		assertNotNull(eModel.getBirdY());
		assertNotNull(eModel.getHUDargs());
		assertNotNull(eModel.nextModel(eModel.frameWidth, eModel.frameHeight, false));
	}
	
	//test different mouse listener methods
	@Test
	void mouseTest() {
		
	}
	
	@Test
	void HUDTest() {
	}
}