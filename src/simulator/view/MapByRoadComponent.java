package simulator.view;

import java.util.List;

import javax.swing.JComponent;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import simulator.model.Road;
import simulator.model.Vehicle;
import simulator.model.VehicleStatus;
import simulator.model.Weather;

@SuppressWarnings("serial")
public class MapByRoadComponent extends JComponent implements TrafficSimObserver {
	
	private static final Color _BG_COLOR = Color.WHITE;
	private static final int _JRADIUS = 10;
	private static final Color _ROAD_COLOR = Color.DARK_GRAY;
	private static final Color _RED_LIGHT_COLOR = Color.RED;
	private static final Color _GREEN_LIGHT_COLOR = Color.GREEN;
	private static final int _IMG_SIZE = 32;
	private RoadMap _map;
	private Image _car;
	private Image _sun;
	private Image _cloud;
	private Image _storm;
	private Image _wind;
	private Image _cont0;
	private Image _cont1;
	private Image _cont2;
	private Image _cont3;
	private Image _cont4;
	private Image _cont5;
	private Image _meta;
	
	public MapByRoadComponent(Controller _ctrl) {
		initGUI();
		_ctrl.addObserver(this);
		setPreferredSize(new Dimension(300,200));
	}
	private void initGUI() {
		_car = loadImage("car.png");
		_sun = loadImage("sun.png");
		_cloud = loadImage("cloud.png");
		_storm = loadImage("storm.png");
		_wind = loadImage("wind.png");
		_cont0 = loadImage("cont_0.png");
		_cont1 = loadImage("cont_1.png");
		_cont2 = loadImage("cont_2.png");
		_cont3 = loadImage("cont_3.png");
		_cont4 = loadImage("cont_4.png");
		_cont5 = loadImage("cont_5.png");
		_meta= loadImage("meta.png");
		
	}
	
	private Image loadImage(String string) {
		Image i = null;
		try {
			return ImageIO.read(new File("resources/icons/" + string));
		} catch (IOException e) {
		}
		return i;
	}
	
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		// clear with a background color
		g.setColor(_BG_COLOR);
		g.clearRect(0, 0, getWidth(), getHeight());

		if (_map == null || _map.getRoads().size() == 0) {
			g.setColor(Color.red);
			g.drawString("No map yet!", getWidth() / 2 - 50, getHeight() / 2);
		} else {
			drawMap(g);
		}
	}
	
	

	private void drawMap(Graphics2D g) {
		int i = 0; 
		Boolean acabado=true;
		for (Road r : _map.getRoads()) {
	
			// the road goes from (x1,y1) to (x2,y2)
			int x1 = 50;
			int x2 = getWidth()-100;
			int y = (i+1)*50;
			g.setColor(_ROAD_COLOR);
			g.drawLine(x1, y, x2, y);
			
			//origen carretera
			Color junction1Color = Color.blue;
			g.setColor(junction1Color);
			g.fillOval(x1 - _JRADIUS / 2, y - _JRADIUS / 2, _JRADIUS, _JRADIUS);
			g.drawString(r.getSrcJunc().getId(), x1, y-10);
			
			//fin
			Color junction2Color = _RED_LIGHT_COLOR;
			int idx = r.getDestJunc().getGreenLightIndex();
			if (idx != -1 && r.equals(r.getDestJunc().getInRoads().get(idx))) {
				junction2Color = _GREEN_LIGHT_COLOR;
			}
			g.setColor(junction2Color);
			g.fillOval(x2 - _JRADIUS / 2, y - _JRADIUS / 2, _JRADIUS, _JRADIUS);
			g.drawString(r.getDestJunc().getId(), x2, y-10);
			
			//coche
			for(Vehicle v: _map.getVehicle()) {
				if(r.equals(v.getRoad())) {
					int vX= x1 +(int) ((x2-x1)*((double)v.getLocation()/(double)r.getLength())); 
					g.drawImage(_car, vX, y-10 , 16, 16, this);
					g.drawString(v.getId(), vX, y-10);
					if(!v.getEstado().equals(VehicleStatus.ARRIVED)) {
						acabado=false;
					}
				}
			}	
			//idetificador ROAD
			g.setColor(Color.DARK_GRAY);
			g.drawString(r.getId(), 10, y);
				i++;
			//contamination
			int cont = (int) Math.floor(Math.min((double) r.getTotalCO2()/(1.0 + (double)r.getCO2Limit()),1.0)/0.19);
			Image contIm = mapContIm(cont); 
			g.drawImage(contIm, x2 +(3*_IMG_SIZE/2), y-(_IMG_SIZE/2), _IMG_SIZE, _IMG_SIZE, this);
			
			//condiciones climatologicas
			Image weather = mapWeatherIm(r.getWeather()); 
			g.drawImage(weather, x2+(_IMG_SIZE/3), y-(_IMG_SIZE/2) , _IMG_SIZE, _IMG_SIZE, this);
			
		}
		//meta
		if(acabado) {
			g.drawImage(_meta,150,190,_IMG_SIZE*6, _IMG_SIZE*3, this);
		}
		
	}
	

	private Image mapContIm(int cont) {

		switch(cont) {
		case 0: return _cont0;
		case 1: return _cont1;
		case 2: return _cont2;
		case 3: return _cont3;
		case 4: return _cont4;
		case 5: return _cont5;
		default: return null;
		}
		
	}
	private Image mapWeatherIm(Weather w) {
		
		switch(w) {
		case SUNNY: return _sun;
		case CLOUDY: return _cloud;
		case STORM: return _storm;
		case WINDY: return _wind;
		default: return null;
		}
	}
	
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		update(map);
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		update(map);
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		update(map);
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		update(map);
	}

	@Override
	public void onError(String err) {
	}
	
	public void update(RoadMap map) {
		_map = map;
		repaint();
	}

}
