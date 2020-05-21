package simulator.model;

import java.util.LinkedList;
import java.util.List;

public class MoveFirstStrategy implements DequeuingStrategy{

	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) { //devuelve una lista con el primer valor de q
		List<Vehicle> resul = new  LinkedList<Vehicle>();
		resul.add(q.get(0)); //devuelve el primero de la lista
		return resul ;
	}

}
