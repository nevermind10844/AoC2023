public class Range {
	private long source;
	private long destination;
	private long range;

	public Range() {
		this.source = 0;
		this.destination = 0;
		this.range = 0;
	}

	public long getSource() {
		return source;
	}

	public void setSource(long source) {
		this.source = source;
	}

	public long getDestination() {
		return destination;
	}

	public void setDestination(long destination) {
		this.destination = destination;
	}

	public long getRange() {
		return range;
	}

	public void setRange(long range) {
		this.range = range;
	}

	public boolean isInRange(long source) {
		return source>=this.source && source < this.source + this.range;
	}

	public long getDestination(long source) {
		return source - this.source + this.destination;
	}

	@Override
	public String toString() {
		return "Range [source=" + source + ", destination=" + destination + ", range=" + range + "]";
	}

//	private boolean greaterOrEquals(BigInteger first, BigInteger second) {
//		return first.compareTo(second) >= 0;
//	}
//
//	private boolean smallerThan(BigInteger first, BigInteger second) {
//		return first.compareTo(second) < 0;
//	}

}
