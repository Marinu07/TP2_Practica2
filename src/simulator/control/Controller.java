package simulator.control;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import Exceptions.IncorrectArgumentException;
import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimulator;

import simulator.model.Observable;
import simulator.model.TrafficSimObserver;

public class Controller implements Observable<TrafficSimObserver>{
	private TrafficSimulator sim;
	private Factory<Event> eventsFactory;
	private OutputStream out;
	private String in;

	//si, no especifica
	public Controller(TrafficSimulator sim, Factory<Event> eventsFactory,OutputStream out) throws IncorrectArgumentException {
		if(sim != null && eventsFactory != null) {
			this.sim = sim;
			this.eventsFactory = eventsFactory;
			this.out=out;
		}
		else {
			throw new IncorrectArgumentException("Incorrect Arguments: Controller Constructor");
		}
		
	}
	
	public void loadEvents(String in) throws IncorrectArgumentException, JSONException, FileNotFoundException {
		this.in=in;
		JSONObject jo = new JSONObject(new JSONTokener( new FileInputStream(in)));
		if(jo.has("events")) {
			JSONArray ar = jo.getJSONArray("events"); 
			  for(int e=0;e<ar.length(); e++){
				  sim.addEvent(eventsFactory.createInstance(ar.getJSONObject(e)));
			  }
			
		}else {
			throw new IncorrectArgumentException("Incorrect Arguments: load Event");
		}
	}
	
	public void run(int n)  throws IncorrectArgumentException , IOException {
		if(in!=null) {
			try {
				String b= "{ \"states\": [";
				for(int i = 0; i < n; i++) {
					sim.advance();
					b += sim.report().toString(3);
					if(i<n-1) {
						b+=",";
					}
				}
				b+="] }";
				if(out!=null) {
					out.write(b.getBytes());
				}
			}catch (IncorrectArgumentException | IOException ex){
				throw ex;
				
			}
		}
		
	}
	
	public void reset() {
		sim.reset();
	}
	public void restart() throws IncorrectArgumentException, JSONException, FileNotFoundException {
		reset();
			loadEvents(this.in);
		
			
	}
	public void addObserver(TrafficSimObserver o){
		sim.addObserver(o);
	}
	
	public void removeObserver(TrafficSimObserver o) {
		sim.removeObserver(o);
	}
	
	public void addEvent(Event e) {
		sim.addEvent(e);
	}
	
}
