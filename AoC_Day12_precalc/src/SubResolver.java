
public class SubResolver extends Thread {

	private Prefitting prefitting;
	private int correctionBlockSize;
	private int startingPosition;

	private long result;

	private boolean done;

	public SubResolver(Prefitting prefitting, int startingPosition) {
		this.prefitting = prefitting;
		this.correctionBlockSize = this.prefitting.input.getCorrectionBlocks().size();
		this.result = 0L;
		this.startingPosition = startingPosition;
		this.done = false;
	}

	public long getResult() {
		return this.result;
	}

	public boolean isDone() {
		return this.done;
	}

	@Override
	public void run() {
//		System.out.println("starting for pos: " + this.startingPosition);
		this.resolve(this.startingPosition, 0, this.prefitting.input.getFaultyData());
		this.done = true;
	}

	public void resolve(int currentPosition, int currentBlock, String currentData) {
		boolean chainBroken = false;
		for (int i = currentBlock; i < this.correctionBlockSize; i++) {
			if (chainBroken)
				break;
			CorrectionBlock block = this.prefitting.input.getCorrectionBlocks().get(i);
			for (int j = currentPosition; j < this.prefitting.data.length; j++) {
				if (j > this.startingPosition && currentBlock == 0) {
					chainBroken = true;
					break;
				} else {
					if (this.prefitting.input.getRemainingCharacters(j) < this.prefitting.input
							.getRemainingBlockSizes(i)) {
						chainBroken = true;
						break;
					} else {
						if (this.blockFits(j, block.getBlockSize())) {
							String result = this.replace(currentData, j, block.getString());
							if (i == this.correctionBlockSize - 1) {
								if (this.prefitting.input.getLastForcedPosition() < j + block.getBlockSize()) {
//									System.out.println(this.startingPosition + " -> " + result);
									this.result++;
								}
							} else {
								resolve(j + block.getBlockSize() + 1, i + 1, result);
							}
						}
						if (this.prefitting.data[j] == '#') {
							chainBroken = true;
							break;
						}
					}
				}
			}
		}
	}

	public boolean blockFits(int position, int length) {
		if (position == 0 || position + length == this.prefitting.data.length)
			return this.prefitting.preFit(position, length);

		String key = String.copyValueOf(this.prefitting.data, position - 1, length + 2);
		return this.prefitting.prefittedVariants.get(length).get(key);
	}

	private String replace(String currentData, int position, String toReplace) {
		char[] currentChars = currentData.toCharArray();
		for (int i = 0; i < toReplace.length(); i++) {
			currentChars[i + position] = toReplace.charAt(i);
		}
		return String.valueOf(currentChars);
	}
}
