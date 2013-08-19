package pengfeil.utils.functional.interfaces;
/**
 * Interface used for map.
 * It's a replacement for function parameter in functional language.
 * @author pengfeil
 *
 */
public interface IMapper<T> {
	/**
	 * Implement the map method to current object and then return the map result.
	 * @param o current object
	 * @return a boolean represent for result
	 */
	public T map(T o);
}
