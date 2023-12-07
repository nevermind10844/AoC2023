import java.util.Arrays;
import java.util.List;

public enum Card {
	TWO('2', 2),
	THREE('3', 3),
	FOUR('4', 4),
	FIVE('5', 5),
	SIX('6', 6),
	SEVEN('7', 7),
	EIGHT('8', 8),
	NINE('9', 9),
	TEN('T', 10),
	JOKER('J', 11),
	QUEEN('Q', 12),
	KING('K', 13),
	ACE('A', 14);
	
	private char name;
	private int strength;
	
	private Card(char name, int strength) {
		this.name = name;
		this.strength = strength;
	}
	
	public char getName () {
		return this.name;
	}
	
	public int getStrength() {
		return this.strength;
	}
	
	public static Card getCardByName(char name) {
		return Arrays.asList(Card.values()).stream().filter(card -> name == card.getName()).findFirst().get();
	}
	
	public static List<Card> asList(){
		return Arrays.asList(Card.values());
	}
}
