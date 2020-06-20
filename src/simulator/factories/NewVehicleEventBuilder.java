package simulator.factories;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewVehicleEvent;

public class NewVehicleEventBuilder extends Builder<Event> {

	public NewVehicleEventBuilder() {
		super("new_vehicle");
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		NewVehicleEvent vehi= null;
		if(data.has("time")&&data.has("id")&&data.has("maxspeed")&& data.has("class") && data.has("itinerary")) {
			List<String> itinerary= new ArrayList<String>();
			Iterator<Object> it = data.getJSONArray("itinerary").iterator(); 
			while(it.hasNext()) {
				String st =(String) it.next();
				itinerary.add(st);
			}
			
			vehi= new NewVehicleEvent(data.getInt("time"),data.getString("id"),data.getInt("maxspeed"),data.getInt("class"),itinerary);
		}
		return vehi;
	}
	
}
