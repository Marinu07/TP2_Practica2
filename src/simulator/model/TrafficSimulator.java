package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import Exceptions.IncorrectArgumentException;
import simulator.misc.SortedArrayList;

public class TrafficSimulator implements Observable<TrafficSimObserver> {
	private RoadMap roadMap;
	private List<Event> eventList;
	private int time;
	private List<TrafficSimObserver> observers;
	
	public TrafficSimulator() {
		this.roadMap =  new  RoadMap(new ArrayList<Junction>(),new ArrayList<Road>() ,new ArrayList<Vehicle>() , new HashMap<String,Junction>() ,
				new HashMap<String,Road>() ,new  HashMap<String,Vehicle>()  );
		this.eventList = new SortedArrayList<Event>(); 
		this.time = 0;
		this.observers= new ArrayList<TrafficSimObserver>();
	}
	
	public void addEvent(Event e) {
		eventList.add(e);
		for(TrafficSimObserver ob: observers)
		ob.onEventAdded(roadMap, eventList, e, time);
	}
	

	public void advance() throws IncorrectArgumentException {
		this.time++;
		for(TrafficSimObserver ob: observers)
		ob.onAdvanceStart(roadMap, eventList, time);
		
		while (eventList.get(0).getTime()==time) {
			eventList.get(0).execute(roadMap); //se van creando y metiendo en roadmap
			eventList.remove(0);
		}
		try {
			for(Junction j: roadMap.juncList) {
				j.advance(time);
			}	
			
			for(Road r: roadMap.roadList) {
				r.advance(time);
			}	
		}catch (IncorrectArgumentException ex) {
			for(TrafficSimObserver ob: observers)
			ob.onError(ex.getMessage());
			throw ex;
		}
		for(TrafficSimObserver ob: observers)
		ob.onAdvanceEnd(roadMap, eventList, time);
	}

	public void reset() {
		roadMap.reset();
		eventList.clear();
		this.time = 0;
		for(TrafficSimObserver ob: observers)
		ob.onReset(roadMap, eventList, time);
	}
	
	

	public JSONObject report() {
		JSONObject json = new JSONObject();
		json.put("time", this.time);
		json.put("state", this.roadMap.report());
		return json;
	}

	@Override
	public void addObserver(TrafficSimObserver o) {
		if(!this.observers.contains(o)) {
			this.observers.add(o);
			o.onRegister(roadMap, eventList, time);
		}
	}

	@Override
	public void removeObserver(TrafficSimObserver o) {
		this.observers.remove(o);
		
	}
	
	public List<String>  getVehicles() {
		List<String> vehiculos = new ArrayList<String>();
		List<Vehicle> origve = this.roadMap.getVehicle();
		for(Vehicle v : origve) {
			vehiculos.add(v.getId());
		}
		return vehiculos;
	}
	public int getTime() {
		return this.time;
	}

	public List<String> getRoads() {
		List<String> roads = new ArrayList<String>();
		List<Road> origro = this.roadMap.getRoads();
		for(Road r : origro) {
			roads.add(r.getId());
		}
		return roads;
	}
}
