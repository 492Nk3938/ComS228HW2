package cs228hw1.stats;

import java.util.ArrayList;


/**
 * A object to create histogram from a given data set
 * @author nicholaskrabbenhoft
 *
 * @param <T> a object that extends number that the stat object will do calculations on
 */
public class Histogram<T extends Number> extends AbStat<T> {

	/**
	 * The number of bins that the histogram contains
	 */
	private int bins;

	/**
	 * The minimum value that will be inclueded in the histogram
	 */
	private float min;

	/**
	 * The maximum value that will be included in the histogram, all values below are in the histogram
	 */
	private float max;



	/**
	 * Defult constructor that sets the min and max to infinity (Long.MIN/MAX) and
	 * sets the bins to 10
	 */
	public Histogram() {
		super();
		max = Long.MAX_VALUE;
		min = Long.MIN_VALUE;
		bins = 10;
		setUpResults();
	}

	/**
	 * Constructor that sets the min and max to infinity (Long.MIN/MAX) and sets the
	 * bins to 10, it also takes the data given and sets it to the working data
	 */
	public Histogram(ArrayList<T> data) {
		super(data);

		max = Long.MAX_VALUE;
		min = Long.MIN_VALUE;
		bins = 10;

		results.clear();
		setUpResults();

	}

	/**
	 * Small function to insure that the bins are empty and there are the correct
	 * number of them
	 */
	private void setUpResults() {
		results.clear();
		for (int i = 0; i < bins; i++) {
			results.add((T) (Integer) 0);
		}
		results.trimToSize();
	}

	/**
	 * A methiod that returns a historam of the data held in the object
	 * 
	 * @return a arraylist of integers that hold the number of vlaues in each bin in
	 *         the style of a histogram
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<T> GetResult() throws RuntimeException {

		// if list is empty return a empty list
		if (myList.size() == 0) {
			results.clear();
			return results;
		}

		Minimum<T> minCalc = new Minimum<T>(myList);

		Maximum<T> maxCalc = new Maximum<T>(myList);

		// check if valid and through error if not
		if (min > max) {
			throw new RuntimeException();
		}

		double binSize = ((max - min) / bins);

		setUpResults();

		// First checks and makes sure its under the max
		// Second checks if it fits in the bin starting with the
		// largest and checking each one as it goes down
		for (int i = 0; i < myList.size(); i++) {

			for (int j = 1
					; j <= bins; j++) {

				Double temp = ((Number) myList.get(i)).doubleValue();

				
				
				
				
				if ((temp <= max) && (temp >= (max - (j * binSize)) 
						&& temp < (max - ((j-1) * binSize)))) {
					System.out.println(temp.toString() + "is going into the bucket starting at " +
							(max - (j * binSize)) + " and ending at "+ (max - ((j-1) * binSize)));

					int index = results.size() - j;
					int amount = results.get(index).intValue() + 1;

					results.set(index, (T) (Integer) amount);

				}

			}

		}

		return results;
	}

	/**
	 * sets the number of bins in the object
	 * @param temp varible to set the bins to
	 */
	public void SetNumberBins(int temp) {

		bins = temp;

		setUpResults();

	}

	/**
	 * Accesor for the bins 
	 * @return int number of bins in the object
	 */
	public int GetNumberBins() {
		return bins;

	}

	
	/**
	 * methiod to set the minimum value included in the histogram
	 * @param temp varible to set the min to
	 */
	public void SetMinRange(T temp) {
		min = temp.floatValue();
	}

	/**
	 * accesor to get the min value stored in the object
	 * @return a float of the minimum value stored
	 */
	public float GetMinRange() {
		return min;
	}

	
	/**
	 * methiod to set the max range
	 * @param temp type T to set the max to
	 */
	public void SetMaxRange(T temp) {
		max = temp.floatValue();
	}

	
	/**
	 * accesor to get the value that the max range is at
	 * @return a float of the value of max
	 */
	public float GetMaxRange() {
		return max;
	}

}
