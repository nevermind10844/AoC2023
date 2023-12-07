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
			List<Card> jokerCards = Card.asList();
			for (Card jokerCard : jokerCards) {
				String jokerString = this.original.replace('J', jokerCard.getName());
				HandEvaluation currentEval = HandEvaluation.HIGH_CARD;
				for(int i=0; i<jokerString.length(); i++) {
					char c = jokerString.charAt(i);
					String result = jokerString.replace(String.valueOf(c), "");
					if(result.length() < jokerString.length() -1) {
						int howManyRemoved = jokerString.length() - result.length();
						if(howManyRemoved == 5)
							currentEval = HandEvaluation.FIVE_OF_A_KIND;
						else if (howManyRemoved == 4)
							currentEval = HandEvaluation.FOUR_OF_A_KIND;
						else if(howManyRemoved == 3) {
							currentEval = HandEvaluation.THREE_OF_A_KIND;
							if (result.charAt(0) == result.charAt(1))
								currentEval = HandEvaluation.FULL_HOUSE;
						} else if(howManyRemoved == 2) {
							currentEval = HandEvaluation.ONE_PAIR;
							if(result.charAt(0) == result.charAt(1) && result.charAt(1) == result.charAt(2))
								currentEval = HandEvaluation.FULL_HOUSE;
							else if(result.charAt(0) == result.charAt(1) || result.charAt(1) == result.charAt(2) || result.charAt(0) == result.charAt(2)){
								currentEval = HandEvaluation.TWO_PAIR;
							}
						}
					}
				}
				if(currentEval.getValue() > this.eval.getValue())
					this.eval = currentEval;
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