package com.amazonia.utils.functional.algorithm;

import org.junit.Assert;
import org.junit.Test;

public class StringUtilTest {
	@Test
	public void testEditDistance() {
		String s1 = "a";
		String s2 = "1";
		Assert.assertEquals(1, StringUtil.editDistance(s1, s2));
		s1 = "qwertyuiop1";
		s2 = "1qwertyuiop";
		Assert.assertEquals(2, StringUtil.editDistance(s1, s2));
		s1 = "qwertyuiop1";
		s2 = "21qwertyuiop1";
		Assert.assertEquals(2, StringUtil.editDistance(s1, s2));
		Assert.assertEquals(0, StringUtil.editDistance("", ""));
		s1 = "大家好";
		s2 = "你们好";
		Assert.assertEquals(2, StringUtil.editDistance(s1, s2));
	}
}
