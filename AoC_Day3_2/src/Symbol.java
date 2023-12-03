
public class Symbol {
	private int row;
	private int col;
	private boolean asterisc;

	public Symbol(int row, int col) {
		this.row = row;
		this.col = col;
		this.asterisc = false;
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

	public boolean isAsterisc() {
		return asterisc;
	}

	public void setAsterisc(boolean asterisc) {
		this.asterisc = asterisc;
	}

	@Override
	public String toString() {
		return "Symbol [row=" + row + ", col=" + col + ", asterisc=" + asterisc + "]";
	}
}
