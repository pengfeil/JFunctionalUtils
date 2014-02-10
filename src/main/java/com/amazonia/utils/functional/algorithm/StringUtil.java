package com.amazonia.utils.functional.algorithm;

import com.amazonia.utils.functional.algorithm.NumberUtil.Action;

/**
 * Provide some popular algorithm related to strings
 * 
 * @author pengfeil
 * 
 */
public class StringUtil {
	/**
	 * Calculate edit distance between s1 and s2. 
	 * Learn more about Edit Distance, go to http://en.wikipedia.org/wiki/Edit_distance
	 * 
	 * @param s1
	 * @param s2
	 * @return distance between s1 and s2
	 */
	public static int editDistance(String s1, String s2) {
		if (s1.length() <= 0)
			return s2.length();
		if (s2.length() <= 0)
			return s1.length();
		// Initialize
		int distanceMap[][] = new int[s1.length() + 1][s2.length() + 1];
		char[] c1 = s1.toCharArray();
		char[] c2 = s2.toCharArray();
		distanceMap[0][0] = 0;
		distanceMap[0][1] = distanceMap[1][0] = 1;
		for (int i = 0; i < s1.length() + 1; i++) {
			distanceMap[i][0] = i;
		}
		for (int i = 0; i < s2.length() + 1; i++) {
			distanceMap[0][i] = i;
		}
		// Based on DP
		for (int i = 1; i <= c1.length; i++) {
			for (int j = 1; j <= c2.length; j++) {
				if (c1[i - 1] == c2[j - 1]) {
					distanceMap[i][j] = distanceMap[i - 1][j - 1];
				} else {
					distanceMap[i][j] = (Integer) NumberUtil
							.select(new Integer[] { distanceMap[i - 1][j] + 1,
									distanceMap[i][j - 1] + 1,
									distanceMap[i - 1][j - 1] + 1 }, Action.MIN);
				}
				System.out.println(i + "/" + j + "   " + c1[i - 1] + "-"
						+ c2[j - 1] + ":" + distanceMap[i][j]);
			}
		}
		// Find min distance
		return distanceMap[s1.length()][s2.length()];
	}
}
