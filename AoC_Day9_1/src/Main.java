import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		List<String> strings = InputReader.readInput();
		List<SingleValueHistory> historyList = new ArrayList<>();
		
		for (String line : strings) {
			SingleValueHistory history = new SingleValueHistory();
			
			String[] mainSplit = line.split(" ");
			for(int i=0; i<mainSplit.length; i++) {
				history.addHistory(Long.valueOf(mainSplit[i]));
			}
			
			historyList.add(history);
		}

		Long sum = 0L;
		for (SingleValueHistory singleValueHistory : historyList) {
			sum += singleValueHistory.evaluate();
		}
		System.out.println(sum);
	}
}
