import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		List<String> strings = InputReader.readInput();
		List<Pixel> pixels = new ArrayList<>();
		Image image = null;

		for (int y = 0; y < strings.size(); y++) {
			char[] pixelArr = strings.get(y).toCharArray();
			for (int x = 0; x < pixelArr.length; x++) {
				Pixel p = new Pixel(new Vector2d(x, y), pixelArr[x]);
				pixels.add(p);
			}
		}
		
		image = new Image(pixels);
		image.postProcess();
		image.evaluate();
		
	}
}
