import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		List<String> strings = InputReader.readInput();
		List<Game> gameList = new ArrayList<>();

		int maxRed = 12;
		int maxGreen = 13;
		int maxBlue = 14;

		for (String line : strings) {
			Game g = new Game();

			String[] mainSplit = line.split(": ");
			g.setId(Integer.valueOf(mainSplit[0].split(" ")[1]));

			String[] drawSplit = mainSplit[1].split("; ");
			for (int i = 0; i < drawSplit.length; i++) {
				Draw draw = new Draw();
				String drawString = drawSplit[i];
				String[] subDraw = drawString.split(", ");
				for (int j = 0; j < subDraw.length; j++) {
					String[] lastSplitIPromise = subDraw[j].split(" ");
					if (lastSplitIPromise[1].contains("blue"))
						draw.setShownBlue(Integer.valueOf(lastSplitIPromise[0]));
					else if (lastSplitIPromise[1].contains("red"))
						draw.setShownRed(Integer.valueOf(lastSplitIPromise[0]));
					else if (lastSplitIPromise[1].contains("green"))
						draw.setShownGreen(Integer.valueOf(lastSplitIPromise[0]));
				}
				g.addDraw(draw);
			}

			gameList.add(g);
		}

		for (Game game : gameList) {
			game.setPossible(true);
			
			for (Draw draw : game.getDrawList()) {
				if(draw.getShownBlue() > maxBlue)
					game.setPossible(false);
				if(draw.getShownGreen() > maxGreen)
					game.setPossible(false);
				if(draw.getShownRed() > maxRed)
					game.setPossible(false);
			}
		}
		
		int result = 0;
		
		for (Game game : gameList) {
			if(game.isPossible())
				result += game.getId();
		}

		System.out.println(result);
	}
}
