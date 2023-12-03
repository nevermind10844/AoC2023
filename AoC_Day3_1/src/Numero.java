
public class Numero {
	int row;
	int col;
	int length;
	int value;
	String stringValue;
	boolean isSchematic;

	public Numero() {
		this.row = 0;
		this.col = 0;
		this.length = 0;
		this.value = 0;
		this.stringValue = "";
		this.isSchematic = false;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getStringValue() {
		return stringValue;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	public boolean isSchematic() {
		return isSchematic;
	}

	public void setSchematic(boolean isSchematic) {
		this.isSchematic = isSchematic;
	}
	
	public void appendChar(char c) {
		this.stringValue += c;
		this.length = this.stringValue.length();
		this.value = Integer.valueOf(this.stringValue);
	}

	@Override
	public String toString() {
		return "Numero [row=" + row + ", col=" + col + ", length=" + length + ", value=" + value + ", stringValue="
				+ stringValue + ", isSchematic=" + isSchematic + "]";
	}

}
