

/**
 * A button that is NOT a component.
 *
 */
public class Button extends MenuObject {
	Runnable func;
	
	public Button(int inx, int iny, int inwidth, int inheight) {
		x = inx;
		y = iny;
		width = inwidth;
		height = inheight;
	}
	
	public Button(int inx, int iny, int inwidth, int inheight, String intext) {
		this(inx, iny, inwidth, inheight);
		text = intext;
	}
	
	public Button(int inx, int iny, int inwidth, int inheight, String intext, Runnable infunc) {
		this(inx, iny, inwidth, inheight, intext);
		func = infunc;
	}	
	
	/**
	 * If this button clicked, executes its function.
	 * @param cx The x-coordinate of the mouse click
	 * @param cy The y-coordinate of the mouse click
	 */
	public void checkClick(int cx, int cy) {
		if (isClicked(cx, cy) && func != null)
			func.run();
	}
}
