package pengfeil.utils.functional;

import java.util.Collection;

import pengfeil.utils.functional.interfaces.IFilter;

/**
 * Functional methods set for collections. Such as filter or map operations
 * 
 * @author pengfeil
 * 
 */
public class JCollections<T> {
	/**
	 * A filter implementation. Put every element of collection into filter
	 * method, then remove all elements we don't needed.
	 * Note that: The original collection will be changed!
	 * @param col
	 * @param filter
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void doFilter(Collection<T> col, IFilter<T> filter) {
		T[] objects = (T[]) col.toArray();
		for (int i = 0; i < objects.length; i++) {
			if (filter.isFiltered(objects[i])) {
				col.remove(objects[i]);
			}
		}
	}
}
