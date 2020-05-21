package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

public class SetWeatherEventBuilder extends Builder<Event> {

	public SetWeatherEventBuilder() {
		super("set_weather");
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		SetWeatherEvent weath= null;
		 List<Pair<String, Weather>> pareja= new ArrayList <Pair<String, Weather>>();
		 Pair<String,Weather> par;
		 if(data.has("time") && data.has("info")) {
			JSONArray array = data.getJSONArray("info");//conversion a array
			  for(int i=0;i<array.length();i++){
				  String road= array.getJSONObject(i).getString("road");
				  Weather wea = Weather.valueOf(array.getJSONObject(i).getString("weather"));
				  par= new Pair<>(road,wea);
				  pareja.add(par);
			  }

			weath= new SetWeatherEvent(data.getInt("time"),pareja);
		}
		return weath;
	}

}
