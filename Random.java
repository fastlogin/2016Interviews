import java.util.Arrays;
import java.util.Map;

/**
 * Random questions acquired from various sources.
 */
public class Random {
	
	// Recursive helper for number of ways of climbing a stair case.
	static int numWaysStairCaseHelper(int n, int[] dpMem) {
		if (dpMem[n] != 0) {
			return dpMem[n];
		}
		int result = 0;
		if (n > 2) {
			result = numWaysStairCaseHelper(n - 1, dpMem) + numWaysStairCaseHelper(n - 2, dpMem) + numWaysStairCaseHelper(n - 3, dpMem);
		}
		dpMem[n] = result;
		return result;
	}
	
	/**
	 * Problem: Given a staircase of height n, and you can either take 1,2, or 3
	 * steps at a time, how many different ways can you reach the top?
	 * @param n
	 * @return number of ways
	 */
	static int numWaysStairCase(int n) {
		if (n < 2) {
			return n;
		}
		int[] dpMem = new int[n + 1];
		dpMem[0] = 1;
		dpMem[1] = 1;
		dpMem[2] = 2;
		int result = numWaysStairCaseHelper(n, dpMem);
		return result;
	}
	
	public static void main(String[] args) {
		
		// Test cases for numWaysStairCaseHelper
		System.out.println(numWaysStairCase(1));
		System.out.println(numWaysStairCase(2));
		System.out.println(numWaysStairCase(3));
		System.out.println(numWaysStairCase(4));
		System.out.println(numWaysStairCase(27));
		System.out.println(numWaysStairCase(30));
	}
}
