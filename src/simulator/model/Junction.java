package simulator.model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import Exceptions.IncorrectArgumentException;
//modulo mapa
public class Junction extends SimulatedObject {
	private List<Road> incList; //lista de carreteras entrantes
	private Map<Junction,Road> outMap; //mapa de carreteras salientes
	private List<List<Vehicle>> incQueueList; //lista de colas
	private Map<Road,List<Vehicle>> roadQueueMap; // mapa carretera-cola
	private int currentGreen; //indice de semaforo en verde
	private int change; //el paso en el cual currentGreen cambio de valor
	private LightSwitchingStrategy lightStrategy; //estrategia para cambio de semaforo
	private DequeuingStrategy DequeStrategy; //estrategia para vaciado de colas
	int xCoor;
	int yCoor;//
	
	
	protected Junction (String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor)throws IncorrectArgumentException{
		super(id);
		if(lsStrategy!=null && dqStrategy!=null && xCoor>0 &&yCoor>0) {
			this.change=0;
			this.lightStrategy=lsStrategy;
			this.DequeStrategy= dqStrategy;
			this.xCoor= xCoor;
			this.yCoor= yCoor;
			this.currentGreen=-1;
			this.incList = new ArrayList<Road>(); //usamos arraylist porque se van a mirar constantemente por indice
			this.outMap= new HashMap<Junction, Road>(); //usamos hash map para que se ordenen por orden de entrada
			this.incQueueList= new ArrayList<List<Vehicle>>(); //usamos arraylist porque se van a mirar constantemente por indice
			this.roadQueueMap= new HashMap<Road, List<Vehicle>>();
		}else {
			throw new IncorrectArgumentException("Incorrect Arguments: Junction Constructor");
		}
	}
	
	void addIncomingRoad(Road r) throws IncorrectArgumentException{
		List<Vehicle> cola = new ArrayList<Vehicle>(); //se crea una cola
		if((r.getDestJunc()) == this) { //cruce dest es el actual
			incList.add(r); //se mete a las carreteras entrantes
			incQueueList.add(cola); //se mete la cola en la lista de colas
			this.roadQueueMap.put(r, cola); //se mete en el hash map
			//Este mapa se va a ir actualizando con los vehiculos
		}
		else {
			throw new IncorrectArgumentException("r is not an inc road");
		}
		
	}
	
	void addOutgoingRoad(Road r)  throws IncorrectArgumentException{
		if(!outMap.containsKey(r.getDestJunc()) && r.getSrcJunc() == this) {
			outMap.put(r.getDestJunc(), r);
		}
		else {
			throw new IncorrectArgumentException("r is not an outgoing road");		}
	}
	
	void enter(Vehicle v) {
		roadQueueMap.get(v.getRoad()).add(v);
	}
	
	Road roadTo(Junction j) {
		return outMap.get(j);
	}
	
	void advance(int time) throws IncorrectArgumentException {
		if(currentGreen!=-1) {
			if(!this.incQueueList.isEmpty()) {
				List<Vehicle> vehiculos;
				if(!this.incQueueList.get(currentGreen).isEmpty()){
					vehiculos = this.DequeStrategy.dequeue(this.incQueueList.get(currentGreen)); //vehiculos es la cola de vehiculos en la carretera con semaforo verde
					if(!vehiculos.isEmpty()) {
						Iterator<Vehicle> it = vehiculos.iterator(); 
							while (it.hasNext()){
								Vehicle vehiculo= it.next();
								this.roadQueueMap.get(vehiculo.getRoad()).remove(vehiculo);//borra a los coches de la lista de espera
								try {
									vehiculo.moveToNextRoad();
								} catch (IncorrectArgumentException e) {
									throw e;
								}
							}
					}
				}
			}
		}
		int green = lightStrategy.chooseNextGreen(incList,incQueueList,currentGreen,change,time);
		if(currentGreen != green) {
			currentGreen = green;
			change = time;
		}	
		
	}
	@Override
	public JSONObject report() {
		JSONObject junct = new JSONObject();
		junct.put("id",this._id);
		if(this.currentGreen==-1) {
			junct.put("green", "none");
		}else {
			junct.put("green",this.incList.get(this.currentGreen).getId());
		}
		junct.put("queues",getJsonCarr());
		
		return junct;
	}

	private JSONArray getJsonCarr() { //array  de JSON  una por cada carretera entrante
		JSONArray carrs = new JSONArray();
		Iterator<Road> it = this.incList.iterator(); //carreteraS ENTRANTES 
		while (it.hasNext()){
			Road road = it.next();
			JSONObject obj = new JSONObject (); //Q
			obj.put("road",road.getId());
			JSONArray v = new JSONArray();//V
			if(this.roadQueueMap.get(road)!=null) {
				Iterator<Vehicle> ve =this.roadQueueMap.get(road).iterator();//devuelve la cola de coches segun caarretera
				
				while (ve.hasNext()) {
					Vehicle co = ve.next();
					v.put(co.getId());
				}
			}
			obj.put("vehicles", v);
			carrs.put(obj);
		}
		return carrs;
	}


	public int getGreenLightIndex() {
		return currentGreen;
	}

	public Object getQueue() {
		String str = "";
		Iterator<Road> it = this.incList.iterator();
		while (it.hasNext()) {
			Road road = it.next();
			str +=( " "+road.getId()+":");
			if (this.roadQueueMap.get(road)!=null) {
				Iterator<Vehicle> ve =this.roadQueueMap.get(road).iterator();
				str += "[";
				while (ve.hasNext()) {
					Vehicle v = ve.next();
					str += v.getId(); 
				}
				str += "]";			}
		}
		return str;
	}
	

	public List<Road>  getInRoads() {
		return incList;
	}

	public int getX() {
		return xCoor;
	}
	
	public int getY() {
		return yCoor;
	}

	public Object getGreenLigh() {
		String resul="NONE";
		if(this.currentGreen!=-1) {
			resul= this.incList.get(this.currentGreen).getId();
		}
		return resul;
	}

	
}
