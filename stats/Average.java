package cs228hw1.stats;

import java.util.ArrayList;

/**
 *  statobject that calculates the average
 * @author nicholaskrabbenhoft
 *
 * @param <T> a object that extends number that the stat object will do calculations on
 */
public class Average<T extends Number> extends AbStat<T> {

	/**
	 * Constructor that takes a arrayList of type T to set the data equal to
	 * 
	 * @param data ArrayList of T to set the data equal to
	 */
	public Average(ArrayList<T> data) {
		super(data);

	}

	/**
	 * Default constructor
	 */
	public Average() {
		super();
	}

	/**
	 * Method to calculate and return the average
	 * 
	 * @return ArrayList of type T containing 1 element which is the average of the
	 *         data
	 * @throws RuntimeException if data is empty or void
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<T> GetResult() throws RuntimeException {

		ifValid(myList);

		double average = 0.0;

		for (int i = 0; i < myList.size(); i++) {

			average += myList.get(i).doubleValue() / myList.size();
		}

		results.clear();
		results.add((T) (Double) average);

		return results;

	}

}
