import java.math.BigInteger;

public class Race {
	private BigInteger duration;
	private BigInteger recordDistance;
	private BigInteger maxDistance;
	private BigInteger maxDistanceHolddownTime;
	private BigInteger marginCounter;

	public Race() {
		this.duration = BigInteger.ZERO;
		this.recordDistance = BigInteger.ZERO;
		this.maxDistance = BigInteger.ZERO;
		this.maxDistanceHolddownTime = BigInteger.ZERO;
		this.marginCounter = BigInteger.ZERO;
	}

	public BigInteger getDuration() {
		return duration;
	}

	public void setDuration(BigInteger duration) {
		this.duration = duration;
	}

	public BigInteger getRecordDistance() {
		return recordDistance;
	}

	public void setRecordDistance(BigInteger recordDistance) {
		this.recordDistance = recordDistance;
	}

	public BigInteger getMaxDistance() {
		return maxDistance;
	}

	public void setMaxDistance(BigInteger maxDistance) {
		this.maxDistance = maxDistance;
	}

	public BigInteger getMaxDistanceHolddownTime() {
		return maxDistanceHolddownTime;
	}

	public void setMaxDistanceHolddownTime(BigInteger maxDistanceHolddownTime) {
		this.maxDistanceHolddownTime = maxDistanceHolddownTime;
	}

	public BigInteger getMarginCounter() {
		return marginCounter;
	}

	public void setMarginCounter(BigInteger marginCounter) {
		this.marginCounter = marginCounter;
	}

	@Override
	public String toString() {
		return "Race [duration=" + duration + ", recordDistance=" + recordDistance + ", maxDistance=" + maxDistance
				+ ", maxDistanceHolddownTime=" + maxDistanceHolddownTime + ", marginCounter=" + marginCounter + "]";
	}

}
