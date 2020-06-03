package simulator.view;

import java.util.ArrayList;
import java.util.List;


import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.NewSetContClassEvent;
import simulator.model.Vehicle;

public class ChangeC02ClassDialog extends MyDialogo<Integer>{
	
	private static final long serialVersionUID = 1L;
	private int _time;
	
	public ChangeC02ClassDialog(Controller c,List<Vehicle> vehiculos, int time) {
		super("Change CO2 Class","Schedule an event to change the CO2 class of a vehicle after a given number of simulation thicks from now.",c,"CO2 Class:","Ticks:");
		 List<String> veh= new ArrayList<String>();
		 
		for(Vehicle vehi :vehiculos) {
			veh.add(vehi.getId());
		}
		Integer [] ceo2 = {1,2,3,4,5,6,7,8,9,10};
		this._time= time;
		initGUI(veh,ceo2);
	}
	
	@Override
	protected Event createEvent(int i, List<Pair<String,Integer>> cs) {
		return  new NewSetContClassEvent(_time+i,cs);
	}

}

