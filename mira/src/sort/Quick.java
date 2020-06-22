package sort;

import java.util.ArrayList;
import java.util.Collections;

public class Quick {
	/**
	 * basic quick sort
	 * @param x - the input array containing products that need to be sorted.
	 */
	public static void sortBasicQuick (ArrayList<Comparable> x) {
		sort(x,0,x.size()-1);	
	}
	
	/* @brief exchange, is used in order to swap the position
	 * of the products compared if the less function returns true. 
	 * @param Comparable[] a, the list of products to be edited. 
	 * @param i, the index of a product to be exchanged
	 * @param j, the index of a product to be exchanged with i
	 */
	
	private static void exchange(ArrayList<Comparable> a, int i, int j) {
		Collections.swap(a, i, j);
	}
	
	/* @brief partition, is used to separate an inputed array and comparing
	 * the values in order to partially sort the array every time is called
	 * and return a partitioning pivot.
	 * @param Comparable x[], the array of products to be partitioned
	 * @param lo, index of the product that is used as a lower bound
	 * @param hi, index of the product that is used as a upper bound
	 */
	
	private static int partition(ArrayList<Comparable> x, int lo, int hi) {
		int i = lo, j = hi+1;
		Comparable v = x.get(lo);
		while(true) {
			while (x.get(++i).compareTo(v) < 0) if (i == hi) break;
			while (v.compareTo(x.get(--j)) < 0) if (j == lo) break;
			if (i >= j) break;
			exchange(x,i,j);
		}
		exchange(x,lo,j);
		return j;
	}
	
	/* @brief sort, calls the partition functions and recursively calls
	 * itself to sort the array.
	 * @param lo, index of the product that is used as a lower bound
	 * @param hi, index of the product that is used as a upper bound
	 */
	
	private static void sort(ArrayList<Comparable> x, int lo, int hi) {
		if (hi <= lo) return;
		int j = partition(x,lo,hi);
		sort(x, lo,j - 1);
		sort(x, j+1, hi);
	}
}