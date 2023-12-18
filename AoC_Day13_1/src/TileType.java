import java.util.Arrays;

public enum TileType {
	ASH('.', '0'), ROCK('#', '1');

	private char symbol;
	private char binary;

	private TileType(char symbol, char binary) {
		this.symbol = symbol;
		this.binary = binary;
	}

	public char getSymbol() {
		return this.symbol;
	}

	public static TileType getTileTypeBySymbol(char c) {
		return Arrays.asList(TileType.values()).stream().filter(type -> type.getSymbol() == c).findFirst().get();
	}
	
	public char getBinary() {
		return this.binary;
	}
}
