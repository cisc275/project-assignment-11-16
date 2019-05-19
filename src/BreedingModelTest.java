import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class BreedingModelTest{
	
	int frameWidth = 500;
	int frameHeight= 500;
	BreedingBird testBird = new BreedingBird(frameWidth/2, frameHeight/2, 30, 0, 0, 8);
	Raccoon testCoon = new Raccoon(frameWidth/2, frameHeight-100, 35, 0, 0, 1);
	Nest testNest = new Nest(frameWidth/2,(frameHeight/2)-100,50);
	BreedingModel bModel = new BreedingModel(frameWidth, frameHeight, testBird, testCoon);
	
	@Test
	void constructor() {
		BreedingModel m1 = new BreedingModel(frameWidth, frameHeight, testBird, new Raccoon(frameWidth/2, frameHeight-100, 35, 0, 0, 1));
		m1.isMigrating = true;
		BreedingModel m2 = new BreedingModel(frameWidth, frameHeight, true);
		BreedingModel m3 = new BreedingModel(frameWidth, frameHeight, false);
		assertEquals(m1.getMoveables(),m2.getMoveables());
		assertNotEquals(m3,m1);
	}
	
	@Test
	void predatorTest() {
		
		//BreedingModel bModel = new BreedingModel(frameWidth, frameHeight, testBird, testCoon);
		BreedingModel bModel2 = new BreedingModel(frameWidth, frameHeight, testBird, testCoon);
		assertEquals(bModel.p.getX(), bModel2.p.getX());
		bModel2.startBrokenWing();
		bModel.update();
		bModel2.update();
		bModel.p.move();
		bModel2.p.move();
		assertEquals(bModel.p, bModel2.p);
	}
	
	@Test
	void updateTest() {
		BreedingModel b1 = new BreedingModel(frameWidth, frameHeight, testBird, testCoon);
		b1.update();
		assertNotEquals(bModel, b1);
	}
	
	@Test
	void nestTest() {
		
		BreedingModel bModel = new BreedingModel(frameWidth, frameHeight, testBird, new Raccoon(testNest.getX(),testNest.getY(), 35, 0, 0), testNest);
		bModel.update();
		assertTrue(bModel.p.collidedWithNest);
		assertTrue(bModel.quizTime);
		assertTrue(bModel.nest.numEggs < 3);
	}
	
	@Test
	void predatorGenerateTest() {
		BreedingModel bModel = new BreedingModel(frameWidth, frameHeight, new BreedingBird(0, 0, 0, 0, 0), testCoon);
		BreedingModel bModel2 = new BreedingModel(frameWidth, frameHeight, new BreedingBird(0, 0, 0, 0, 0), testCoon);
		
		bModel.generatePredators();
		
		assertNotEquals(bModel, bModel2);
	}
	
	@Test
	void predatorDespawnTest() {
		BreedingModel topleft1 = new BreedingModel(frameWidth, frameHeight, new BreedingBird(0, 0, 0, 0, 0), new Raccoon(0, 0, 35, 0, 0));
		BreedingModel bottomleft1 = new BreedingModel(frameWidth, frameHeight, new BreedingBird(0, 0, 0, 0, 0), new Raccoon(frameWidth, 0, 35, 0, 0));
		BreedingModel topright1 = new BreedingModel(frameWidth, frameHeight, new BreedingBird(0, 0, 0, 0, 0), new Raccoon(0, frameHeight, 35, 0, 0));
		BreedingModel bottomright1 = new BreedingModel(frameWidth, frameHeight, new BreedingBird(0, 0, 0, 0, 0), new Raccoon(frameHeight+40, frameWidth+40, 35, 0, 0));
		BreedingModel topleft2 = new BreedingModel(frameWidth, frameHeight, new BreedingBird(0, 0, 0, 0, 0), new Raccoon(0, 0, 35, 0, 0));
		BreedingModel bottomleft2 = new BreedingModel(frameWidth, frameHeight, new BreedingBird(0, 0, 0, 0, 0), new Raccoon(frameWidth, 0, 35, 0, 0));
		BreedingModel topright2 = new BreedingModel(frameWidth, frameHeight, new BreedingBird(0, 0, 0, 0, 0), new Raccoon(0, frameHeight, 35, 0, 0));
		BreedingModel bottomright2 = new BreedingModel(frameWidth, frameHeight, new BreedingBird(0, 0, 0, 0, 0), new Raccoon(frameHeight+40, frameWidth+40, 35, 0, 0));
		topleft1.byeByePredator();
		bottomleft1.byeByePredator();
		topright1.byeByePredator();
		bottomright1.byeByePredator();
		topleft1.update();
		bottomleft1.update();
		topright1.update();
		bottomright1.update();
		assertNotEquals(topleft1.p.getFacing(),topleft2.p.getFacing());
		assertNotEquals(bottomleft1.p.getFacing(),bottomleft2.p.getFacing());
		assertNotEquals(topright1.p.getFacing(),topright2.p.getFacing());
		assertNotEquals(bottomleft1.p.getFacing(),bottomleft2.p.getFacing());
		
		topleft1.distractCountdown = -1;
		topleft1.despawnPredators();
		bottomright1.despawnPredators();
		assertNotEquals(bottomright1.numPredators,bottomright2.numPredators);
		
	}
	
	@Test
	void brokenWingTest() {
		BreedingModel bModel = new BreedingModel(frameWidth, frameHeight, new BreedingBird(0, 0, 0, 0, 0), testCoon);
		assertFalse(bModel.bird.brokenWing);
		bModel.startBrokenWing();
		assertTrue(bModel.bird.brokenWing);
		bModel.stopBrokenWing();
		assertFalse(bModel.bird.brokenWing);
	}
	
	@Test 
	void setDest() {
		BreedingModel bModel1 = new BreedingModel(frameWidth, frameHeight, new BreedingBird(0, 0, 0, 0, 0), testCoon);
		bModel1.bird.setDestination(0, 4);
		BreedingBird bird2 = new BreedingBird(frameWidth/2,frameHeight/2);
		bird2.setDestination(0, 4);
		BreedingModel bModel2 = new BreedingModel(frameWidth, frameHeight, bird2, testCoon);
		
		assertEquals(bModel1.bird.getDestinationX(), bModel2.bird.getDestinationX());
		assertEquals(bModel1.bird.getDestinationY(), bModel2.bird.getDestinationY());
		
		
	}
	@Test
	void mouseTest() {
		BreedingModel bModel2 = new BreedingModel(frameWidth, frameHeight, testBird, testCoon);
		bModel.mouseMoved(50, 50);
		bModel.mouseReleased();
		assertFalse(bModel.bird.getBrokenWing());
		
		bModel.mouseDragged(100, 100, 1000, 1000);
		bModel.update();
		assertNotEquals(bModel.bird.destinationX, bModel2.bird.destinationX);
		assertNotEquals(bModel.bird.destinationY, bModel2.bird.destinationY);
	}
	@Test
	void mouseTest2() {
		BreedingModel bModel2 = new BreedingModel(frameWidth, frameHeight, testBird, testCoon);
		bModel2.mousePressed(130, 700, 1200, 10, true, false);
		bModel2.update();
		assertNotEquals(bModel.bird.destinationX, bModel2.bird.destinationX);
		assertNotEquals(bModel.bird.destinationY, bModel2.bird.destinationY);
	}
	@Test
	void mouseTest3() {
		BreedingModel bModel3 = new BreedingModel(frameWidth, frameHeight, testBird, testCoon);
		bModel3.mousePressed(130, 700, 130, 700, false, true);
		bModel3.update();
		assertTrue(bModel3.bird.getBrokenWing());
	}
	
	@Test
	void correctAnsTest() {
		bModel.correctAnswer = 1;
		bModel.buttonClicked(1);
		assertEquals(bModel.getScore(),100);
		bModel.buttonClicked(2);
		assertEquals(bModel.getScore(),50);
	}
	
	@Test
	void endGameTest() {
		assertFalse(bModel.endGame());
		bModel.numPredators = 0;
		assertTrue(bModel.endGame());
	}
	
	@Test
	void HUDTest() {
		bModel.isMigrating = false;
		int pX = bModel.p.getX();
		int pY = bModel.p.getY();
		int wingFlag = 0;
		int countdown = bModel.distractCountdown;
		int duration = bModel.DISTRACT_DURATION;
		int mig = 0;
		
		int[] HUDargs = bModel.getHUDargs();
		
		assertEquals(HUDargs[0], pX);
		assertEquals(HUDargs[1], pY);
		assertEquals(HUDargs[2], wingFlag);
		assertEquals(HUDargs[3], countdown);
		assertEquals(HUDargs[4], duration);
		assertEquals(HUDargs[5], mig);
	}
	
}