package simulator.model;

import java.util.Iterator;
import java.util.List;

import Exceptions.IncorrectArgumentException;
import simulator.misc.Pair;



public class SetStrategyEvent extends Event {
	
	private List<Pair<String, LightSwitchingStrategy>> _st;

	public SetStrategyEvent(int time, List<Pair<String, LightSwitchingStrategy>> st) {
		super(time);
		this._st = st;
	}
	@Override
	void execute(RoadMap map) throws IncorrectArgumentException {
		if(_st != null) {
			
			Iterator<Pair<String, LightSwitchingStrategy>> it = _st.iterator(); 
			
			while (it.hasNext()){
				Pair<String, LightSwitchingStrategy> s = it.next();
				map.juncMap.get(s.getFirst()).setStrategy(s.getSecond());
			}
			
		}
		else {
			throw new IncorrectArgumentException("Incorrect argument: Weather list is null");
		}
	}

}
