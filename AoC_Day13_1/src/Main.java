import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		List<String> strings = InputReader.readInput();
		List<WasteLand> maps = new ArrayList<>();
		
		WasteLand map = new WasteLand();
		int y = 0;
		
		for(int i=0; i<strings.size(); i++) {
			String s = strings.get(i);
			if(s.length() == 0) {
				maps.add(map);
				map = new WasteLand();
				y = 0;
			} else {
				for(int x=0; x<s.length(); x++) {
					Tile t = new Tile(TileType.getTileTypeBySymbol(s.charAt(x)), x, y);
					map.addTile(t);
				}
				y++;
			}
		}
		
		maps.add(map);

		for (WasteLand wasteLand : maps) {
			wasteLand.done();
			int currentResult = wasteLand.getLinesBefore() + (100 * wasteLand.getLinesAbove());
			List<Tile> tiles = wasteLand.getTiles();
			for(int i=0; i<tiles.size(); i++) {
				wasteLand.reset();
				if(i>0) {
					tiles.get(i-1).flipType();
				}
				tiles.get(i).flipType();
				wasteLand.done();
				int newResult = wasteLand.getLinesBefore() + (100 * wasteLand.getLinesAbove());
				if(newResult > 0 && newResult != currentResult)
					break;
			}
		}
		
		int linesBefore = maps.stream().mapToInt(WasteLand::getLinesBefore).sum();
		int linesAbove = maps.stream().mapToInt(WasteLand::getLinesAbove).sum();
		
		System.out.println(linesBefore + (100 * linesAbove));
	}
}
