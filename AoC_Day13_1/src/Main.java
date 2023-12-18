import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		List<String> strings = InputReader.readInput();
		List<Map> maps = new ArrayList<>();
		
		Map map = new Map();
		int y = 0;
		
		for(int i=0; i<strings.size(); i++) {
			String s = strings.get(i);
			if(s.length() == 0) {
				maps.add(map);
				map = new Map();
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
		
		maps.forEach(m -> System.out.println(m));
	}
}
