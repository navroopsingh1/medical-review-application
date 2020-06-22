package mira;

import java.util.ArrayList;

/**
 * @author Ahmed and Amrit
 *
 */
public class Drug implements Comparable<Drug>{

	private final String name;
	private final String condition;
	private ArrayList<Comparable> reviews = new ArrayList<Comparable>(); 
	private float score = 0;

	/**
	 * @param name A string which represents the name of the drug.
	 * @param condition
	 */
	public Drug(String name, String condition) {
		this.name = name;
		this.condition = condition;
	}
	
	/**
	 * @return The name of the drug
	 */
	public String getName() { return this.name; }
	
	/**
	 * @return The condition associated to the drug.
	 */
	public String getCond() {return this.condition; }
	
	/**
	 * @return Arraylist containing all the reviews for a particular drug.
	 */
	public ArrayList<Comparable> getReviews() {return this.reviews; }
	
	/**
	 * @return The calculated score of the drug based on the reviews and sentiment analysis.
	 */
	public float getScore() { return this.score; }
	
	/**
	 * @brief Updates the score of a review.
	 */
	private void updateScore() {
		float s = 0;
		for(Comparable r : this.reviews) {
			Review rev = (Review) r;
			s += rev.getSrating();
		}
		this.score = s/this.reviews.size();
	}
	/**
	 * @param review Adds a review to a drug.
	 */
	public void add(Review review) {
		this.reviews.add(review);
		sort.Quick.sortBasicQuick(this.reviews);
		this.updateScore();
	}	
	/**
	 * @brief Gives the information of a drug in string format, including the associated condition, its score, and its reviews.
	 */
	@Override
	public String toString() {
		return "Drug [name: " + this.name + " condition: " + this.condition + " score: " + this.score +" # of reviews: "+this.reviews.size()+"]";
	}
	/**
	 * @brief Compares two drugs based on score.
	 */ 
	@Override
	public int compareTo(Drug j)
	{
		//TODO
		if (this.score < j.score ) {
			return -1;
		}
		else if (this.score  > j.score ) {
			return 1;
		}
		return 0;		
	}
}