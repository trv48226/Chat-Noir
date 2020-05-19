package chatNoir;

/**
 * Colors Class that creates a color object with an x value, y value, and a
 * color
 * 
 * @author Lukas and Trevor
 * @Version Spring2020
 */
public class Colors {
	/* private x coordinate of the object */
	private int xValue;
	/* private y coordinate of the object */
	private int yValue;
	/* private color of the object */
	private String color;

	/**
	 * Colors Constructor
	 * 
	 * @param newColor the color to set
	 * @param x        the x value to set
	 * @param y        the y value to set
	 */
	public Colors(String newColor, int x, int y) {
		setColor(newColor);
		setxValue(x);
		setyValue(y);
	}

	/**
	 * Default Constructor
	 */
	public Colors() {
		setColor("White");
		setxValue(0);
		setyValue(0);
	}

	/**
	 * Getter for the x value of the object
	 * 
	 * @return the x value
	 */
	public int getxValue() {
		return xValue;
	}

	/**
	 * Setter for the x value of the object
	 * 
	 * @param xValue the new x value
	 */
	public void setxValue(int xValue) {
		this.xValue = xValue;
	}

	/**
	 * Getter for the y value of the object
	 * 
	 * @return the y value
	 */
	public int getyValue() {
		return yValue;
	}

	/**
	 * Setter for the y value
	 * 
	 * @param yValue the new y value
	 */
	public void setyValue(int yValue) {
		this.yValue = yValue;
	}

	/**
	 * Getter for the color object
	 * 
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * Setter for the color
	 * 
	 * @param color the new color
	 */
	public void setColor(String color) {
		this.color = color;
	}
}
