package simulator.model;

import java.util.Iterator;
import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy{
	private int timeSlot; //Tiks consecutivos mediante los cuales puede estar en verde
	public MostCrowdedStrategy(int timeSlot) {
		this.timeSlot=timeSlot;
	}

	public int chooseNextGreen(List<Road>roads,List<List<Vehicle>>qs,int currGreen,int lastSwitchingTime, int currTime) {
		int resul=0;
		int tamListaMay=-1;
		int indListMay=0;
		int cont=0;
		int cont2=0;
		if(roads.isEmpty()) {
			resul=-1; //nadie verde
		}else if(currGreen==-1){//si todos en rojo
			Iterator<List<Vehicle>> it = qs.iterator(); 
			while (it.hasNext()){
				List<Vehicle> lista = it.next();
				if(tamListaMay<lista.size()) {
					tamListaMay=lista.size();
					indListMay=cont;
				}
				cont++;
			}
			resul=indListMay;
		}else if(currTime-lastSwitchingTime<timeSlot) { //si no da tiempo a cruzar
			resul=currGreen; //no cambia nada
		}else {
			cont= ((currGreen +1 ) % roads.size());
			Iterator<List<Vehicle>> it = qs.iterator(); 
			while(cont2<qs.size()) {
				List<Vehicle> lista = it.next();
				if(tamListaMay<lista.size()) {
					tamListaMay=lista.size();
					indListMay=cont;
				}
				cont=( (cont +1) % roads.size());
				cont2++;
			}
			resul=indListMay;
		}
		return resul;
	}
}
