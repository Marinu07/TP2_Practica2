package simulator.model;


import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import Exceptions.IncorrectArgumentException;
import simulator.misc.SortedArrayList;

public abstract class Road extends SimulatedObject {
		
		private Junction srcJunc; //cruce origen
		private Junction destJunc; //cruce destino
		private int length; //longitud de la carretera
		protected int maxSpeed; //velocidad maxima permitida por el fabricante de carreteras 
		protected int limSpeed; //limite actual de velocidad
		protected int contLimit; //limite de contaminacion
		protected Weather weather;
		protected int totalCont; //contaminacion acumulada
		private List<Vehicle> vehicles; //orden descendente segun localizacion
		
	protected Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) throws IncorrectArgumentException{
		super(id);
		if(maxSpeed>0 && contLimit>=0 && length>0 && srcJunc!= null && destJunc!= null && weather!= null ) {
			this.maxSpeed = maxSpeed;
			this.contLimit = contLimit;
			this.length = length;
			this.weather= weather;
			this.limSpeed= this.maxSpeed;
			this.totalCont=0;
			vehicles = new SortedArrayList<Vehicle>(); //usamos linked list porque se van a modificar con frecuencia
			this.srcJunc= srcJunc;
			this.destJunc= destJunc;
			this.srcJunc.addOutgoingRoad(this);
			this.destJunc.addIncomingRoad(this);
		}else {
			throw new IncorrectArgumentException("Incorrect Argument: road constructor");
		}
	}
	
	protected void enter (Vehicle v) throws IncorrectArgumentException { //lo llama vehicle
		if(v.getLocation() == 0 && v.getCurrentSpeed() == 0) { 
			vehicles.add(v); //se añade al final
		}
		else {
			throw new IncorrectArgumentException("Incorrect Argument: location or speed are not zero");
		}
	}
	
	protected void exit(Vehicle v) { //lo llama Vehicle
		vehicles.remove(v);//elimina v de la lista
	}
	
	void setWeather(Weather w) throws IncorrectArgumentException {
		if(w != null) {
			weather = w;
		}
		else {
			throw new IncorrectArgumentException("Incorrect Argument: weather is null");
		}
	}
	
	void addContamination(int c) throws IncorrectArgumentException {
		totalCont += c;
		if(c<0) {
			throw new IncorrectArgumentException("Incorrect Argument: contamination is zero");
		}
	}
	
	abstract void reduceTotalContamination() throws IncorrectArgumentException;
	
	abstract void updateSpeedLimit();
	
	abstract int calculateVehicleSpeed(Vehicle v);
	
	void advance(int time) throws IncorrectArgumentException{
		reduceTotalContamination();
		
		updateSpeedLimit();//velocidad en el paso actual de la simulacion
		
		Iterator<Vehicle> it = vehicles.iterator(); 
		try {
			while (it.hasNext()){
				Vehicle n = it.next();
				n.setSpeed( calculateVehicleSpeed(n)); //lanza excepcion
				n.advance(time);
			}
			if(!vehicles.isEmpty()) {
				Collections.sort(vehicles);
			}
		 } catch (IncorrectArgumentException ex){
			throw ex;
		}
		
		
	}

	public String getId() {
		return _id;
	}
	
	public int getLong() {
		return this.length;
	}
	
	public JSONObject report() {
		JSONObject road = new JSONObject();
		
		road.put("id",this._id);
		road.put("speedlimit", this.limSpeed);
		road.put("weather", this.weather);
		road.put("co2", this.totalCont);
		road.put("vehicles", vehiclesJason());
		return road;
	}

	private JSONArray vehiclesJason() {
		JSONArray json = new JSONArray();
		Iterator<Vehicle> it = vehicles.iterator(); 
		
		while (it.hasNext()){
			Vehicle n = it.next();
			json.put(n.getId());
		}
		return json;
	}

	public Junction getDestJunc() {
		return destJunc;
	}

	public Junction getSrcJunc() {
		return srcJunc;
	}

	public double getTotalCO2() {
		return totalCont;
	}

	public Weather getWeather() {
		return weather;
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}

	public int getSpeedLimit() {
		return limSpeed;
	}

	public int getCO2Limit() {
		return contLimit;
	} 
	
	public int getLength() {
		return length;
	}
}
