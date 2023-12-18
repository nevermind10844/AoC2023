
public class Pixel {
	private Vector2d pos;
	private char sign;
	private int identifier;

	public Pixel(Vector2d pos, char sign) {
		this.pos = pos;
		this.sign = sign;
		this.identifier = -1;
	}

	public Vector2d getPos() {
		return this.pos;
	}

	public void setPos(Vector2d pos) {
		this.pos = pos;
	}

	public char getSign() {
		return this.sign;
	}

	public void setSign(char sign) {
		this.sign = sign;
	}

	public int getIdentifier() {
		return identifier;
	}

	public void setIdentifier(int identifier) {
		this.identifier = identifier;
	}

	@Override
	public String toString() {
		return "Pixel [pos=" + pos + ", sign=" + sign + ", identifier=" + identifier + "]";
	}

}
