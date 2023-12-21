public class SeedRange {
	private long seed;
	private long range;
	private boolean checked;

	public SeedRange() {
		this.seed = 0L;
		this.range = 0L;
		setChecked(false);
	}

	public long getSeed() {
		return seed;
	}

	public void setSeed(long seed) {
		this.seed = seed;
	}

	public long getRange() {
		return range;
	}

	public void setRange(long range) {
		this.range = range;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	public boolean isInRange(long source) {
		return source >= this.seed && source <= this.seed + this.range;
	}
	
//	private boolean greaterOrEquals(BigInteger first, BigInteger second) {
//		return first.compareTo(second) >= 0;
//	}
//	
//	private boolean smallerOrEquals(BigInteger first, BigInteger second) {
//		return second.compareTo(first) >= 0;
//	}

	@Override
	public String toString() {
		return "SeedRange [seed=" + seed + ", range=" + range + ", checked=" + checked + "]";
	}

}
