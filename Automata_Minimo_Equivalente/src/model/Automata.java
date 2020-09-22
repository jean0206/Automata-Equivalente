package model;

import java.util.ArrayList;
import java.util.HashSet;

public interface Automata {
	public String[][] getInitialTable();
	public boolean setInitialData(String[] initialData);
	public String[][] calculate();
	public boolean addRow(String[] add);
	public HashSet<String> stepOne();
	public ArrayList<ArrayList<String>> stepTwo(HashSet<String> q);
	public String[][] stepThree(ArrayList<ArrayList<String>> stepTwo);
	public void restart();
}
