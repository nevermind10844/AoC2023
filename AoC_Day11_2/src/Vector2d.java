import java.util.Objects;

public class Vector2d {
	private int x;
	private int y;

	public Vector2d(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return this.x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return this.y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public int getDistance(Vector2d other) {
		return Math.abs(this.x - other.x) + Math.abs(this.y - other.y);
	}

	@Override
	public String toString() {
		return "Vector2d [x=" + x + ", y=" + y + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vector2d other = (Vector2d) obj;
		return x == other.x && y == other.y;
	}

}
