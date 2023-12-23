import java.util.HashMap;
import java.util.Map;

public class Resolver extends Thread {
	private Thread t;
	private Input input;
	private long result;

	private Map<String, Boolean> mappingTable;
	private Map<String, Boolean> globalMappingTable;

	private int correctionBlockSize;

	private boolean done;

	private static int STACKS = 5;

	Resolver(Input input) {
		this.input = input;
		this.result = 0L;
		this.done = false;
		this.correctionBlockSize = this.input.getCorrectionBlocks().size();
		mappingTable = new HashMap<>();
	}

	public void setGlobalMappingTable(Map<String, Boolean> globalMappingTable) {
		this.globalMappingTable = globalMappingTable;
	}

	public Map<String, Boolean> getMappingTable() {
		return this.mappingTable;
	}

	public void run() {
		this.result = 0L;

		String faultyData = this.input.getFaultyData();
		for (int i = 0; i < STACKS - 1; i++) {
			this.input.setFaultyData(this.input.getFaultyData() + "?" + faultyData);
		}
		this.input.stackBlocks(STACKS);
		this.correctionBlockSize = this.input.getCorrectionBlocks().size();

		this.input.preProcess();

		this.resolve(this.input.getFaultyData(), 0, 0);
		Long secondResult = this.result;

		System.out.println(String.format("result: %d", secondResult));

		this.done = true;
	}

	public boolean isDone() {
		return this.done;
	}

	public Long getResult() {
		return this.result;
	}

	public void resolve(String currentData, int start, int currentBlockPosition) {
		boolean chainBroken = false;
		for (int i = currentBlockPosition; i < this.correctionBlockSize; i++) {
			if (chainBroken) {
				break;
			} else {
				for (int j = start; j < currentData.length(); j++) {
					if (this.valid(currentData, j)) {
						if (this.input.getRemainingWithoutsDots(j) < this.input.getRemainingPositionCount(i)) {
							chainBroken = true;
							break;
						} else {
							CorrectionBlock block = input.correctionBlocks.get(i);
							if (this.blockFits(currentData, j, block.getBlockSize())) {
								String result = replace(currentData, j, block.getString());
								if (i == this.correctionBlockSize - 1) {
									if (!result.contains("#") && containsAllBlocks(result)) {
										this.result++;
//										System.out.println(result);
									} else {
										chainBroken = true;
										break;
									}
								} else {
									// System.out.println(result);
									resolve(result, j + block.getBlockSize() + 1, i + 1);
								}
							}
						}
					} else {
						chainBroken = true;
						break;
					}
				}
			}
		}
	}

	private boolean containsAllBlocks(String s) {
		boolean containsAllBlocks = true;
		for (CorrectionBlock block : this.input.getCorrectionBlocks()) {
			if (!s.contains(block.getString())) {
				containsAllBlocks = false;
				break;
			}
		}
		return containsAllBlocks;
	}

	private boolean valid(String currentData, int position) {
		if (position > 0)
			return !currentData.substring(0, position - 1).contains("#");
		else
			return true;
	}

	private boolean blockFits(String currentData, int position, int length) {
		int currentDataLength = currentData.length();

		if (position + length > currentDataLength)
			return false;

		boolean notFirst = position > 0;
		boolean notLast = position + length < currentDataLength - 1;

		String key = null;

		if (notFirst && notLast) {
			key = currentData.substring(position - 1, position + length + 1) + "::" + length;
			Boolean mappedValue = null;
			mappedValue = globalMappingTable.get(key);
			if (mappedValue == null)
				mappedValue = mappingTable.get(key);
			if (mappedValue != null) {
				return mappedValue;
			}
		}

		char[] data = currentData.toCharArray();

		if (position < currentDataLength - length) {
			char c = data[position + length];
			if (c != '?' && c != '.') {
				if (notFirst && notLast)
					mappingTable.put(key, false);
				return false;
			}
		}

		if (notFirst) {
			char c = data[position - 1];
			if (c != '?' && c != '.') {
				if (notFirst && notLast)
					mappingTable.put(key, false);
				return false;
			}
		}

		for (int i = position; i < length + position; i++) {
			char c = data[i];
			if (c != '#' && c != '?') {
				if (notFirst && notLast)
					mappingTable.put(key, false);
				return false;
			}
		}

		if (notFirst && notLast)
			mappingTable.put(key, true);

		return true;
	}

	private String replace(String currentData, int position, String toReplace) {
		char[] currentChars = currentData.toCharArray();
		for (int i = 0; i < toReplace.length(); i++) {
			currentChars[i + position] = toReplace.charAt(i);
		}
		return String.valueOf(currentChars);
	}

	public void start() {
		if (t == null) {
			t = new Thread(this, this.input.getFaultyData());
			t.start();
		}
	}

	public String getThreadName() {
		return this.t.getName() + " " + this.input.getCorrectionBlocksAsString();
	}
}
