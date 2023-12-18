import java.util.Arrays;
import java.util.Optional;

public enum TileType {
	VERTICAL('|', Direction.NORTH, Direction.SOUTH),
	HORIZONTAL('-', Direction.WEST, Direction.EAST),
	SOUTH_EAST('F', Direction.SOUTH, Direction.EAST),
	SOUTH_WEST('7', Direction.SOUTH, Direction.WEST),
	NORTH_WEST('J', Direction.NORTH, Direction.WEST),
	NORTH_EAST('L', Direction.NORTH, Direction.EAST),
	GORUND('.', Direction.NONE, Direction.NONE),
	START('S', Direction.ALL, Direction.ALL);
	
	private char sign;
	private Direction a;
	private Direction b;

	private TileType(char sign, Direction a, Direction b) {
		this.sign = sign;
		this.a = a;
		this.b = b;
	}

	public static TileType getTileTypeBySign(char sign) {
		return Arrays.asList(TileType.values()).stream().filter(tt -> tt.getSign() == sign).findFirst().get();
	}

	public char getSign() {
		return this.sign;
	}
	
	public boolean connectiongToWest() {
		return this.a == Direction.ALL || this.a == Direction.WEST || this.b == Direction.WEST;
	}
	
	public boolean connectiongToEast() {
		return this.a == Direction.ALL || this.a == Direction.EAST || this.b == Direction.EAST;
	}
	
	public boolean connectiongToNorth() {
		return this.a == Direction.ALL || this.a == Direction.NORTH || this.b == Direction.NORTH;
	}

	public boolean connectiongToSouth() {
		return this.a == Direction.ALL || this.a == Direction.SOUTH || this.b == Direction.SOUTH;
	}
	
	public static TileType getTileTypeByDirections(Direction first, Direction second) {
		Optional<TileType> type = Arrays.asList(TileType.values()).stream().filter(tt -> tt.a == first && tt.b == second || tt.a == second && tt.b == first).findFirst();
		return type.isPresent() ? type.get() : null; 
	}
	
	public Direction getOpposingDirection(Direction d) {
		return this.a == d ? this.b : this.a;
	}
}