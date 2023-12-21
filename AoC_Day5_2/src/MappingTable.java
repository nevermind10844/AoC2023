import java.util.ArrayList;
import java.util.List;

public class MappingTable {
	private String name;
	private List<Range> ranges;

	public MappingTable() {
		this.name = "";
		this.ranges = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Range> getRanges() {
		return ranges;
	}

	public void setRanges(List<Range> ranges) {
		this.ranges = ranges;
	}
	
	public void addRange(Range range) {
		this.ranges.add(range);
	}
	
	public long getDestination(long source) {
		long destination = source;
		for(Range range : ranges) {
			if(range.isInRange(source)) {
				//System.out.println(String.format("%d in range %s", source, range));
				destination = range.getDestination(source);
				break;
			} else {
				//System.out.println(String.format("%d not in range %s", source, range));
			}
		}
		return destination;
	}

	@Override
	public String toString() {
		return "MappingTable [name=" + name + ", ranges=" + ranges + "]";
	}

}
