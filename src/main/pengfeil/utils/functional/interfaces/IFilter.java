package pengfeil.utils.functional.interfaces;
/**
 * Interface used for filter.
 * It's a replacement for function parameter in functional language.
 * @author pengfeil
 *
 */
public interface IFilter<T> {
	/**
	 * Determine whether current object should be filter out.
	 * @param o current object
	 * @return a boolean represent for result
	 */
	public boolean isFiltered(T o);
}
