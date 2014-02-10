package com.amazonia.utils.functional.algorithm;

import org.junit.Assert;
import org.junit.Test;

import com.amazonia.utils.functional.algorithm.NumberUtil.Action;

public class NumberUtilTest {
	@Test
	public void testSelect() {
		Integer array[] = new Integer[] { 4, 3, 2, 1, 2 };
		Assert.assertEquals(4,
				((Integer) NumberUtil.select(array, Action.MAX)).intValue());
		Assert.assertEquals(1,
				((Integer) NumberUtil.select(array, Action.MIN)).intValue());
	}
}
