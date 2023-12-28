import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Input {
	private String faultyData;
	private List<CorrectionBlock> correctionBlocks;
	private Pattern pattern;
	private Map<Integer, Integer> remainingBlockSizes;
	private Map<Integer, Integer> remainingCharacters;

	private int firstPossibleStart;
	private int lastPossibleStart;
	private int lastForcedPosition;

	public Input() {
		this.faultyData = null;
		this.correctionBlocks = new ArrayList<>();
		this.remainingBlockSizes = new HashMap<>();
		this.remainingCharacters = new HashMap<>();
		this.firstPossibleStart = -1;
		this.lastPossibleStart = -1;
	}

	public int getFirstPossibleStart() {
		return firstPossibleStart;
	}

	public int getLastPossibleStart() {
		return lastPossibleStart;
	}

	public int getLastForcedPosition() {
		return lastForcedPosition;
	}

	public String getFaultyData() {
		return this.faultyData;
	}

	public void setFaultyData(String faultyData) {
		this.faultyData = faultyData;
	}

	public List<CorrectionBlock> getCorrectionBlocks() {
		return correctionBlocks;
	}

	public void stackBlocks(int finalStackSize) {
		int currentSize = this.correctionBlocks.size();
		for (int j = 1; j < finalStackSize; j++) {
			for (int i = 0; i < currentSize; i++) {
				int blockSize = this.getCorrectionBlocks().get(i).getBlockSize();
				CorrectionBlock block = new CorrectionBlock(i + (currentSize * j), false, blockSize);
				this.addCorrectionBlock(block);
			}
		}
	}

	public Pattern getPattern() {
		return this.pattern;
	}

	public int getRemainingBlockSizes(int index) {
		return this.remainingBlockSizes.get(index);
	}

	public void addCorrectionBlock(CorrectionBlock block) {
		this.correctionBlocks.add(block);
	}

	public int getRemainingCharacters(int index) {
		return this.remainingCharacters.get(index);
	}

	public String getCorrectionBlocksAsString() {
		List<String> blockSizes = new ArrayList<>();
		for (int i = 0; i < this.correctionBlocks.size(); i++) {
			blockSizes.add(this.correctionBlocks.get(i).getBlockSize() + "");
		}
		return String.join(",", blockSizes);
	}

	public void preProcess() {

		for (int i = 0; i < this.correctionBlocks.size(); i++) {
			int sum = 0;
			for (int y = i; y < this.correctionBlocks.size(); y++) {
				if (sum != 0)
					sum += 1;
				sum += this.correctionBlocks.get(y).getBlockSize();
			}
			this.remainingBlockSizes.put(i, sum);
		}

		for (int i = 0; i < this.faultyData.length(); i++) {
			this.remainingCharacters.put(i, this.faultyData.substring(i).length());
		}
		
		for (int i = 0; i < this.faultyData.length(); i++) {
			char c = this.faultyData.charAt(i);
			if (c == '#') {
				this.lastPossibleStart = i;
				if (this.firstPossibleStart < 0) {
					this.firstPossibleStart = i;
				}
				break;
			} else if (c == '?' && firstPossibleStart < 0) {
				this.firstPossibleStart = i;
			}
			if (firstPossibleStart >= 0 && lastPossibleStart >= 0)
				break;
		}
		
		if (this.firstPossibleStart < 0)
			this.firstPossibleStart = 0;
		if (this.lastPossibleStart < 0)
			this.lastPossibleStart = this.faultyData.length() - 1 - this.getRemainingBlockSizes(0);
		
		this.lastForcedPosition = this.faultyData.lastIndexOf('#');
		
	}

	@Override
	public String toString() {
		return "Input [faultyData=" + faultyData + ", correctionData=" + correctionBlocks + "]";
	}

}
