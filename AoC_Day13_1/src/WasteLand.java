import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WasteLand {
	List<Tile> tiles;
	private int width;
	private int height;
	Map<Integer, Integer> rowValues;
	Map<Integer, Integer> colValues;
	private int linesAbove;
	private int linesBefore;

	public WasteLand() {
		this.tiles = new ArrayList<>();
		this.width = 0;
		this.height = 0;
		this.reset();
	}
	
	public void reset() {
		this.rowValues = new HashMap<>();
		this.colValues = new HashMap<>();
		this.linesAbove = 0;
		this.linesBefore = 0;
	}

	public List<Tile> getTiles() {
		return tiles;
	}

	public void setTiles(List<Tile> tiles) {
		this.tiles = tiles;
	}

	public void addTile(Tile tile) {
		this.tiles.add(tile);

		this.width = tile.getPosX() + 1 > this.width ? tile.getPosX() + 1 : this.width;
		this.height = tile.getPosY() + 1 > this.height ? tile.getPosY() + 1 : this.height;
	}

	public int getLinesAbove() {
		return linesAbove;
	}

	public int getLinesBefore() {
		return linesBefore;
	}
	
	public boolean isVerticalMirror() {
		return linesBefore > 0;
	}

	public boolean isHorizontalMirror() {
		return linesAbove > 0;
	}

	
	public void done() {
		this.done(false, false, 0);
	}
	
	public void done(boolean ignore, boolean vertical, int index) {
		for (int y = 0; y < this.height; y++) {
			String bin = "";
			for (int x = 0; x < this.width; x++) {
				bin += this.getTile(x, y).getType().getBinary();
			}
			this.rowValues.put(y, Integer.parseInt(bin, 2));
		}
//		System.out.println(this.rowValues);
		for (int x = 0; x < this.width; x++) {
			String bin = "";
			for (int y = 0; y < this.height; y++) {
				bin += this.getTile(x, y).getType().getBinary();
			}
			this.colValues.put(x, Integer.parseInt(bin, 2));
		}
//		System.out.println(this.colValues);

		this.findMirrors(ignore, vertical, index);
	}

	public void findMirrors(boolean ignore, boolean vertical, int index) {
		Map<Integer, Boolean> mirrorMap = new HashMap<>();
		for (int y = 0; y < this.height; y++) {
			mirrorMap.put(y, true);
		}
		
		if(ignore && !vertical)
			mirrorMap.put(index, false);
		
		mirrorMap.put(this.height - 1, false);
		for (int y = 0; y < this.height - 1; y++) {
			if (!this.rowValues.get(y).equals(this.rowValues.get(y + 1))) {
				mirrorMap.put(y, false);
			}
		}
//		System.out.println(mirrorMap);

		for (int y = 0; y < this.height; y++) {
			if (mirrorMap.get(y)) {
				if (isMirror(this.rowValues, this.height, y)) {
					this.linesAbove = y + 1;
					break;
				}
			}
		}
		
//		System.out.println(this.linesAbove);
		
		mirrorMap = new HashMap<>();
		for (int x = 0; x < this.width; x++) {
			mirrorMap.put(x, true);
		}
		
		if(ignore && vertical)
			mirrorMap.put(index, false);
		
		mirrorMap.put(this.width - 1, false);
		for (int x = 0; x < this.width - 1; x++) {
			if (!this.colValues.get(x).equals(this.colValues.get(x + 1))) {
				mirrorMap.put(x, false);
			}
		}
//		System.out.println(mirrorMap);
		
		for (int x = 0; x < this.width; x++) {
			if (mirrorMap.get(x)) {
				if (isMirror(this.colValues, this.width, x)) {
					this.linesBefore = x + 1;
					break;
				}
			}
		}
		
//		System.out.println(this.linesBefore);
	}

	private boolean isMirror(Map<Integer, Integer> values, int length, int index) {
		int linesAbove = index + 1;
		int linesBelow = length - index - 1;

		int checks = Math.min(linesAbove, linesBelow);

		int sum = 0;

		for (int i = 0; i < checks; i++) {
			sum += values.get(index - i) - values.get(index + 1 + i);
		}
		
		return sum == 0;
	}

	public Tile getTile(int posX, int posY) {
		return this.tiles.get(this.width * posY + posX);
	}

	@Override
	public String toString() {
		return "WasteLand [tiles=" + tiles + ", width=" + width + ", height=" + height + ", rowValues=" + rowValues
				+ "]";
	}
	
	public void print() {
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~");
		for(int y=0; y<this.height; y++) {
			char[] lineArr = new char[this.width];
			for(int x=0; x<this.width; x++) {
				lineArr[x] = this.getTile(x, y).getType().getSymbol();
			}
			System.out.println(new String(lineArr));
		}
		System.out.println("=========================");
		System.out.println();
	}

}
