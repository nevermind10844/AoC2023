import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		List<String> strings = InputReader.readInput();
		List<Race> races = new ArrayList<>();
		
		for (String line : strings) {
			String cleanLine = line.replace("  ", " ").replace("  ", " ").replace("  ", " ");
						
			String[] mainSplit = cleanLine.split(": ");
			String[] listSplit = mainSplit[1].replace(" ", "").split(" ");
			
			if(mainSplit[0].contains("Time")) {
				for(int i=0; i<listSplit.length; i++) {
					Race r = new Race();
					r.setDuration(new BigInteger(listSplit[i]));
					races.add(r);
				}
			} else if(mainSplit[0].contains("Distance")) {
				for(int i=0; i<listSplit.length; i++) {
					Race r = races.get(i);
					r.setRecordDistance(new BigInteger(listSplit[i]));
				}
			}
		}
		
		for (Race race : races) {
			BigInteger holddownTime = BigInteger.ZERO;
			
			while(holddownTime.compareTo(race.getDuration()) <= 0) {
				BigInteger raceTime = race.getDuration().subtract(holddownTime);
				BigInteger distance = raceTime.multiply(holddownTime);

				if(distance.compareTo(race.getRecordDistance()) > 0) {
					race.setMarginCounter(race.getMarginCounter().add(BigInteger.ONE));
					//System.out.println(String.format("%9s (-> %15s)     is better than %s)", holddownTime.toString(), distance, race.getRecordDistance()));
				}
				//else
					//System.out.println(String.format("%9s (-> %15s) is not better than %s)", holddownTime.toString(), distance, race.getRecordDistance()));
				
				holddownTime = holddownTime.add(BigInteger.ONE);
			}
			//System.out.println(race);
		}
		
		BigInteger result = BigInteger.ZERO;
		
		for (Race race : races) {
			if(result.compareTo(BigInteger.ZERO) <= 0)
				result = race.getMarginCounter();
			else
				result = result.multiply(race.getMarginCounter());
		}
		
		System.out.println(result);
	}
}
