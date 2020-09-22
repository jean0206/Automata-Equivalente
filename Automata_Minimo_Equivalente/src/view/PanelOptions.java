package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JButton;

public class PanelOptions extends JPanel implements ActionListener{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * buttons attributes
	 */
	private JButton insertTable;
	private JButton start;
	private JButton clean;
	private JButton reboot;
	private JButton about;
	
	/**
	 * constants for buttons
	 */
	public final static String INSERT ="insert";
	public final static String START ="start";
	public final static String CLEAN ="clean";
	public final static String REBOOT ="reboot";
	public final static String ABOUT ="about";
	
	/**
	 * interfaz main AutomataGui
	 */
	private AutomataGUI main;

	public PanelOptions(AutomataGUI main) {
		setLayout(new BorderLayout());
		this.main = main;
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 5));
		
		insertTable = new JButton("Insert");
		insertTable.addActionListener(this);
		insertTable.setActionCommand(INSERT);
		start = new JButton("Start");
		start.addActionListener(this);
		start.setActionCommand(START);
		clean = new JButton("Clean");
		clean.addActionListener(this);
		clean.setActionCommand(CLEAN);
		reboot = new JButton("Reboot");
		reboot.addActionListener(this);
		reboot.setActionCommand(REBOOT);
		about = new JButton("About");
		about.addActionListener(this);
		about.setActionCommand(ABOUT);
		
		panel.add(insertTable);
		panel.add(start);
		panel.add(clean);
		panel.add(reboot);
		panel.add(about);
		
		buttonsDisable();
		add(panel, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String event = e.getActionCommand();
		
		if(event.equals(INSERT)) {
			main.insertTable();
			buttonsEnable();
		}
		if(event.equals(START)){
			main.conexo();
		}
		if(event.equals(CLEAN)) {
			main.clean();
		}
		if(event.equals(REBOOT)) {
			main.reboot();
			buttonsDisable();
		}
		if(event.equals(ABOUT)) {
			
		}
	}
	
	public void buttonsDisable() {
		start.setEnabled(false);
		reboot.setEnabled(false);
		clean.setEnabled(false);
		insertTable.setEnabled(true);
	}
	
	public void buttonsEnable() {
		insertTable.setEnabled(false);
		start.setEnabled(true);
		clean.setEnabled(true);
		reboot.setEnabled(true);
	}
}
