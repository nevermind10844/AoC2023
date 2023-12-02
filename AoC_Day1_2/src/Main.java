import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class Main {
	private static int getNumberFromLine(String input) {
		int result = 0;
		for(int i=0; i<input.length(); i++) {
			String sub = input.substring(i);
			if(Character.isDigit(sub.charAt(0)))
				result = Integer.valueOf(String.valueOf(sub.charAt(0))) * 10;
			else {
				if(sub.startsWith("one"))
					result = 10;
				else if(sub.startsWith("two"))
					result = 20;
				else if(sub.startsWith("three"))
					result = 30;
				else if(sub.startsWith("four"))
					result = 40;
				else if(sub.startsWith("five"))
					result = 50;
				else if(sub.startsWith("six"))
					result = 60;
				else if(sub.startsWith("seven"))
					result = 70;
				else if(sub.startsWith("eight"))
					result = 80;
				else if(sub.startsWith("nine"))
					result = 90;
			}
			if(result > 0)
				break;
		}
		
		String reversedInput = StringUtils.reverse(input);
		
		int secondResult = 0;
		for(int i=0; i<reversedInput.length(); i++) {
			String sub = reversedInput.substring(i);
			if(Character.isDigit(sub.charAt(0)))
				secondResult = Integer.valueOf(String.valueOf(sub.charAt(0)));
			else {
				if(sub.startsWith("eno"))
					secondResult = 1;
				else if(sub.startsWith("owt"))
					secondResult = 2;
				else if(sub.startsWith("eerht"))
					secondResult = 3;
				else if(sub.startsWith("ruof"))
					secondResult = 4;
				else if(sub.startsWith("evif"))
					secondResult = 5;
				else if(sub.startsWith("xis"))
					secondResult = 6;
				else if(sub.startsWith("neves"))
					secondResult = 7;
				else if(sub.startsWith("thgie"))
					secondResult = 8;
				else if(sub.startsWith("enin"))
					secondResult = 9;
			}
			if(secondResult > 0)
				break;
		}
		return result + secondResult;
	}
	
	public static void main(String[] args) {
		List<String> strings = InputReader.readInput();
		
		int result = 0;
		for (String string : strings) {
			result += getNumberFromLine(string);
		}
		
		System.out.println(result);
	}
}
