import java.math.BigInteger;

public class Resolver extends Thread {
	private Thread t;
	private Input input;
	private BigInteger result;

	private int correctionBlockSize;

	private boolean done;

	Resolver(Input input) {
		this.input = input;
		this.result = BigInteger.ZERO;
		this.done = false;
		this.correctionBlockSize = this.input.getCorrectionBlocks().size();
	}

	public void run() {
		this.resolve(this.input.getFaultyData(), 0, 0);
		BigInteger firstResult = this.result;
		
		this.result = BigInteger.ZERO;
		
		this.input.setFaultyData(this.input.getFaultyData() + "?" + this.input.getFaultyData());
		this.input.doubleBlocks();
		this.correctionBlockSize = this.input.getCorrectionBlocks().size();
		
		this.resolve(this.input.getFaultyData(), 0, 0);
		BigInteger secondResult = this.result;
		
		BigInteger factor = secondResult.divide(firstResult);
		
		this.result = firstResult.multiply(factor).multiply(factor).multiply(factor).multiply(factor);
		
		System.out.println(String.format("first: %d    second: %d    factor: %d    result: %d", firstResult, secondResult, factor, result));
		
		this.done = true;
	}

	public boolean isDone() {
		return this.done;
	}

	public BigInteger getResult() {
		return this.result;
	}

	public void resolve(String currentData, int start, int currentBlockPosition) {
		boolean chainBroken = false;
		for (int i = currentBlockPosition; i < this.correctionBlockSize; i++) {
			if (chainBroken) {
				break;
			} else {
				CorrectionBlock block = input.correctionBlocks.get(i);
				for (int j = start; j < currentData.length(); j++) {
					if (this.valid(currentData, j)) {
						if (this.blockFits(currentData, j, block.getString())) {
							String result = replace(currentData, j, block.getString());
							if (i == this.correctionBlockSize - 1) {
								if (!result.contains("#") && containsAllBlocks(result)) {
									this.result = this.result.add(BigInteger.ONE);
								}
							} else {
								resolve(result, j + block.getBlockSize() + 1, i + 1);
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

	private boolean blockFits(String currentData, int position, String toFit) {
		if (position + toFit.length() > currentData.length())
			return false;

		if (position < currentData.length() - toFit.length()) {
			char c = currentData.charAt(position + toFit.length());
			if (c != '?' && c != '.')
				return false;
		}

		if (position > 0) {
			char c = currentData.charAt(position - 1);
			if (c != '?' && c != '.')
				return false;
		}

		for (int i = position; i < toFit.length() + position; i++) {
			char c = currentData.charAt(i);
			if (c != '#' && c != '?') {
				return false;
			}
		}

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
