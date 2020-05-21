package simulator.model;

import Exceptions.IncorrectArgumentException;



public class NewCityRoad extends Event {
	private String _id;
	private String _srcJunc;
	private String _destJunc; 
	private int _co2Limt;
	private int _maxSpeed;
	private Weather _weather;
	private int _length;


	public NewCityRoad(int time,String id, String srcJunc, String destJunc,int length, int co2Limt, int maxSpeed,Weather weather) {
		super(time);
		this._id=id;
		this._srcJunc= srcJunc;
		this._destJunc= destJunc;
		this._co2Limt= co2Limt;
		this._maxSpeed= maxSpeed;
		this._weather= weather;
		this._length= length;
	}

	@Override
	void execute(RoadMap map) throws IncorrectArgumentException {
		CityRoad cityRoad= new CityRoad(this._id,map.juncMap.get(_srcJunc),map.juncMap.get(_destJunc),this._maxSpeed,this._co2Limt,this._length,this._weather);
		try {
		map.addRoad(cityRoad);
		}catch(IncorrectArgumentException e){
			throw e;
		}
	}
	@Override
	public String toString() {
		return "New City Road '"+_id+"'";
	}

}
