import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Maze {
	private List<Tile> tiles;
	private Tile start;
	private TileType startTileType;

	public Maze() {
		this.tiles = new ArrayList<>();
		this.startTileType = TileType.START;
	}

	public List<Tile> getTiles() {
		return tiles;
	}

	public Tile getStart() {
		return start;
	}

	public void setStart(Tile start) {
		this.start = start;
	}

	public TileType getStartTileType() {
		return this.startTileType;
	}

	public void setStartTileType(TileType type) {
		this.startTileType = type;
	}

	public void addTile(Tile tile) {
		this.tiles.add(tile);
		if (tile.getType() == TileType.START)
			this.start = tile;
	}

	public List<Tile> getNeighbours(Tile tile) {
		List<Tile> horizontalPosssibleNeighbours = this.tiles.stream().filter(t -> t.getPosY() == tile.getPosY())
				.toList();
		List<Tile> horizontalNeighbours = horizontalPosssibleNeighbours.stream()
				.filter(t -> t.getPosX() == tile.getPosX() + 1 || t.getPosX() == tile.getPosX() - 1).toList();
		List<Tile> verticalPosssibleNeighbours = this.tiles.stream().filter(t -> t.getPosX() == tile.getPosX())
				.toList();
		List<Tile> verticalNeighbours = verticalPosssibleNeighbours.stream()
				.filter(t -> t.getPosY() == tile.getPosY() + 1 || t.getPosY() == tile.getPosY() - 1).toList();

		List<Tile> neighbours = new ArrayList<>();
		neighbours.addAll(horizontalNeighbours);
		neighbours.addAll(verticalNeighbours);

		return neighbours;
	}

	public void trace() {
		Tile currentTile = this.start;
		boolean done = false;
		while (!done) {
			List<Tile> neighbours = this.getNeighbours(currentTile);
			for (Tile tile : neighbours) {
				if (!tile.equals(currentTile.getParent())) {
					if (tile.getPosX() > currentTile.getPosX() && currentTile.getType().connectiongToEast() && tile.getType().connectiongToWest()) {
						if(tile.equals(this.start)) 
							done = true;
//						System.out.println(String.format("%s (%4d : %4d)", currentTile.getType().getSign(), currentTile.getPosX(), currentTile.getPosY()));
						tile.setParent(currentTile);
						currentTile = tile;
						break;
					} else if (tile.getPosX() < currentTile.getPosX() && currentTile.getType().connectiongToWest() && tile.getType().connectiongToEast()) {
						tile.setParent(currentTile);
						if(tile.equals(this.start))
							done = true;
//						System.out.println(String.format("%s (%4d : %4d)", currentTile.getType().getSign(), currentTile.getPosX(), currentTile.getPosY()));
						currentTile = tile;
						break;
					} else if (tile.getPosY() < currentTile.getPosY() && currentTile.getType().connectiongToNorth() && tile.getType().connectiongToSouth()) {
						tile.setParent(currentTile);
						if(tile.equals(this.start))
							done = true;
//						System.out.println(String.format("%s (%4d : %4d)", currentTile.getType().getSign(), currentTile.getPosX(), currentTile.getPosY()));
						currentTile = tile;
						break;
					} else if (tile.getPosY() > currentTile.getPosY() && currentTile.getType().connectiongToSouth() && tile.getType().connectiongToNorth()) {
						tile.setParent(currentTile);
						if(tile.equals(this.start))
							done = true;
//						System.out.println(String.format("%s (%4d : %4d)", currentTile.getType().getSign(), currentTile.getPosX(), currentTile.getPosY()));
						currentTile = tile;
						break;
					}
					if(done)
						break;
				}
			}
		}

	}

	@Override
	public String toString() {
		return "Maze [tiles=" + tiles + ", start=" + start + "]";
	}

}
