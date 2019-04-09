import static org.junit.Assert.*;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class BreedingModelTest{
	
	BreedingBird testBird = new BreedingBird(0, 0, 0, 0, 0);
	ArrayList<Predator> predatorList = new ArrayList<>();
	int frameWidth = 500;
	int frameHeight= 500;
	
	@Test
	void predatorTest() {
		predatorList.add(new Predator(10, 10, 10, 1, 1));
		predatorList.add(new Predator(100, 100, 10, 1, 1));
		
		BreedingModel bModel = new BreedingModel(frameWidth, frameHeight, testBird, predatorList);
		
		for (Predator p : predatorList) {
			p.move();
		}
		
		BreedingModel bModel2 = new BreedingModel(frameWidth, frameHeight, testBird, predatorList);
		
		assertNotEquals(bModel, bModel2);
		bModel.update();
		assertEquals(bModel, bModel2);
	}
	
	@Test
	void predatorTest2() {
		predatorList.add(new Predator(10, 10, 10, 1, 1));
		predatorList.add(new Predator(100, 100, 10, 1, 1));
		
		BreedingModel bModel = new BreedingModel(frameWidth, frameHeight, new BreedingBird(10, 10, 10, 1, 1), predatorList);
		
		for (Predator p : predatorList) {
			p.move();
		}

		BreedingModel bModel2 = new BreedingModel(frameWidth, frameHeight, new BreedingBird(10, 10, 10, 1, 1), predatorList);
		
		assertNotEquals(bModel, bModel2);
		bModel.update();
		assertEquals(bModel, bModel2);
	}
	
	@Test
	void nestTest() {
		BreedingModel bModel = new BreedingModel(frameWidth, frameHeight, new BreedingBird(0, 0, 0, 0, 0), predatorList, new Nest(0,0,0));
		
		BreedingModel bModel2 = new BreedingModel(frameWidth, frameHeight, new BreedingBird(0, 0, 0, 0, 0), predatorList, new Nest(0,0,0,0));
		
		assertFalse(bModel.isQuizTime());
		assertTrue(bModel2.endGame());
	}
	
	@Test
	void predatorGenerateTest() {
		BreedingModel bModel = new BreedingModel(frameWidth, frameHeight, new BreedingBird(0, 0, 0, 0, 0), predatorList);
		BreedingModel bModel2 = new BreedingModel(frameWidth, frameHeight, new BreedingBird(0, 0, 0, 0, 0), predatorList);
		
		bModel.generatePredators();
		
		assertNotEquals(bModel, bModel2);
	}
	
}