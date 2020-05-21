package simulator.model;

import java.util.List;

public class RoundRobinStrategy implements LightSwitchingStrategy{
	private int timeSlot; //Tiks consecutivos mediante los cuales puede estar en verde
	public RoundRobinStrategy(int timeSlot) {
		this.timeSlot=timeSlot;
	}
	
	public int chooseNextGreen(List<Road>roads,List<List<Vehicle>>qs,int currGreen,int lastSwitchingTime, int currTime) {
		int resul=0;
		if(roads.isEmpty()) {
			resul=-1; //nadie verde
		}else if(currGreen==-1){
			resul=0; //primer semaforo se pone verde
		}else if(currTime-lastSwitchingTime<this.timeSlot) {
			return currGreen; //todavia no se puede cambiar el semaforo no le da tiempo a la gente a cruzar 
		}else {
			resul=(currGreen+1)%roads.size(); //la siguiente carretera entrante (lista en forma circular)
		}
		return resul;
	}
}
