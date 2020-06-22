package mira;

import java.util.ArrayList;

/**
 * @author Amrit Pandher
 *
 */
public class Condition implements Comparable<Condition>{
	private final String name;
	private final ArrayList<Comparable> drugs;
//	private final ArrayList<Comparable> drugs = new ArrayList<Comparable>();
	
	/**
	 * @brief ADT to store the name of the condition and an array list containing drugs used to treat that condition.
	 * @param name The name of the condition.
	 */
	public Condition(String name) {
		this.name = name;	
		this.drugs = new ArrayList<Comparable>();
	}
	
	/**
	 * @return The name of the condition in string format.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * @return The list of drugs as an array list of strings.
	 */
	public ArrayList<Comparable> getDrugs(){
		return this.drugs;
	}
	
	/**
	 * @brief Adds drugs to the array list in a condition.
	 * @param drug The drug to be added to the array list in condition.
	 */
	public void add(Drug drug) {
		this.drugs.add(drug);
//		sort.Quick.sortBasicQuick(this.drugs);
	}
	
	/**
	 * @brief Adds drugs to the array list in a condition.
	 * @param arg0 A condition to be compared.
	 */
	@Override
	public int compareTo(Condition arg0) {
		// TODO Auto-generated method stub
		return this.getName().compareTo(arg0.getName());
	}
	/**
	 * @return String format of a condition and the drugs used to treat it.
	 */
	@Override
	public String toString() {
		return "Condition: "+ this.getName() +" Drugs: "+ this.getDrugs().size();
	}
}
