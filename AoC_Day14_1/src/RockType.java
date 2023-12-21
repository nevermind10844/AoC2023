import java.util.Arrays;

public enum RockType {
	NONE('.', 0),
	ROUND('O', 1),
	SQUARE('#', 0);
	
	private char symbol;
	private int steps;
	
	private RockType(char symbol, int steps) {
		this.symbol = symbol;
		this.steps = steps;
	}
	
	public char getSymbol() {
		return this.symbol;
	}
	
	public int getSteps() {
		return this.steps;
	}
	
	public static RockType getBySymbol(char symbol) {
		return Arrays.asList(RockType.values()).stream().filter(type -> type.getSymbol() == symbol).findFirst().orElse(null);
	}
}
