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

	static boolean isValidNeighbor(int[][] ocean, int x, int y) {
		return (x >= 0 && 
				x < ocean.length && 
				y >= 0 && 
				y < ocean[0].length &&
				ocean[x][y] > 0);
	}

	static List<List<Integer>> findNeighbors(int[][] ocean, Set<List<Integer>> visited, List<Integer> point) {
		List<List<Integer>> neighbors = new ArrayList<List<Integer>>();
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				int xCor = point.get(0) + i;
				int yCor = point.get(1) + j;
				if (isValidNeighbor(ocean, xCor, yCor) && 
						!visited.contains(Arrays.asList(xCor, yCor))) {
					neighbors.add(Arrays.asList(xCor, yCor));
				}
			}
		}
		return neighbors;
	}

	/**
	 * Given a 2D array representing an ocean/body of water where 0 values
	 * correspond to water and > 0 values represent land, return the number
	 * of continuous islands there are in the ocean.
	 * @param ocean
	 * @return number of islands
	 */
	static int numIslands(int[][] ocean) {
		int numberOfIslands = 0;
		Set<List<Integer>> visited = new HashSet<List<Integer>>();
		for (int i = 0; i < ocean.length; i++) {
			for (int j = 0; j < ocean[0].length; j++) {
				if (ocean[i][j] > 0 && !visited.contains(Arrays.asList(i, j))) {
					numberOfIslands++;
					Queue<List<Integer>> frontier = new LinkedList<List<Integer>>();
					frontier.add(Arrays.asList(i,j));
					while (!frontier.isEmpty()) {
						List<Integer> currPoint = frontier.poll();
						visited.add(currPoint);
						List<List<Integer>> neighbors = 
								findNeighbors(ocean, visited, currPoint);
						frontier.addAll(neighbors);
					}
				}
			}
		}
		return numberOfIslands;
	}

	static final List<Integer> coins = Arrays.asList(25,10,5,1);

	static int makeChangeNumWaysHelper(int n, int[][] dpMem, int currCoinIndex) {
		if (dpMem[n][currCoinIndex] != 0) {
			return dpMem[n][currCoinIndex];
		}
		int result = 0;
		for (int i = currCoinIndex; i < coins.size(); i++) {
			if (n >= coins.get(i)) {
				result += makeChangeNumWaysHelper(n - coins.get(i), dpMem, i);
			}
		}
		dpMem[n][currCoinIndex] = result;
		return result;
	}
	/**
	 * Problem: Given a certain dollar amount where the last two digits represent
	 * the cents, return the total number of ways to make that amount using standard
	 * common-found U.S coins.
	 * @param n
	 * @return total number of ways
	 */
	static int makeChangeNumWays(int n) {
		int[][] dpMem = new int[n + 1][coins.size() + 1];
		for (int i = 0; i < dpMem[0].length; i++) {
			dpMem[0][i] = 1;
		}
		return makeChangeNumWaysHelper(n, dpMem, 0);
	}

	static int makeChangeMinCoinsHelper(int n, int[][] dpMem, int currCoinIndex) {
		if (n == 0) {
			return 0;
		}
		if (dpMem[n][currCoinIndex] != 0) {
			return dpMem[n][currCoinIndex];
		}
		int currMin = Integer.MAX_VALUE - 1;
		for (int i = currCoinIndex; i < coins.size(); i++) {
			if (coins.get(i) <= n) {
				int currNumCoins = makeChangeMinCoinsHelper(n - coins.get(i), dpMem, i);
				if (currNumCoins <= currMin) {
					currMin = currNumCoins;
				}
			}
		}
		currMin++;
		dpMem[n][currCoinIndex] = currMin;
		return currMin;
	}

	/**
	 * Problem: Given a certain dollar amount where the last two digits represent
	 * the cents, return the mininum number of coins to make that value.
	 * @param n
	 * @return mininum number of coins to make the dollar amount
	 */
	static int makeChangeMinCoins(int n) {
		if (n == 0) {
			return 0;
		}
		if (n <= 4) {
			return 1;
		}
		int[][] dpMem = new int[n+1][coins.size() + 1];
		for (int i = 0; i < dpMem[0].length; i++) {
			dpMem[1][i] = 1;
		}
		return makeChangeMinCoinsHelper(n, dpMem, 0);
	}

	// Helper function for subsetSum
	static void subsetSumHelper(
			List<List<Integer>> allSubSets, 
			List<Integer> someSet, 
			List<Integer> currSet, 
			int target, 
			int currListIndex) 
	{
		if (target == 0) {
			allSubSets.add(currSet);
		}
		int i = currListIndex;
		while (i < someSet.size()) {
			List<Integer> newCurrSet = new ArrayList<>(currSet);
			newCurrSet.add(someSet.get(i));
			subsetSumHelper(allSubSets, someSet, newCurrSet, target - someSet.get(i), i + 1);
			i++;
		}
	}

	/**
	 * Problem: Given a target value and a list of values, return all subsets of the list
	 * that sum to that value.
	 * @param someSet
	 * @param target
	 * @return list of subsets
	 */
	static List<List<Integer>> subsetSum(List<Integer> someSet, int target) {
		List<List<Integer>> result = new ArrayList<>();
		subsetSumHelper(result, someSet, new ArrayList<>(), target, 0);
		return result;
	}
	
	// Class for node for singular Linked List
	static class Node {
		int val;
		Node next;
		
		public Node(int value) {
			this.val = value;
			this.next = null;
		}
		
		public void setNext(Node someNode) {
			this.next = someNode;
		}
	}
	
	// Function to print a Linked List in order
	static void printLinkedList(Node head) {
		String acc = "";
		Node curr = head;
		while (curr != null) {
			acc += curr.val + " ";
			curr = curr.next;
		}
		System.out.println(acc);
	}
	
	// Function to create a Linked List out of an array
	static Node createLinkedList(int[] values) {
		Node curr = new Node(values[0]);
		Node head = curr;
		for (int i = 1; i < values.length; i++) {
			Node temp = new Node(values[i]);
			curr.next = temp;
			curr = curr.next;
		}
		return head;
	}
	
	/**
	 * Problem: Reverse a Linked List in place and return the head
	 * @param head
	 * @return the new head
	 */
	static Node reverseLinkedList(Node head) {
		Node curr = null;
		Node next = head;
		while (next != null) {
			Node temp = next.next;
			next.next = curr;
			curr = next;
			next = temp;
		}
		return curr;
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

		// Test cases for numIslands
		System.out.println();
		int[][] oceanOne = {{1,0,3},{1,0,3},{1,0,3}};
		for (int i = 0 ; i < oceanOne.length; i++) {
			System.out.println(Arrays.toString(oceanOne[i]));
		}
		System.out.println(numIslands(oceanOne));
		int[][] oceanTwo = {{1,2,3},{1,2,3},{1,2,3}};
		for (int i = 0 ; i < oceanTwo.length; i++) {
			System.out.println(Arrays.toString(oceanTwo[i]));
		}
		System.out.println(numIslands(oceanTwo));
		int[][] oceanThree = {{1,0,1},{0,0,0},{1,0,1}};
		for (int i = 0 ; i < oceanThree.length; i++) {
			System.out.println(Arrays.toString(oceanThree[i]));
		}
		System.out.println(numIslands(oceanThree));
		int[][] oceanFour = {{0,0,0},{0,0,0},{0,0,0}};
		for (int i = 0 ; i < oceanFour.length; i++) {
			System.out.println(Arrays.toString(oceanFour[i]));
		}
		System.out.println(numIslands(oceanFour));

		// Test cases for makeChangeNumWays
		System.out.println();
		System.out.println(makeChangeNumWays(5));
		System.out.println(makeChangeNumWays(10));
		System.out.println(makeChangeNumWays(25));

		// Test cases for makeChangeMinCoins
		System.out.println();
		System.out.println(makeChangeMinCoins(42069));

		// Test cases for subsetSum
		System.out.println();
		System.out.println(subsetSum(Arrays.asList(1,2,3,4,-1), 2));
		
		// Test cases for basic Linked List stuff
		System.out.println();
		int[] values = {1,2,3,4,5,6,7,8};
		Node linkedList = createLinkedList(values);
		Node reversed = reverseLinkedList(linkedList);
		printLinkedList(reversed);
	}
}
