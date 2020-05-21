package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class RoadsTableModel extends AbstractTableModel implements TrafficSimObserver {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Road> _roads;
	private String col[] = {"Id", "Length", "Weather", "Max. Speed", "Speed limit", "Total CO2", "CO2 Limit"};
	
	RoadsTableModel(Controller ctrl) {
		_roads = new ArrayList<>();
		ctrl.addObserver(this);
	}
	
	
	@Override
	public Object getValueAt(int row, int col) {
		switch(col) {
		case 0: return _roads.get(row).getId();
		case 1: return _roads.get(row).getLength();
		case 2: return _roads.get(row).getWeather();
		case 3: return _roads.get(row).getMaxSpeed();
		case 4: return _roads.get(row).getSpeedLimit();
		case 5: return (int)_roads.get(row).getTotalCO2();
		case 6: return _roads.get(row).getCO2Limit();
		default: return null;
		}
	}
	
	@Override
	public int getColumnCount() {
		return col.length;
	}
	
	@Override
	public int getRowCount() {
		return _roads.size();
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
		this._roads= map.getRoads();
		fireTableDataChanged();	
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// nada
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		this._roads= map.getRoads();
		fireTableDataChanged();	
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// nada
		
	}

	@Override
	public void onError(String err) {
		// nada
		
	}

}
