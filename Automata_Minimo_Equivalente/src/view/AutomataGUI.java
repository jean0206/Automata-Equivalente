package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.Controller;

/**
 * 
 * @author Jhusseth
 *
 */
public class AutomataGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private PanelTable table;
	private PanelOptions options;
	private AutomataConexo automata;
	private Controller controller;
	private PanelBanner banner;
	
	
	public AutomataGUI() {
		
		setTitle("Automata Equivalente");
		setSize(432,370);
		setLayout(new BorderLayout());
		
		banner =  new PanelBanner();
		
		table = new PanelTable(this);
		automata = new AutomataConexo();
		controller = new Controller();
		options = new PanelOptions(this);
		JPanel panelOptions = new JPanel();
		panelOptions.setLayout(new BorderLayout());
		
		JPanel tables = new JPanel();
		tables.setLayout(new GridLayout(1,2,3,3));
		
		tables.add(table);
		tables.add(automata);
		panelOptions.add(options,BorderLayout.SOUTH);
		
		setLocationRelativeTo(null);
		
		add(banner,BorderLayout.NORTH);
		add(tables,BorderLayout.CENTER);
		add(panelOptions,BorderLayout.SOUTH);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		
		AutomataGUI loader  = new AutomataGUI();
		loader.setVisible(true);
		
	}
	
	
	public void insertTable() {
		int type = askType();
		
		if(type==-1) {
			insertTable();
		}
		
		else if(type==0) {
			controller.createAutomata(true);
		}
		else if(type==1) {
			controller.createAutomata(false);
		}
		
		boolean access = false;
		while (!access) {
			String[] initialData = null;
			try {			
				initialData = askInputs();
			} catch (NullPointerException ex) {
				return;
			}
			access = controller.InitialData(initialData);
			if (!access)
				JOptionPane.showMessageDialog(null, "Se detecto un error con las entradas","Vuelva a intentarlo", JOptionPane.ERROR_MESSAGE);
		}
		
		table.createTable(controller.createTable());
	}
	
	public int askType() {
		String[] opt = {"Mealy","Moore"};
		int x = JOptionPane.showOptionDialog(null, "Seleccione el tipo de aut�mata a insertar","Elija", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opt, null);
		return x;
	}
	
	public String[] askInputs() throws NullPointerException {
		String Q = JOptionPane.showInputDialog(this, "Ingrese los estados Q separados por espacios: ", "Ejemplo: A B C D", JOptionPane.QUESTION_MESSAGE).toString().trim();
		String S = JOptionPane.showInputDialog(this, "Ingrese el alfabeto finito de entrada S: ", "Ejemplo: a b c", JOptionPane.QUESTION_MESSAGE).toString().trim();
		String R = JOptionPane.showInputDialog(this, "Ingrese el alfabeto finito de salida R: ", "Ejemplo: 0 1 2", JOptionPane.QUESTION_MESSAGE).toString().trim();
		String q0 = JOptionPane.showInputDialog(this, "Ingrese el estado inicial q0 (tiene que estar definido en Q): ", "Ejemplo: A", JOptionPane.QUESTION_MESSAGE).toString().trim();
		return new String[] {Q,S,R,q0};
	}
	
	
	public void clean() {
		table.clean();
		automata.clean();
	}
	
	
	public void reboot() {
		clean();
		table.removeAll();
		automata.removeAll();
		controller.restart();
		table.createTable(null);
		table.revalidate();
		table.repaint();
		insertTable();
	}
	
	
	public void conexo() {
		try {
			controller.setData(table.dataMatrix());
			automata.createTable(controller.calculate());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
