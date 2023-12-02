import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		List<String> strings = InputReader.readInput();
		
		List<String> digitLines = new ArrayList<>();
		strings.forEach(line -> digitLines.add(line.replaceAll("[^0-9.]", "")));
		
		List<String> values = new ArrayList<>();
		digitLines.forEach(line -> values.add(String.valueOf(line.charAt(0)) + String.valueOf(line.charAt(line.length()-1))));
		
		Integer result = 0;
		
		for (String string : values) {
			result += Integer.valueOf(string);
		}
		
		System.out.println(result);
	}
}
