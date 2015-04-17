package u1171639.shared.main.java.exception;

public class RiverMonitoringSystemException extends Exception {
	private String error;
	
	public RiverMonitoringSystemException(String error) {
		this.error = error;
	}
	
	@Override
	public String toString() {
		return this.error;
	}
}
