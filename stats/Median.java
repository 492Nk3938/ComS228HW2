package cs228hw1.stats;

import java.util.ArrayList;
/**
 * object that takes an set of data T type T extends number and returns the median of the data set
 * @author nicholaskrabbenhoft
 *
 * @param <T> a object that extends number that the stat object will do calculations on
 */
public class Median<T extends Number> extends AbStat<T> {

	/**
	 * Defalt constructor
	 */
	public Median() {
		super();
	}

	/**
	 * Constructor takes an arraylist of type T and sets it to the data stored in
	 * the object
	 * 
	 * @param data
	 */
	public Median(ArrayList<T> data) {
		super(data);

	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * returns the median of the stored data
	 */
	public ArrayList<T> GetResult() throws RuntimeException {
		ifValid(myList);

		ArrayList<T> sortedArr = super.sort(myList);

		results.clear();

		if (sortedArr.size() % 2 == 0) {
			int size = sortedArr.size();
			T midLow = sortedArr.get(size / 2 - 1);
			T midHigh = sortedArr.get(size / 2);

			// this will find the average of the 2 middle numbers and add
			// it to the results
			results.add((T) ((Number) ((midHigh.doubleValue() + midLow.doubleValue()) / 2)));

		} else {

			// This finds the middle number and returns the results
			results.add(sortedArr.get(sortedArr.size() / 2));

		}

		return results;
	}

}
