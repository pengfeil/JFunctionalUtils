package com.amazonia.utils.functional;

import java.util.List;

/**
 * Functional methods set for lists. Such as get element by negative position
 * 
 * @author pengfeil
 * 
 * @param <T>
 */
public class JLists<T> extends JCollections<T> {
	/**
	 * Get element by position. Negative position is supported.
	 * example: position=-1 => return last element in list.
	 * @param list
	 * @param position
	 * @return
	 */
	public T at(List<T> list, int position) {
		if (position >= 0) {
			return list.get(position);
		} else {
			return list.get(list.size() + position);
		}
	}
}
