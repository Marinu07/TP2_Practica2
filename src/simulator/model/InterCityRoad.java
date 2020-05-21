package simulator.model;

import Exceptions.IncorrectArgumentException;

public class InterCityRoad extends Road { 

//carreteras que conectaan ciudades
	
	
	InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather)throws IncorrectArgumentException{
		super (id,  srcJunc, destJunc, maxSpeed, contLimit, length, weather); //da error si pongo try y catch
		
	}
	
	@Override
	void reduceTotalContamination() {
		int x= 20;
		if(this.weather==Weather.SUNNY) {
			x=2;
		}else if(this.weather== Weather.CLOUDY) {
			x=3;
		}else if(this.weather== Weather.RAINY) {
			x=10;
		}else if(this.weather==Weather.WINDY){
			x=15;
		}
		this.totalCont =(int)(((100.0-x)/100.0)* this.totalCont);
	}
	
	@Override
	void updateSpeedLimit() {
		if(this.totalCont>this.contLimit) {
			this.limSpeed=(int)(this.maxSpeed*0.5);
		}
	}
	
	@Override
	int calculateVehicleSpeed(Vehicle v) {
		int speed = limSpeed;
		if(weather == Weather.STORM) {
			speed =(int)(speed* 0.8);
		}
		return speed;
	}

}
