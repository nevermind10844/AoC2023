
public class Instruction {
	private String key;
	private String left;
	private String right;

	public Instruction(String key, String left, String right) {
		this.key = key;
		this.left = left;
		this.right = right;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getLeft() {
		return left;
	}

	public void setLeft(String left) {
		this.left = left;
	}

	public String getRight() {
		return right;
	}

	public void setRight(String right) {
		this.right = right;
	}

	@Override
	public String toString() {
		return "Instruction [key=" + key + ", left=" + left + ", right=" + right + "]";
	}

}
