public class CorrectionBlock{
	private int position;
	private boolean placed;
	private int blockSize;
	private char replacementCharacter;
	private String replacementString;
	private String pattern;

	public CorrectionBlock(int position, boolean placed, int blockSize) {
		this.position = position;
		this.placed = placed;
		this.blockSize = blockSize;
		this.replacementCharacter = (char)(65+position);
		
		char[] chars = new char[this.blockSize];
		for (int i = 0; i < this.blockSize; i++) {
			chars[i] = this.replacementCharacter;
		}
		this.replacementString = String.valueOf(chars);
		this.pattern = String.format("(?=.*%s)", this.replacementCharacter);
	}
	
	public String getPattern() {
		return this.pattern;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public boolean isPlaced() {
		return placed;
	}

	public void setPlaced(boolean placed) {
		this.placed = placed;
	}

	public int getBlockSize() {
		return blockSize;
	}

	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}

	public char getReplacementCharacter() {
		return replacementCharacter;
	}
	
	public String getString() {
		return this.replacementString;
	}

	@Override
	public String toString() {
		return "CorrectionBlock [position=" + position + ", placed=" + placed + ", blockSize=" + blockSize
				+ ", replacementCharacter=" + replacementCharacter + "]";
	}

//	@Override
//	public int compareTo(CorrectionBlock that) {
//		if (this.blockSize > that.blockSize)
//			return 1;
//		if (that.blockSize > this.blockSize)
//			return -1;
//		else
//			return 0;
//	}
}
