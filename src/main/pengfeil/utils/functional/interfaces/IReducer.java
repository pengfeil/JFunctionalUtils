package pengfeil.utils.functional.interfaces;
/**
 * Interface used for reduce.
 * It's a replacement for function parameter in functional language.
 * @author pengfeil
 *
 */
public interface IReducer<T> {
	/**
	 * Implement the two objects and then return the single reduce result.
	 * @param o current object
	 * @return a boolean represent for result
	 */
	public T reduce(T o1, T o2);
}
