package simulator.model;

import java.util.Iterator;
import java.util.List;

import Exceptions.IncorrectArgumentException;
import simulator.misc.Pair;

public class SetWeatherEvent extends Event{

	private List<Pair<String, Weather>> _ws;

	public SetWeatherEvent(int time, List<Pair<String, Weather>> ws) {
		super(time);
		this._ws = ws;
	}

	@Override
	void execute(RoadMap map) throws IncorrectArgumentException {
		if(_ws != null) {
			
			Iterator<Pair<String, Weather>> it = _ws.iterator(); 
			
			while (it.hasNext()){
				Pair<String, Weather> w = it.next();
				map.roadMap.get(w.getFirst()).setWeather(w.getSecond());
			}
			
		}
		else {
			throw new IncorrectArgumentException("Incorrect argument: Weather list is null");
		}
		
	}
	
	public String toString() {
		String st = "Change Weather: [";
		for(Pair<String, Weather> par :_ws) {
			st+="("+par.getFirst()+","+par.getSecond()+")";
		}
		st+="]";
		return st;
	}

}
