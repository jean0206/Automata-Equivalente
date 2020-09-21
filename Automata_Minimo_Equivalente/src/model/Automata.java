package model;

import java.util.ArrayList;
import java.util.HashSet;

public interface Automata {
	
	/**
	 * Method to get a string matrix that represents the values of each cell in the table to show
	 * @return the String matrix with their cell values
	 */
	public String[][] getInitialTable();
	
	/**
	 * Method to calculate the conex and minimum equivalent automata introduce before
	 * @return a string matrix with the answer
	 */
	public String[][] calculate();
	
	/**
	 * Method to setup the values of Q,S,R and q1 of the machine
	 * @param initialData with the values to store, each row represents Q,S,R,q1 respectively
	 * @return a boolean if everything goes fine
	 */
	public boolean setInitialData(String[] initialData);
	
	/**
	 * Method to add the information of a row or rows, the state with their transition (Where it moves with some entry)
	 * @param add with the cell information
	 * @return a boolean if everything goes fine
	 */
	public boolean addRow(String[] add);
	
	/**
	 * Method public for call stepOne, get the accessible nodes from initial state
	 * @return hashSet wich has all the states accessible from initial state
	 */
	public HashSet<String> stepOne();
	
	/**
	 * Method for step Two, get out the final partition 
	 * @param cEq with all the states it must to work
	 * @return a list of list that has the blocks of final partition
	 */
	public ArrayList<ArrayList<String>> stepTwo(HashSet<String> stq);
	
	/**
	 * Method to do final step, step three to get the minimum equivalent
	 * @param stepTwo with the final partition produced by step Two
	 * @return a string matrix with their cells meaning the information of calculus computed
	 */
	public String[][] stepThree(ArrayList<ArrayList<String>> stepTwo);
	
	/**
	 * Method to know type of automata working on
	 * @return automataType - True for Mealy and False for Moore
	 */
	public boolean getAutomataType();
}
