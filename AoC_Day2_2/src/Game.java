import java.util.ArrayList;
import java.util.List;

public class Game {
	private int id;
	List<Draw> drawList;
	private boolean possible;
	
	private int maxRed;
	private int maxGreen;
	private int maxBlue;
	
	private int power;

	public Game() {
		this.id = 0;
		this.drawList = new ArrayList<>();
		this.possible = false;
		
		this.maxRed = 0;
		this.maxGreen = 0;
		this.maxBlue = 0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Draw> getDrawList() {
		return drawList;
	}

	public void setDrawList(List<Draw> drawList) {
		this.drawList = drawList;
	}

	public boolean isPossible() {
		return possible;
	}

	public void setPossible(boolean possible) {
		this.possible = possible;
	}
	
	public int getMaxRed() {
		return maxRed;
	}

	public void setMaxRed(int maxRed) {
		this.maxRed = maxRed;
	}

	public int getMaxGreen() {
		return maxGreen;
	}

	public void setMaxGreen(int maxGreen) {
		this.maxGreen = maxGreen;
	}

	public int getMaxBlue() {
		return maxBlue;
	}

	public void setMaxBlue(int maxBlue) {
		this.maxBlue = maxBlue;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public void addDraw(Draw draw) {
		this.drawList.add(draw);
	}

	@Override
	public String toString() {
		return "Game [id=" + id + ", drawList=" + drawList + ", possible=" + possible + "]";
	}

}
