package simulator.factories;

import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.NewJunctionEvent;

public class NewJunctionEventBuilder  extends Builder<Event>{
	private Factory<LightSwitchingStrategy> _lss;
	private Factory<DequeuingStrategy> _dqs;

	public NewJunctionEventBuilder(Factory<LightSwitchingStrategy> lss, Factory<DequeuingStrategy> dqs) {
		super("new_junction");
		this._lss=lss;
		this._dqs=dqs;
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		
		NewJunctionEvent junction = null;
		if(data.has("time")&&data.has("id")&&data.has("coor")&& data.has("ls_strategy") && data.has("dq_strategy")) {
			junction= new NewJunctionEvent(data.getInt("time"),data.getString("id"),_lss.createInstance(data.getJSONObject("ls_strategy")),
			    	_dqs.createInstance(data.getJSONObject("dq_strategy")),data.getJSONArray("coor").getInt(0),data.getJSONArray("coor").getInt(1));
			
		}

		
		return junction;
	}

}
