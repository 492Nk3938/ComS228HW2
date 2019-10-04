package cs228hw1.stats;

/**
 * Parser for Longs that extends Iparser ment to parse strings that contain longs
 * @author nicholaskrabbenhoft
 *
 */
public class LongParser extends IParser<Long> {

	public LongParser() {
	}

	/**
	 * * Given a String, returns the Number it represents.
	 * 
	 * @param str The string form of the Long.
	 * @return Returns the Number represented by the input if valid. If it's not
	 *         valid null is returned
	 */
	@Override
	public Long parse(String str) {
		try {
			return Long.parseLong(str);
		} catch (Exception e) {
			return null;
		}

	}

}