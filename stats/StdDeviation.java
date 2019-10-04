package cs228hw1.stats;

import java.util.ArrayList;
import java.math.*;

/**
 * object to take a data set and then finds the standard devation of it
 * @author nicholaskrabbenhoft
 *
 * @param <T> a object that extends number that the stat object will do calculations on
 */
public class StdDeviation<T extends Number> extends AbStat<T> {

	/**
	 * Defalt constructor
	 */
	public StdDeviation() {
		super();
	}

	/**
	 * Constructor that takes a arraylist of T and sets it as the objects data set
	 * 
	 * @param data
	 */
	public StdDeviation(ArrayList<T> data) {
		super(data);

	}

	/**
	 * Returns the stdandard devation of the data in the object
	 * 
	 * @return arraylist of T with a single value whichis the standard deviation of
	 *         the data
	 * @throws RuntimeException if data in the object is null or empty
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<T> GetResult() throws RuntimeException {

		Average<T> averageObject = new Average<T>(myList);

		double average = ((Double) (averageObject.GetResult()).get(0)).doubleValue();

		float temp = 0;

		for (int i = 0; i < myList.size(); i++) {

			// System.out.println( myList.get(i).getClass());

			// this is to find (myList[i]-average)^2
			temp += (((Number) myList.get(i)).doubleValue() - average)
					* (((Number) myList.get(i)).doubleValue() - average);

		}

		temp /= (myList.size() - 1);

		results.clear();

		results.add((T) (Double) Math.sqrt(temp));

		return results;
	}

}