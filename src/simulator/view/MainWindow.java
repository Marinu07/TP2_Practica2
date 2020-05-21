package simulator.view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;

import simulator.control.Controller;

public class MainWindow extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Color _BLUE = new Color(0, 153, 153);
	private Controller _ctrl;
	private ControlPanel conpanel;
	
	public MainWindow(Controller ctrl) {
		super("Traffic Simulator"); //la ventana tendra este nombre
		_ctrl = ctrl;
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
	    conpanel = new ControlPanel(_ctrl);
		this.setJMenuBar(getMenu());
		mainPanel.add(conpanel, BorderLayout.PAGE_START);
		mainPanel.add(new StatusBar(_ctrl), BorderLayout.PAGE_END);
		
		JPanel viewsPanel = new JPanel(new GridLayout(1,2));
		mainPanel.add(viewsPanel, BorderLayout.CENTER);
		
		JPanel tablesPanel = new JPanel();
		tablesPanel.setLayout(new BoxLayout(tablesPanel, BoxLayout.Y_AXIS));
		viewsPanel.add(tablesPanel);
		
		JPanel mapsPanel =new JPanel();
		mapsPanel.setLayout(new BoxLayout(mapsPanel,BoxLayout.Y_AXIS));
		viewsPanel.add(mapsPanel);
		
		//tables
		JPanel eventsView= createViewPanel(new JTable(new EventsTableModel(_ctrl)),"Events");
		eventsView.setPreferredSize(new Dimension(500,200));
		eventsView.setToolTipText("Events from event list and time");
		tablesPanel.add(eventsView);
		
		JPanel vehiclesView= createViewPanel(new JTable(new VehiclesTableModel(_ctrl)),"Vehicles");
		vehiclesView.setPreferredSize(new Dimension(500,200));
		vehiclesView.setToolTipText("All vehicles and their info");
		tablesPanel.add(vehiclesView);
		
		JPanel RoadsView= createViewPanel(new JTable(new RoadsTableModel(_ctrl)),"Roads");
		RoadsView.setPreferredSize(new Dimension(500,200));
		RoadsView.setToolTipText("All roads and their info");
		tablesPanel.add(RoadsView);
		
		JPanel JunctionsView= createViewPanel(new JTable(new JunctionsTableModel(_ctrl)),"Junctions");
		JunctionsView.setPreferredSize(new Dimension(500,200));
		JunctionsView.setToolTipText("All junctions and their info");
		tablesPanel.add(JunctionsView);
		
		//maps
		JPanel mapView = createViewPanel(new MapComponent(_ctrl), "Map");
		mapView.setPreferredSize(new Dimension(500, 400));
		mapView.setToolTipText("Mapa general");
		mapsPanel.add(mapView);
		
		JPanel mapByRoadView = createViewPanel(new MapByRoadComponent(_ctrl), "Map by Road");
		mapByRoadView.setPreferredSize(new Dimension(500, 400));
		mapByRoadView.setToolTipText("Mapa por carretera");
		mapsPanel.add(mapByRoadView);
		
		//no tiene porque conque solo se aga new una vez vale
		//JFileChooser fc = new JFileChooser();
		
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}
	
	private JMenuBar getMenu() {
		JMenuBar menuBar1 = new JMenuBar();

		JMenuItem open =new JMenuItem("open");
		open.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				conpanel.loadEventsFileDialog();
				
			}
			
		});
		open.setForeground(_BLUE);
		
		JMenuItem co2 =new JMenuItem("co2");
		co2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				conpanel.co2();
			}
			
		});
		co2.setForeground(_BLUE);
		
		JMenuItem weather =new JMenuItem("weather");
		weather.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				conpanel.weth();
				
			}
			
		});
		weather.setForeground(_BLUE);
		
		JMenuItem restart =new JMenuItem("restart");
		restart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					_ctrl.restart();
				}catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Could not restart: " + e1.getMessage(), 
							"restart Error", JOptionPane.ERROR_MESSAGE);
				}
				
			}
			
		});
		restart.setForeground(_BLUE);
		
		JMenuItem run =new JMenuItem("run");
		run.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				conpanel.run();
				
			}
			
		});
		run.setForeground(_BLUE);
		
		JMenuItem stop =new JMenuItem("stop");
		stop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				conpanel.run();
				
			}
			
		});
		stop.setForeground(_BLUE);
		
		JMenuItem exit =new JMenuItem("exit");
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int n = JOptionPane.showOptionDialog(null, "Are you sure you want to exit the program?", "Exit Window",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, 
						new String[] {"Yes", "No"}, "No");
				if (n == JOptionPane.YES_OPTION) System.exit(0);
				
				
			}
			
		});
		exit.setForeground(_BLUE);
		
		
		
		
		
		
		
		menuBar1.add(open);
		menuBar1.add(co2);
		menuBar1.add(weather);
		menuBar1.add(restart);
		menuBar1.add(run);
		menuBar1.add(stop);
		menuBar1.add(exit);
		return menuBar1;
		
	}

	private JPanel createViewPanel(JComponent c, String title) {
		JPanel p= new JPanel( new BorderLayout() );
		Border b = BorderFactory.createLineBorder(Color.black,1);
		//si quisieramos bordearlo de una imagen
		//p.setBorder(BorderFactory.createMatteBorder(-1, -1, -1, -1,new ImageIcon("resources/icons/wavy.png")));
		p.setBorder(BorderFactory.createTitledBorder(b, title));
		p.add(new JScrollPane(c));//para poder hacer scroll
		return p;
	}
	
}
