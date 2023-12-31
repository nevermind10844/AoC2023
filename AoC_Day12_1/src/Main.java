import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

	public static void main(String[] args) {
		List<String> strings = InputReader.readInput();
		List<Resolver> resolvers = new ArrayList<>();
		List<Resolver> running = new ArrayList<>();
		List<Resolver> done = new ArrayList<>();
		Map<String, Boolean> globalValidReplacements = new HashMap<>();

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

		int numResolvers = resolvers.size();
		int maxThreads = 12;

		boolean measured = false;
		long start = System.currentTimeMillis();

		while (done.size() < numResolvers) {
			if (resolvers.size() > 0 && running.size() < maxThreads) {
				Resolver r = resolvers.get(0);
				r.setGlobalValidReplacements(globalValidReplacements);
				running.add(r);
				resolvers.remove(r);
				r.start();
			}

			List<Resolver> ready = running.stream().filter(r -> r.isDone()).toList();
			running.removeAll(ready);

//			for (Resolver res: ready) {
//				globalValidReplacements.putAll(res.getLocalValidReplacements());
//			}
			
			done.addAll(ready);
			
			if (!measured && done.size() >= 200) {
				long end = System.currentTimeMillis();
				System.err.println(String.format("time to 200: %f", (end - start) / 1000f));
				measured = true;
			}

			long currentSum = getSum(done);
//			System.out.println(String.format("todo: %4d  running: %4d  done: %4d  (%d)", resolvers.size(),
//					running.size(), done.size(), currentSum));

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		long result = getSum(done);
		System.out.println("final: " + result);
	}

	private static long getSum(List<Resolver> resolvers) {
		long currentSum = 0L;
		List<Long> resultList = resolvers.stream().map(item -> item.getResult()).toList();
		for (long result : resultList) {
			currentSum += result;
		}
		return currentSum;
	}
}
