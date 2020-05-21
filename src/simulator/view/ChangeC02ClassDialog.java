package simulator.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.NewSetContClassEvent;

public class ChangeC02ClassDialog extends MyDialogo{
	
	private static final long serialVersionUID = 1L;
	private static final Color _lIGHT_BLUE = new Color(236, 242, 248);
	private static final Color _MEDIUM_BLUE = new Color(217, 229, 242);
	private static final Color _DARK_BLUE = new Color(179, 204, 230);
	private JList<String> vegicbox;
	private JList <Integer> ceodos;
	private JList<Integer> ticks;
	private Controller c;
	
	public ChangeC02ClassDialog(Controller c) {
		super("Change CO2 Class","Schedule an event to change the CO2 class of a vehicle after a given number of simulation thicks from now.");
		this.c= c;
		initGUI();
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		List<Pair<String, Integer>> cs = new ArrayList <>();
		int[] indices = vegicbox.getSelectedIndices();
		for(int i=0; i<indices.length;i++) {
			cs.add(new Pair<String, Integer>( this.c.getVehicles().get(indices[i]).toString(),(int)ceodos.getSelectedIndex()));
		}
		NewSetContClassEvent contEvent = new NewSetContClassEvent(c.getTime()+(int)(ticks.getSelectedValue()),cs);
 		c.addEvent(contEvent);
 		setVisible(false);
		
	}

	@Override
	protected Color getColor(int a) {
		switch(a) {
		case 1: return ChangeC02ClassDialog._lIGHT_BLUE;
		case 2: return ChangeC02ClassDialog._MEDIUM_BLUE;
		case 3: return ChangeC02ClassDialog._DARK_BLUE;
		default: return null;
		}
	}

	@Override
	protected Component segundaFila() {
		JPanel panelSelecion = new JPanel(new FlowLayout());
		panelSelecion.setBackground(getColor(2));
		JLabel vehic= new JLabel("vehicle:"); 
		vegicbox = new JList<String> ( this.c.getVehicles().toArray(new String[0]));
		vegicbox.setVisibleRowCount(1);
		vegicbox.setFixedCellHeight(20);
		vegicbox.setFixedCellWidth(60);
		vegicbox.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		
		JLabel co2= new JLabel("CO2 Class:");
		Integer[] numeros = {1,2,3,4,5,6,7,8,9,10};
		ceodos = new JList <Integer>(numeros);
		ceodos.setVisibleRowCount(1);
		ceodos.setFixedCellHeight(20);
		ceodos.setFixedCellWidth(60);
		ceodos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JLabel tiks= new JLabel("Ticks:");
		ticks = new JList <Integer>(numeros);
		//ticks.setSelectedIndex(1);
		ticks.setVisibleRowCount(1);
		ticks.setFixedCellHeight(20);
		ticks.setFixedCellWidth(60);
		ticks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		panelSelecion.add(vehic);
		panelSelecion.add(new JScrollPane(vegicbox));
		panelSelecion.add(co2);
		panelSelecion.add(new JScrollPane(ceodos));
		panelSelecion.add(tiks);
		panelSelecion.add(new JScrollPane(ticks));
		return panelSelecion;
	}
	
}

