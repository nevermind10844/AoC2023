import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Image {
	private List<Pixel> pixels;
	private int width;
	private int height;

	public Image(List<Pixel> pixels) {
		this.pixels = pixels;
		this.setDimensions();
	}

	private void setDimensions() {
		Optional<Pixel> highestXPixel = this.pixels.stream()
				.max((p1, p2) -> Integer.compare(p1.getPos().getX(), p2.getPos().getX()));
		Optional<Pixel> highestYPixel = this.pixels.stream()
				.max((p1, p2) -> Integer.compare(p1.getPos().getY(), p2.getPos().getY()));

		if (highestXPixel.isPresent())
			this.width = highestXPixel.get().getPos().getX() + 1;

		if (highestYPixel.isPresent())
			this.height = highestYPixel.get().getPos().getY() + 1;
	}

	public List<Pixel> getPixels() {
		return this.pixels;
	}

	public void postProcess() {
		printImage();
		
		List<Integer> colsWithGalaxies = this.pixels.stream().filter(p -> p.getSign() == '#')
				.map(p -> p.getPos().getX()).distinct().toList();
		for (int x = this.width - 1; x >= 0; x--) {
			if (!colsWithGalaxies.contains(Integer.valueOf(x))) {
				insertColAt(x);
			}
		}
		
		List<Integer> rowsWithGalaxies = this.pixels.stream().filter(p -> p.getSign() == '#')
				.map(p -> p.getPos().getY()).distinct().toList();
		for (int y = this.height - 1; y >= 0; y--) {
			if (!rowsWithGalaxies.contains(Integer.valueOf(y))) {
				insertRowAt(y);
			}
		}
		System.out.println();
		printImage();
	}
	
	public void evaluate() {
		List<Pixel> galaxies = this.pixels.stream().filter(p -> p.getSign() == '#').toList();
		for(int i=0;i<galaxies.size(); i++) {
			galaxies.get(i).setIdentifier(i);
		}
		
		List<Edge> edges = new ArrayList<>();
		
		for (Pixel pixel : galaxies) {
			for (Pixel p : galaxies) {
				if(!p.equals(pixel)) {
					Edge e = new Edge(pixel.getPos(), p.getPos());
					if(!edges.contains(e))
						edges.add(e);
				}
			}
		}
		
		int sum = edges.stream().mapToInt(Edge::getDistance).sum();
		
		System.out.println(sum);
	}

	private void insertColAt(int x) {
		List<Pixel> pixelsToShift = this.pixels.stream().filter(p -> p.getPos().getX() >= x).toList();
		pixelsToShift.forEach(p -> p.getPos().setX(p.getPos().getX() + 1));
		for (int y = 0; y < this.height; y++) {
			Pixel p = new Pixel(new Vector2d(x, y), '.');
			this.pixels.add(p);
		}
		this.setDimensions();
	}
	
	private void insertRowAt(int y) {
		List<Pixel> pixelsToShift = this.pixels.stream().filter(p -> p.getPos().getY() >= y).toList();
		pixelsToShift.forEach(p -> p.getPos().setY(p.getPos().getY() + 1));
		for (int x = 0; x < this.width; x++) {
			Pixel p = new Pixel(new Vector2d(x, y), '.');
			this.pixels.add(p);
		}
		this.setDimensions();
	}

	@Override
	public String toString() {
		return "Image [pixels=" + pixels + "]";
	}

	public void printImage() {
		for (int y = 0; y < this.height; y++) {
			int realY = y;
			for (int x = 0; x < this.width; x++) {
				int realX = x;
				Optional<Pixel> pixel = this.pixels.stream()
						.filter(p -> p.getPos().getX() == realX && p.getPos().getY() == realY).findFirst();
				System.out.print(pixel.get().getSign());
			}
			System.out.println();
		}
	}

}
