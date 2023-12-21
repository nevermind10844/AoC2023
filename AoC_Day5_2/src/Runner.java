import java.util.List;

public class Runner extends Thread {
	private Thread t;

	private SeedRange seedRange;
	private List<MappingTable> tables;
	
	private long result = -1L;
	private boolean done = false;

	Runner(SeedRange seedRange, List<MappingTable> tables) {
		this.seedRange = seedRange;
		this.tables = tables;
	}
	
	public long getResult() {
		return this.result;
	}
	
	public boolean isDone() {
		return this.done;
	}

	public void run() {
		long result = -1L;
		long seed = seedRange.getSeed();
		
		while (seed < seedRange.getSeed() + seedRange.getRange()) {
			long source = seed;
			for (int j = 0; j < tables.size(); j++) {
				source = tables.get(j).getDestination(source);
			}

			if (result < 0) {
				result = source;
			} else if (source < result) {
				result = source;
			}

			seed++;;
			this.result = result;
		}
		
		this.result = result;
		this.done = true;
	}

	public void start() {
		System.out.println("Starting " + seedRange.toString());
		if (t == null) {
			t = new Thread(this, seedRange.toString());
			t.start();
		}
	}
}
