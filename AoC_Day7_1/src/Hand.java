import java.util.ArrayList;
import java.util.List;

public class Hand implements Comparable<Hand>{
	private List<Card> cards;
	private String original;
	private int bid;
	private int value;
	private HandEvaluation eval;
	
	public Hand() {
		this.cards = new ArrayList<>();
		this.value = 0;
		this.bid = 0;
		this.original = "";
		this.eval = HandEvaluation.HIGH_CARD;
	}

	public List<Card> getCards() {
		return cards;
	}
	
	public void addCard(Card card) {
		this.cards.add(card);
		this.original = this.original.concat(String.valueOf(card.getName()));
		if(this.cards.size() >= 5) {
			for(int i=0; i< this.original.length(); i++) {
				char c = this.original.charAt(i);
				String result = this.original.replace(String.valueOf(c), "");
				if(result.length() < this.original.length() -1) {
					int howManyRemoved = this.original.length() - result.length();
					if(howManyRemoved == 5)
						this.eval = HandEvaluation.FIVE_OF_A_KIND;
					else if (howManyRemoved == 4)
						this.eval = HandEvaluation.FOUR_OF_A_KIND;
					else if(howManyRemoved == 3) {
						this.eval = HandEvaluation.THREE_OF_A_KIND;
						if (result.charAt(0) == result.charAt(1))
							this.eval = HandEvaluation.FULL_HOUSE;
					} else if(howManyRemoved == 2) {
						this.eval = HandEvaluation.ONE_PAIR;
						if(result.charAt(0) == result.charAt(1) && result.charAt(1) == result.charAt(2))
							this.eval = HandEvaluation.FULL_HOUSE;
						else if(result.charAt(0) == result.charAt(1) || result.charAt(1) == result.charAt(2) || result.charAt(0) == result.charAt(2)){
							this.eval = HandEvaluation.TWO_PAIR;
						}
					}
				}
			}
			this.value = this.eval.getValue();
		}
	}
	
	public int getBid() {
		return this.bid;
	}
	
	public void setBid(int bid) {
		this.bid = bid;
	}

	public int getValue() {
		return this.value;
	}

	@Override
	public int compareTo(Hand o) {
		if(this.value > o.value)
			return 1;
		else if(this.value < o.value)
			return -1;
		else {
			for(int i=0; i<5; i++) {
				int thisStrength = this.cards.get(i).getStrength();
				int thatStrength = o.cards.get(i).getStrength();
				if(thisStrength > thatStrength)
					return 1;
				else if (thisStrength < thatStrength)
					return -1;
			}
			return 0;
		}
	}
	
	@Override
	public String toString() {
		return "Hand [cards=" + cards + ", original=" + original + ", bid=" + bid + ", value=" + value + ", eval="
				+ eval + "]";
	}
}