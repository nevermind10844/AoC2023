import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class InputReader {
	
	public static List<String> readInput(){
		return readInput("ext/input.txt");
	}
	
	public static List<String> readInput(String file){
		FileInputStream fis;
		BufferedReader br;
		List<String> strings = new ArrayList<>();
		try {
			fis = new FileInputStream(new File(file));
			br = new BufferedReader(new InputStreamReader(fis));
			String line;
			while ((line = br.readLine()) != null) {
				strings.add(line);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return strings;
	}
}