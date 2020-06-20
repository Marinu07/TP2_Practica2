package simulator.model;

import java.util.List;

import org.json.JSONObject;

import Exceptions.IncorrectArgumentException;

public class VehicleNovice extends Vehicle{

	private int totalContamination;
	private int distance;
	private VehicleStatus status;
	private int maxDistance;
	private int indexIt;
	private int location;
	private int previousLocation;
	private int currentSpeed;

	protected VehicleNovice(String id, int maxSpeed, int contClass, List<Junction> itinerary)
			throws IncorrectArgumentException {
		super(id, maxSpeed, contClass, itinerary);
		//crear una copia para la lista
		if(maxSpeed>=0 && contClass<=10 && contClass>=0 && itinerary.size()>=2 ) {
			this.totalContamination=0;
			this.distance=0;
			this.status= VehicleStatus.PENDING;
		}
		else {
			throw new IncorrectArgumentException("Incorrect Arguments: Vehicle Constructor");
		}
	}
	
	@Override
	protected void advance(int time) throws IncorrectArgumentException {
		int min;
		if(status == VehicleStatus.TRAVELING) {
			int c;
			//se actualiza localización
			try {
			min = Math.min(getLocation()+getCurrentSpeed(),getRoad().getLong()); 
			if((distance + (min-location)) < maxDistance) {
				this.previousLocation = location;
				location = min;
				this.distance+= location-previousLocation;
			}
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
	
	@Override
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
		vehicle.put("max distance", this.maxDistance);
		return vehicle;
	}

}
