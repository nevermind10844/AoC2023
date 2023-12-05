import java.math.BigInteger;

public class Range {
	private BigInteger source;
	private BigInteger destination;
	private BigInteger range;

	public Range() {
		this.source = BigInteger.ZERO;
		this.destination = BigInteger.ZERO;
		this.range = BigInteger.ZERO;
	}

	public BigInteger getSource() {
		return source;
	}

	public void setSource(BigInteger source) {
		this.source = source;
	}

	public BigInteger getDestination() {
		return destination;
	}

	public void setDestination(BigInteger destination) {
		this.destination = destination;
	}

	public BigInteger getRange() {
		return range;
	}

	public void setRange(BigInteger range) {
		this.range = range;
	}

	public boolean isInRange(BigInteger source) {
		return greaterOrEquals(source, this.source) && smallerThan(source, this.source.add(this.range));
	}

	public BigInteger getDestination(BigInteger source) {
		return source.subtract(this.source).add(this.destination);
	}

	@Override
	public String toString() {
		return "Range [source=" + source + ", destination=" + destination + ", range=" + range + "]";
	}

	private boolean greaterOrEquals(BigInteger first, BigInteger second) {
		return first.compareTo(second) >= 0;
	}
	
	private boolean smallerThan(BigInteger first, BigInteger second) {
		return first.compareTo(second) < 0;
	}

}
