package simulator.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Exceptions.IncorrectArgumentException;

public class NewVehicleEvent extends Event {

	private String _id;
	private int _maxSpeed;
	private int _contClass;
	private List<String> _itinerary;

	public NewVehicleEvent(int time, String id, int maxSpeed, int contClass, List<String> itinerary) {
		super(time);
		this._id = id;
		this._maxSpeed = maxSpeed;
		this._contClass = contClass;
		this._itinerary = itinerary;
	}
	
	@Override
	void execute(RoadMap map) throws IncorrectArgumentException {
		List<Junction> itineraryJunc = new ArrayList<>();
		Iterator<String> it = _itinerary.iterator(); 

		while (it.hasNext()){
			String n = it.next();
			itineraryJunc.add(map.juncMap.get(n));
		}
		
		Vehicle vehicle = new Vehicle(_id, _maxSpeed, _contClass, itineraryJunc);
		try {
			map.addVehicle(vehicle);
			vehicle.moveToNextRoad();
		}catch(IncorrectArgumentException e){
			throw e;
		}
	}
	
	public String toString() {
		return "New Vehicle '"+ _id + "'";
	}

}
