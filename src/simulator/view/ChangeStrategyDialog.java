package simulator.view;

import java.util.ArrayList;
import java.util.List;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.Junction;

public class ChangeStrategyDialog extends MyDialogo<String>{
	int _time;

	public ChangeStrategyDialog(Controller c, List<Junction> junctions, int time) {
		super("Change lights strategy","Schedule an event to change the strategy class of a junction after a given number of simulation thicks from now.",c,"Lights Strategy Class:","Ticks:");
		 List<String> junc = new ArrayList<String>();
		 
		for(Junction junci : junctions) {
			junc.add(junci.getId());
		}
		String [] lights = {"MostCrowdedStrateg","RoundaboutStrategy","RoundRobinStrategy"};
		this._time= time;
		initGUI(junc,lights);
	}

	@Override
	protected Event createEvent(int i, List<Pair<String, String>> cs) {
	
		return null;
	}

}
