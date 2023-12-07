import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		List<String> strings = InputReader.readInput();
		List<Hand> hands = new ArrayList<>();
		
		for (String string : strings) {
			Hand h = new Hand();

			String[] mainSplit = string.split(" ");
			char[] chars = mainSplit[0].toCharArray();
			
			for(int i=0; i<chars.length; i++){
				Card c = Card.getCardByName(chars[i]);
				h.addCard(c);
			}
			
			h.setBid(Integer.valueOf(mainSplit[1]));
			hands.add(h);
		}
		
		hands.forEach(hand -> System.out.println(hand));
		
		Collections.sort(hands);
		
		int result = 0;
		
		for(int i=0; i<hands.size(); i++) {
			result += hands.get(i).getBid() * (i+1);
		}
		
		System.out.println(result);
	}
}
