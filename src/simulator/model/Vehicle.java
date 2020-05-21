package simulator.model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;//en las diapositivas pone que usemos java.util. pero poner solo eso da error 

import org.json.JSONObject;

import Exceptions.IncorrectArgumentException;

public class Vehicle extends SimulatedObject implements Comparable<Vehicle>{
	
	private List <Junction> itinerary; //lista de cruces que representa el itinerario SOLO LECTURA
	private int maxSpeed; //velocidad máxima a la que va a ir el vehículo
	private int currentSpeed; //velocidad actual a la que se encuentra el vehículo
	private VehicleStatus status; //estado del vehículo PENDING, TRAVELING, WAITING, ARRIVED
	private Road road; //carretera actual sobre la cual está circulando (tipo road)
	private int location; //distancia desde el comienzo de la carretera actual 
	private int contClass; //grado de contaminacion emitido por el vehículo cada tick
	private int totalContamination; // contaminacion emitida durante su trayectoria
	private int distance; //distancia total recorrida
	private int previousLocation; //kilometros recorridos 
	private int indexIt; //indica por donde del itinerario vamos
	
	protected Vehicle(String id, int maxSpeed, int contClass, List <Junction> itinerary) throws IncorrectArgumentException{
		super(id);
		//crear una copia para la lista
		this.itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary)); 
		if(maxSpeed>=0 && contClass<=10 && contClass>=0 && itinerary.size()>=2 ) {
			this.maxSpeed = maxSpeed;
			this.contClass = contClass;
			this.totalContamination=0;
			this.distance=0;
			this.indexIt=0;
			this.status= VehicleStatus.PENDING;
		}
		else {
			throw new IncorrectArgumentException("Incorrect Arguments: Vehicle Constructor");
		}
	}
	
	void setSpeed(int s) throws IncorrectArgumentException {
		if(s >=0) {
			if(status==VehicleStatus.TRAVELING) {
				currentSpeed = Math.min(s, maxSpeed);
			}
		}else {
			throw new IncorrectArgumentException("Incorrect Arguments: speed is incorrect");
		}
	}
	
	public void setContaminationClass(int c) throws IncorrectArgumentException{
		if(c>=0 && c<=10) {
			contClass = c;
		}
		else {
			throw new IncorrectArgumentException("Incorrect Arguments: contamination is incorrect");
		}
	}
	

	protected void advance(int time) throws IncorrectArgumentException {
		if(status == VehicleStatus.TRAVELING) {
			int c;
			//se actualiza localización
			try {
			this.previousLocation = location;
			location = Math.min(getLocation()+getCurrentSpeed(),getRoad().getLong()); 
			this.distance+= location-previousLocation;
			//calcula la contaminación
			c = getContClass() * (getLocation()-previousLocation);
			totalContamination += c;
			//añade c a la contaminacion de la carretera
			getRoad().addContamination(c);
			//el segundo argumento es la longitud de la carretera
			if(getLocation() ==getRoad().getLong() ) {
				// entra a la cola del cruce correspondiente (llamando a un metodo de junction)
				status = VehicleStatus.WAITING;
				this.currentSpeed=0;
				this.getItinerary().get(this.indexIt).enter(this);
				
			}
			}catch ( IncorrectArgumentException ex){
				throw ex;
			}
		}
	}
	
	void moveToNextRoad() throws IncorrectArgumentException{
		
		if( (status != VehicleStatus.PENDING)&&(status!= VehicleStatus.WAITING)){
			throw new IncorrectArgumentException("Incorrect Arguments: cannot move vehicle to next road");
		}else {

			if(status== VehicleStatus.WAITING) {
				//sale de la carretera source del cruce 
				road.exit(this);
			}
			//metodo junction para saber en el cruce cual es la siguiente
			if((indexIt+1)<this.itinerary.size()){//si no a acabado su ruta
				this.road = getItinerary().get(indexIt).roadTo(getItinerary().get(indexIt+1));		
				this.location=0;
				this.road.enter(this);
				this.indexIt++;
				status = VehicleStatus.TRAVELING;
			}else {
				status= VehicleStatus.ARRIVED;
			}
		}
	}
	
	public JSONObject report() {
		JSONObject vehicle = new JSONObject();
		vehicle.put("id",this.getId());
		vehicle.put("speed",this.getCurrentSpeed());
		vehicle.put("distance", this.distance);
		vehicle.put("co2",this.totalContamination);
		vehicle.put("class", this.getContClass());
		vehicle.put("status", this.status.toString());
		if (status != VehicleStatus.PENDING && status != VehicleStatus.ARRIVED) {
			vehicle.put("road", this.getRoad().getId());
			vehicle.put("location", this.getLocation());
		}
		return vehicle;
	}

	public int getLocation() {
		return location;
	}
	
	public int getCurrentSpeed() {
		return currentSpeed;
	}

	public int getContClass() {
		return contClass;
	}

	@Override
	//ordenado segun su posicion en la carretera actual descendente
	public int compareTo(Vehicle arg0) { 
		if (this.location ==arg0.location) return 0;
		 else if (this.location < arg0.location) return 1;
		 else return -1;
	}

	public Road getRoad() {
		return road;
	}

	//TODO: cambiada a publica
	public List <Junction> getItinerary() {
		return itinerary;
	}

	public Object getMaxSpeed() {
		return maxSpeed;
	}

	public Object getTotalCont() {
		return totalContamination;
	}

	public Object getDistance() {
		return distance;
	}
	
	//TODO: mirar bien el uso de esto en VehiclesTableModel
	public Object getStatus() {
		switch(status) {
		case PENDING: return "Pending";
		case TRAVELING: return road + ":" + location;
		case WAITING: return getItinerary().get(indexIt);
		case ARRIVED: return "Arrived";
		default: return null;
		}
	
	}
	
	public VehicleStatus getEstado() {
		return status;
		
	}
}
