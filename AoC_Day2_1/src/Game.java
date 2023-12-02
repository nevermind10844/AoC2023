import java.util.ArrayList;
import java.util.List;

public class Game {
	private int id;
	List<Draw> drawList;
	private boolean possible;

	public Game() {
		this.id = 0;
		this.drawList = new ArrayList<>();
		this.possible = false;
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
	
	public void addDraw(Draw draw) {
		this.drawList.add(draw);
	}

	@Override
	public String toString() {
		return "Game [id=" + id + ", drawList=" + drawList + ", possible=" + possible + "]";
	}

}
