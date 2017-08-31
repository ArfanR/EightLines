package messages;

public class NoBuzzInActionMessage implements Message {

	private boolean update;
	
	public NoBuzzInActionMessage(boolean update) {
		this.update = update;
	}
	
	public boolean getUpdate() {
		return update;
	}
	
}
