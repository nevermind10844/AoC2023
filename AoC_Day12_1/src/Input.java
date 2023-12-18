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

	public Input() {
		this.faultyData = null;
		this.correctionBlocks = new ArrayList<>();
		remainders = new HashMap<>();
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
	
	public void doubleBlocks() {
		int currentSize = this.correctionBlocks.size();
		for(int i=0; i<currentSize; i++) {
			int blockSize = this.getCorrectionBlocks().get(i).getBlockSize();
			CorrectionBlock block = new CorrectionBlock(i + currentSize, false, blockSize);
			this.addCorrectionBlock(block);
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
	
	public String getCorrectionBlocksAsString() {
		List<String> blockSizes = new ArrayList<>();
		for (int i=0; i<this.correctionBlocks.size(); i++) {
			blockSizes.add(this.correctionBlocks.get(i).getBlockSize() + "");
		}
		return String.join(",", blockSizes);
	}

	@Override
	public String toString() {
		return "Input [faultyData=" + faultyData + ", correctionData=" + correctionBlocks + "]";
	}

}
