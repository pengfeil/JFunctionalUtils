package com.amazonia.utils.functional.algorithm;

/**
 * Provide some utility methods for "numbers"
 * 
 * @author pengfeil
 * 
 */
public class NumberUtil {
	public static enum Action {
		MAX, MIN
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Comparable select(Comparable[] numbers, Action action) {
		Comparable tmp = numbers[0];
		for (Comparable t : numbers) {
			if (action == Action.MAX && t.compareTo(tmp) > 0) {
				tmp = t;
			}
			if (action == Action.MIN && t.compareTo(tmp) < 0) {
				tmp = t;
			}
		}
		System.out.println(action + ":" + tmp);
		return tmp;
	}
}
