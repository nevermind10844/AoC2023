import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Runner extends Thread {
	private Thread t;

	private char[] directions;
	private Instruction startingNode;
	private Map<String, Instruction> instructions;
	
	private List<Long> resultList;
	
	private long result = 0;
	private boolean done = false;

	Runner(char[] directions, Instruction startingNode, Map<String, Instruction> instructions) {
		this.directions = directions;
		this.startingNode = startingNode;
		this.instructions = instructions;
		this.resultList = new ArrayList<>();
	}
	
	public long getResult() {
		return this.result;
		
	}
	
	public String getResultString(){
		return this.resultList.toString();
	}
	
	public boolean isDone() {
		return this.done;
	}

	public void run() {
		int directionsCounter = 0;
		long steps = 0;
		
		while(resultList.size() < 4) {
			steps++;
			char dir = directions[directionsCounter];
			if(directionsCounter >= directions.length-1)
				directionsCounter = 0;
			else
				directionsCounter++;
			
			Instruction nextInst = instructions.get(startingNode.getTail());
			if(dir == 'L') {
				String left = nextInst.getLeft();
				startingNode.setTail(left);
			} else {
				String right = nextInst.getRight();
				startingNode.setTail(right);
			}
			
			if(startingNode.getTail().endsWith("Z"))
				this.resultList.add(steps);
				
		}
		
		this.done = true;
	}

	public void start() {
		System.out.println("Starting " + this.startingNode.getKey());
		if (t == null) {
			t = new Thread(this, this.startingNode.getKey());
			t.start();
		}
	}
}
