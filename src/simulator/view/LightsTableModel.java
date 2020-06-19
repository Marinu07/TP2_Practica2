package simulator.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

@SuppressWarnings("serial")
public class LightsTableModel extends AbstractTableModel implements TrafficSimObserver{
	private List<Junction> _junctions;
	private List<Integer> _tiempo = new ArrayList<>();
	private int i = 0;
	private  String col[] = {"Time", "Green Lights"};
	
	public LightsTableModel(Controller ctrl) {
		_junctions = new ArrayList<>();
		ctrl.addObserver(this);
	}

	@Override
	public int getColumnCount() {
		return col.length;
	}

	@Override
	public int getRowCount() {
		return _tiempo.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		Object obj = null;
		if(col == 0) {
			obj = _tiempo.get(row);
		}
		if(col == 1 ) {
			obj = roadsTime();
		}
		return obj;
	}

	private Object roadsTime() {
		List<String> roadTimeList = new ArrayList<String>();
		Iterator<Junction> it = _junctions.iterator();
		while(it.hasNext()) {
			Junction junc = it.next();
			roadTimeList.add((String) junc.getGreenLigh());
		}
		return roadTimeList;
	}
	


	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		_junctions = map.getJunctions(); 
		_tiempo.add(i);
		i++;
		fireTableDataChanged();	
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		_junctions = map.getJunctions();
		fireTableStructureChanged();	
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		_junctions = map.getJunctions(); 
		fireTableStructureChanged();	
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}
	
}
