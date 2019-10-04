package cs228hw1.stats;

/**
 * A object that extends Iparser that parses doubles
 * @author nicholaskrabbenhoft
 *
 */
public class DoubleParser extends IParser<Double> {

	public DoubleParser() {
	}

	/**
	 * * Given a String, returns the Number it represents.
	 * 
	 * @param str The string form of the number.
	 * @return Returns the Number represented by the input if valid. If it's not
	 *         valid null is returned
	 */
	@Override
	public Double parse(String str) {
		try {
			return ((Double) Double.parseDouble(str));
		} catch (Exception e) {
			return null;

		}

	}

}
