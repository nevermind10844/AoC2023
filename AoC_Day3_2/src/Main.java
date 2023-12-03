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
							s.setAsterisc(c == '*');
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
							s.setAsterisc(c == '*');
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
							s.setAsterisc(c == '*');
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
							s.setAsterisc(c == '*');
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
		
		List<Symbol> asteriscs = symbols.stream().filter(symbol -> symbol.isAsterisc()).toList();
		
		int result = 0;
		
		for (Symbol symbol : asteriscs) {
			List<Numero> relevantNumbers = numbers.stream().filter(number -> number.getRow() >= symbol.getRow()-1 && number.getRow() <= symbol.getRow()+1).toList();
			List<Numero> touchingNumbers = relevantNumbers.stream().filter(number -> number.getCol() + number.getLength()-1 >= symbol.getCol()-1 && number.getCol() <= symbol.getCol()+1).toList();
			if(touchingNumbers.size() == 2) {
				System.out.println(symbol);
				System.out.println(touchingNumbers.get(0));
				System.out.println(touchingNumbers.get(1));
				System.out.println("==========================");
				result += touchingNumbers.get(0).getValue() * touchingNumbers.get(1).getValue();
			}
				
		}
		
		System.out.println(result);

	}

}
