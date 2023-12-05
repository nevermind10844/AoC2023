import java.math.BigInteger;

public class SeedRange {
	private BigInteger seed;
	private BigInteger range;
	private boolean checked;

	public SeedRange() {
		this.seed = BigInteger.ZERO;
		this.range = BigInteger.ZERO;
		setChecked(false);
	}

	public BigInteger getSeed() {
		return seed;
	}

	public void setSeed(BigInteger seed) {
		this.seed = seed;
	}

	public BigInteger getRange() {
		return range;
	}

	public void setRange(BigInteger range) {
		this.range = range;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	public boolean isInRange(BigInteger source) {
		return greaterOrEquals(source, this.seed) && smallerOrEquals(source, this.seed.add(this.range));
	}
	
	private boolean greaterOrEquals(BigInteger first, BigInteger second) {
		return first.compareTo(second) >= 0;
	}
	
	private boolean smallerOrEquals(BigInteger first, BigInteger second) {
		return second.compareTo(first) >= 0;
	}

	@Override
	public String toString() {
		return "SeedRange [seed=" + seed + ", range=" + range + ", checked=" + checked + "]";
	}

}
