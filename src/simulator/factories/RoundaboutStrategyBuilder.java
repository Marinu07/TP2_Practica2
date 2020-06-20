package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.RoundaboutStrategy;

public class RoundaboutStrategyBuilder extends Builder <LightSwitchingStrategy>{
	public RoundaboutStrategyBuilder() {
		super("roundabout_strategy_lss");
	}

	@Override
	protected LightSwitchingStrategy createTheInstance(JSONObject data) {
		int time=1;
		if(data.has("timeslot")) {
			time= data.getInt("timeslot");
		}
		return new RoundaboutStrategy(time);
	}
}
