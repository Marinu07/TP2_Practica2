package simulator.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Exceptions.IncorrectArgumentException;

public class NewVehicleNoviceEvent extends NewVehicleEvent{
	private String _id;
	private List<String> _itinerary;
	private int maxSpeed;
	private int contClass;
	private int maxDistance;
	public NewVehicleNoviceEvent(int time, String id, int maxSpeed, int contClass, List<String> itinerary, int maxDistance) {
		super(time, id, maxSpeed, contClass, itinerary);
		this.maxDistance = maxDistance;
	}
	
	@Override
	void execute(RoadMap map) throws IncorrectArgumentException {
		List<Junction> itineraryJunc = new ArrayList<>();
		Iterator<String> it = _itinerary.iterator(); 

		while (it.hasNext()){
			String n = it.next();
			itineraryJunc.add(map.juncMap.get(n));
		}
		
		Vehicle vehicle = new VehicleNovice(_id, maxSpeed, contClass, itineraryJunc, maxDistance);
		try {
			map.addVehicle(vehicle);
			vehicle.moveToNextRoad();
		}catch(IncorrectArgumentException e){
			throw e;
		}
	}
	
	@Override
	public String toString() {
		return "New Vehicle Novice'"+ _id + "'";
	}


}
