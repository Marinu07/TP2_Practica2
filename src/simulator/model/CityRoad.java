package simulator.model;

import Exceptions.IncorrectArgumentException;

public class CityRoad extends Road {

	
	CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather)throws IncorrectArgumentException{
		super (id,  srcJunc, destJunc, maxSpeed, contLimit, length, weather); //da error si pongo try y catch
	}
	
	@Override
	void reduceTotalContamination() throws IncorrectArgumentException{
		//x depende de Weather
		//totalCont no se devuelve siendo negativo
		int x=2;
		if(weather==Weather.WINDY||weather==Weather.STORM) {
			x=10;
		}
		this.totalCont -= x;
		if(this.totalCont<0) {
			this.totalCont=0;
		}
	}
	
	@Override
	void updateSpeedLimit() {
		//no hace nada
	}
	
	int calculateVehicleSpeed(Vehicle v) {
		return ((int)Math.ceil(((11.0-v.getContClass())/11.0)*this.maxSpeed));
	}

	
}
