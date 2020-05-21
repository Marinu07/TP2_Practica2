package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewCityRoad;
import simulator.model.Weather;

public class NewCityRoadEventBuilder extends Builder<Event>{

	public NewCityRoadEventBuilder() {
		super("new_city_road");
		
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		NewCityRoad cityRoad = null;
		if(data.has("time")&&data.has("id")&&data.has("src")&& data.has("dest") && data.has("length")&&data.has("co2limit")&&data.has("maxspeed")&&data.has("weather")){
			cityRoad= new NewCityRoad(data.getInt("time"),data.getString("id"),data.getString("src"),data.getString("dest"),data.getInt("length"),data.getInt("co2limit"),
					data.getInt("maxspeed"),Weather.valueOf(data.getString("weather")));
			
		}
		return cityRoad;
	}
	
	  

}
