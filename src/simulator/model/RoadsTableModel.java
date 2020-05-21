package simulator.model;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;


@SuppressWarnings("serial")
public class RoadsTableModel extends AbstractTableModel implements TrafficSimObserver{
	private List<Road> _roads;
	private static final String col[] = {"Id", "Lenght", "Weather", "Max. Speed", "Speed limit", "Total CO2", "CO2 limit"};
			
	RoadsTableModel(Controller ctrl) {
		_roads = new ArrayList<>();
		ctrl.addObserver(this);
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
	public Object getValueAt(int row, int col) {
		switch(col) {
		case 0: return _roads.get(row).getId();
		case 1: return _roads.get(row).getLong();
		case 2: return _roads.get(row).getWeather();
		case 3: return _roads.get(row).getMaxSpeed();
		case 4: return _roads.get(row).getSpeedLimit();
		case 5: return _roads.get(row).getTotalCO2();
		case 6: return _roads.get(row).getCO2Limit();
		default: return null;
		}
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		_roads = map.getRoads();
		fireTableDataChanged();
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		_roads = map.getRoads();
		fireTableDataChanged();
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		_roads = map.getRoads();
		fireTableDataChanged();
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		_roads = map.getRoads();
		fireTableDataChanged();
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		_roads = map.getRoads();
		fireTableDataChanged();
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}

}
