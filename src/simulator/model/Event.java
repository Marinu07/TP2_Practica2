package simulator.model;

import Exceptions.IncorrectArgumentException;

public abstract class Event implements Comparable<Event> {

	protected int _time;

	Event(int time) {
		if (time < 1)
			throw new IllegalArgumentException("Time must be positive (" + time + ")");
		else
			_time = time;
	}

	//TODO:se ha cambiado a public
	public int getTime() {
		return _time;
	}

	@Override
	public int compareTo(Event o) {
		if (this._time ==o._time) return 0;
		 else if (this._time < o._time) return -1;
		 else return 1;
	}

	abstract void execute(RoadMap map) throws IncorrectArgumentException;
}
