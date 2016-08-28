import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
