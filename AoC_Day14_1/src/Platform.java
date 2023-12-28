import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Platform {
	private List<Rock> rocks = null;
	private List<Rock> roundRocks = null;
	private List<Rock> squareRocks = null;
	private List<Rock> artificialRocks = null;
	private int width;
	private int height;

	public Platform() {
		this.rocks = new ArrayList<>();
		this.roundRocks = new ArrayList<>();
		this.squareRocks = new ArrayList<>();
		this.artificialRocks = new ArrayList<>();
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
		for (Rock rock : this.squareRocks) {
			int posX = rock.getPosX();
			int posY = rock.getPosY();

//			// get information relevant when tilting east
//			for (int x = posX - 1; x >= 0; x--) {
//				if (this.getRock(x, posY).getRockType().equals(RockType.SQUARE))
//					break;
//				else {
//					for (int y = posY - 1; y >= -1; y--) {
//						if (y < 0) {
//							Rock artificialRock = new Rock(x, y, RockType.SQUARE);
//							artificialRock.setArtificial(true);
//							if (!this.artificialRocks.contains(artificialRock))
//								this.artificialRocks.add(artificialRock);
//							// this.squareRocks.add(artificialRock); //dont... you are iterating over that
//							// exact list!
//							rock.addReleventBit(artificialRock, rock.getPosY() - artificialRock.getPosY() + 1);
//							break;
//						} else {
//							Rock probe = this.getRock(x, y);
//							if (probe.getRockType().equals(RockType.SQUARE)) {
//								rock.addReleventBit(probe, rock.getPosY() - probe.getPosY() + 1);
//							}
//						}
//					}
//				}
//			}
//
			// get information relevant when tilting north
			for (int y = posY + 1; y < this.height; y++) {
				if (this.getRock(posX, y).getRockType().equals(RockType.SQUARE))
					break;
				else {
					for (int x = posX - 1; x >= -1; x--) {
						if (x < 0) {
							Rock artificialRock = new Rock(x, y, RockType.SQUARE);
							artificialRock.setArtificial(true);
							if (!this.artificialRocks.contains(artificialRock))
								this.artificialRocks.add(artificialRock);
							rock.addRelevantBitNorth(artificialRock, rock.getPosX() - artificialRock.getPosX() + 1);
							break;
						} else {
							Rock probe = this.getRock(x, y);
							if (probe.getRockType().equals(RockType.SQUARE)) {
								rock.addRelevantBitNorth(probe, rock.getPosX() - probe.getPosX() + 1);
							}
						}
					}
				}
			}
//
//			// get information relevant when tilting west
//			for (int x = posX + 1; x < this.width; x++) {
//				if (this.getRock(x, posY).getRockType().equals(RockType.SQUARE))
//					break;
//				else {
//					for (int y = posY + 1; y <= this.height; y++) {
//						if (y > this.height - 1) {
//							Rock artificialRock = new Rock(x, y, RockType.SQUARE);
//							artificialRock.setArtificial(true);
//							if (!this.artificialRocks.contains(artificialRock))
//								this.artificialRocks.add(artificialRock);
//							// this.squareRocks.add(artificialRock); //dont... you are iterating over that
//							// exact list!
//							rock.addReleventBitSouth(artificialRock, rock.getPosY() - artificialRock.getPosY() + 1);
//							break;
//						} else {
//							Rock probe = this.getRock(x, y);
//							if (probe.getRockType().equals(RockType.SQUARE)) {
//								rock.addReleventBitSouth(probe, probe.getPosY() - rock.getPosY() + 1);
//							}
//						}
//					}
//				}
//			}

			// get information relevant when tilting south
			for (int y = posY - 1; y >= -1; y--) {
				if (y < 0 || this.getRock(posX, y).getRockType().equals(RockType.SQUARE))
					break;
				else {
					for (int x = posX + 1; x < this.height; x++) {
						if (x >= this.height) {
							Rock artificialRock = new Rock(x, y, RockType.SQUARE);
							artificialRock.setArtificial(true);
							if (!this.artificialRocks.contains(artificialRock))
								this.artificialRocks.add(artificialRock);
							rock.addReleventBitSouth(artificialRock, artificialRock.getPosX() - rock.getPosX() - 1);
							break;
						} else {
							Rock probe = this.getRock(x, y);
							if (probe.getRockType().equals(RockType.SQUARE)) {
								rock.addReleventBitSouth(probe, probe.getPosX() - rock.getPosX() - 1);
							}
						}
					}
				}
			}
		}
	}

	public void tiltNorth() {

	}

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

	public void print(boolean relevance) {
		if (relevance)
			this.squareRocks.forEach(rock -> this.print(rock));
		else {
			for (int y = 0; y < this.height; y++) {
				char[] line = new char[this.width];
				for (int x = 0; x < this.width; x++) {
					line[x] = this.getRock(x, y).getRockType().getSymbol();
				}
				System.out.println(new String(line));
			}
		}
		System.out.println();
	}

	public void print(Rock relevance) {
		System.out.println(relevance);
		Map<Rock, Integer> relevantBitsSouth = relevance.getRelevantBitsSouth();
		for (Map.Entry<Rock, Integer> entry : relevantBitsSouth.entrySet()) {
			System.out.println(entry.getKey());
			System.out.println(entry.getValue());
		}
//		for (int y = 0; y < this.height; y++) {
//			char[] line = new char[this.width];
//			for (int x = 0; x < this.width; x++) {
//				line[x] = this.getRock(x, y).getRockType().getSymbol();
//			}
//			System.out.println(new String(line));
//		}
//		System.out.println();
	}
}
