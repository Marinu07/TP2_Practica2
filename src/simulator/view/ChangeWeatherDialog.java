package simulator.view;

import java.util.ArrayList;
import java.util.List;


import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

public class ChangeWeatherDialog extends MyDialogo<Weather> {

	private static final long serialVersionUID = 1L;
	/*private static final Color _lIGHT_GREEN = new Color(230, 255, 243);
	private static final Color _MEDIUM_GREEN = new Color(179, 255, 217);
	private static final Color _DARK_GREEN = new Color(77, 255, 169);*/
	private int _time;
	
	
	public ChangeWeatherDialog(Controller c, List<Road> roads, int time) {
		super("Change Road Weather","Schedule an event to change the weather of a road after a given number of simulation ticks from now",c,"Road:","Weather:");
		this._time=time;
		List<String> carreteras= new ArrayList<String>();
		for(Road ro :roads) {
			carreteras.add(ro.getId());
		}
		Weather[] tiempo = {Weather.CLOUDY,Weather.RAINY,Weather.STORM,Weather.SUNNY,Weather.SUNNY,Weather.WINDY};
		initGUI(carreteras,tiempo);
	}


	@Override
	protected Event createEvent(int i, List<Pair<String, Weather>> cs) {
		return new SetWeatherEvent(_time+i,cs);
	}

}
