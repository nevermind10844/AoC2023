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
		return greaterOrEquals(source, this.source) && smallerOrEquals(source, this.source.add(this.range));
	}
	
	public BigInteger getDestination(BigInteger source) {
		if(isInRange(source))
			return source.subtract(this.source).add(this.destination);
		else
			throw new IndexOutOfBoundsException("wrong map for this source!");
	}

	@Override
	public String toString() {
		return "Range [source=" + source + ", destination=" + destination + ", range=" + range + "]";
	}
	
	private boolean greaterOrEquals(BigInteger first, BigInteger second) {
		return first.compareTo(second) >= 0;
	}
	
	private boolean smallerOrEquals(BigInteger first, BigInteger second) {
		return second.compareTo(first) >= 0;
	}

}
