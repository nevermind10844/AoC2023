public class Tile {
	private TileType type;
	private int posX;
	private int posY;

	public Tile(TileType type, int posX, int posY) {
		super();
		this.type = type;
		this.posX = posX;
		this.posY = posY;
	}

	public TileType getType() {
		return type;
	}

	public void setType(TileType type) {
		this.type = type;
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

	@Override
	public String toString() {
		return "Tile [type=" + type + ", posX=" + posX + ", posY=" + posY + "]";
	}

}