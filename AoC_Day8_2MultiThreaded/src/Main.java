import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

	public static void main(String[] args) {
		List<String> strings = InputReader.readInput();
		Map<String, Instruction> instructions = new HashMap<>();
		
		List<Runner> runners = new ArrayList<>();

		char[] directions = strings.get(0).toCharArray();

		for (int i = 2; i < strings.size(); i++) {
			String[] mainSplit = strings.get(i).replaceAll("[() ]", "").split("[=,]");
			instructions.put(mainSplit[0], new Instruction(mainSplit[0], mainSplit[1], mainSplit[2]));
		}

		List<Instruction> startingNodes = instructions.values().stream().filter(inst -> inst.getKey().endsWith("A"))
				.toList();

		System.out.println("amount of starting nodes: " + startingNodes.size());

		for (Instruction instruction : startingNodes) {
			Runner r = new Runner(directions, instruction, instructions);
			runners.add(r);
			r.start();
		}
		
		
		boolean done = false;
		
		while(!done) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			boolean threadsDone = true;
			
			for (Runner runner : runners) {
				if(!runner.isDone()) {
					threadsDone = false;
					break;
				}
			}
			
			done = !threadsDone;
		}
		
		for (Runner runner : runners) {
			System.out.println(runner.getResultString());
		}
	}

//	private static boolean evaluate(List<Instruction> nodes, long step) {
//		int endingWithZ = nodes.stream().filter(inst -> inst.getTail().endsWith("Z")).toList().size();
//		if (step % 100000 == 0) {
//			System.out.println(String.format("%d out of %d ending at step %d", endingWithZ, nodes.size(), step));
//		}
//		return endingWithZ == nodes.size();
//	}
}
