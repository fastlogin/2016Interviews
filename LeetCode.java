import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Questions acquired from LeetCode (https://leetcode.com/)
 */
public class LeetCode {

	// Class for node of a tree
	public class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		TreeNode(int x) { val = x; }
	}

	/**
	 * Problem: Given the root node of a tree return the postorder traversal.
	 * @param root
	 * @return List of ordered in postorder traversal of root.
	 * @url https://leetcode.com/problems/binary-tree-postorder-traversal/
	 */
	public List<Integer> postorderTraversal(TreeNode root) {
		if (root == null) {
			return new ArrayList<Integer>();
		}
		List<Integer> postOrder = new ArrayList<Integer>();
		postOrder.addAll(postorderTraversal(root.left));
		postOrder.addAll(postorderTraversal(root.right));
		postOrder.add(root.val);
		return postOrder;
	}

	// Helper function to return string version of Gray Code.
	public List<String> grayCodeBaseTwo(int n) {
		List<String> grayCode = new ArrayList<String>();
		if (n == 0) {
			grayCode.add("0");
			return grayCode;
		}
		if (n == 1) {
			grayCode.add("0");
			grayCode.add("1");
			return grayCode;
		}
		List<String> grayCodeMinusOne = grayCodeBaseTwo(n-1);

		// First Half
		for (String code : grayCodeMinusOne) {
			grayCode.add(code + "1");
		}

		// Second Half
		for (int i = grayCodeMinusOne.size() - 1; i >= 0; i--) {
			grayCode.add(grayCodeMinusOne.get(i) + "0");
		}

		return grayCode;
	}

	/**
	 * Problem: Return a gray code of for size n.
	 * @param n
	 * @return Gray Code represented by the decimal value of each Gray Code
	 * binary in a list where the order of the list is associated with the
	 * bit change of the Gray Code.
	 * @url https://leetcode.com/problems/gray-code/
	 */
	public List<Integer> grayCode(int n) {
		List<String> grayCodeBinary = grayCodeBaseTwo(n);
		return grayCodeBinary
				.stream()
				.map(p -> Integer.parseInt(p, 2))
				.collect(Collectors.toList());
	}

	// Class for node of a singly-linked-list
	public class ListNode {
		int val;
		ListNode next;
		ListNode(int x) { val = x; }
	}

	/**
	 * Problem: Given a linked list by its head and a value x, partition the linked
	 * list such that all values less than x are in a left half and all values greater
	 * than x are in a right half. The order relative to the original list of each half
	 * should be preserved.
	 * @param head
	 * @param x
	 * @return Head of partitioned linked list
	 * @url https://leetcode.com/problems/partition-list/
	 */
	public ListNode partition(ListNode head, int x) {
		if (head == null || head.next == null) {
			return head;
		}
		ListNode left = new ListNode(69);
		ListNode right = new ListNode(420);
		ListNode firstNodeLeft = left;
		ListNode firstNodeRight = right;
		while (head != null) {
			if (head.val < x) {
				left.next = new ListNode(head.val);
				left = left.next;
			} else {
				right.next = new ListNode(head.val);
				right = right.next;
			}
			head = head.next;
		}
		left.next = firstNodeRight.next;
		return firstNodeLeft.next;
	}

	/**
	 * Given a list of edges representing courses and their prereqs and a value numCourses. 
	 * Return a sequence of courses that you can take that matches the number of numCourses.
	 * @param numCourses
	 * @param prerequisites
	 * @return List of courses taken in that order.
	 * @url https://leetcode.com/problems/course-schedule-ii/
	 */
	// TODO: Do This problem.
	public int[] findOrder(int numCourses, int[][] prerequisites) {
		HashMap<Integer, Set<Integer>> graph = new HashMap<Integer, Set<Integer>>();
		for (int[] prereq : prerequisites) {
			if (graph.containsKey(prereq[1])) {
				graph.get(prereq[1]).add(prereq[0]);
			} else {
				Set<Integer> prereqEdges = new HashSet<Integer>();
				graph.put(prereq[1], prereqEdges);
			}
		}
		return new int[0];
	}

	// Recursive binary search implementation
	static int binarySearch(int[] sortedArr, int n, int start, int end) {
		if (sortedArr.length == 1) {
			if (sortedArr[0] == n) {
				return 0;
			}
		}
		if (start > end) {
			return -1;
		}
		if (start >= sortedArr.length) {
			return -1;
		}
		if (start == end) {
			if (sortedArr[start] == n) {
				return start;
			} else {
				return -1;
			}
		}
		int mid = (start + end) / 2;
		if (sortedArr[mid] == n) {
			return mid;
		} else if (sortedArr[mid] > n) {
			return binarySearch(sortedArr, n, start, mid);
		} else {
			return binarySearch(sortedArr, n, mid + 1, end);
		}
	}

	/**
	 * Given a sorted array find the starting and ending interval of
	 * a given target value.
	 * @param nums
	 * @param target
	 * @return [i, j] where i is the starting interval and j is the ending
	 * interval.
	 * @url https://leetcode.com/problems/search-for-a-range/
	 */
	static int[] searchRange(int[] nums, int target) {
		int foundIndex = binarySearch(nums, target, 0, nums.length);
		int i = foundIndex;
		int j = foundIndex;
		if (foundIndex == -1) {
			return new int[] {-1, -1};
		}
		boolean foundLeft = false;
		boolean foundRight = false;
		while (i >= -1 || j < nums.length + 1) {
			if (foundLeft && foundRight) {
				break;
			}
			if (i < 0 || nums[i] != target) {
				i++;
				foundLeft = true;
			}
			if (j >= nums.length || nums[j] != target) {
				j--;
				foundRight = true;
			}
			if (nums[i] == target && !foundLeft) {
				i--;
			}
			if (nums[j] == target && !foundRight) {
				j++;
			}
		}
		return new int[] {i, j};
	}

	// Helper function for restoreIpAddresses
	public static List<String> restoreIpRecurHelper(String s) {
		if (s.length() == 0) {
			return new ArrayList<String>();
		}
		if (s.length() == 1) {
			return Arrays.asList(new String[] {s});
		}
		int i = 1;
		List<String> result = new ArrayList<String>();
		while (i <= 3) {
			if (i <= s.length()) {
				String ipSection = s.substring(0, i);
				if (i > 1) {
					if (ipSection.charAt(0) == '0') {
						i++;
						continue;
					}
				}
				if (i == 3) {
					if (Integer.parseInt(ipSection) > 255) {
						break;
					}
				}
				if (i == s.length()) {
					result.add(ipSection);
				} else {
					List<String> allOtherSections = restoreIpRecurHelper(s.substring(i, s.length()));
					for (String section : allOtherSections) {
						result.add(ipSection + "." + section);
					}
				}
			}
			i++;
		}
		return result;
	}

	/**
	 * Problem: Given a string of digits return all of the valid ip address combinations
	 * @param digits
	 * @return list of all IP addressed
	 * @url https://leetcode.com/problems/restore-ip-addresses/
	 */
	public static List<String> restoreIpAddresses(String s) {
		List<String> allIpAddresses = restoreIpRecurHelper(s);
		return allIpAddresses
				.stream()
				.filter(p -> {
					int count = 0;
					for (int i = 0; i < p.length(); i++) {
						if (p.charAt(i) == '.') {
							count++;
						}
					}
					return count == 3;
				})
				.collect(Collectors.toList());
	}

	/**
	 * Problem: Given a string of words, reverse the words in the String.
	 * @param s
	 * @return String with words reversed and space separated.
	 * @url https://leetcode.com/problems/reverse-words-in-a-string/
	 */
	public String reverseWords(String s) {
		StringBuilder acc = new StringBuilder();
		int i = s.length() - 1;
		while (i >= 0) {
			if (s.charAt(i) != ' ') {
				int j = i - 1;
				while(j >= 0 && s.charAt(j) != ' ') {
					j--;
				}
				if (j < 0) {
					j++;
				}
				if (s.charAt(j) == ' ') {
					j++;
				}
				acc.append(s.substring(j,i+1));
				acc.append(" ");
				i = j;
			}
			i--;
		}
		return acc.toString().trim();
	}

	// Helper function for integerBreak
	public int integerBreakHelper(int n, int[] dpMem) {
		if (dpMem[n] != 0) {
			return dpMem[n];
		}
		int max = 0;
		for (int i = 1; i < n; i++) {
			int recurBreak = i * integerBreakHelper(n - i, dpMem);
			int recurDontBreak = i * (n - i);
			int currMax = Math.max(recurBreak, recurDontBreak);
			if (currMax > max) {
				max = currMax;
			}
		}
		dpMem[n] = max;
		return max;
	}

	/**
	 * Problem: Given an integer, break it up to the sum of smaller integers
	 * where the product of these integers is maximized across all possibilities.
	 * @param n
	 * @return the max product
	 * @url https://leetcode.com/problems/integer-break/
	 */
	public int integerBreak(int n) {
		int[] dpMem = new int[n+1];
		dpMem[0] = 1;
		dpMem[1] = 1;
		return integerBreakHelper(n, dpMem);
	}

	// Helper function for sumNumbers
	public void sumNumbersHelper(int currNum, TreeNode root, List<Integer> numbersToAdd) {
		if (root.left == null && root.right == null) {
			numbersToAdd.add(currNum * 10 + root.val);
			return;
		}
		if (root.left != null) {
			sumNumbersHelper(currNum * 10 + root.val, root.left, numbersToAdd);
		}
		if (root.right != null) {
			sumNumbersHelper(currNum * 10 + root.val, root.right, numbersToAdd);
		}
	}

	/**
	 * Problem: Given a tree, return the sum of all the numbers represented by
	 * root to leaf paths.
	 * @param root
	 * @return the sum of all the numbers represented by paths.
	 * @url https://leetcode.com/problems/sum-root-to-leaf-numbers/
	 */
	public int sumNumbers(TreeNode root) {
		if (root == null) {
			return 0;
		}
		if (root.left == null && root.right == null) {
			return root.val;
		}
		List<Integer> numsToAdd = new ArrayList<>();
		sumNumbersHelper(0, root, numsToAdd);
		int result = 0;
		for (Integer num : numsToAdd) {
			result += num;
		}
		return result;
	}

	/**
	 * Problem: Given a ransome note and a magazine return true if the ransome note
	 * can be constructed with the letters found in the magazine.
	 * @param ransomNote
	 * @param magazine
	 * @return true if the ransome note can be constructed, false otherwise.
	 * @url https://leetcode.com/problems/ransom-note/
	 */
	public boolean canConstruct(String ransomNote, String magazine) {
		if (ransomNote.length() > magazine.length()) {
			return false;
		}
		Map<Character, Integer> charCountMapMagazine = new HashMap<>();
		for (int i = 0; i < magazine.length(); i++) {
			if (!charCountMapMagazine.containsKey(magazine.charAt(i))) {
				charCountMapMagazine.put(magazine.charAt(i), 1);
			} else {
				charCountMapMagazine.put(magazine.charAt(i), charCountMapMagazine.get(magazine.charAt(i)) + 1);
			}

		}
		for (int j = 0; j < ransomNote.length(); j++) {
			if (!charCountMapMagazine.containsKey(ransomNote.charAt(j))) {
				return false;
			} else if (charCountMapMagazine.get(ransomNote.charAt(j)) == 0) {
				return false;
			} else {
				charCountMapMagazine.put(ransomNote.charAt(j), charCountMapMagazine.get(ransomNote.charAt(j)) - 1);
			}
		}
		return true;
	}

	public static void main(String[] args) {
		/**
		 * LeetCode handles all of the testing for me. So no
		 * final functions will be tested here. Only helper functions
		 * will be tested.
		 */

		// Test Case for Binary Search
		int[] binarySearchTest = new int[] { 0, 2, 4, 6, 8, 10, 12, 14, 16};
		System.out.println(binarySearch(binarySearchTest, 0, 0, binarySearchTest.length));
		System.out.println(binarySearch(binarySearchTest, 2, 0, binarySearchTest.length));
		System.out.println(binarySearch(binarySearchTest, 4, 0, binarySearchTest.length));
		System.out.println(binarySearch(binarySearchTest, 6, 0, binarySearchTest.length));
		System.out.println(binarySearch(binarySearchTest, 8, 0, binarySearchTest.length));
		System.out.println(binarySearch(binarySearchTest, 10, 0, binarySearchTest.length));
		System.out.println(binarySearch(binarySearchTest, 12, 0, binarySearchTest.length));
		System.out.println(binarySearch(binarySearchTest, 14, 0, binarySearchTest.length));
		System.out.println(binarySearch(binarySearchTest, 16, 0, binarySearchTest.length));
		System.out.println(binarySearch(binarySearchTest, 9, 0, binarySearchTest.length));
	}
}
