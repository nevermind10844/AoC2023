import java.math.BigInteger;
import java.util.List;

public class Runner extends Thread {
	private Thread t;

	private SeedRange seedRange;
	private List<MappingTable> tables;
	
	private BigInteger result = BigInteger.valueOf(-1L);
	private boolean done = false;

	Runner(SeedRange seedRange, List<MappingTable> tables) {
		this.seedRange = seedRange;
		this.tables = tables;
	}
	
	public BigInteger getResult() {
		return this.result;
	}
	
	public boolean isDone() {
		return this.done;
	}

	public void run() {
		BigInteger result = BigInteger.valueOf(-1L);
		BigInteger seed = seedRange.getSeed();
		
		while (seed.compareTo(seedRange.getSeed().add(seedRange.getRange())) < 0) {
			BigInteger source = seed;
			for (int j = 0; j < tables.size(); j++) {
				source = tables.get(j).getDestination(source);
			}

			if (result.compareTo(BigInteger.ZERO) < 0) {
				result = source;
			} else if (source.compareTo(result) < 0) {
				result = source;
			}

			seed = seed.add(BigInteger.ONE);
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
