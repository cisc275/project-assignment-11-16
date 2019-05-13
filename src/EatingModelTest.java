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

	}
	
	@Test
	void foodSpawnTest() {
		
	}
	
	//test different get methods
	@Test
	void getsTest() {
		
	}
	
	//test different mouse listener methods
	@Test
	void mouseTest() {
		
	}
	
	@Test
	void HUDTest() {
		
	}
	
	@Test
	void newModelTest() {
		
	}
}