package simulator.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.Road;
import simulator.model.SetWeatherEvent;
import simulator.model.Vehicle;
import simulator.model.Weather;

public class ChangeWeatherDialog extends MyDialogo {

	private static final long serialVersionUID = 1L;
	private static final Color _lIGHT_GREEN = new Color(230, 255, 243);
	private static final Color _MEDIUM_GREEN = new Color(179, 255, 217);
	private static final Color _DARK_GREEN = new Color(77, 255, 169);
	private JList <String> roadlist;
	private JList <Weather> weather;
	private JList<Integer> ticks;
	private List<String> _carreteras;
	private Controller c;
	
	public ChangeWeatherDialog(Controller c, List<Road> roads) {
		super("Change Road Weather","Schedule an event to change the weather of a road after a given number of simulation ticks from now");
		this.c= c;
		this._carreteras= new ArrayList<String>();
		for(Road ro :roads) {
			_carreteras.add(ro.getId());
		}
		initGUI();
	}

	/*private void initGUI() {
		JPanel mainPanel = new JPanel();
	    mainPanel.setLayout(new GridLayout(3,1));
	    
	    //primera fila
	    JPanel infor = new JPanel(new BorderLayout());
		JTextArea info = new JTextArea("Schedule an event to change the weather of a road after a given number of simulation ticks from now");
		info.setPreferredSize(new Dimension(400, 30));
		info.setBackground(_lIGHT_GREEN);
		info.setEditable(false);
		info.setLineWrap(true);
		info.setWrapStyleWord(true);
		infor.add(info,BorderLayout.CENTER);
		mainPanel.add(infor);
		
		

		//segunda fila
		
		//tercera fila
		JPanel butonpanel= new JPanel(new FlowLayout());
		butonpanel.setBackground(_DARK_GREEN);
		JButton cancelCO2= new JButton("Cancel");
		cancelCO2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});

		JButton okCO2 = new JButton("Ok");
		okCO2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		butonpanel.add(cancelCO2);
		butonpanel.add(okCO2);
		mainPanel.add(butonpanel);
 		
	}
	protected Color getColor(int a) {
		switch(a) {
		case 0: return _lIGHT_GREEN;
		case 1: return _MEDIUM_GREEN;
		case 2: return _DARK_GREEN;
		default: return null;
		}
	}*/

	@Override
	public void actionPerformed(ActionEvent e) {
		if(!roadlist.isSelectionEmpty()&&!weather.isSelectionEmpty()&&!ticks.isSelectionEmpty()) {
			List<Pair<String, Weather>> cs = new ArrayList <>();
			int[] indices = roadlist.getSelectedIndices();
			for(int i=0; i<indices.length;i++) {
			cs.add(new Pair<String, Weather>(this._carreteras.get(indices[i]),(Weather) weather.getSelectedValue()));
			}
			SetWeatherEvent contEvent = new SetWeatherEvent(c.getTime()+(int)ticks.getSelectedValue(),cs);
	 		c.addEvent(contEvent);
	 		setVisible(false);
		}else {
			JOptionPane.showMessageDialog(null, "Arguments not selected " , 
					"Weather Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	protected Component segundaFila() {
		JPanel panelSelecion = new JPanel(new FlowLayout());
		panelSelecion.setBackground(getColor(2));
		JLabel road= new JLabel("Road:"); 
		roadlist = new JList<String> (( this._carreteras).toArray(new String[0]));
		roadlist.setVisibleRowCount(1);
		roadlist.setFixedCellHeight(20);
		roadlist.setFixedCellWidth(60);
		roadlist.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		JLabel weath= new JLabel("Weather:");
		Weather[] tiempo = {Weather.CLOUDY,Weather.RAINY,Weather.STORM,Weather.SUNNY,Weather.SUNNY,Weather.WINDY};
		weather = new JList <>(tiempo);
		weather.setVisibleRowCount(1);
		weather.setFixedCellHeight(20);
		weather.setFixedCellWidth(60);
		weather.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JLabel tiks= new JLabel("Ticks:");
		Integer[] numeros = {1,2,3,4,5,6,7,8,9,10};
		ticks = new JList <>(numeros);
		ticks.setVisibleRowCount(1);
		ticks.setFixedCellHeight(20);
		ticks.setFixedCellWidth(60);
		ticks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		panelSelecion.add(road);
		panelSelecion.add(new JScrollPane(roadlist));
		panelSelecion.add(weath);
		panelSelecion.add(new JScrollPane(weather));
		panelSelecion.add(tiks);
		panelSelecion.add(new JScrollPane(ticks));
		
		return panelSelecion;
	}

	@Override
	protected Color getColor(int a) {
		switch(a) {
		case 1: return ChangeWeatherDialog._lIGHT_GREEN;
		case 2: return ChangeWeatherDialog._MEDIUM_GREEN;
		case 3: return ChangeWeatherDialog._DARK_GREEN;
		default: return null;
		}
	}
	
}
