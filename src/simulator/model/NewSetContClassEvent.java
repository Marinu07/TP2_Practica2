package simulator.model;

import java.util.Iterator;
import java.util.List;

import Exceptions.IncorrectArgumentException;
import simulator.misc.Pair;

public class NewSetContClassEvent extends Event {

	private List<Pair<String, Integer>> _cs;

	public NewSetContClassEvent(int time, List<Pair<String, Integer>> cs) {
		super(time);
		this._cs = cs;
	}

	@Override
	void execute(RoadMap map) throws IncorrectArgumentException {
		if(_cs != null) {
			
			Iterator<Pair<String, Integer>> it = _cs.iterator(); 
					
			while (it.hasNext()){
				Pair<String, Integer> c = it.next();
				map.vehicleMap.get(c.getFirst()).setContaminationClass(c.getSecond());
			}
					
		}
		else {
			throw new IncorrectArgumentException("Incorrect argument: Weather list is null");
		}
				
	}
	@Override
	public String toString() {
		String st = "Change CO2 Class: [";
		for(Pair<String, Integer> par :_cs) {
			st+="("+par.getFirst()+","+par.getSecond()+")";
		}
		st+="]";
		return st;
	}

	

}
