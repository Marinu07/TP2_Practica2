package simulator.model;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

import Exceptions.IncorrectArgumentException;

public class RoadMap {
	List <Junction> juncList;
	List<Road> roadList;
	List<Vehicle> vehicleList;
	Map<String,Junction> juncMap;
	Map<String,Road> roadMap;
	Map<String,Vehicle>  vehicleMap;
	
	public RoadMap(List <Junction> juncList, List<Road> roadList,List<Vehicle> vehicleList, Map<String,Junction> juncMap,
	Map<String,Road> roadMap, Map<String,Vehicle>  vehicleMap){
		this.juncList = juncList;
		this.roadList = roadList;
		this.vehicleList = vehicleList;
		this.juncMap = juncMap;
		this.roadMap = roadMap;
		this.vehicleMap = vehicleMap;
	}
	
	void addJunction(Junction j) {
		if(!juncMap.containsKey(j.getId())){
			juncList.add(j);
			juncMap.put(j.getId(),j);
		}
	}
	
	void addRoad(Road r) throws IncorrectArgumentException {
		if(!roadMap.containsKey(r.getId()) && (juncMap.containsKey(r.getDestJunc()._id)) && (juncMap.containsKey(r.getSrcJunc()._id))) {
			roadList.add(r);
			roadMap.put(r.getId(),r);
		}else {
			throw new IncorrectArgumentException("Incorrect Arguments: cannot create new road");
		}
	}
	void addVehicle(Vehicle v) throws IncorrectArgumentException {
		Iterator<Junction> it = v.getItinerary().iterator();
		Boolean correct= true;
		if( it.hasNext()) {
			Junction prevJunc = it.next(); // Primer elemento del array
			while (it.hasNext() && correct){ 
				Junction currJunc = it.next();
				correct = prevJunc.roadTo(currJunc)!=null;
				prevJunc = currJunc;
			}
		}
		
		if(!vehicleMap.containsKey(v.getId())&&correct) {
			vehicleList.add(v);
			vehicleMap.put(v.getId(),v);
		}else {
			throw new IncorrectArgumentException("Incorrect Arguments: cannot create new vehicle");
		}
	}
	
	public Junction getJunction(String id) {
		Junction value = null;
		if(juncMap.containsKey(id)) {
			value = juncMap.get(id);
		}
		return value;
	}
	
	public Road getRoad(String id) {
		Road value = null;
		if(roadMap.containsKey(id)) {
			value = roadMap.get(id);
		}
		return value;
	}
	
	public Vehicle getVehicle(String id) {
		Vehicle value = null;
		if(vehicleMap.containsKey(id)) {
			value = vehicleMap.get(id);
		}
		return value;
	}
	
	public  List<Junction>getJunctions(){
		return Collections.unmodifiableList(new ArrayList<>(juncList)); 
	}
	
	public List<Road>getRoads(){
		return Collections.unmodifiableList(new ArrayList<>(roadList)); 
	}
	
	public List<Vehicle>getVehicle(){
		return Collections.unmodifiableList(new ArrayList<>(vehicleList));
	}
	
	void reset() {
		juncList.clear();
		roadList.clear();
		vehicleList.clear();
		juncMap.clear();
		roadMap.clear();
		vehicleMap.clear();
	}
	
	public JSONObject report() {
		JSONObject json = new JSONObject();
		
		JSONArray juncArr = new JSONArray();
		Iterator <Junction> junIt = this.juncList.iterator();
		while(junIt.hasNext()) {
			Junction  jun = junIt.next(); 
			juncArr.put(jun.report());
		}
		json.put("junctions",juncArr);
		
		JSONArray roadArr = new JSONArray();
		Iterator <Road> roIt = this.roadList.iterator();
		while(roIt.hasNext()) {
			Road  ro = roIt.next(); 
			roadArr.put(ro.report());
		}
		json.put("roads", roadArr);
		
		JSONArray vehArr = new JSONArray();
		Iterator <Vehicle> veIt = this.vehicleList.iterator();
		while(veIt.hasNext()) {
			Vehicle  ve = veIt.next(); 
			vehArr.put(ve.report());
		}
		json.put("vehicles", vehArr);
		
		return json;
	}
}
