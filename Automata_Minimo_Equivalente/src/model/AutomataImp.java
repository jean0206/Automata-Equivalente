package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class AutomataImp implements Automata {
	
	
	/**
	 * The states in a machine
	 */
	private HashMap<String,String> states;
	
	/**
	 * Input alphabet of the machine
	 */
	private HashSet<Character> inputAlphabet;
	
	/**
	 * The output alphabet of the machine
	 */
	private HashSet<String> outputAlphabet;
	
	/**
	 * Initial state in the machine
	 */
	private String stateInitial;
	
	/**
	 * Rows in the machine
	 */
	private Integer rows;
	
	/**
	 * Type of machine, True for Mealy and False for Moore.
	 */
	private Boolean type;
	
	/**
	 * The matrix of automata
	 */
	private HashMap<String,String[]> data;
	
	
	/**
	 * Contructor
	 * @param type - machine state True for Mealy and False for Moore
	 */
	public AutomataImp (boolean type) {
		data = new HashMap<String,String[]>();
		states = new HashMap<String,String>();
		inputAlphabet = new HashSet<Character>();
		outputAlphabet = new HashSet<String>(); 
		this.type=type;
	}
	
	
	@Override
	public String[][] getInitialTable() {
		String[][] rst = new String[data.keySet().size()+1][rows+1];
		int i = 0;
		for (Character c : inputAlphabet) {
			rst[0][i+1] = c+"";
			i++;
		}
		i = 0;
		for (String out : data.keySet()) {
			rst[i+1][0] = out;
			i++;
		}
		i = 1;
		for (String key : data.keySet()) {
			if (data.get(key)==null)
				data.put(key, new String[rows]);
			for (int j = 0; j < data.get(key).length; j++) {
				rst[i][j+1] = data.get(key)[j];
			}
			i++;
		}
		return rst;
	}
	
	@Override
	public String[][] calculate() {
		HashSet<String> a = stepOne();
		ArrayList<ArrayList<String>> b = stepTwo(a);
		String[][] c = stepThree(b);
		return c;
	}
	
	@Override
	public boolean setInitialData(String[] initialData) {
		
		if (initialData[0].length()<1 || initialData[1].length()<1 || initialData[2].length()<1 || initialData[3].length()<1)
			return false;
		
		String[] Q = initialData[0].split(" ");
		String[] S = initialData[1].split(" ");
		rows = type ? S.length:S.length+1;
		String[] R = initialData[2].split(" ");
		String q1 = initialData[3].toString();
		boolean entroQ1 = false;
		
		for (String aux: S) {
			if (aux.length()>1)
				return false;
			inputAlphabet.add(aux.charAt(0));
		}
		
		for (String aux : R) {
			if (aux.length()>1)
				return false;
			outputAlphabet.add(aux);
		}
		
		for (String aux: Q) {
			if (!data.containsKey(aux)) {
				if (q1.equals(aux)) {
					this.stateInitial = aux;
					entroQ1 = true;
				}
				data.put(aux,null);
			}
		}
		
		return entroQ1;
		
	}
	
	
	@Override
	public boolean addRow(String[] add) {
		if (add.length<1)
			return false;
		if (type) {			
			for (String s : add) {
				if (s.length()<1)
					return false;
				states.put(s.substring(0,s.length()-1),s.substring(s.length()-1));
			}
		}
		for (String aux: add) {
			String tmp[] = aux.split(" ");
			if (tmp.length!=(rows+1) || !data.containsKey(tmp[0]))
				return false;
			String rowAdd[] = new String[rows];
			for (int i = 1; i < tmp.length; i++)
				rowAdd[i-1] = tmp[i];
			data.put(tmp[0],rowAdd);
		}
		return true;
	}
	
	
	
	/**
	 * A recursive method that get out the accessible states from a given
	 * @param visited that help to store the states visited
	 * @param add with the state to add
	 * @return a hashSet collection that has all the states accessible from add
	 */
	private HashSet<String> aux(HashSet<String> visited,String add) {
		
		if (visited.contains(add))
			return visited;
		visited.add(add);
		for (String v : data.get(add)) {
			v = v.split("\r")[0];
			if (!type && outputAlphabet.contains(v))
				break;
			aux(visited, type ? v.substring(0, v.length()-1):v);
		}
		return visited;
		
	}
	
	
	@Override
	public HashSet<String> stepOne() {
		HashSet<String> t = aux(new HashSet<String>(),stateInitial);
		return t;
	}
	
	@Override
	public ArrayList<ArrayList<String>> stepTwo(HashSet<String> cEq) {
		ArrayList<ArrayList<String>> rst = stepTwoA(cEq);
		rst = stepTwoB(rst);
		return rst;
	}
	
	
	/**
	 * Method for step To One, gets the start partition
	 * @param cEq with all states it has to work
	 * @return a list of list that has the blocks of first partition
	 */
	private ArrayList<ArrayList<String>> stepTwoA(HashSet<String> cEq) {
		ArrayList<ArrayList<String>> rst = new ArrayList<ArrayList<String>>();
		HashMap<String,HashSet<String>> aux = new HashMap<String,HashSet<String>>();
		for (String actual : cEq) {
			String[] b = data.get(actual);
			String key = !type ? data.get(actual)[data.get(actual).length-1]:"";
			key = key.split("\r")[0];
			if (type) {
				for (String a : b) {
					a = a.split("\r")[0];
					key += a.charAt(a.length()-1);
				}
			}
			HashSet<String> tmp = aux.getOrDefault(key, new HashSet<String>());
			if (!tmp.contains(actual))
				tmp.add(actual);
			aux.put(key, tmp);
		}
		for (String s : aux.keySet()) {
			ArrayList<String> tmp = new ArrayList<String>();
			tmp.addAll(aux.get(s));
			rst.add(tmp);
		}
		return rst;
	}
	
	
	/**
	 * Method to find the states achievable from a state
	 * @param tmp the state to know their accessible states
	 * @return a list with their accessible nodes 
	 */
	private ArrayList<String> findStatesAchievable(String tmp) {
		ArrayList<String> rst = new ArrayList<String>();
		for (String a : data.get(tmp)) {
			a =  a.split("\r")[0];
			if (!type && outputAlphabet.contains(a))
				break;
			rst.add(type ? a.substring(0, a.length()-1):a);
		}
		return rst;
	}
	
	
	/**
	 * Method to find the first achievable states from a given state
	 * @param arr with the partition to search
	 * @param tmp the state to find
	 * @return a block where the state tmp is
	 */
	private ArrayList<String> findStatesFirstAchievable(ArrayList<ArrayList<String>> arr, String tmp) {
		for (ArrayList<String> a : arr) {
			for (String s : a) {
				if (tmp.equals(s))
					return a;
			}
		}
		return null;
	}
	
	
	/**
	 * Method to know if to states belongs to same block of a partition
	 * @param arr with the partition to search
	 * @param tmp with the name of state one
	 * @param tmp2 with the name of state two
	 * @return
	 */
	private Boolean isPartOfSameBlock(ArrayList<ArrayList<String>> arr, String tmp, String tmp2) {
		ArrayList<String> auxTmp = findStatesAchievable(tmp);
		ArrayList<String> auxTmp2 = findStatesAchievable(tmp2);
		if (auxTmp.size()!=auxTmp2.size())
			return false;
		for (int i = 0; i < auxTmp.size(); i++) {
			String a = auxTmp.get(i);
			String b = auxTmp2.get(i);
			if (findStatesFirstAchievable(arr,a)!=findStatesFirstAchievable(arr,b))
				return false;
		}
		return true;
	}
	
	
	/**
	 * Method for step Two B that help to get next partition of a given
	 * @param parts that represents the partition that has to be partitioned again
	 * @return the final partition equivalent
	 */
	private ArrayList<ArrayList<String>> stepTwoB(ArrayList<ArrayList<String>> parts) {
		ArrayList<ArrayList<String>> rst = new ArrayList<ArrayList<String>>();
		for (ArrayList<String> arr : parts) {
			String tmp = arr.get(0);
			ArrayList<String> aux = new ArrayList<>();
			ArrayList<String> aux2 = new ArrayList<>();
			rst.add(aux);
			aux.add(tmp);
			for (int i = 1; i < arr.size(); i++) {
				String tmp2 = arr.get(i);
				if (isPartOfSameBlock(parts,tmp,tmp2))
					aux.add(tmp2);
				else
					aux2.add(tmp2);
			}
			if (aux2.size()>0 && !rst.contains(aux2))
				rst.add(aux2);
		}
		while (!stepTwoC(parts,rst)) {
			parts = rst;
			rst = stepTwoB(rst);
		}
		return rst;
	}
	
	
	
	/**
	 * Method to check if to partitions are equal
	 * @param pm with one partition
	 * @param pm1 with another partition
	 * @return true if there equal, false otherwise
	 */
	private Boolean stepTwoC(ArrayList<ArrayList<String>> pm,ArrayList<ArrayList<String>> pm1) {
		if (pm.size()!=pm1.size())
			return false;
		for (int i = 0; i < pm.size(); i++) {
			if (pm.get(i).size()!=pm1.get(i).size())
				return false;
			for (int j = 0; j < pm.get(i).size(); j++) {
				if (!pm.get(i).get(j).equals(pm.get(i).get(j)))
					return false;
			}
		}
		return true;
	}
	
	
	/**
	 * Method to find the index of a state in a partition
	 * @param stepTwo it's the partition where to search
	 * @param find it's the state to find
	 * @return the index where was found the state in the partition
	 */
	private int findQn(ArrayList<ArrayList<String>> stepTwo, String find) {
		int i = -1;
		for (ArrayList<String> arr : stepTwo) {
			for (String arr2 : arr) {
				if (arr2.equals(find))
					return i+1;
			}
			i++;
		}
		return i+1;
	}
	
	@Override
	public String[][] stepThree(ArrayList<ArrayList<String>> stepTwo) {
		HashMap<String,HashSet<String>> veryHelpful = new HashMap<String,HashSet<String>>();
		String[][] rst = new String[stepTwo.size()+1][rows+1];
		rst[0][0] = "   ";
		int a = 1;
		for (Character c : inputAlphabet) {
			rst[0][a] = c+"";
			a++;
		}
		if (!type)
			rst[0][rows] = "";
		a = 1;
		for (int i = 0; i < stepTwo.size(); i++) { //Each qn
			rst[i+1][0] = "q"+a;
			HashSet<String> helpful = new HashSet<String>();
			veryHelpful.put(rst[i+1][0],helpful);
			String test = stepTwo.get(i).get(0);
			for (int j = 0; j < inputAlphabet.size(); j++) {
				String cell = data.get(test)[j];
				cell = cell.split("\r")[0];
				String qn = "q"+(findQn(stepTwo, type ? cell.substring(0,cell.length()-1):cell)+1);
				qn = type ? qn+"/"+cell.substring(cell.length()-1):qn;
				rst[i+1][j+1] = qn;
				helpful.add(data.get(test)[rows-1].split("\r")[0]);
			}
			a++;
		}
		if (!type) {
			for (int i = 0; i < veryHelpful.keySet().size(); i++) {
				String key = (String) veryHelpful.keySet().toArray()[i];
				rst[i+1][rows] = veryHelpful.get(key).toArray()[0].toString();
			}
		}
		return rst;
	}
	
	
	@Override
	public boolean getAutomataType() {
		return type;
	}

}
