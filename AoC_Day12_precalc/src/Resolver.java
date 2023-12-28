import java.util.ArrayList;
import java.util.List;

public class Resolver extends Thread {

	private Input input;
	private long result;

	private List<SubResolver> subResolvers;
	private List<SubResolver> doneSubResolvers;

	private boolean done;

	private static int STACKS = 1;

	public Resolver(Input input) {
		this.input = input;
		this.result = 0L;
		this.done = false;
		this.subResolvers = new ArrayList<>();
		this.doneSubResolvers = new ArrayList<>();
	}

	public boolean isDone() {
		return this.done;
	}

	public Long getResult() {
		return this.result;
	}

	@Override
	public void run() {
		this.result = 0L;

		String faultyData = this.input.getFaultyData();
		for (int i = 0; i < STACKS - 1; i++) {
			this.input.setFaultyData(this.input.getFaultyData() + "?" + faultyData);
		}
		this.input.stackBlocks(STACKS);

		this.input.preProcess();
		Prefitting prefitting = new Prefitting(input);

		for (int i = this.getFirstPossibleStart(); i <= this.getLastPossibleStart(); i++) {
			SubResolver subResolver = new SubResolver(prefitting, i);
			this.subResolvers.add(subResolver);
			subResolver.start();
		}

		while (subResolvers.size() > 0) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			List<SubResolver> readyResolvers = this.subResolvers.stream().filter(resolver -> resolver.isDone())
					.toList();
			this.subResolvers.removeAll(readyResolvers);
			this.doneSubResolvers.addAll(readyResolvers);
		}

		this.result = this.doneSubResolvers.stream().mapToLong(res -> res.getResult()).sum();

		this.done = true;
	}

	public int getFirstPossibleStart() {
		return this.input.getFirstPossibleStart();
	}

	public int getLastPossibleStart() {
		return this.input.getLastPossibleStart();
	}



}
