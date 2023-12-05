import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class Main {

	public static void main(String[] args) {
		List<String> strings = InputReader.readInput();
		
		List<Game> games = new ArrayList<>();
		
		for (String line : strings) {
			Game g = new Game();
			String[] mainSplit = line.split(": ");
			g.setId(Integer.valueOf(mainSplit[0].replace("  ", " ").replace("  ", " ").split(" ")[1]));
			String[] numberSplit = mainSplit[1].split(" \\| ");
			
			String[] winningNumbers = numberSplit[0].replace("  ", " ").split(" ");
			String[] drawnNumbers = numberSplit[1].replace("  ", " ").split(" ");
			
			for(int i=0; i< winningNumbers.length; i++) {
				if(!StringUtils.isBlank(winningNumbers[i]))
					g.addWinningNumber(Integer.valueOf(winningNumbers[i]));
			}
			
			for(int i=0; i< drawnNumbers.length; i++) {
				if(!StringUtils.isBlank(drawnNumbers[i]))
					g.addDrawnNumber(Integer.valueOf(drawnNumbers[i]));
			}
			
			games.add(g);
		}
		
		for(Game g : games){
			g.addCopies(games);
		}
		
		Integer result = 0;
		result = countGames(result, games);
		
		
		System.out.println(result);
	}
	
	private static int countGames(int count, List<Game> games) {
		count = games.size();
		for (Game game : games) {
			count += countGames(count, game.getCopies());
		}
		return count;
	}
}
