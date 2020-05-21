package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

@SuppressWarnings("serial")
public class JunctionsTableModel extends AbstractTableModel implements TrafficSimObserver{
	private List<Junction> _junctions;
	private  String col[] = {"Id", "Green", "Queue"};
			
	JunctionsTableModel(Controller ctrl) {
		_junctions = new ArrayList<>();
		ctrl.addObserver(this);
	}

	@Override
	public int getColumnCount() {
		return col.length;
	}

	@Override
	public int getRowCount() {
		return _junctions.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		
		switch(col) {
		case 0: return _junctions.get(row).getId();
		case 1: return _junctions.get(row).getGreenLigh();
		case 2: return _junctions.get(row).getQueue();
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
		_junctions = map.getJunctions(); 
		fireTableDataChanged();	
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		//nada
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
		// nada
		
	}
}
