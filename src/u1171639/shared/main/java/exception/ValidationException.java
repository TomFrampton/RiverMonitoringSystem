package u1171639.shared.main.java.exception;

import java.util.List;

import net.sf.oval.ConstraintViolation;

public class ValidationException extends RiverMonitoringSystemException {
	private List<ConstraintViolation> violations;
	
	public ValidationException(String error, List<ConstraintViolation> violations) {
		super(error);
		this.violations = violations;
	}

	public List<ConstraintViolation> getViolations() {
		return this.violations;
	}

	public void setViolations(List<ConstraintViolation> violations) {
		this.violations = violations;
	}
}
