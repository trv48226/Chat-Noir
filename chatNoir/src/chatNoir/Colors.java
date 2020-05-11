package chatNoir;
//Colors
public class Colors {
	private int xValue;
	private int yValue;
	private String color;

	public Colors(String newColor, int x, int y) {
		setColor(newColor);
		setxValue(x);
		setyValue(y);
	}

	public Colors() {
		setColor("White");
		setxValue(0);
		setyValue(0);
	}

	public int getxValue() {
		return xValue;
	}

	public void setxValue(int xValue) {
		this.xValue = xValue;
	}

	public int getyValue() {
		return yValue;
	}

	public void setyValue(int yValue) {
		this.yValue = yValue;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}
