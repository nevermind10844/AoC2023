import java.util.ArrayList;
import java.util.List;

public class Game {
	private int id;
	private List<Integer> winningNumbers;
	private List<Integer> drawnNumbers;
	private int value;
	private List<Game> copies;
	
	public Game() {
		this.id = 0;
		this.winningNumbers = new ArrayList<>();
		this.drawnNumbers = new ArrayList<>();
		this.value = 0;
		this.copies = new ArrayList<>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Integer> getWinningNumbers() {
		return winningNumbers;
	}

	public List<Integer> getDrawnNumbers() {
		return drawnNumbers;
	}

	public int getValue() {
		return value;
	}
	
	public void addWinningNumber(int number) {
		this.winningNumbers.add(number);
	}
	
	public void addDrawnNumber(int number) {
		this.drawnNumbers.add(number);
		if(this.winningNumbers.contains(number)) {
			if(this.value == 0)
				this.value = 1;
			else
				this.value *= 2;
		}
	}

	@Override
	public String toString() {
		return "Game [id=" + id + ", winningNumbers=" + winningNumbers + ", drawnNumbers=" + drawnNumbers + ", value="
				+ value + "]";
	}
	
}
