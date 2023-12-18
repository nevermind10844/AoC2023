import java.util.ArrayList;
import java.util.List;

public class Map {
	List<Tile> tiles;

	public Map() {
		this.tiles = new ArrayList<>();
	}

	public List<Tile> getTiles() {
		return tiles;
	}

	public void setTiles(List<Tile> tiles) {
		this.tiles = tiles;
	}
	
	public void addTile(Tile tile) {
		this.tiles.add(tile);
	}

	@Override
	public String toString() {
		return "Map [tiles=" + tiles + "]";
	}
	
	
}
