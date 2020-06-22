package mira;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

/**
 * @author Amrit Pandher
 *
 */
public class Review implements Comparable<Review> {
	//private final String drugName;
	private final String review;
	private final float rating;
	private final String condition;
	private final int useful;
	private final double sentiment;
	private final double srating;
	private Drug parent;
	
	/**
	 * @param review The review from the data in String format.
	 * @param condition The condition associated to the review in String format.
	 * @param rating The rating of the drug from the data.
	 * @param useful The useful rating from the data.
	 */
	public Review(String review, String condition, float rating, int useful) {
		this.review = review;
		this.condition = condition;
		this.rating = rating;
		this.useful = useful;
		this.sentiment = this.getSetimentScore();
		this.srating = 0.8 * rating + 0.2*useful*this.sentiment;
//		Formula for final smart rating
//		sentiment = [-1,1] f
//		rating = [0,10] int
//		useful = (0, ♾)
//		srating = 0.8*rating + 0.2 * useful
	}

	
	/**
	 * @return The review from the data in String format.
	 */
	public String getReview() { return this.review; }
	
	/**
	 * @return The smart rating of the review calculated using our own formula.
	 */
	public double getSrating() { return this.srating; }
	
	/**
	 * @return The condition associated to the review in String format.
	 */
	public String getCond() { return this.condition; }
	
	/**
	 * @return The useful rating from the data for the drug as an integer.
	 */
	public int getUseful() { return this.useful; }
	
	/**
	 * @param port The port needed for the server call.
	 * @return A sentiment score calculated through a server call.
	 */
	private double getSetimentScore(int port) {
		HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead 

		try {
			JSONObject json = new JSONObject();
			json.put("text", this.getReview());  

		    HttpPost request = new HttpPost("http://localhost:"+port);
		    StringEntity params = new StringEntity(json.toString());
		    request.addHeader("content-type", "application/json");
		    request.setEntity(params);
		    HttpResponse response = httpClient.execute(request);
		    String json_string = EntityUtils.toString(response.getEntity());
		    JSONObject temp1 = new JSONObject(json_string);
		    return (double) temp1.get("polarity");
		    //handle response here...
		}catch (HttpHostConnectException ex) {
		    //handle exception here
			System.out.println("Server not running! Please run the python server in the api folder");
			System.out.println("Exiting program!");						
			System.exit(0);			
		}catch(Exception ex) {			
		}
		finally {
		    //Deprecated
		    //httpClient.getConnectionManager().shutdown(); 
		}
		return 0.0f;	
	}
	
	/**
	 * @return Gets the sentiment score for the particular review.
	 */
	private double getSetimentScore() {
//		Default port
		return this.getSetimentScore(8080);
	}
	
	/**
	 * @brief Compares two reviews based on score.
	 */ 
	@Override
	public int compareTo(Review j)
	{
		//TODO
		if (this.useful < j.useful ) {
			return -1;
		}
		else if (this.useful  > j.useful ) {
			return 1;
		}
		return 0;		
	}
	
	/**
	 * @brief Gives the reviews details in String format, includes the various ratings.
	 */
	@Override
	public String toString() {
		return "Review [condition: "+ this.condition + "\tsmart_rating: "+this.srating+ "\tsentiment: "+this.sentiment+ "\trating: "+this.rating+"\tuseful: "+this.useful+"\n\ttext: "+this.review+"\n]";
	}
	
//	Test code for sentiment stuff
//	public static void main(String[] args) {
//		double sentiment = getSetimentScore(8080, "My son is halfway through his fourth week of Intuniv. We became concerned when he began this last week, when he started taking the highest dose he will be on. For two days, he could hardly get out of bed, was very cranky, and slept for nearly 8 hours on a drive home from school vacation (very unusual for him.) I called his doctor on Monday morning and she said to stick it out a few days. See how he did at school, and with getting up in the morning. The last two days have been problem free. He is MUCH more agreeable than ever. He is less emotional (a good thing), less cranky. He is remembering all the things he should. Overall his behavior is better.");
//		System.out.println(sentiment);
//	}
}
