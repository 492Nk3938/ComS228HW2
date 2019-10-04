package cs228hw1.stats;

/**
 * A parser that extends Iparser that parses strings that hold ints
 * @author nicholaskrabbenhoft
 *
 */
public class IntParser extends IParser<Integer> {

	public IntParser() {}

	/**
	 * Given a String, returns the Number it represents, as an integer.
	 * 
	 * @param str The string form of the number.
	 * @return Returns the Number represented by the input if valid. If it's not
	 *         valid null is returned
	 */
	@Override
	public Integer parse(String str) {
		try {
			return  Integer.parseInt(str);
		} catch (Exception e) {
			return null;
		}

	}

}
