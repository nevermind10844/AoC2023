import java.util.Arrays;

public enum TileType {
	ASH('.'), ROCK('#');

	private char symbol;

	private TileType(char symbol) {
		this.symbol = symbol;
	}

	public char getSymbol() {
		return this.symbol;
	}

	public static TileType getTileTypeBySymbol(char c) {
		return Arrays.asList(TileType.values()).stream().filter(type -> type.getSymbol() == c).findFirst().get();
	}
}
