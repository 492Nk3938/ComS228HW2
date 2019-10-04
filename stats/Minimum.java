package cs228hw1.stats;

import java.util.ArrayList;


/**
 * A object that takes a data set of type T extends Number and can return the minimum value
 * @author nicholaskrabbenhoft
 *
 * @param <T> a object that extends number that the stat object will do calculations on
 */
public class Minimum<T extends Number> extends AbStat<T> {
	/**
	 * Defalt constructor
	 */
	public Minimum() {
		super();
	}

	/**
	 * Constructor takes an arraylist of type T and sets it to the data stored in
	 * the object
	 * 
	 * @param data
	 */
	public Minimum(ArrayList<T> data) {
		super(data);

	}

	/**
	 * Returns the minimum value of the data stored in the object
	 */
	public ArrayList<T> GetResult() throws RuntimeException {

		int index = 0;

		for (int i = 1; i < myList.size(); i++) {
			if (myList.get(i).doubleValue() < myList.get(index).doubleValue()) {
				index = i;
			}
		}

		results.clear();

		results.add(0, myList.get(index));

		return results;

	}

}