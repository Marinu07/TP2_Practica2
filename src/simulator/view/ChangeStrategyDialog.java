package simulator.view;

import java.util.ArrayList;
import java.util.List;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.LightSwitchingStrategy;
import simulator.model.SetStrategyEvent;

public class ChangeStrategyDialog extends MyDialogo<LightSwitchingStrategy>{
	private static final long serialVersionUID = 1L;
	int _time;

	public ChangeStrategyDialog(Controller c, List<Junction> junctions, int time) {
		super("Change lights strategy","Schedule an event to change the strategy class of a junction after a given number of simulation thicks from now.",c,"Lights Strategy Class:","Ticks:");
		 List<String> junc = new ArrayList<String>();
		 
		for(Junction junci : junctions) {
			junc.add(junci.getId());
		}
		//TODO: habrá que poner algo aqui abajo xd
		LightSwitchingStrategy[] lights = {RoundaboutStrategy,RoundRobinStrategy,MostCrowdedStrategy} ;
		this._time= time;
		initGUI(junc,lights);
	}

	@Override
	protected Event createEvent(int i, List<Pair<String, LightSwitchingStrategy>> cs) {
		return new SetStrategyEvent(_time+i,cs);
	}

}
