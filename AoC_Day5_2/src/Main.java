import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		List<String> strings = InputReader.readInput();
		List<SeedRange> seeds = new ArrayList<>();
		List<MappingTable> tables = new ArrayList<>();

		String mapName = null;
		MappingTable currentMappingTable = null;

		for (String string : strings) {
			if (string.contains("seeds:")) {
				String[] seedsArr = string.split(": ")[1].split(" ");
				for (int i = 0; i < seedsArr.length; i+=2) {
					BigInteger start = new BigInteger(seedsArr[i]);
					BigInteger range = new BigInteger(seedsArr[i+1]);
					SeedRange sr = new SeedRange();
					sr.setSeed(start);
					sr.setRange(range);
					seeds.add(sr);
				}
				System.out.println("das hier ist durch...");
			} else if (string.contains("map:")) {
				mapName = string.replace(" map:", "");
				currentMappingTable = new MappingTable();
				currentMappingTable.setName(mapName);
			} else if (string.isEmpty() && mapName != null) {
				tables.add(currentMappingTable);
				mapName = null;
				currentMappingTable = null;
			} else if (mapName != null) {
				String[] ranges = string.split(" ");
				Range range = new Range();
				range.setDestination(new BigInteger(ranges[0]));
				range.setSource(new BigInteger(ranges[1]));
				range.setRange(new BigInteger(ranges[2]));
				currentMappingTable.addRange(range);
			}
		}

		tables.add(currentMappingTable);
		mapName = null;
		currentMappingTable = null;
		
		BigInteger result = BigInteger.valueOf(-1L);
		
		List<Runner> runnerList = new ArrayList<>();
		
		for (int i = seeds.size()-1; i>=0; i--) {
			SeedRange seedRange = seeds.get(i);
			
			Runner r = new Runner(seedRange, tables);
			runnerList.add(r);
			r.start();
		}
		
		boolean done = false;
		
		while(!done) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for (Runner runner : runnerList) {
				BigInteger source = runner.getResult();
				if (result.compareTo(BigInteger.ZERO) < 0) {
					result = source;
					System.out.println("initially smallest location: " + result);
				} else if (source.compareTo(result) < 0) {
					result = source;
					System.out.println("newest smallest location: " + result);
				}
			}
			
			List<Runner> notDoneList = runnerList.stream().filter(runner -> !runner.isDone()).toList();
			if(notDoneList.size() <= 0)
				done = true;
		}

		System.out.println(result);
		tables.forEach(table -> System.out.println(table));
	}
}
