package mira;

import search.BinarySearch;
import java.io.BufferedReader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.text.StringEscapeUtils;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * @author Harsh Gupta and Navroop Singh
 *
 */
public class Parse {
	/**
	 * @return Reads the data to map a string in the CSV data file to a condition.
	 * @throws IOException
	 */
	public static Map<String, Condition> loadData() throws IOException {
//       System.out.println("Working Directory = " +
//               System.getProperty("user.dir"));
		System.out.println("Started Data Preprocessing");
		String csvFile = "./dataset/uci/drugsComTest_raw.csv";
//		String csvFile = "./dataset/uci/drugsComTest_raw.csv";		
		Map<String, Condition> conditions = new HashMap<String, Condition>();
		Reader in = new FileReader(csvFile);
		Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
		int a = 0;
		for (CSVRecord record : records) {
			if (a == 0) {
				a++;
				continue;
			}
//			System.out.println("Current record: "+ a);
			String review = record.get(3);
			review = review.replace("\"", "");
			review = StringEscapeUtils.unescapeHtml4(review);//
			String[] conds = record.get(2).split(", ");
			String medication = record.get(1);
			int useful = Integer.parseInt(record.get(6));
			int rating = Integer.parseInt(record.get(4));
			if (conds[0].contains("</span>")) {
				System.out.println("Skipped " + a + "!");
				continue;
			}
			for (String condition : conds) {
				if (conditions.containsKey(condition)) {
//    			System.out.println("Condition found");
//    			The conditions list has the drug entry
//    			Check for drug in condition
					Condition cond = conditions.get(condition);
					int drug_index = BinarySearch.binarySearch_D(cond.getDrugs(), medication);
					if (drug_index != -1) {
//    				Drug found. Add review to the drug
						Drug drug = (Drug) cond.getDrugs().get(drug_index);
						drug.add(new Review(review, condition, rating, useful));
					} else {
//    				Drug not found. Add drug to condition
						Drug new_drug = new Drug(medication, condition);
						new_drug.add(new Review(review, condition, rating, useful));
						cond.add(new_drug);
					}
				} else {
//    			Condition not found. Make a new condition and add the drug to it.
//    			System.out.println("Condition not found");
					Drug new_drug = new Drug(medication, condition);
					Condition cond = new Condition(condition);
					new_drug.add(new Review(review, condition, rating, useful));
					cond.add(new_drug);
					conditions.put(condition, cond);
				}
			}

			if (a == 1000) {
				break;
			}
			a++;
		}
		System.out.println("Loading done.");
		return conditions;
	}

	public static void main(String[] args) throws IOException {
		Map<String, Condition> l = loadData();

		for (Map.Entry<String, Condition> entry : l.entrySet()) {
//			System.out.println("["+i+"] "+condition.getName() +" : "+ condition.getDrugs().size());			
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}

//		
//		Condition first = l.get("Crohn's Disease");		
//		System.out.println(first.getName() + " | number of drugs : " + first.getDrugs().size());
//		ArrayList<Comparable> weight = first.getDrugs();
//		for (Comparable drug : weight) {
//			System.out.println(((Drug) drug).getName());
//			ArrayList<Comparable> reviews = (((Drug) drug).getReviews());
//			for (Comparable review : reviews) {
//				System.out.println(((Review) review));				
//			}
//			System.out.println("========================================");
//		}

	}

}