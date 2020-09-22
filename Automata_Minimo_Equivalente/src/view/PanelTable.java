package view;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

public class PanelTable extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private AutomataGUI main;
	private JPanel stateTable;
	private JTextField[][] matrix;

	public PanelTable(AutomataGUI main) {
		setLayout(new BorderLayout());
		
		this.main =  main;
		
		JPanel panelTable = new JPanel();
		
		panelTable.setLayout(new BorderLayout());
		stateTable = new JPanel();
		panelTable.add(stateTable,BorderLayout.CENTER);
		
		panelTable.revalidate();
		panelTable.repaint();
		
		add(panelTable,BorderLayout.CENTER);
	}
	
	
	public void createTable(String[][] data) {
		int rows = data.length;
		int columns = data[0].length;
		matrix = new JTextField[rows][columns];
		
		initializeMatrix(data, rows, columns);
		
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
					matrix[i][j].setEditable(false);
				}else if (i>0 && j==0) {
					matrix[i][j].setText(data[i][j]);
					tmp.setBackground(Color.BLACK);
					tmp.setForeground(Color.WHITE);
					matrix[i][j].setEditable(false);
				} else {
					tmp.setBackground(new Color(218, 222, 230));
					matrix[i][j].setText(data[i][j]);
					matrix[i][j].setEditable(true);
				}
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
		this.revalidate();
		this.repaint();
		cleanMatriz();
	}
	
	public void cleanMatriz() {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				if (i==0 && j>0) {
				}
				else if (i>0 && j==0) {
				}
				else {
					matrix[i][j].setText(" ");
					matrix[i][j].setEditable(true);
				}
			}
		}
	}
	
	
	public String[][] dataMatrix() throws Exception{
		
		int rows = matrix.length;
		int cols = matrix[0].length;
		String[][] dt = new String[rows][cols];
		
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				if(i!=0) {
					if(matrix[i][j].getText().isEmpty() || matrix[i][j].getText()==" ") {
						JOptionPane.showMessageDialog(null, "No deben haber campos vacios");
						new Exception("No pueden haber campos vacios");
					}
					dt[i-1][j]=matrix[i][j].getText();
				}
			}
		}
		
		return dt;
	}

}
