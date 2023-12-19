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
		
		int verticals = 0;
		int horizontals = 0;

		for (WasteLand wasteLand : maps) {
			wasteLand.done();
			boolean isVertical = wasteLand.isVerticalMirror();
			boolean isHorizontal = wasteLand.isHorizontalMirror();
			
			System.out.println(String.format("isVertical: %b isHorizontal: %b", isVertical, isHorizontal));
			
			//int currentResult = wasteLand.getLinesBefore() + (100 * wasteLand.getLinesAbove());
			List<Tile> tiles = wasteLand.getTiles();
			for(int i=0; i<tiles.size(); i++) {
				wasteLand.reset();
				if(i>0) {
					tiles.get(i-1).flipType();
				}
				tiles.get(i).flipType();
				wasteLand.done();
				if(isVertical) {
					int newVertical = wasteLand.getLinesBefore();
					if(newVertical > 0) {
						verticals += newVertical;
						break;
					}
				} else if(isHorizontal){
					int newHorizontal = wasteLand.getLinesAbove();
					if(newHorizontal > 0) {
						horizontals += newHorizontal;
						break;
					}
				}
			
			}
		}
		
		
		
		System.out.println(verticals + (100 * horizontals));
	}
}
