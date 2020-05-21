package simulator.view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JToolBar;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class StatusBar extends  JToolBar implements TrafficSimObserver{

private static final long serialVersionUID = 1L;
private JLabel _time;
private JLabel _news;
private	Controller _ctrl;

	public StatusBar(Controller _ctrl) {
		this._ctrl=_ctrl;
		_ctrl.addObserver(this);
		initGui();
	}

	private void initGui() {
		setLayout(new FlowLayout());
		_time= new JLabel("Time: "+String.valueOf( _ctrl.getTime())+"         ");
		_time.setForeground(new Color(0, 153, 153));
		this.add(_time);
		_news= new JLabel("Simulation started");
		this.add(_news);
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		this._time.setText("Time: "+String.valueOf(time)+"         ");
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// NADA	
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		this._news.setText("Event added: ("+e.toString()+")");
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		this._time.setText("Time: "+String.valueOf(time)+"         ");
		this._news.setText("restarted");
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// NADA
		
	}

	@Override
	public void onError(String err) {
		this._news.setText("error 404 :(");
		
	}

}
