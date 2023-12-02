import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		List<String> strings = InputReader.readInput();
		List<Game> gameList = new ArrayList<>();

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
		
		int result = 0;
		
		for (Game game : gameList) {
			for (Draw draw : game.getDrawList()) {
				if(draw.getShownBlue() > game.getMaxBlue())
					game.setMaxBlue(draw.getShownBlue());
				if(draw.getShownRed() > game.getMaxRed())
					game.setMaxRed(draw.getShownRed());
				if(draw.getShownGreen() > game.getMaxGreen())
					game.setMaxGreen(draw.getShownGreen());
			}
			
			game.setPower(game.getMaxBlue() * game.getMaxGreen() * game.getMaxRed());
			result += game.getPower();
		}
		
		System.out.println(result);
	}
}
