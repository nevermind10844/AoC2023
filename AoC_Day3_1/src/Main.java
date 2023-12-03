import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class Main {

	public static void main(String[] args) {
		ReaderSate state = ReaderSate.START;

		List<String> strings = InputReader.readInput();

		List<Numero> numbers = new ArrayList<>();
		List<Symbol> symbols = new ArrayList<>();

		for (int i = 0; i < strings.size(); i++) {
			String line = strings.get(i);
			Numero number = null;
			Symbol s = null;
			for (int j = 0; j < line.length(); j++) {
				char c = line.charAt(j);
				switch (state) {
				case START: {
					if (Character.isDigit(c)) {
						number = new Numero();
						number.setRow(i);
						number.setCol(j);
						number.appendChar(c);
						state = ReaderSate.NUMBER;
					} else {
						if (c == '.')
							state = ReaderSate.DOT;
						else {
							state = ReaderSate.SYMBOL;
							s = new Symbol(i, j);
						}
					}
					break;
				}
				case DOT:
					if (Character.isDigit(c)) {
						number = new Numero();
						number.setRow(i);
						number.setCol(j);
						number.appendChar(c);
						state = ReaderSate.NUMBER;
					} else {
						if (c == '.')
							state = ReaderSate.DOT;
						else {
							state = ReaderSate.SYMBOL;
							s = new Symbol(i, j);
							symbols.add(s);
						}
					}
					break;
				case END:
					break;
				case NUMBER:
					if (Character.isDigit(c)) {
						number.appendChar(c);
					} else {
						numbers.add(number);
						number = null;
						if (c == '.')
							state = ReaderSate.DOT;
						else {
							state = ReaderSate.SYMBOL;
							s = new Symbol(i, j);
							symbols.add(s);
						}
					}
					break;
				case SYMBOL:
					if (Character.isDigit(c)) {
						number = new Numero();
						number.setRow(i);
						number.setCol(j);
						number.appendChar(c);
						state = ReaderSate.NUMBER;
					} else {
						if (c == '.')
							state = ReaderSate.DOT;
						else {
							state = ReaderSate.SYMBOL;
							s = new Symbol(i, j);
							symbols.add(s);
						}
					}
					break;
				default:
					break;
				}

				if (j == line.length() - 1) {
					if (state == ReaderSate.NUMBER) {
						numbers.add(number);
						number = null;
					}
					state = ReaderSate.START;
				}
			}
		}
		
		for (Numero number : numbers) {
			List<Symbol> relevantSymbols = symbols.stream().filter(symbol -> symbol.getRow() >= number.getRow()-1 && symbol.getRow() <= number.getRow()+1).toList();
			List<Symbol> hittingSymbols = relevantSymbols.stream().filter(symbol -> symbol.getCol() >= number.getCol()-1 && symbol.getCol() <= number.getCol()+number.getLength()).toList();
			if(hittingSymbols.size() > 0)
				number.setSchematic(true);
		}
		
		int result = 0;
		
		for (Numero number : numbers) {
			System.out.println(number);
			if(number.isSchematic())
				result += number.getValue();
		}
		
		System.out.println(result);
	}

}
