//Leidy Vanesa Vidales Gonzalez
//María Núñez Conde
package simulator.launcher;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import Exceptions.IncorrectArgumentException;
import simulator.control.Controller;
import simulator.factories.Builder;
import simulator.factories.BuilderBasedFactory;
import simulator.factories.Factory;
import simulator.factories.MostCrowdedStrategyBuilder;
import simulator.factories.MoveAllStrategyBuilder;
import simulator.factories.MoveFirstStrategyBuilder;
import simulator.factories.NewCityRoadEventBuilder;
import simulator.factories.NewInterCityRoadEventBuilder;
import simulator.factories.NewJunctionEventBuilder;
import simulator.factories.NewVehicleEventBuilder;
import simulator.factories.RoundRobinStrategyBuilder;
import simulator.factories.SetContClassEventBuilder;
import simulator.factories.SetWeatherEventBuilder;
import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.TrafficSimulator;
import simulator.view.MainWindow;

public class Main {

	private final static Integer _timeLimitDefaultValue = 10;
	private static String _inFile = null;
	private static String _outFile = null;
	private static Factory<Event> _eventsFactory = null;
	private static int _time= _timeLimitDefaultValue;
	private static Boolean _consoleMode = null;

	private static void parseArgs(String[] args) {
		
	
		// define the valid command line options
		//
		Options cmdLineOptions = buildOptions();

		// parse the command line as provided in args
		//
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine line = parser.parse(cmdLineOptions, args);
			parseHelpOption(line, cmdLineOptions);
			parseInFileOption(line);
			parseOutFileOption(line);
			parseTicksOption(line);
			parseModeOption(line);

			// if there are some remaining arguments, then something wrong is
			// provided in the command line!
			//
			String[] remaining = line.getArgs();
			if (remaining.length > 0) {
				String error = "Illegal arguments:";
				for (String o : remaining)
					error += (" " + o);
				throw new ParseException(error);
			}

		} catch (ParseException e) {
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}

	}

	private static Options buildOptions() {
		Options cmdLineOptions = new Options();

		cmdLineOptions.addOption(Option.builder("i").longOpt("input").hasArg().desc("Events input file").build());
		cmdLineOptions.addOption(Option.builder("o").longOpt("output").hasArg().desc("Output file, where reports are written.").build());
		cmdLineOptions.addOption(Option.builder("h").longOpt("help").desc("Print this message").build());
		cmdLineOptions.addOption(Option.builder("t").longOpt("tick").hasArg().desc("Ticks to the simulator's main loop (default value is 10)").build());
		cmdLineOptions.addOption(Option.builder("m").longOpt("mode").hasArg().desc("Modes gui (default) or console").build());
		
		return cmdLineOptions;

	}

	
	private static void parseModeOption(CommandLine line) throws ParseException {
		String m = line.getOptionValue("m");
		if (m == null) {
			_consoleMode = false;
		} 
		else {
			if (!m.equalsIgnoreCase("console") && !m.equalsIgnoreCase("gui")) 
				throw new ParseException("Mode: console or gui");
			_consoleMode = m.equalsIgnoreCase("console");			
		}
	}

	private static void parseHelpOption(CommandLine line, Options cmdLineOptions) {
		if (line.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Main.class.getCanonicalName(), cmdLineOptions, true);
			System.exit(0);
		}
	}

	private static void parseInFileOption(CommandLine line) throws ParseException {
		_inFile = line.getOptionValue("i");
	}

	private static void parseOutFileOption(CommandLine line) throws ParseException {
		_outFile = line.getOptionValue("o");
	}
	
	private static void parseTicksOption(CommandLine line) throws ParseException{
		String st= line.getOptionValue("t");
		if(st!=null) {
			_time= Integer.parseInt(st);
		}
	}
	
	private static void initFactories() {
		
		// this method  initializes _eventsFactory
		
		List<Builder<LightSwitchingStrategy>> lsbs = new ArrayList<>();
		lsbs.add(new RoundRobinStrategyBuilder());
		lsbs.add(new MostCrowdedStrategyBuilder());
		lsbs.add(new RoundaboutStrategyBuilder());

		Factory<LightSwitchingStrategy> lssFactory= new BuilderBasedFactory<>(lsbs);
		
		List<Builder<DequeuingStrategy>> dqbs = new ArrayList<>();
		dqbs.add(new MoveFirstStrategyBuilder());
		dqbs.add(new MoveAllStrategyBuilder());
		Factory<DequeuingStrategy> dqsFactory= new BuilderBasedFactory<>(dqbs);
		
		List<Builder<Event>> ebs = new ArrayList<>();
		ebs.add( new NewJunctionEventBuilder(lssFactory,dqsFactory) );
		ebs.add( new NewCityRoadEventBuilder() );
		ebs.add( new NewInterCityRoadEventBuilder() );
		ebs.add( new NewVehicleEventBuilder() );
		ebs.add( new SetWeatherEventBuilder());
		ebs.add( new SetContClassEventBuilder());
		
		//Factory<Event> eventsFactory = new BuilderBasedFactory<>(ebs);
		_eventsFactory = new BuilderBasedFactory<>(ebs);
	

	}

	private static void startConsoleMode() throws IOException, IncorrectArgumentException {
		try {
		Controller con =null;
			if(_outFile!=null) {
				con= new Controller(new TrafficSimulator(),_eventsFactory,new FileOutputStream(_outFile));
			}
			
			else {
				con = new Controller(new TrafficSimulator(), _eventsFactory, System.out);
			}if(_inFile!=null) {
			con.loadEvents(_inFile);}
			con.run(_time);
			
		} catch (IncorrectArgumentException|IOException e) {
			throw e;
		} 
	}

	private static void start(String[] args) throws IOException, IncorrectArgumentException, InvocationTargetException, InterruptedException {
		initFactories();
		parseArgs(args);
		try {
			if (_consoleMode) startConsoleMode();
			else startGUImode();

		} catch (IncorrectArgumentException|IOException e) {
			throw e;
		}
	}

	// example command lines:
	//
	// -i resources/examples/ex1.json
	// -i resources/examples/ex1.json -t 300
	// -i resources/examples/ex1.json -o resources/tmp/ex1.out.json
	// --help
	
	
	public static void startGUImode() throws InvocationTargetException, InterruptedException, IncorrectArgumentException, IOException {	
		Controller con = new Controller(new TrafficSimulator(),_eventsFactory, System.out);

		
		if (_inFile != null) {
			//InputStream in = new FileInputStream(_inFile);
			con.loadEvents(_inFile);
		}
		
		SwingUtilities.invokeAndWait(new Runnable() {
			@Override
			public void run() {
				(new MainWindow(con)).setVisible(true);
			}
		});
	

	}


	public static void main(String[] args) {
		try {
			start(args);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
