import java.util.List;

public class Main {

	public static void main(String[] args) {
		List<String> strings = InputReader.readInput();
		
//		int width = strings.get(0).length();
//		int height = strings.size();
		
		Maze maze = new Maze();

		for (int y = 0; y < strings.size(); y++) {
			String line = strings.get(y);
			for (int x = 0; x < line.length(); x++) {
				Tile tile = new Tile(x, y, TileType.getTileTypeBySign(line.charAt(x)));
				if(tile.getType() != TileType.GORUND)
					maze.addTile(tile);
			}
		}
		
		maze.trace();
		
		Tile start = maze.getStart();
		Tile current = start.getParent();
		
		int count = 0;
		while(!current.equals(start)) {
			count++;
			current = current.getParent();
		}
		
		
		System.out.println(Math.round(count/2f));
		
	}
}
