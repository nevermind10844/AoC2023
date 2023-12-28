import java.util.List;

public class Main {

	public static void main(String[] args) {
		List<String> strings = InputReader.readInput();
		
		Platform platform = new Platform();
		
		for(int y=0; y<strings.size(); y++) {
			String line = strings.get(y);
			char[] lineChars = line.toCharArray();
			for(int x=0; x<line.length(); x++) {
				platform.addRock(new Rock(x, y, RockType.getBySymbol(lineChars[x])));
			}
		}
		
		platform.done();
		platform.print(true);
//		platform.tiltNorth();
		long load = platform.getLoad();
		System.out.println(load);
		
	}
}
