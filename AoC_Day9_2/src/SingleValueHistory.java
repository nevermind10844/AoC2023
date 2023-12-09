import java.util.ArrayList;
import java.util.List;

public class SingleValueHistory {
	private List<Long> history;
	private SingleValueHistory differences;
	private boolean allZero;

	public SingleValueHistory() {
		this.history = new ArrayList<>();
		this.allZero = true;
	}

	public List<Long> getHistory() {
		return history;
	}

	public void setHistory(List<Long> history) {
		this.history = history;
	}

	public SingleValueHistory getDifferences() {
		return differences;
	}

	public void setDifferences(SingleValueHistory differences) {
		this.differences = differences;
	}

	public void addHistory(Long value) {
		if (!value.equals(0L))
			this.allZero = false;
		this.history.add(value);
		if (this.history.size() > 1) {
			if (this.differences == null)
				this.differences = new SingleValueHistory();
			this.differences.addHistory(this.history.get(this.history.size() - 1) - this.history.get(this.history.size() - 2));
		}
	}
	
	public Long evaluate() {
		Long result;
		if(!this.isAllZero()) {
			Long lowerResult = this.differences.evaluate();
			result = this.history.get(0) - lowerResult;
		} else {
			result = 0L;
		}
		return result;
	}

	public boolean isAllZero() {
		return allZero;
	}

	public void setAllZero(boolean allZero) {
		this.allZero = allZero;
	}

	@Override
	public String toString() {
		return "SingleValueHistory [history=" + history + ", differences=" + differences + ", allZero=" + allZero + "]";
	}

}
