import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Input {
	private String faultyData;
	List<CorrectionBlock> correctionBlocks;
	private Pattern pattern;
	private Map<Integer, Integer> remainders;
	private Map<Integer, Integer> remainingWithoutDots;

	public Input() {
		this.faultyData = null;
		this.correctionBlocks = new ArrayList<>();
		this.remainders = new HashMap<>();
		this.remainingWithoutDots = new HashMap<>();
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

	public int getRemainingPositionCount(int index) {
		return this.remainders.get(index);
	}

	public void addCorrectionBlock(CorrectionBlock block) {
		this.correctionBlocks.add(block);
	}
	
	public int getRemainingWithoutsDots(int index) {
		return this.remainingWithoutDots.get(index);
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
			for(int y=i; y<this.correctionBlocks.size(); y++) {
				sum += this.correctionBlocks.get(y).getBlockSize();
			}
			this.remainders.put(i, sum);
		}
		
		for(int i=0; i< this.faultyData.length(); i++) {
			this.remainingWithoutDots.put(i, this.faultyData.substring(i).replace("/.", "").length());
		}
	}

	@Override
	public String toString() {
		return "Input [faultyData=" + faultyData + ", correctionData=" + correctionBlocks + "]";
	}

}
