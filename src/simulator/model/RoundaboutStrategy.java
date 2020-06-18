package simulator.model;

import java.util.Iterator;
import java.util.List;

public class RoundaboutStrategy implements LightSwitchingStrategy{
	private int timeSlot; //Tiks consecutivos mediante los cuales puede estar en verde
	public RoundaboutStrategy(int timeSlot) {
		this.timeSlot=timeSlot;
	}
	
	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,
			int currTime) {
		int resul=0;
		int indListaOcupada = 0;
		int cont = 0;
		if(roads.isEmpty()) {
			resul=-1; //nadie verde
		}else if(currGreen==-1){
			resul=0; //primer semaforo se pone verde
		}else if(currTime-lastSwitchingTime<this.timeSlot) {
			return currGreen; //todavia no se puede cambiar el semaforo no le da tiempo a la gente a cruzar 
		}else {
			Iterator<List<Vehicle>> it = qs.iterator(); 
			while (it.hasNext() || indListaOcupada != 0){
				List<Vehicle> lista = it.next();
				if(!lista.isEmpty()) {
					indListaOcupada=cont;
				}
				cont++;
			}
			resul=indListaOcupada;
			if (indListaOcupada == -1) {
				resul=(currGreen+1)%roads.size(); //la siguiente carretera entrante (lista en forma circular)
			}
		}
		return resul;
	}

}
