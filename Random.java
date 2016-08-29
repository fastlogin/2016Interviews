import java.util.*;
import java.util.stream.Collectors;

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
	
	// Helper for printing out all possible ways to get to top of stair case
	static List<StringBuilder> printNumWaysStairCaseHelper(int n, ArrayList<List<StringBuilder>> dpMem) {
		if (dpMem.get(n) != null) {
			return dpMem.get(n);
		}
		List<StringBuilder> result = new ArrayList<StringBuilder>();
		if (n > 2) {
			List<StringBuilder> takeOne = printNumWaysStairCaseHelper(n - 1, dpMem);
			List<StringBuilder> takeTwo = printNumWaysStairCaseHelper(n - 2, dpMem);
			List<StringBuilder> takeThree = printNumWaysStairCaseHelper(n - 3, dpMem);
			if (takeOne.isEmpty()) {
				result.add(new StringBuilder("1"));
			} else {
				result.addAll(takeOne
						.stream()
						.map(p -> (new StringBuilder(p)).append(",").append("1"))
						.collect(Collectors.toList()));
			}
			if (takeTwo.isEmpty()) {
				result.add(new StringBuilder("2"));
			} else {
				result.addAll(takeTwo
						.stream()
						.map(p -> (new StringBuilder(p)).append(",").append("2"))
						.collect(Collectors.toList()));
			}
			if (takeThree.isEmpty()) {
				result.add(new StringBuilder("3"));
			} else {
				result.addAll(takeThree
						.stream()
						.map(p -> (new StringBuilder(p)).append(",").append("3"))
						.collect(Collectors.toList()));
			}
		}
		dpMem.add(n, result);
		return result;
	}
	
	/**
	 * Problem: Given a staircase of height n, and you can either take 1,2, or 3
	 * steps at a time, print out all the different ways you can reach the top.
	 * @param n
	 * @return list of ways
	 */
	static List<String> printNumWaysStairCase(int n) {
		ArrayList<List<StringBuilder>> dpMem = new ArrayList<List<StringBuilder>>(n + 1);
		for (int i = 0; i < n + 1; i++) {
			if (i == 0) {
				dpMem.add(i, new ArrayList<StringBuilder>());
			}
			else if (i == 1) {
				dpMem.add(i, Arrays.asList(new StringBuilder("1")));
			}
			else if (i == 2) {
				dpMem.add(i, Arrays.asList(new StringBuilder("1,1"), new StringBuilder("2")));
			} else {
				dpMem.add(i, null);
			}
		}
		return printNumWaysStairCaseHelper(n, dpMem)
				.stream()
				.map(p -> p.toString())
				.collect(Collectors.toList());
	}
	
	public static void main(String[] args) {
		
		// Test cases for numWaysStairCaseHelper
		System.out.println();
		System.out.println(numWaysStairCase(1));
		System.out.println(numWaysStairCase(2));
		System.out.println(numWaysStairCase(3));
		System.out.println(numWaysStairCase(4));
		System.out.println(numWaysStairCase(27));
		System.out.println(numWaysStairCase(30));
		
		// Test cases for printNumWaysStairCaseHelper
		System.out.println();
		System.out.println(printNumWaysStairCase(0));
		System.out.println(printNumWaysStairCase(1));
		System.out.println(printNumWaysStairCase(2));
		System.out.println(printNumWaysStairCase(3));
		System.out.println(printNumWaysStairCase(4));
	}
}
