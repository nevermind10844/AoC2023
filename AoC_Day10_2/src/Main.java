import java.util.List;

public class Main {

	public static void main(String[] args) {
		List<String> strings = InputReader.readInput();
		
		int width = strings.get(0).length();
		int height = strings.size();
		
		Maze maze = new Maze(width, height);

		for (int y = 0; y < strings.size(); y++) {
			String line = strings.get(y);
			for (int x = 0; x < line.length(); x++) {
				Tile tile = new Tile(x, y, TileType.getTileTypeBySign(line.charAt(x)));
				maze.addTile(tile);
			}
		}
		
		maze.trace();
		maze.categorize();
		maze.printTrace();
		maze.printTrace(Category.P);
		List categoryPTiles = maze.getTiles().stream().filter(tile -> tile.getCategory() == Category.P).toList();
		List categoryOTiles = maze.getTiles().stream().filter(tile -> tile.getCategory() == Category.O).toList();
		List categoryITiles = maze.getTiles().stream().filter(tile -> tile.getCategory() == Category.I).toList();
		System.out.println(String.format("P: %12d O: %12d I: %12d", categoryPTiles.size(), categoryOTiles.size(), categoryITiles.size()));
	}
}
