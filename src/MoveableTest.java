import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;

class MoveableTest {
	
	
	@Test
	public void basicCollision() {
		EatingBird bird = new EatingBird(0, 0); //radius 20
		assertTrue("together", bird.collidesWith(new Earthworm(0, 0, 10, 0, 0)));
		assertTrue("no center", bird.collidesWith(new Earthworm(30, 0, 20, 0, 0)));
		assertTrue("diagonal", bird.collidesWith(new Earthworm(21, 21, 20, 0, 0)));
		assertTrue("edges touching", bird.collidesWith(new Earthworm(bird.getRadius()+10, 0, 10, 0, 0)));
		assertFalse("just barely not", bird.collidesWith(new Earthworm(bird.getRadius()+10, 1, 10, 0, 0)));
		//assertTrue( "close location, but doesn't collide" , mBird.collidesWith(closeEnemy) );
		//assertTrue( "close location, but doesn't collide" , closeEnemy.collidesWith(mBird) );
		//assertFalse("far away, but collides", mBird.collidesWith(farEnemy) );
		//assertFalse("far away, but collides", farEnemy.collidesWith(closeEnemy) );
	}
	
	
	/*@Test
	public void edgeCollision() {
		assertTrue("edges touch, but doesn't collide", mBird.collidesWith(edgeEnemy1));
		assertTrue("edges touch, but doesn't collide", mBird.collidesWith(edgeEnemy2));
	}
	
	@Test
	public void exitsFrame() {
		assertTrue(mBird.exitsFrame(10, 10) );
		assertFalse(mBird.exitsFrame(100,100));
	}*/
	


}
