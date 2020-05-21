package simulator.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MoveAllStrategy implements DequeuingStrategy{

	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) { //devuelve una lista con el primer valor de q
		List<Vehicle> resul = new  LinkedList<Vehicle>();
		resul= Collections.unmodifiableList(q); //la lista no se puede modificar
		return resul ;
	}

}
