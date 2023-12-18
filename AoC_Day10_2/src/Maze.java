import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Maze {
	private List<Tile> tiles;
	private Tile start;
	private TileType startTileType;

	private int width;
	private int height;

	public Maze(int width, int height) {
		this.tiles = new ArrayList<>();
		this.startTileType = TileType.START;
		this.width = width;
		this.height = height;
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

	public Tile getTileByPos(int x, int y) {
		return this.tiles.get(y * this.width + x);
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
		List<Tile> startingNeighbours = this.getNeighbours(this.start);
		Optional<Tile> easternNeighbour = startingNeighbours.stream()
				.filter(tile -> tile.getPosY() == this.start.getPosY() && tile.getPosX() == this.start.getPosX() + 1)
				.findFirst();
		Optional<Tile> westernNeighbour = startingNeighbours.stream()
				.filter(tile -> tile.getPosY() == this.start.getPosY() && tile.getPosX() == this.start.getPosX() - 1)
				.findFirst();
		Optional<Tile> northernNeighbour = startingNeighbours.stream()
				.filter(tile -> tile.getPosX() == this.start.getPosX() && tile.getPosY() == this.start.getPosY() - 1)
				.findFirst();
		Optional<Tile> southernNeighbour = startingNeighbours.stream()
				.filter(tile -> tile.getPosX() == this.start.getPosX() && tile.getPosY() == this.start.getPosY() + 1)
				.findFirst();

		Direction firstStartDirection = null;
		Direction secondStartDirection = null;

		Tile firstNeighbour = null;

		if (easternNeighbour.isPresent() && easternNeighbour.get().getType().connectiongToWest()) {
			firstStartDirection = Direction.EAST;
			firstNeighbour = easternNeighbour.get();
			firstNeighbour.setDiscoverdFrom(Direction.WEST);
		}

		if (westernNeighbour.isPresent() && westernNeighbour.get().getType().connectiongToEast()) {
			if (firstStartDirection == null) {
				firstStartDirection = Direction.WEST;
				firstNeighbour = westernNeighbour.get();
				firstNeighbour.setDiscoverdFrom(Direction.EAST);
			} else
				secondStartDirection = Direction.WEST;
		}

		if (northernNeighbour.isPresent() && northernNeighbour.get().getType().connectiongToSouth())
			if (firstStartDirection == null) {
				firstStartDirection = Direction.NORTH;
				firstNeighbour = northernNeighbour.get();
				firstNeighbour.setDiscoverdFrom(Direction.SOUTH);
			} else if (secondStartDirection == null)
				secondStartDirection = Direction.NORTH;

		if (southernNeighbour.isPresent() && southernNeighbour.get().getType().connectiongToNorth())
			if (firstStartDirection == null) {
				firstStartDirection = Direction.SOUTH;
				firstNeighbour = northernNeighbour.get();
				firstNeighbour.setDiscoverdFrom(Direction.NORTH);
			} else if (secondStartDirection == null)
				secondStartDirection = Direction.SOUTH;

		this.start.setType(TileType.getTileTypeByDirections(firstStartDirection, secondStartDirection));
		this.start.setStart(true);
		this.start.setCategory(Category.P);

		Tile currentTile = firstNeighbour;
		currentTile.setParent(this.start);
		currentTile.setCategory(Category.P);
		boolean done = false;
		while (!done) {
			Direction nextDirection = currentTile.getType().getOpposingDirection(currentTile.getDiscoverdFrom());
			Tile nextTile = new Tile(-1, -1, TileType.GORUND);
			Direction discoveredFrom = Direction.NONE;
			switch (nextDirection) {
			case EAST:
				nextTile = getTileByPos(currentTile.getPosX() + 1, currentTile.getPosY());
				discoveredFrom = Direction.WEST;
				break;
			case NORTH:
				nextTile = getTileByPos(currentTile.getPosX(), currentTile.getPosY() - 1);
				discoveredFrom = Direction.SOUTH;
				break;
			case SOUTH:
				nextTile = getTileByPos(currentTile.getPosX(), currentTile.getPosY() + 1);
				discoveredFrom = Direction.NORTH;
				break;
			case WEST:
				nextTile = getTileByPos(currentTile.getPosX() - 1, currentTile.getPosY());
				discoveredFrom = Direction.EAST;
				break;
			default:
				break;
			}
			nextTile.setDiscoverdFrom(discoveredFrom);
			nextTile.setParent(currentTile);
			nextTile.setCategory(Category.P);

			currentTile = nextTile;

			if (nextTile == this.start)
				done = true;

		}

	}

	@Override
	public String toString() {
		return "Maze [tiles=" + tiles + ", start=" + start + "]";
	}

	public void printTrace() {
		printTrace(Category.P);
	}
	
	public void printTrace(Category c) {
		List<Tile> pathTiles = this.tiles.stream().filter(tile -> tile.getCategory() == c).toList();
		for (int y = 0; y < this.height; y++) {
			int realY = y;
			List<Tile> line = pathTiles.stream().filter(tile -> tile.getPosY() == realY).toList();
			for (int x = 0; x < this.width; x++) {
				int realX = x;
				Optional<Tile> t = line.stream().filter(tile -> tile.getPosX() == realX).findFirst();
				if (t.isPresent())
					System.out.print(t.get().getType().getSign());
				else
					System.out.print(" ");
			}
			System.out.println();
		}
		System.out.println("===========================");
	}

	@SuppressWarnings("incomplete-switch")
	public void categorize() {
		Tile current = this.getStart();

		do {
			List<Direction> leftDirections = new ArrayList<>();
			List<Direction> rightDirections = new ArrayList<>();
			switch (current.getType()) {
				case HORIZONTAL:
					switch (current.getDiscoverdFrom()) {
						case EAST:
							leftDirections.add(Direction.SOUTH);
							rightDirections.add(Direction.NORTH);
							break;
						case WEST:
							leftDirections.add(Direction.NORTH);
							rightDirections.add(Direction.SOUTH);
							break;
					}
					break;
				case NORTH_EAST:
					switch (current.getDiscoverdFrom()) {
						case EAST:
							leftDirections.add(Direction.SOUTH);
							leftDirections.add(Direction.SOUTH_WEST);
							leftDirections.add(Direction.WEST);
							break;
						case NORTH:
							rightDirections.add(Direction.SOUTH);
							rightDirections.add(Direction.SOUTH_WEST);
							rightDirections.add(Direction.WEST);
							break;
					}
					break;
				case NORTH_WEST:
					switch (current.getDiscoverdFrom()) {
						case NORTH:
							leftDirections.add(Direction.EAST);
							leftDirections.add(Direction.SOUTH_EAST);
							leftDirections.add(Direction.SOUTH);
							break;
						case WEST:
							rightDirections.add(Direction.EAST);
							rightDirections.add(Direction.SOUTH_EAST);
							rightDirections.add(Direction.SOUTH);
							break;
					}
					break;
				case SOUTH_EAST:
					switch (current.getDiscoverdFrom()) {
						case SOUTH:
							leftDirections.add(Direction.WEST);
							leftDirections.add(Direction.NORTH_WEST);
							leftDirections.add(Direction.NORTH);
							break;
						case EAST:
							rightDirections.add(Direction.WEST);
							rightDirections.add(Direction.NORTH_WEST);
							rightDirections.add(Direction.NORTH);
							break;
					}
					break;
				case SOUTH_WEST:
					switch (current.getDiscoverdFrom()) {
						case SOUTH:
							rightDirections.add(Direction.EAST);
							rightDirections.add(Direction.NORTH_EAST);
							rightDirections.add(Direction.NORTH);
							break;
						case WEST:
							leftDirections.add(Direction.EAST);
							leftDirections.add(Direction.NORTH_EAST);
							leftDirections.add(Direction.NORTH);
							break;
					}
					break;
				case VERTICAL:
					switch (current.getDiscoverdFrom()) {
						case SOUTH:
							leftDirections.add(Direction.WEST);
							rightDirections.add(Direction.EAST);
							break;
						case NORTH:
							leftDirections.add(Direction.EAST);
							rightDirections.add(Direction.WEST);
							break;
					}
					break;
			}
			
			for (Direction direction : leftDirections) {
				int xDiff = 0;
				int yDiff = 0;
				switch (direction) {
					case NORTH:
						yDiff = -1;
						break;
					case NORTH_EAST:
						yDiff = -1;
						break;
					case NORTH_WEST:
						yDiff = -1;
						break;
					case SOUTH:
						yDiff = 1;
						break;
					case SOUTH_EAST:
						yDiff = 1;
						break;
					case SOUTH_WEST:
						yDiff = 1;
						break;
				}
				switch (direction) {
					case WEST:
						xDiff = -1;
						break;
					case NORTH_WEST:
						xDiff = -1;
						break;
					case SOUTH_WEST:
						xDiff = -1;
						break;
					case EAST:
						xDiff = 1;
						break;
					case SOUTH_EAST:
						xDiff = 1;
						break;
					case NORTH_EAST:
						xDiff = 1;
						break;
				}
				
				int posX = current.getPosX()+xDiff;
				int posY = current.getPosY()+yDiff;
				
				if(posX < 0 || posY < 0 || posX >= this.width || posY >= this.height)
					break;
				
				Tile t = this.getTileByPos(posX, posY);
				if(t.getCategory() == null)
					t.setCategory(Category.O);
			}
			
			for (Direction direction : rightDirections) {
				int xDiff = 0;
				int yDiff = 0;
				switch (direction) {
				case NORTH:
					yDiff = -1;
					break;
				case NORTH_EAST:
					yDiff = -1;
					break;
				case NORTH_WEST:
					yDiff = -1;
					break;
				case SOUTH:
					yDiff = 1;
					break;
				case SOUTH_EAST:
					yDiff = 1;
					break;
				case SOUTH_WEST:
					yDiff = 1;
					break;
				}
				switch (direction) {
				case WEST:
					xDiff = -1;
					break;
				case NORTH_WEST:
					xDiff = -1;
					break;
				case SOUTH_WEST:
					xDiff = -1;
					break;
				case EAST:
					xDiff = 1;
					break;
				case SOUTH_EAST:
					xDiff = 1;
					break;
				case NORTH_EAST:
					xDiff = 1;
					break;
				}
				
				int posX = current.getPosX()+xDiff;
				int posY = current.getPosY()+yDiff;
				
				if(posX < 0 || posY < 0 || posX >= this.width || posY >= this.height)
					break;
				
				Tile t = this.getTileByPos(posX, posY);
				if(t.getCategory() == null)
					t.setCategory(Category.I);
			}
			current = current.getParent();
		} while (!current.equals(start));
		
		List<Tile> uncategorized = this.tiles.stream().filter(tile -> tile.getCategory() == null).toList();
		
		while (uncategorized.size() > 0) {
			System.out.println(uncategorized.size() + " uncategorized tiles");
//		for(int i=0; i<1; i++) {
			for (Tile tile : uncategorized) {
				int x = tile.getPosX() - 1;
				int y = tile.getPosY() - 1;
				
				Tile t;
				
				if(x < 0 || y < 0 || x >= this.width || y >= this.height) {
					
				} else {
					t = this.getTileByPos(x, y);
					
					if(t.getCategory() != null && !t.getCategory().equals(Category.P))
						tile.setCategory(t.getCategory());
				}
					
				if(tile.getCategory() == null) {
					x = tile.getPosX() + 1;
					y = tile.getPosY() + 1;
					if(x < 0 || y < 0 || x >= this.width || y >= this.height) {
						
					} else {
						t = this.getTileByPos(x, y);
						
						if(t.getCategory() != null && t.getCategory() != Category.P)
							tile.setCategory(t.getCategory());
					}
				}
				
				if(tile.getCategory() == null) {
					x = tile.getPosX() - 1;
					y = tile.getPosY() + 1;
					if(x < 0 || y < 0 || x >= this.width || y >= this.height) {
						
					} else {
						t = this.getTileByPos(x, y);
						
						if(t.getCategory() != null && t.getCategory() != Category.P)
							tile.setCategory(t.getCategory());
					}
				}
				
				if(tile.getCategory() == null) {
					x = tile.getPosX() + 1;
					y = tile.getPosY() - 1;
					if(x < 0 || y < 0 || x >= this.width || y >= this.height) {
						
					} else {
						t = this.getTileByPos(x, y);
						
						if(t.getCategory() != null && t.getCategory() != Category.P)
							tile.setCategory(t.getCategory());
					}
				}
				
				if(tile.getCategory() == null) {
					x = tile.getPosX();
					y = tile.getPosY() - 1;
					if(x < 0 || y < 0 || x >= this.width || y >= this.height) {
						
					} else {
						t = this.getTileByPos(x, y);
						
						if(t.getCategory() != null && t.getCategory() != Category.P)
							tile.setCategory(t.getCategory());
					}
				}
				
				if(tile.getCategory() == null) {
					x = tile.getPosX();
					y = tile.getPosY() + 1;
					if(x < 0 || y < 0 || x >= this.width || y >= this.height) {
						
					} else {
						t = this.getTileByPos(x, y);
						
						if(t.getCategory() != null && t.getCategory() != Category.P)
							tile.setCategory(t.getCategory());
					}
				}
				
				if(tile.getCategory() == null) {
					x = tile.getPosX() - 1;
					y = tile.getPosY();
					if(x < 0 || y < 0 || x >= this.width || y >= this.height) {
						
					} else {
						t = this.getTileByPos(x, y);
						
						if(t.getCategory() != null && t.getCategory() != Category.P)
							tile.setCategory(t.getCategory());
					}
				}
				
				if(tile.getCategory() == null) {
					x = tile.getPosX() + 1;
					y = tile.getPosY();
					if(x < 0 || y < 0 || x >= this.width || y >= this.height) {
						
					} else {
						t = this.getTileByPos(x, y);
						
						if(t.getCategory() != null && t.getCategory() != Category.P)
							tile.setCategory(t.getCategory());
					}
				}
			}
			uncategorized = this.tiles.stream().filter(tile -> tile.getCategory() == null).toList();
		}
		
	}

}
