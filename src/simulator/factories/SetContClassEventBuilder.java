package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.NewSetContClassEvent;

public class SetContClassEventBuilder extends Builder<Event> {

	public SetContClassEventBuilder() {
		super("set_cont_class");
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		NewSetContClassEvent cont= null;
		 List<Pair<String, Integer>> pareja= new ArrayList <Pair<String, Integer>>();
		 Pair<String, Integer> par;
		if(data.has("time") && data.has("info")) {
			JSONArray array = data.getJSONArray("info");//conversion a array
			  for(int i=0;i<array.length();i++){
				  String vehicle= array.getJSONObject(i).getString("vehicle");
				  int clas = array.getJSONObject(i).getInt("class");
				  par= new Pair<>(vehicle,clas);
				  pareja.add(par);
			  }
			cont=new NewSetContClassEvent(data.getInt("time"),pareja);
		}
		return cont;
	}

}
