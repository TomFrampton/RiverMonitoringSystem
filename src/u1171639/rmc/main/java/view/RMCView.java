package u1171639.rmc.main.java.view;

import u1171639.rmc.main.java.controller.RMCController;
import u1171639.rmc.main.java.model.Alarm;

public interface RMCView {
	public void start(RMCController controller);
	public void alarmRaised(Alarm alarm);
	public void updateView();
}
