package com.amazonia.utils.functional;

import java.util.Collection;
import java.util.Comparator;

import com.amazonia.utils.functional.exceptions.IllegalParameterException;
import com.amazonia.utils.functional.interfaces.IFilter;
import com.amazonia.utils.functional.interfaces.IMapper;
import com.amazonia.utils.functional.interfaces.IReducer;

/**
 * Functional methods set for collections. Such as filter or map operations
 * 
 * @author pengfeil
 * 
 */
public class JCollections<T> {
	/**
	 * A filter implementation. Put every element of collection into filter
	 * method, then remove all elements we don't needed. Note that: The original
	 * collection will be changed!
	 * 
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

	/**
	 * A map implementation. Implement mapper to every element in collection and
	 * return the results as a collection.
	 * 
	 * @param col
	 * @param mapper
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	public Collection<T> doMap(Collection<T> col, IMapper<T> mapper)
			throws InstantiationException, IllegalAccessException {
		Collection<T> rCol = col.getClass().newInstance();
		for (T t : col) {
			rCol.add(mapper.map(t));
		}
		return rCol;
	}

	/**
	 * A reducer implementation. Implement reducer to every pair element in
	 * collection and return a single element as result
	 * 
	 * @param col
	 * @param reducer
	 * @return
	 */
	public T doReducer(Collection<T> col, IReducer<T> reducer) {
		if (col.size() <= 0)
			throw new IllegalParameterException(
					"Collection can't be empty here!");
		T initVal = col.iterator().next();
		col.remove(initVal);
		T result = initVal;
		for (T t : col) {
			result = reducer.reduce(result, t);
		}
		col.add(initVal);
		return result;
	}

	/**
	 * Union the two input collection into one and return a new collection
	 * represent for result. Type of c1 will be used as type of result
	 * collection
	 * 
	 * @param c1
	 *            Collection<T>
	 * @param c2
	 *            Collection<T>
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	public Collection<T> doUnion(Collection<T> c1, Collection<T> c2)
			throws InstantiationException, IllegalAccessException {
		Collection<T> rCol = c1.getClass().newInstance();
		insertItem(c1, rCol);
		insertItem(c2, rCol);
		return rCol;
	}

	private void insertItem(Collection<T> from, Collection<T> to) {
		for (T t : from) {
			to.add(t);
		}
	}

	/**
	 * Get the 'max' item in collection
	 * 
	 * @param c
	 *            collection
	 * @param com
	 *            Comparator to used
	 * @return
	 */
	public T max(Collection<T> c, Comparator<T> com) {
		if (c.size() <= 0)
			throw new IllegalParameterException(
					"Collection can't be empty here!");
		T max = c.iterator().next();
		for (T t : c) {
			if (com.compare(t, max) > 0) {
				max = t;
			}
		}
		return max;
	}
}
