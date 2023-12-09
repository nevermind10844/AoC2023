import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		List<String> strings = InputReader.readInput();
		List<Instruction> instructions = new ArrayList<>();
		
		char[] directions = strings.get(0).toCharArray();
		
		for(int i=2; i<strings.size(); i++) {
			String[] mainSplit = strings.get(i).replaceAll("[() ]", "").split("[=,]");
			instructions.add(new Instruction(mainSplit[0], mainSplit[1], mainSplit[2]));
		}
		for(int i=0; i<directions.length; i++) {
			System.out.println(directions[i]);
		}
		
		Instruction current = instructions.stream().filter(inst -> inst.getKey().equals("AAA")).findFirst().get();
		int directionsCounter = 0;
		int steps = 0;
		while(!current.getKey().equals("ZZZ")) {
			steps++;
			char dir = directions[directionsCounter];
			if(directionsCounter >= directions.length-1)
				directionsCounter = 0;
			else
				directionsCounter++;
			if(dir == 'L') {
				String left = current.getLeft();
				current = instructions.stream().filter(inst -> inst.getKey().equals(left)).findFirst().get();
			} else {
				String right = current.getRight();
				current = instructions.stream().filter(inst -> inst.getKey().equals(right)).findFirst().get();
			}
		}
		System.out.println(steps);
	}
}
