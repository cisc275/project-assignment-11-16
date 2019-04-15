import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class EatingModelTest {
	
	EatingBird testBird = new EatingBird(0, 0);
	List<Food> foodList = new ArrayList<Food>();
	int frameWidth = 500;
	int frameHeight= 500;
	
	@Test
	void eatingTest() {
		foodList.add(new Food(0, 0, 0, 0, 0));
		//EatingModel eModel = new EatingModel(frameWidth, frameHeight, testBird, foodList);
		
	}
	
	@Test
	void generateFoodTest() {
		EatingModel eModel = new EatingModel(frameWidth, frameHeight, testBird, foodList);
		EatingModel eModel2 = new EatingModel(frameWidth, frameHeight, testBird, foodList);
		
		eModel.generateFood();
		
		assertNotEquals(eModel, eModel2);
		
		eModel.despawnFood();
		
		assertEquals(eModel, eModel2);
	}
	
	@Test
	void endGameTest() {
		EatingModel eModel = new EatingModel(frameWidth, frameHeight, testBird, foodList, 5, 5);
		eModel.setScore(5);
		assertTrue(eModel.endGame());
	}
}