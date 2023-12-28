import java.util.ArrayList;
import java.util.List;

public class GameMaster extends Thread {

	private List<Resolver> resolvers;
	private List<Resolver> running;
	private List<Resolver> done;

	private int maxThreads;
	private long interval;
	private boolean ready;
	private long result;

	public GameMaster(List<Resolver> resolvers, int maxThreads, long interval) {
		this.resolvers = resolvers;
		this.running = new ArrayList<>();
		this.done = new ArrayList<>();
		this.maxThreads = maxThreads;
		this.interval = interval;
		this.ready = false;
		this.result = 0L;
	}

	public String printCurrentState() {
		return String.format("todo: %4d  running: %4d  done: %4d  (%d)", resolvers.size(), running.size(), done.size(),
				this.result);
	}

	public boolean isReady() {
		return this.ready;
	}

	public long getResult() {
		return this.result;
	}

	@Override
	public void run() {
		int numResolvers = resolvers.size();

		boolean measured = false;
		long start = System.currentTimeMillis();

		while (done.size() < numResolvers) {
			if (resolvers.size() > 0 && running.size() < this.maxThreads) {
				Resolver r = resolvers.get(0);
				running.add(r);
				resolvers.remove(r);
				r.start();
			}

			List<Resolver> ready = running.stream().filter(r -> r.isDone()).toList();
			running.removeAll(ready);
			
			ready.forEach(item -> this.result += item.getResult());

			done.addAll(ready);

			if (!measured && done.size() >= 200) {
				long end = System.currentTimeMillis();
				System.err.println(String.format("time to 200: %f", (end - start) / 1000f));
				measured = true;
			}

			try {
				Thread.sleep(this.interval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		this.result = getSum(done);
		
		this.ready = true;
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
