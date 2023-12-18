public class Tile {
	private int posX;
	private int posY;
	private TileType type;
	private int distance;
	private Tile parent;

	public Tile(int posX, int posY, TileType type) {
		this.posX = posX;
		this.posY = posY;
		this.type = type;
		this.distance = 0;
		this.parent = null;
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

	public TileType getType() {
		return type;
	}

	public void setType(TileType type) {
		this.type = type;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public Tile getParent() {
		return parent;
	}

	public void setParent(Tile parent) {
		this.parent = parent;
	}

	public boolean isNextTo(Tile tile) {
		if (tile.posY == tile.getPosY()) {
			return tile.getPosX() >= this.posX - 1 && tile.getPosX() <= this.posX + 1;
		} else if (tile.posX == tile.getPosX()) {
			return tile.getPosX() >= this.posY - 1 && tile.getPosY() <= this.posY + 1;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Tile [posX=" + posX + ", posY=" + posY + ", type=" + type + ", distance=" + distance + ", parent="
				+ parent + "]";
	}

}
