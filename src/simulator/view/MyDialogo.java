package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public abstract class MyDialogo extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String definicion;
	
	public MyDialogo(String title, String defin) {
		this.definicion=defin;
		setTitle(title);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setLocation(300, 200);
		this.setPreferredSize(new Dimension (450,150));
	}

	protected void initGUI() {
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
		okCO2.addActionListener(this );
		butonpanel.add(cancelCO2);
		butonpanel.add(okCO2);
		return butonpanel;
	}

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

	protected abstract Color getColor(int a) ;

	protected abstract Component segundaFila();

}
