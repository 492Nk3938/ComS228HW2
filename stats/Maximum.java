package cs228hw1.stats;

import java.util.ArrayList;

/**
 * A object that takes data and calculates the max imum
 * @author nicholaskrabbenhoft
 *
 * @param <T> a object that extends number that the stat object will do calculations on
 */
public class Maximum<T extends Number> extends AbStat<T> {

	/**
	 * defult constructor
	 */
	public Maximum() {
		super();
	}

	/**
	 * Constructor takes data and sets it to the objects data
	 * 
	 * @param data the data as arraylist <T> that it will later find the max of
	 */
	public Maximum(ArrayList<T> data) {
		super(data);

	}

	@Override
	/**
	 * Methiod to get the maximum of the data stored in the object
	 * 
	 * @return the largest value in the object
	 */
	public ArrayList<T> GetResult() throws RuntimeException {

		int index = 0; // (0, myList.get(0).doubleValue());

		for (int i = 1; i < myList.size(); i++) {
			if (((Number) myList.get(i)).doubleValue() > ((Number) myList.get(index)).doubleValue()) {
				index = i;
			}
		}

		results.clear();

		results.add(myList.get(index));

		return results;

	}

}
