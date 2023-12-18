import java.util.Arrays;

public enum TileType {
	VERTICAL('|'),
	HORIZONTAL('-'),
	SOUTH_EAST('F'),
	SOUTH_WEST('7'),
	NORTH_WEST('J'),
	NORTH_EAST('L'),
	GORUND('.'),
	START('S');
	
	private char sign;

	private TileType(char sign) {
		this.sign = sign;
	}

	public static TileType getTileTypeBySign(char sign) {
		return Arrays.asList(TileType.values()).stream().filter(tt -> tt.getSign() == sign).findFirst().get();
	}

	public char getSign() {
		return this.sign;
	}
	
	public boolean connectiongToWest() {
		return this.sign == '-' || this.sign == '7' || this.sign == 'J' || this.sign == 'S';
	}
	
	public boolean connectiongToEast() {
		return this.sign == '-' || this.sign == 'F' || this.sign == 'L' || this.sign == 'S';
	}
	
	public boolean connectiongToNorth() {
		return this.sign == '|' || this.sign == 'J' || this.sign == 'L' || this.sign == 'S';
	}

	public boolean connectiongToSouth() {
		return this.sign == '|' || this.sign == 'F' || this.sign == '7' || this.sign == 'S';
	}
	

}