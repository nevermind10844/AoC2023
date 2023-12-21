
public class Rock {
	private int posX;
	private int posY;
	private RockType rockType = null;

	public Rock(int posX, int posY, RockType rockType) {
		this.posX = posX;
		this.posY = posY;
		this.rockType = rockType;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public RockType getRockType() {
		return rockType;
	}

	public void setRockType(RockType rockType) {
		this.rockType = rockType;
	}

	@Override
	public String toString() {
		return "Rock [posX=" + posX + ", posY=" + posY + ", rockType=" + rockType + "]";
	}
}
