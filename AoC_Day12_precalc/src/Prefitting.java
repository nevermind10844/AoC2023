import java.util.HashMap;
import java.util.Map;

public class Prefitting {
	
	Input input;
	Map<Integer, Map<String, Boolean>> prefittedVariants;
	
	char[] data;
	
	public Prefitting(Input input) {
		this.input = input;
		this.data = this.input.getFaultyData().toCharArray();
		this.prefittedVariants = new HashMap<>();
		this.preProcess();
	}
	
	private void preProcess() {
		for (int i = 0; i < this.input.getCorrectionBlocks().size(); i++) {
			CorrectionBlock block = this.input.getCorrectionBlocks().get(i);
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
	
	public boolean preFit(int position, int length) {
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
}
