package simulator.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MoveAllStrategy implements DequeuingStrategy{
	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) { 
		List<Vehicle> resul = new  LinkedList<Vehicle>();
		Iterator<Vehicle> it = q.iterator();
		while (it.hasNext()) {
			Vehicle v = it.next();
			resul.add(v);
		}
		return resul ;
	}
}
