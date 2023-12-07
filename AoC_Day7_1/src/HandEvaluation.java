
public enum HandEvaluation {

	FIVE_OF_A_KIND(70),
	FOUR_OF_A_KIND(60),
	FULL_HOUSE(50),
	THREE_OF_A_KIND(40),
	TWO_PAIR(30),
	ONE_PAIR(20),
	HIGH_CARD(0);
	
	private int value;
	
	private HandEvaluation(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return this.value;
	}
	
}
