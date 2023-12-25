import java.util.HashMap;
import java.util.Map;

public class Resolver extends Thread {
	private Thread t;
	private Input input;
	private long result;
	
	private char[] data;

	private int correctionBlockSize;

	private Map<Integer, Map<String, Boolean>> prefittedVariants;

	private boolean done;

	private static int STACKS = 5;

	Resolver(Input input) {
		this.input = input;
		this.result = 0L;
		this.done = false;
		this.correctionBlockSize = this.input.getCorrectionBlocks().size();
		this.prefittedVariants = new HashMap<>();
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
		this.data = this.input.getFaultyData().toCharArray();
		this.preProcess();

		this.resolve(0, 0);

		System.out.println(String.format("result: %d", this.result));

		this.done = true;
	}

	public boolean isDone() {
		return this.done;
	}

	public Long getResult() {
		return this.result;
	}

	public void resolve(int start, int currentBlockPosition) {
		boolean chainBroken = false;
		for (int i = currentBlockPosition; i < this.correctionBlockSize; i++) {
			if (chainBroken) {
				break;
			} else {
				for (int j = start; j < this.data.length; j++) {
					if (this.input.getRemainingCharacters(j) < this.input.getRemainingBlockSizes(i)) {
						chainBroken = true;
						break;
					} else {
						CorrectionBlock block = input.correctionBlocks.get(i);
						if (this.blockFits(j, block.getBlockSize())) {
							if (i == this.correctionBlockSize - 1) {
								this.result++;
							} else {
								resolve(j + block.getBlockSize() + 1, i + 1);
							}
						} else {
							if (this.data[j] == '#') {
								chainBroken = true;
								break;
							}
						}
					}
				}
			}
		}
	}

	private void preProcess() {
		for (int i = 0; i < this.input.correctionBlocks.size(); i++) {
			CorrectionBlock block = this.input.correctionBlocks.get(i);
			if (!this.prefittedVariants.containsKey(block.getBlockSize())) {
				Map<String, Boolean> blockwisePrefittedVariants = new HashMap<>();
				String data = this.input.getFaultyData();
				for (int y = 1; y < data.length() - block.getBlockSize(); y++) {
					blockwisePrefittedVariants.put(data.substring(y - 1, y + block.getBlockSize() + 1),
							this.preFit(y, block.getBlockSize()));
				}
				this.prefittedVariants.put(block.getBlockSize(), blockwisePrefittedVariants);
			}
		}
	}

	private boolean blockFits(int position, int length) {
		if (position == 0 || position + length == this.data.length)
			return preFit(position, length);

		String key = String.copyValueOf(this.data, position-1, length + 2);
		return this.prefittedVariants.get(length).get(key);

	}

	private boolean preFit(int position, int length) {
		// precalculating some values
		int currentDataLength = this.data.length;
		boolean notFirst = position > 0;
		int precalcLength = length + position;

		// checking characters spanning the block
		for (int i = position; i < precalcLength; i++) {
			char c = this.data[i];
			if (c != '#' && c != '?') {
				return false;
			}
		}

		// checking character after
		if (position < currentDataLength - length) {
			char c = this.data[precalcLength];
			if (c != '?' && c != '.') {
				return false;
			}
		}

		// checking character before
		if (notFirst) {
			char c = this.data[position - 1];
			if (c != '?' && c != '.') {
				return false;
			}
		}

		return true;
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
