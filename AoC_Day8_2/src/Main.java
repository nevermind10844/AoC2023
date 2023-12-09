import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

	public static void main(String[] args) {
		List<String> strings = InputReader.readInput();
		Map<String, Instruction> instructions = new HashMap<>();
		
		char[] directions = strings.get(0).toCharArray();
		
		for(int i=2; i<strings.size(); i++) {
			String[] mainSplit = strings.get(i).replaceAll("[() ]", "").split("[=,]");
			instructions.put(mainSplit[0], new Instruction(mainSplit[0], mainSplit[1], mainSplit[2]));
		}
		
		List<Instruction> startingNodes = instructions.values().stream().filter(inst -> inst.getKey().endsWith("A")).toList();
		
		System.out.println("amount of starting nodes: " + startingNodes.size());
		
		int directionsCounter = 0;
		long steps = 0;
		boolean evaluation = false;
		while(!evaluation) {
			steps++;
			char dir = directions[directionsCounter];
			if(directionsCounter >= directions.length-1)
				directionsCounter = 0;
			else
				directionsCounter++;
			for(Instruction current : startingNodes) {
				Instruction nextInst = instructions.get(current.getTail());
				if(dir == 'L') {
					String left = nextInst.getLeft();
					current.setTail(left);
				} else {
					String right = nextInst.getRight();
					current.setTail(right);
				}
				
			}
			
			evaluation = evaluate(startingNodes, steps);
		}
		System.out.println(steps);
	}
	
	private static boolean evaluate(List<Instruction> nodes, long step) {
		int endingWithZ = nodes.stream().filter(inst -> inst.getTail().endsWith("Z")).toList().size();
		if(step % 100000 == 0) {
			System.out.println(String.format("%d out of %d ending at step %d", endingWithZ, nodes.size(), step));
		}
		return endingWithZ == nodes.size();
	}
}
