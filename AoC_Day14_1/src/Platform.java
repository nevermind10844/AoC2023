import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Platform {
	private List<Rock> rocks = null;
	private List<Rock> roundRocks = null;
	private List<Rock> squareRocks = null;
	private Map<Integer, List<Rock>> verticalSquareRockMap;
	private Map<Integer, List<Rock>> horizontalSquareRockMap;
	private int width;
	private int height;

	public Platform() {
		this.rocks = new ArrayList<>();
		this.roundRocks = new ArrayList<>();
		this.squareRocks = new ArrayList<>();
		this.verticalSquareRockMap = new HashMap<>(this.width);
		this.horizontalSquareRockMap = new HashMap<>(this.width);
		this.width = 0;
		this.height = 0;
	}

	public List<Rock> getRocks() {
		return rocks;
	}

	public void setRocks(List<Rock> rocks) {
		this.rocks = rocks;
	}

	public void addRock(Rock rock) {
		this.rocks.add(rock);
		this.width = rock.getPosX() + 1 > this.width ? rock.getPosX() + 1 : this.width;
		this.height = rock.getPosY() + 1 > this.height ? rock.getPosY() + 1 : this.height;
		if (rock.getRockType().equals(RockType.ROUND))
			this.roundRocks.add(rock);
		else if (rock.getRockType().equals(RockType.SQUARE))
			this.squareRocks.add(rock);
	}

	public Rock getRock(int i) {
		return this.rocks.get(i);
	}

	public Rock getRock(int x, int y) {
		return this.getRock(this.width * y + x);
	}

	public void done() {
		for (int x = 0; x < this.width; x++) {
			int realX = x;
			this.verticalSquareRockMap.put(x, this.squareRocks.stream().filter(rock -> rock.getPosX() == realX)
					.sorted((a, b) -> a.getPosY() > b.getPosY() ? 1 : -1).toList());
		}
		for (int y = 0; y < this.width; y++) {
			int realY = y;
			this.verticalSquareRockMap.put(y, this.squareRocks.stream().filter(rock -> rock.getPosY() == realY)
					.sorted((a, b) -> a.getPosX() > b.getPosX() ? 1 : -1).toList());
		}
	}

	public void tiltNorth() {
		for (int x = 0; x < this.width; x++) {
			int realX = x;
			List<Rock> verticalSquareRocks = this.verticalSquareRockMap.get(x);
			List<Rock> verticalRoundRocks = this.roundRocks.stream().filter(rock -> rock.getPosX() == realX).toList();
			Rock first = null;
			int obstacleY = -1;
			Rock second = verticalSquareRocks.get(0);
			for (int i = 1; i < verticalSquareRocks.size(); i++) {
				int reach = this.height - 1;
				if (first != null) {
					obstacleY = first.getPosY();
				}
				if (second != null) {
					reach = second.getPosY();
				}
				int realObstacleY = obstacleY;
				int realReach = reach;
				List<Rock> roundRocksInReach = verticalRoundRocks.stream()
						.filter(rock -> rock.getPosY() > realObstacleY && rock.getPosY() < realReach).toList();
				int matchingRocks = roundRocksInReach.size();
				for (Rock rock : roundRocksInReach) {
					rock.setRockType(RockType.NONE);
				}
				for (int y = obstacleY + 1; y < obstacleY + matchingRocks; y++) {
					this.getRock(realX, y).setRockType(RockType.ROUND);
				}

				first = second;
				if (i >= verticalSquareRocks.size())
					second = null;
				else
					second = verticalSquareRocks.get(i);
			}

		}

		this.roundRocks = this.rocks.stream().filter(rock -> rock.getRockType().equals(RockType.ROUND)).toList();
	}

//	public void tiltNorth() {
//		for (Rock rock : this.roundRocks) {
//			int x = rock.getPosX();
//			int y = rock.getPosY();
//			if (rock.getRockType().equals(RockType.ROUND)) {
//				int mostNortherPlace = y;
//				Rock above = null;
//				for (int i = y - 1; i >= 0; i--) {
//					above = this.getRock(x, i);
//					if (above.getRockType().equals(RockType.NONE)) {
//						mostNortherPlace--;
//					} else {
//						break;
//					}
//				}
//
//				if (mostNortherPlace != y) {
//					Rock toSwitch = this.getRock(x, mostNortherPlace);
//					toSwitch.setRockType(RockType.ROUND);
//					rock.setRockType(RockType.NONE);
//				}
//
//				this.print();
//			}
//		}
//		
//		this.roundRocks = this.rocks.stream().filter(rock -> rock.getRockType().equals(RockType.ROUND)).toList();
//	}

	public void tiltWest() {
		for (Rock rock : this.roundRocks) {
			int x = rock.getPosX();
			int y = rock.getPosY();
			if (rock.getRockType().equals(RockType.ROUND)) {
				int mostWesternPlace = x;
				Rock above = null;
				for (int i = x - 1; i < this.width; i++) {
					above = this.getRock(i, y);
					if (above.getRockType().equals(RockType.NONE)) {
						mostWesternPlace++;
					} else {
						break;
					}
				}

				if (mostWesternPlace != x) {
					Rock toSwitch = this.getRock(mostWesternPlace, y);
					toSwitch.setRockType(RockType.ROUND);
					rock.setRockType(RockType.NONE);
				}

				this.print();
			}
		}

		this.roundRocks = this.rocks.stream().filter(rock -> rock.getRockType().equals(RockType.ROUND)).toList();
	}

	public long getLoad() {
		long load = 0;
		List<Rock> roundRocks = this.rocks.stream().filter(rock -> rock.getRockType().equals(RockType.ROUND)).toList();
		for (Rock rock : roundRocks)
			load += this.height - rock.getPosY();
		return load;
	}

	@Override
	public String toString() {
		return "Platform [rocks=" + rocks + ", width=" + width + ", height=" + height + "]";
	}

	public void print() {
		for (int y = 0; y < this.height; y++) {
			char[] line = new char[this.width];
			for (int x = 0; x < this.width; x++) {
				line[x] = this.getRock(x, y).getRockType().getSymbol();
			}
			System.out.println(new String(line));
		}
		System.out.println();
	}
}
