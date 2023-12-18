import java.util.Objects;

public class Edge {
	private Vector2d a;
	private Vector2d b;

	public Edge(Vector2d a, Vector2d b) {
		this.a = a;
		this.b = b;
	}

	public Vector2d getA() {
		return a;
	}

	public void setA(Vector2d a) {
		this.a = a;
	}

	public Vector2d getB() {
		return b;
	}

	public void setB(Vector2d b) {
		this.b = b;
	}

	@Override
	public String toString() {
		return "Edge [a=" + a + ", b=" + b + "]";
	}

	public int getDistance() {
		return this.a.getDistance(this.b);
	}

	@Override
	public int hashCode() {
		return Objects.hash(a, b);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Edge other = (Edge) obj;
		return this.a.equals(other.a) && this.b.equals(other.b) || this.a.equals(other.b) && this.b.equals(other.a);
	}

}
