package simulator.model;
import org.json.JSONObject;

import Exceptions.IncorrectArgumentException;

public abstract class SimulatedObject {

	protected String _id; //este id va a ser unico para todos los objetos que hereden de esta clase

	SimulatedObject(String id) {
		_id = id;
	}

	public String getId() {
		return _id;
	}

	@Override
	public String toString() {
		return _id;
	}

	abstract void advance(int time)throws IncorrectArgumentException;

	abstract public JSONObject report();
}
