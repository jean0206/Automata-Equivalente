package controller;

import java.util.ArrayList;

import model.Automata;
import model.AutomataImp;

public class Controller {
	
	private Automata automata;
	
	public Controller() {
	}
	
	public void createAutomata(boolean type) {
		this.automata = new AutomataImp(type);
	}
	
	public String[][] createTable() {
		return automata.getInitialTable();
	}
	
	public boolean InitialData(String[] data) {
		return automata.setInitialData(data);
	}
	
	public void restart() {
		automata.restart();
	}
	
	public String[][] calculate() {
		return automata.calculate();
	}
	
	
	public void setData(String[][] dt) {
		
		int rows = dt.length;
		int cols = dt[0].length;
		
		String b = "";
		
		for (int i = 0; i < rows; i++) {
			String r ="";
			for (int j = 0; j < cols; j++) {
				if(dt[i][j]!=null) {
					 r += dt[i][j] + " ";
					
				}
			}
			
			b +=r + "\n";
		}
		aux(b.split("\n"));
		
	}
	
	
	public void aux(String[] b) {
		automata.addRow(b);
	}

}
