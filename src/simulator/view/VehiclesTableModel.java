package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

@SuppressWarnings("serial")
public class VehiclesTableModel extends AbstractTableModel implements TrafficSimObserver{
	private List<Vehicle> _vehicles;
	private String col[] = {"Id", "Location", "Itinerary", "CO2 class", "Max Speed", "Speed", "Total CO2", "Distance"};
	
	VehiclesTableModel(Controller ctrl) {
		_vehicles = new ArrayList<>();
		ctrl.addObserver(this);
	}

	@Override
	public int getColumnCount() {
		return col.length;
	}

	@Override
	public int getRowCount() {
		return _vehicles.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		switch(col) {
		case 0: return _vehicles.get(row).getId();
		case 1: return _vehicles.get(row).getStatus();
		case 2: return _vehicles.get(row).getItinerary();
		case 3: return _vehicles.get(row).getContClass();
		case 4: return _vehicles.get(row).getMaxSpeed();
		case 5: return _vehicles.get(row).getCurrentSpeed();
		case 6: return _vehicles.get(row).getTotalCont();
		case 7: return _vehicles.get(row).getDistance();
		default: return null;
		}

	}
	
	@Override
	public String getColumnName(int col){
		return this.col[col];
		
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		//nada
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {	
		this._vehicles= map.getVehicle();
		fireTableDataChanged();	
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		this._vehicles= map.getVehicle();
		fireTableDataChanged();
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		this._vehicles= map.getVehicle();
		fireTableDataChanged();
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		this._vehicles= map.getVehicle();
		fireTableDataChanged();
	}

	@Override
	public void onError(String err) {
		// nada
		
	}

}
