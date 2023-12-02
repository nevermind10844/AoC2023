
public class Draw {
	private int shownBlue;
	private int shownRed;
	private int shownGreen;

	public Draw() {
		this.shownBlue = 0;
		this.shownRed = 0;
		this.shownGreen = 0;
	}

	public int getShownBlue() {
		return shownBlue;
	}

	public void setShownBlue(int shownBlue) {
		this.shownBlue = shownBlue;
	}

	public int getShownRed() {
		return shownRed;
	}

	public void setShownRed(int shownRed) {
		this.shownRed = shownRed;
	}

	public int getShownGreen() {
		return shownGreen;
	}

	public void setShownGreen(int shownGreen) {
		this.shownGreen = shownGreen;
	}

	@Override
	public String toString() {
		return "Draw [shownBlue=" + shownBlue + ", shownRed=" + shownRed + ", shownGreen=" + shownGreen + "]";
	}

}
