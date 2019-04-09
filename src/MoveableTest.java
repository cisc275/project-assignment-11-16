import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;

class MoveableTest {

	//(int xP, int yP, int r, int xV, int yV)
	MigratingBird mBird = new MigratingBird(30, 30, 10, 1 ,1);
	Enemy closeEnemy = new Enemy(25, 25, 10, 1, 1);
	Enemy farEnemy = new Enemy(100, 100, 10, 1, 1);

	Enemy edgeEnemy1 = new Enemy(10, 10, 10, 1 , 1);
	Enemy edgeEnemy2 = new Enemy(50, 50, 10, 1 , 1);
	
	
	@Test
	public void basicCollision() {		
		assertTrue( "close location, but doesn't collide" , mBird.collidesWith(closeEnemy) );
		assertTrue( "close location, but doesn't collide" , closeEnemy.collidesWith(mBird) );
		assertFalse("far away, but collides", mBird.collidesWith(farEnemy) );
		assertFalse("far away, but collides", farEnemy.collidesWith(closeEnemy) );
	}
	
	
	@Test
	public void edgeCollision() {
		assertTrue("edges touch, but doesn't collide", mBird.collidesWith(edgeEnemy1));
		assertTrue("edges touch, but doesn't collide", mBird.collidesWith(edgeEnemy2));
	}
	
	@Test
	public void exitsFrame() {
		assertTrue(mBird.exitsFrame(10, 10) );
		assertFalse(mBird.exitsFrame(100,100));
	}
	


}
