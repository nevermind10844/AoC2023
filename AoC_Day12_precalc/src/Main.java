import java.util.ArrayList;
import java.util.List;

public class Main {
	
	private static int MAX_THREADS = 16;
	private static int THREAD_LOOP_INTERVAL = 20;
	private static int STATE_CHECK_INTERVAL = 1000;

	public static void main(String[] args) {
		List<String> strings = InputReader.readInput();
		List<Resolver> resolvers = new ArrayList<>();

		for (String line : strings) {
			Input input = new Input();
			String[] mainSplit = line.split(" ");

			input.setFaultyData(mainSplit[0]);

			String[] correctionSplit = mainSplit[1].split(",");

			for (int i = 0; i < correctionSplit.length; i++) {
				int blockSize = Integer.valueOf(correctionSplit[i]);
				CorrectionBlock block = new CorrectionBlock(i, false, blockSize);
				input.addCorrectionBlock(block);
			}

			Resolver r = new Resolver(input);
			resolvers.add(r);
		}
		
		GameMaster gm = new GameMaster(resolvers, MAX_THREADS, THREAD_LOOP_INTERVAL);
		gm.start();
		
		while (!gm.isReady()) {
			System.out.println(gm.printCurrentState());
			try {
				Thread.sleep(STATE_CHECK_INTERVAL);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		long result = gm.getResult();
		
		System.out.println("final: " + result);
	}


}
