import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Rock {

	private int posX;
	private int posY;
	private RockType rockType = null;
	private boolean artificial;
	private Map<Rock, Integer> relevantBitsNorth;
	private Map<Rock, Integer> relevantBitsSouth;

	public Rock(int posX, int posY, RockType rockType) {
		this.posX = posX;
		this.posY = posY;
		this.rockType = rockType;
		this.artificial = false;
		this.relevantBitsNorth = new HashMap<>();
		this.relevantBitsSouth = new HashMap<>();
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public RockType getRockType() {
		return rockType;
	}

	public void setRockType(RockType rockType) {
		this.rockType = rockType;
	}

	public boolean isArtificial() {
		return artificial;
	}

	public void setArtificial(boolean artificial) {
		this.artificial = artificial;
	}

	public Map<Rock, Integer> getRelevantBitsSouth() {
		return relevantBitsSouth;
	}

	public void addReleventBitSouth(Rock rock, int bit) {
		this.relevantBitsSouth.put(rock, bit);
	}

	public Map<Rock, Integer> getRelevantBitNorth() {
		return relevantBitsNorth;
	}

	public void addRelevantBitNorth(Rock rock, int bit) {
		this.relevantBitsNorth.put(rock, bit);
	}

	@Override
	public String toString() {
		return "Rock [posX=" + posX + ", posY=" + posY + ", rockType=" + rockType + ", artificial=" + artificial + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(posX, posY);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rock other = (Rock) obj;
		return posX == other.posX && posY == other.posY;
	}
}
