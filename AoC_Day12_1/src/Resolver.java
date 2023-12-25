import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Resolver extends Thread {
	private Thread t;
	private Input input;
	private long result;

	private int correctionBlockSize;
	
	private Map<String, Boolean> globalValidReplacements;
	private List<String> localValidReplacements;
	
	private boolean done;

	private static int STACKS = 5;

	Resolver(Input input) {
		this.input = input;
		this.result = 0L;
		this.done = false;
		this.correctionBlockSize = this.input.getCorrectionBlocks().size();
		this.localValidReplacements = new ArrayList<>();
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

		System.out.println(String.format("result: %d", this.result));

		this.done = true;
	}

	public List<String> getLocalValidReplacements() {
		return localValidReplacements;
	}

	public void setGlobalValidReplacements(Map<String, Boolean> globalValidReplacements) {
		this.globalValidReplacements = globalValidReplacements;
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
						if (this.input.getRemainingCharacters(j) < this.input.getRemainingBlockSizes(i)) {
							chainBroken = true;
							break;
						} else {
							CorrectionBlock block = input.correctionBlocks.get(i);
							if (this.blockFits(currentData, j, block.getBlockSize())) {
								String result = replace(currentData, j, block.getString());
								if (i == this.correctionBlockSize - 1) {
									this.result++;
								} else {
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

	private boolean valid(String currentData, int position) {
		if (position > 0)
			return !currentData.substring(0, position - 1).contains("#");
		else
			return true;
	}

	private boolean blockFits(String currentData, int position, int length) {
		//precalculating some values
		int currentDataLength = currentData.length();
		boolean notFirst = position > 0;
		boolean notLast = position + length < currentDataLength - 1;
		int precalcLength = length + position;

		String key = null;

		//checking map first
		if (notFirst && notLast) {
			key = currentData.substring(position - 1, precalcLength + 1) + ":" + length;
			boolean contains = this.localValidReplacements.contains(key);
			if (contains)
				return true;
		}

		char[] data = currentData.toCharArray();

		//checking characters spanning the block
		for (int i = position; i < precalcLength; i++) {
			char c = data[i];
			if (c != '#' && c != '?') {
				return false;
			}
		}

		//checking character after
		if (position < currentDataLength - length) {
			char c = data[precalcLength];
			if (c != '?' && c != '.') {
				return false;
			}
		}

		//checking character before
		if (notFirst) {
			char c = data[position - 1];
			if (c != '?' && c != '.') {
				return false;
			}
		}

		//adding valid placement to map and returning true
		if (notFirst && notLast)
			this.localValidReplacements.add(key);

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
