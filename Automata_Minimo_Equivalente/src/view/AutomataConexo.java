package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

/**
 * 
 * @author Jhusseth
 *
 */
public class AutomataConexo extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private PanelOptions options;
	private AutomataGUI main;
	private JPanel stateTable;
	private JTextField[][] matrix;
	
	public AutomataConexo() {
		setLayout(new BorderLayout());
		
		JPanel panelTable = new JPanel();
		
		panelTable.setLayout(new BorderLayout());
		stateTable = new JPanel();
		panelTable.add(stateTable,BorderLayout.CENTER);
		
		panelTable.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), "Automata Minimo Equivalente", TitledBorder.CENTER, TitledBorder.TOP, null, Color.BLACK));
		
		
		panelTable.revalidate();
		panelTable.repaint();
		
		add(panelTable,BorderLayout.CENTER);
	}
	
	public void createTable(String[][] conexo) {
		int rows = conexo.length;
		int columns = conexo[0].length;
		matrix = new JTextField[rows][columns];
		
		initializeMatrix(conexo, rows, columns);
		
		showTable(rows,columns);
	}
	
	private void initializeMatrix(String[][] data,int rows, int columns) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				JTextField tmp = matrix[i][j];
				if (tmp==null)
					tmp = new JTextField();
				tmp.setHorizontalAlignment(JTextField.CENTER);
				matrix[i][j] = tmp;
				if (i==0 && j>0) {
					matrix[i][j].setText(data[i][j]);
					tmp.setBackground(Color.WHITE);
					tmp.setForeground(Color.BLACK);
				}else if (i>0 && j==0) {
					matrix[i][j].setText(data[i][j]);
					tmp.setBackground(Color.BLACK);
					tmp.setForeground(Color.WHITE);
				} else {
					tmp.setBackground(new Color(218, 222, 230));
					matrix[i][j].setText(data[i][j]);
				}
				matrix[i][j].setEditable(false);
			}
		}
		matrix[0][0].setEditable(false);
	}
	
	private void showTable(int rows, int columns) {
		stateTable.setLayout(new GridLayout(rows,columns));
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				stateTable.add(matrix[i][j]);
			}
		}
		stateTable.revalidate();
		stateTable.repaint();
		this.revalidate();
		this.repaint();
	}
	
	public void clean() {
		stateTable.removeAll();
		this.revalidate();
		this.repaint();
	}

}
