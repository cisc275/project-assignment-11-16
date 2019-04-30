/**
 * 
 * @author Prescott
 * @deprecated
 */
public abstract class MenuObject {
	int x;
	int y;
	int width;
	int height;
	String text = "";
	
	int getX() {return x;}
	int getY() {return y;}
	int getWidth() {return width;}
	int getHeight() {return height;}
	String getText() {return text;}
	/**
	 * 
	 * @param cx The x-coordinate of the mouse click
	 * @param cy The y-coordinate of the mouse click
	 * @return Whether the button is clicked
	 */
	public boolean isClicked(int cx, int cy) {
		return cx >= x && cy >= y && cx <= x+width && cy <= y+height;
	}
	
	public abstract void checkClick(int x, int y);
}
