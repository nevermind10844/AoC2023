import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		List<String> strings = InputReader.readInput();
		List<BigInteger> seeds = new ArrayList<>();
		List<MappingTable> tables = new ArrayList<>();

		String mapName = null;
		MappingTable currentMappingTable = null;

		for (String string : strings) {
			if (string.contains("seeds:")) {
				String[] seedsArr = string.split(": ")[1].split(" ");
				for (int i = 0; i < seedsArr.length; i++) {
					seeds.add(new BigInteger(seedsArr[i]));
				}
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
		
		for (int i = 0; i < seeds.size(); i++) {
			BigInteger seed = seeds.get(i);
			System.out.println("checking for seed " + seed);
			BigInteger source = new BigInteger(seed.toString());
			for (int j = 0; j < tables.size(); j++) {
				System.out.println(String.format("pre %s: %d", tables.get(j).getName(), source));
				source = tables.get(j).getDestination(source);
				System.out.println(String.format("post %s: %d \n", tables.get(j).getName(), source));
			}
			System.out.println("final Destination for seed is " + source);
		}

		tables.forEach(table -> System.out.println(table));
	}
}
