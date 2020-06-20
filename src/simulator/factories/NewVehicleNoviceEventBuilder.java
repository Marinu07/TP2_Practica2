package simulator.factories;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewVehicleNoviceEvent;

public class NewVehicleNoviceEventBuilder extends Builder<Event> {

	public NewVehicleNoviceEventBuilder() {
		super("new_vehicle_novice");
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		NewVehicleNoviceEvent vehi= null;
		if(data.has("time")&&data.has("id")&&data.has("maxspeed")&& data.has("class")
				&& data.has("itinerary")&&data.has("maxdistance")) {
			List<String> itinerary= new ArrayList<String>();
			Iterator<Object> it = data.getJSONArray("itinerary").iterator(); 
			
			while(it.hasNext()) {
				String st =(String) it.next();
				itinerary.add(st);
			}
			
			vehi= new NewVehicleNoviceEvent(data.getInt("time"),data.getString("id"),data.getInt("maxspeed"),data.getInt("class"),itinerary, data.getInt("maxdistance"));
		}
		return vehi;
	}

}
