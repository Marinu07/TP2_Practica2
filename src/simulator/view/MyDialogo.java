package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.Event;


public abstract class MyDialogo<W> extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Color _lIGHT_BLUE = new Color(236, 242, 248);
	private static final Color _MEDIUM_BLUE = new Color(217, 229, 242);
	private static final Color _DARK_BLUE = new Color(179, 204, 230);
	private JList <String> jList1;
	private JList <W> jList2;
	private JList<Integer> ticks;
	private List<String>lista1;
	private W[] lista2;
	private String nl1;
	private String nl2;
	private String definicion;
	private Controller c;
	
	
	public MyDialogo(String title, String defin,Controller c, String nl1, String nl2) {
		this.c=c;
		this.definicion=defin;
		this.nl1=nl1;
		this.nl2=nl2;
		setTitle(title);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setLocation(300, 200);
		this.setMaximumSize(new Dimension (450,150));
		this.setMinimumSize(new Dimension (450,150));
		this.setResizable(false);
	}

	protected void initGUI(List<String>list1, W[] list2) {
		this.lista1=list1;
		this.lista2=list2;
		JPanel mainPanel = new JPanel();
	    mainPanel.setLayout(new GridLayout(3,1));
	    mainPanel.add(primeraFila());
	    mainPanel.add(segundaFila());
		mainPanel.add(terceraFila());
		this.add(mainPanel);
 		this.setVisible(true); 
	    this.pack();
	}


	private Component terceraFila() {
		JPanel butonpanel= new JPanel(new FlowLayout());
		butonpanel.setBackground(getColor(3));
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
				if(!jList1.isSelectionEmpty()&&!jList2.isSelectionEmpty()&&!ticks.isSelectionEmpty()) {
					List<Pair<String, W>> cs = new ArrayList <>();
					int[] indices = jList1.getSelectedIndices();
					for(int i=0; i<indices.length;i++) {
						cs.add(new Pair<String, W>( lista1.get(indices[i]),(W)jList2.getSelectedValue()));
					}
					Event event= createEvent(ticks.getSelectedValue(),cs);
			 		c.addEvent(event);
			 		setVisible(false);
				}else {
					JOptionPane.showMessageDialog(null, "Arguments not selected " , 
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		butonpanel.add(cancelCO2);
		butonpanel.add(okCO2);
		return butonpanel;
	}

	protected abstract Event createEvent(int i, List<Pair<String, W>> cs);

	private Component primeraFila() {
		 JPanel infor = new JPanel(new BorderLayout());
			JTextArea info = new JTextArea(this.definicion);
			info.setPreferredSize(new Dimension(400, 30));
			info.setBackground(getColor(1));
			info.setEditable(false);
			info.setLineWrap(true);
			info.setWrapStyleWord(true);
			infor.add(info,BorderLayout.CENTER);
		return infor;
	}

	private Color getColor(int a) {
		switch(a) {
		case 1: return MyDialogo._lIGHT_BLUE;
		case 2: return MyDialogo._MEDIUM_BLUE;
		case 3: return MyDialogo._DARK_BLUE;
		default: return null;
		}
	}

	private Component segundaFila() {
		JPanel panelSelecion = new JPanel(new FlowLayout());
		panelSelecion.setBackground(getColor(2));
		JLabel vehic= new JLabel(this.nl1); 
		jList1 = new JList<String> ( (String[]) this.lista1.toArray(new String[0]));
		jList1.setVisibleRowCount(1);
		jList1.setFixedCellHeight(20);
		jList1.setFixedCellWidth(60);
		jList1.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		
		JLabel co2= new JLabel(this.nl2);
		Integer[] numeros = {1,2,3,4,5,6,7,8,9,10};
		jList2 = new JList <W>(this.lista2);
		jList2.setVisibleRowCount(1);
		jList2.setFixedCellHeight(20);
		jList2.setFixedCellWidth(60);
		jList2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JLabel tiks= new JLabel("Ticks:");
		ticks = new JList <Integer>(numeros);
		ticks.setSelectedIndex(1);
		ticks.setVisibleRowCount(1);
		ticks.setFixedCellHeight(20);
		ticks.setFixedCellWidth(60);
		ticks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		panelSelecion.add(vehic);
		panelSelecion.add(new JScrollPane(jList1));
		panelSelecion.add(co2);
		panelSelecion.add(new JScrollPane(jList2));
		panelSelecion.add(tiks);
		panelSelecion.add(new JScrollPane(ticks));
		return panelSelecion;
	}

}
