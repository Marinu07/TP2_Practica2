package simulator.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.misc.SortedArrayList;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

@SuppressWarnings("serial")
public class EventsTableModel extends AbstractTableModel implements TrafficSimObserver{
	private List<Event> _events;
	private  String col[] = {"Time", "Desc."};
	
	EventsTableModel(Controller ctrl) {
		
		_events =  new SortedArrayList<Event>();
		ctrl.addObserver(this);
	}

	
	@Override
	public int getColumnCount() {
		return col.length;
	}

	@Override
	public int getRowCount() {
		return _events.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		Object obj = null;
		if(col == 0) {
			obj = _events.get(row).getTime();
		}
		if(col == 1 ) {
			obj = _events.get(row).toString();
		}
		return obj;
	}
	
	@Override
	public String getColumnName(int col){
		return this.col[col];
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		_events = events;
		fireTableDataChanged();
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		//nada
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		_events = events;
		fireTableDataChanged();
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		_events = events;
		fireTableDataChanged();
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		_events = events;
		fireTableDataChanged();
		
	}

	@Override
	public void onError(String err) {
		// nada
		
	}

}
