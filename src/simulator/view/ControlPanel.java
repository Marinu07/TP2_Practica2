package simulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import simulator.control.Controller;


import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;


public class ControlPanel extends JToolBar implements  TrafficSimObserver{

	private static final long serialVersionUID = 1L;
	private Controller c;
	private static final String ICONS_DIR = "resources/icons/";
	private static final int SEPARATOR_PADDING = 0;
	private static final Number DEFAULT_STEPS = 10;
	private JButton _exitButton;
	private JButton _openButton;
	private JButton _runButton;
	private JButton _stopButton;
	private JButton _changeCO2Button;
	private JButton _changeWeatherButton;
	private JButton _resetButton;
	private JSpinner _stepsField;
	private JFileChooser fc;//se pone aqui para que se guarde la ultima posicion
	private Boolean _stopped;
	private static final Color _BLUE = new Color(0, 153, 153);
	private List<Road> _carreteras;//prueba
	private List<Vehicle> _vehiculos;
	private int _time;
	
	public ControlPanel(Controller _ctrl) {
		this.c = _ctrl;
		c.addObserver(this);
		initGUI();
		this.fc = new JFileChooser();
		_stopped=false;
		fc.setCurrentDirectory(new File("resources/examples"));
	}


	private JButton createButton(String imageFileName, String toolTipText) {
		JButton ret = new JButton();
		ret.setIcon(new ImageIcon(ICONS_DIR+imageFileName));
		ret.setToolTipText(toolTipText);
		return ret;
	}
	
	private JPanel createStepsInput() {
		JPanel ret = new JPanel();
		ret.setToolTipText("Number of steps to run");
		ret.setLayout(new BoxLayout(ret, BoxLayout.X_AXIS));
		
		JLabel label = new JLabel(" Ticks: ");
		_stepsField = new JSpinner(new SpinnerNumberModel(DEFAULT_STEPS, 1, null, 10));
		
		int h = _stepsField.getFontMetrics(_stepsField.getFont()).getHeight() + 10;
		int w = _stepsField.getFontMetrics(_stepsField.getFont()).getMaxAdvance()*5;
		
		_stepsField.setPreferredSize(new Dimension(w, h));
		_stepsField.setMaximumSize(new Dimension(w*2, h));
		_stepsField.setMinimumSize(new Dimension(w, h));
		
		ret.add(label);
		ret.add(_stepsField);
		
		return ret;
	}


	private JComponent createSeparator() {
		JSeparator ret = new JSeparator(JSeparator.VERTICAL);
		ret.setMaximumSize(new Dimension(ret.getPreferredSize().width+SEPARATOR_PADDING, ret.getMaximumSize().height));
		
		return ret;
	}


	private void initGUI() {
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
	
		
		add(_openButton = createButton("open.png", "Open file"));
		_openButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				loadEventsFileDialog();
			}
		});
		
		add(createSeparator());
		
		add(_changeCO2Button = createButton("co2class.png","Change contamination"));
		_changeCO2Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				co2();
				
			}
		});
		
		add(_changeWeatherButton= createButton("weather.png","Change weather"));
		_changeWeatherButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				weth();
			}
		});
		
		add(createSeparator());
		
		add(_resetButton= createButton("reset.png", "reset simulation"));
		_resetButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					c.restart();
				}catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Could not restart: " + e1.getMessage(), 
							"restart Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		add(_runButton = createButton("run.png", "Run simulation"));
		_runButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				run();
			}
			
		});
		
		add(_stopButton = createButton("stop.png", "Stop simulation"));
		_stopButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				stop();
			}
		});

		add(createStepsInput());

		add(Box.createHorizontalGlue());//para que se quede a la derecha
		add(createSeparator());
		
		add(_exitButton = createButton("exit.png", "Exits the program"));
		_exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int n = JOptionPane.showOptionDialog(null, "Are you sure you want to exit the program?", "Exit Window",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, 
						new String[] {"Yes", "No"}, "No");
				if (n == JOptionPane.YES_OPTION) System.exit(0);
				
			}
		});
		enableToolBar(true);
	}


	public void loadEventsFileDialog() {

		JDialog dialogEvents = new JDialog();
		dialogEvents.setTitle("Carga del fichero de eventos");

		int returnVal = fc.showOpenDialog(this);//dialogo para abrir ficheros 
		
        if (returnVal == JFileChooser.APPROVE_OPTION) {
        	
        	c.reset();
        	
        	File file = fc.getSelectedFile();
            System.out.println("Opening: " + file.getName());
       
			try {
				c.loadEvents(file.getPath());
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(this, "Could not load file: " + e1.getMessage(), 
						"Loading Error", JOptionPane.ERROR_MESSAGE);
			}

     
        } 
        else {
            System.out.println("load cancelled by user.");
        }
    } 
		
/*
	@Override
	public void actionPerformed(ActionEvent e) {
			
		} else if (e.getActionCommand() == "stop") {
			stop();
			
		} else if (e.getActionCommand() == "exit") {
			int n = JOptionPane.showOptionDialog(this, "Are you sure you want to exit the program?", "Exit Window",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, 
					new String[] {"Yes", "No"}, "No");
			if (n == JOptionPane.YES_OPTION) System.exit(0);
			
		
		} else if (e.getActionCommand() == "open") {
			loadEventsFileDialog();
			
		}else if(e.getActionCommand()=="restart") {
			try {
				c.restart();
			}catch (Exception e1) {
				JOptionPane.showMessageDialog(this, "Could not restart: " + e1.getMessage(), 
						"restart Error", JOptionPane.ERROR_MESSAGE);
			}
		}else{
			throw new UnsupportedOperationException();
			
		}
	}*/



	public void run() {
		try {
			int steps = (Integer)_stepsField.getValue();
			if (steps < 0) throw new IllegalArgumentException("Steps and delay should be greater than 0");
			enableToolBar(false);
			_stopped=false;
			run_sim(steps);
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Couldn't start simulation",
					"Simulation Error", JOptionPane.ERROR_MESSAGE);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Could not start simulation: " + ex.getMessage(), 
					"Simulation error", JOptionPane.ERROR_MESSAGE);
		}
		
	}


	private void enableToolBar(boolean enabled) {
		_openButton.setEnabled(enabled);
		_runButton.setEnabled(enabled);
		_stopButton.setEnabled(!enabled);
		_stepsField.setEnabled(enabled);
		_changeWeatherButton.setEnabled(enabled);
		_changeCO2Button.setEnabled(enabled);
		
	}
	private void run_sim(int n) {
		if(n>0 && !_stopped) {
			try {
				c.run(1);
			}catch(Exception e) {
				_stopped= true;
				return;
			}
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					run_sim(n-1);
				}
			});
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			enableToolBar(true);
			_stopped=true;
		}
	}
	private void stop() {
		_stopped=true;
	}


	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		this._time= time;
	}


	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		this._carreteras= map.getRoads();
		this._vehiculos= map.getVehicle();
	}


	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}



	public void co2() {
		new ChangeC02ClassDialog(c,_vehiculos,_time);
		
	}


	public void weth() {
		new ChangeWeatherDialog(c);
	}
	
	
}
