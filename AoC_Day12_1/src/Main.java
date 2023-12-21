import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		List<String> strings = InputReader.readInput();
		List<Resolver> resolvers = new ArrayList<>();
		List<Resolver> running = new ArrayList<>();
		List<Resolver> done = new ArrayList<>();

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
		
		while(done.size() < numResolvers) {
			if(resolvers.size() > 0 && running.size() < maxThreads) {
				Resolver r = resolvers.get(0);
				running.add(r);
				resolvers.remove(r);
				r.start();
			}
			List<Resolver> ready = running.stream().filter(r -> r.isDone()).toList();
			running.removeAll(ready);
			done.addAll(ready);
			
			BigInteger currentSum = getSum(done);
			System.out.println(String.format("todo: %4d  running: %4d  done: %4d  (%d)", resolvers.size(), running.size(), done.size(), currentSum));
			
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		BigInteger result = getSum(done);
		System.out.println("final: " + result);
	}
	
	private static BigInteger getSum(List<Resolver> resolvers) {
		BigInteger currentSum = BigInteger.ZERO;
		List<BigInteger> resultList = resolvers.stream().map(item -> item.getResult()).toList();
		for (BigInteger result : resultList) {
			currentSum = currentSum.add(result);
		}
		return currentSum;
	}
}
