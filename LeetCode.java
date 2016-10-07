import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Stack;
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

	// Helper function for setZeroes
	public void setZeroesHelper(int[][] matrix, int x, int y, Set<List<Integer>> visited) {
		if (visited.contains(new ArrayList<Integer>(Arrays.asList(x,y)))) {
			return;
		}
		for (int i = 0; i < matrix.length; i++) {
			List<Integer> coor = new ArrayList<Integer>(Arrays.asList(i,y));
			if (visited.contains(coor)) {
				continue;
			}
			if (matrix[i][y] != 0) {
				visited.add(coor);
				matrix[i][y] = 0; 
			}
		}
		for (int j = 0; j < matrix[0].length; j++) {
			List<Integer> coor = new ArrayList<Integer>(Arrays.asList(x,j));
			if (visited.contains(coor)) {
				continue;
			}
			if (matrix[x][j] != 0) {
				visited.add(coor);
				matrix[x][j] = 0; 
			}
		}
	}

	/**
	 * Problem: Given a matrix and given an element is 0, set all elements
	 * in the same row and col as that element to 0 as well in place.
	 * @param matrix
	 * @return nothing, modify the matrix in place
	 * @url https://leetcode.com/problems/set-matrix-zeroes/
	 */
	public void setZeroes(int[][] matrix) {
		Set<List<Integer>> visited = new HashSet<>();
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				if (matrix[i][j] == 0) {
					setZeroesHelper(matrix, i, j, visited);
				}
			}
		}
	}

	/**
	 * Problem: Two sum
	 * @param nums
	 * @param target
	 * @return [index 1, index 2]
	 * @url https://leetcode.com/problems/two-sum/
	 */
	public int[] twoSum(int[] nums, int target) {
		Map<Integer, Integer> differenceMap = new HashMap<>();
		int[] result = new int[2];
		for (int i = 0; i < nums.length; i++) {
			if (differenceMap.containsKey(nums[i])) {
				result[0] = differenceMap.get(nums[i]);
				result[1] = i;
				break;
			} else {
				differenceMap.put(target - nums[i] , i);
			}
		}
		return result;
	}

	// Helper function for wordBreak
	public boolean wordBreakHelper(String s, Set<String> wordDict, Map<String, Boolean> dpMem) {
		if (dpMem.containsKey(s)) {
			return dpMem.get(s);
		}
		if (wordDict.contains(s)) {
			dpMem.put(s, true);
			return true;
		}
		if (s.length() == 1) {
			return wordDict.contains(s);
		}
		for (int i = 1; i <= s.length(); i++) {
			if (wordDict.contains(s.substring(0,i)) && wordBreakHelper(s.substring(i, s.length()), wordDict, dpMem)) {
				dpMem.put(s, true);
				return true;
			}
		}
		dpMem.put(s, false);
		return false;
	}

	/**
	 * Problem: Given a word and a dictionary return true if the word can be
	 * broken up into smaller words that are in the dictionary.
	 * @param s
	 * @param wordDict
	 * @return true if it can, false otherwise
	 * @url https://leetcode.com/problems/word-break/
	 */
	public boolean wordBreak(String s, Set<String> wordDict) {
		Map<String, Boolean> dpMem = new HashMap<>();
		return wordBreakHelper(s, wordDict, dpMem);
	}
	
	// Helper function for isInterleave
	public boolean isInterleaveHelper(String s1, String s2, String s3, int currIndex, Map<String, Map<String, Map<Integer,Boolean>>> dpMem) {
        if (dpMem.get(s1).get(s2).containsKey(currIndex)) {
            return dpMem.get(s1).get(s2).get(currIndex);
        }
        if (currIndex == s3.length() && s1.isEmpty() && s2.isEmpty()) {
            dpMem.get(s1).get(s2).put(currIndex, true);
            return true;
        } 
        if (s1.isEmpty()) {
            boolean initialCheck = s2.charAt(0) == s3.charAt(currIndex);
            if (initialCheck) {
                boolean recurCheck = isInterleaveHelper(s1, s2.substring(1,s2.length()), s3, currIndex + 1, dpMem);
                dpMem.get(s1).get(s2).put(currIndex, recurCheck);
                return recurCheck;
            } else {
                dpMem.get(s1).get(s2).put(currIndex, false);
                return false;
            }
        }
        if (s2.isEmpty()) {
            boolean initialCheck = s1.charAt(0) == s3.charAt(currIndex);
            if (initialCheck) {
                boolean recurCheck = isInterleaveHelper(s1.substring(1,s1.length()), s2, s3, currIndex + 1, dpMem);
                dpMem.get(s1).get(s2).put(currIndex, recurCheck);
                return recurCheck;
            } else {
                dpMem.get(s1).get(s2).put(currIndex, false);
                return false;
            }
        }
        if (s1.charAt(0) == s3.charAt(currIndex) && s2.charAt(0) == s3.charAt(currIndex)) {
            boolean recurCheckOne = isInterleaveHelper(s1.substring(1,s1.length()), s2, s3, currIndex + 1, dpMem);
            if (recurCheckOne) {
                dpMem.get(s1).get(s2).put(currIndex, true);
                return true;
            } else {
                boolean recurCheckTwo = isInterleaveHelper(s1, s2.substring(1,s2.length()), s3, currIndex + 1, dpMem);
                dpMem.get(s1).get(s2).put(currIndex, recurCheckTwo);
                return recurCheckTwo;
            }
        } else if (s1.charAt(0) == s3.charAt(currIndex)) {
            boolean recurCheckSOne = isInterleaveHelper(s1.substring(1,s1.length()), s2, s3, currIndex + 1, dpMem);
            dpMem.get(s1).get(s2).put(currIndex, recurCheckSOne);
            return recurCheckSOne;
        } else if (s2.charAt(0) == s3.charAt(currIndex)) {
            boolean recurCheckSTwo = isInterleaveHelper(s1, s2.substring(1,s2.length()), s3, currIndex + 1, dpMem);
            dpMem.get(s1).get(s2).put(currIndex, recurCheckSTwo);
            return recurCheckSTwo;
        } else {
            dpMem.get(s1).get(s2).put(currIndex, false);
            return false;
        }
    }
    
	/**
	 * Problem: Given three strings return true if s3 is an interleaving of s1 and s2, false otherwise.
	 * @param s1
	 * @param s2
	 * @param s3
	 * @return true if condition is true, false otherwise.
	 * @url https://leetcode.com/problems/interleaving-string/
	 */
    public boolean isInterleave(String s1, String s2, String s3) {
        if (s3.length() != s1.length() + s2.length()) {
            return false;
        }
        Map<String, Map<String, Map<Integer,Boolean>>> dpMem = new HashMap<>();
        for (int i = 0; i <= s1.length(); i++) {
            dpMem.put(s1.substring(i,s1.length()), new HashMap<>());
        }
        for (String key : dpMem.keySet()) {
            for (int j = 0; j <= s2.length(); j++) {
                dpMem.get(key).put(s2.substring(j,s2.length()), new HashMap<>());
            }
        }
        return isInterleaveHelper(s1,s2,s3,0,dpMem);
    }
    
    // Class for a node in a Trie
    class TrieNode {
        Boolean val;
        Map<Character, TrieNode> children;
        
        public TrieNode() {
            this.val = null;
            this.children = new HashMap<>();
        }
            
        public TrieNode(Boolean val) {
            this.val = val;
            this.children = new HashMap<>();
        }
        
    }

    /**
     * Problem: Implement a trie
     * @author George
     * @url https://leetcode.com/problems/implement-trie-prefix-tree/
     */
    public class Trie {
        private TrieNode root;

        public Trie() {
            root = new TrieNode();
        }
        
        public void insertHelper(String word, TrieNode currNode, int currIndex) {
            if (currIndex >= word.length()) {
                currNode.val = true;
                return;
            }
            if (currNode.children.containsKey(word.charAt(currIndex))) {
                insertHelper(word, currNode.children.get(word.charAt(currIndex)), currIndex + 1);
            } else {
                TrieNode newNode = new TrieNode(false);
                currNode.children.put(word.charAt(currIndex), newNode);
                insertHelper(word, newNode, currIndex + 1);
            }
        }

        // Inserts a word into the trie.
        public void insert(String word) {
            insertHelper(word, root, 0);
        }
        
        public boolean searchHelper(String word, TrieNode currNode, int currIndex) {
            if (currIndex >= word.length()) {
                return currNode.val;
            }
            if (currNode.children.containsKey(word.charAt(currIndex))) {
                return searchHelper(word, currNode.children.get(word.charAt(currIndex)), currIndex + 1);
            } else {
                return false;
            }
        }

        // Returns if the word is in the trie.
        public boolean search(String word) {
            return searchHelper(word, root, 0);
        }
        
        public boolean startsWithHelper(String word, TrieNode currNode, int currIndex) {
            if (currIndex >= word.length()) {
                return true;
            }
            if (currNode.children.containsKey(word.charAt(currIndex))) {
                return startsWithHelper(word, currNode.children.get(word.charAt(currIndex)), currIndex + 1);
            } else {
                return false;
            }
        }

        // Returns if there is any word in the trie
        // that starts with the given prefix.
        public boolean startsWith(String prefix) {
            return startsWithHelper(prefix, root, 0);
        }
    }
    
    // Class to represent an updated state for gameOfLife
    class updatedCoor {
        int state;
        int x;
        int y;
        public updatedCoor(int state, int x, int y) {
            this.state = state;
            this.x = x;
            this.y = y;
        }
    }
    
    // Helper function for gameOfLife
    public updatedCoor getUpdate(int[][] board, int xCor, int yCor) {
        int numLiveNeighbors = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                int xCorD = xCor + i;
                int yCorD = yCor + j;
                if (xCorD >= 0 && 
                    xCorD < board.length && 
                    yCorD >= 0 && 
                    yCorD < board[0].length) {
                    if (board[xCorD][yCorD] == 1) {
                        numLiveNeighbors++;   
                    }
                }
            }
        }
        int updatedState = 0;
        if (board[xCor][yCor] == 1) {
            if (numLiveNeighbors == 2 || numLiveNeighbors == 3) {
                updatedState = 1;
            }
        } else {
            if (numLiveNeighbors == 3) {
                updatedState = 1;
            }
        }
        return new updatedCoor(updatedState, xCor, yCor);
    }
    
    /**
     * Problem: Write an updated board for Game of Life
     * @param board
     * @return nothing, update the board
     * @url https://leetcode.com/problems/game-of-life/
     */
    public void gameOfLife(int[][] board) {
        List<updatedCoor> updatedCoors = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                updatedCoors.add(getUpdate(board, i, j));
            }
        }
        for (updatedCoor coor : updatedCoors) {
            board[coor.x][coor.y] = coor.state;
        }
    }
    
    // Set of arithmetic operators
    public final Set<String> operators = new HashSet<>(Arrays.asList("+", "-", "/", "*"));

    /**
     * Problem: Evaluate Reverse Polish Notation
     * @param tokens
     * @return resulting integer
     * @url https://leetcode.com/problems/evaluate-reverse-polish-notation/
     */
    public int evalRPN(String[] tokens) {
        Stack<Integer> operands = new Stack<>();
        for (int i = 0; i < tokens.length; i++) {
            if (operators.contains(tokens[i])) {
                if (operands.size() < 2) {
                    return -1;
                }
                int b = operands.pop();
                int a = operands.pop();
                if (tokens[i].equals("+")) {
                    operands.push(a + b);
                }
                if (tokens[i].equals("-")) {
                    operands.push(a - b);
                }
                if (tokens[i].equals("/")) {
                    operands.push(a / b);
                }
                if (tokens[i].equals("*")) {
                    operands.push(a * b);
                }
            } else {
                operands.push(Integer.parseInt(tokens[i]));
            }
        }
        if (operands.isEmpty()) {
            return -1;
        }
        return operands.pop();
    }
    
    /**
     * Check if two strings are anagrams, no memory
     * @param s
     * @param t
     * @return true if they are anagrams, false otherwise
     * @url https://leetcode.com/problems/valid-anagram/
     */
    public boolean isAnagram(String s, String t) {
        char[] sSorted = s.toCharArray();
        char[] tSorted = t.toCharArray();
        Arrays.sort(sSorted);
        Arrays.sort(tSorted);
        return Arrays.equals(sSorted, tSorted);
    }
    
    // Helper function for maxDepth
    public int maxDepthHelper(TreeNode root, int currDepth) {
        if (root == null) {
            return currDepth;
        }
        return Math.max(maxDepthHelper(root.left, currDepth + 1), maxDepthHelper(root.right, currDepth+1));
    }
    
    /**
     * Problem: Find maximum depth in a binary tree
     * @param root
     * @return the max depth
     * @url https://leetcode.com/problems/maximum-depth-of-binary-tree/
     */
    public int maxDepth(TreeNode root) {
        return maxDepthHelper(root, 0);
    }
    
    /**
     * Problem: Given an array with possible duplicates return a random index of a target number.
     * @param arr
     * @param target
     * @return random index of target in arr
     * @url https://leetcode.com/problems/random-pick-index/
     */
    public int pick(int[] arr, int target) {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                if (Math.random() < 0.5) {
                    return i;
                } else {
                    indices.add(i);
                }
            }
        }
        return indices.get((int)(Math.random() * indices.size()));
    }
    
    /**
     * Problem: Powerset
     * @param nums
     * @return powerset of nums
     * @url https://leetcode.com/problems/subsets/
     */
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums.length == 0) {
            result.add(new ArrayList<>());
            return result;
        }
        List<List<Integer>> recurRes = subsets(Arrays.copyOfRange(nums,1,nums.length));
        for (List<Integer> subset : recurRes) {
            List<Integer> subsetAdded = new ArrayList<>(subset);
            subsetAdded.add(nums[0]);
            result.add(subset);
            result.add(subsetAdded);
        }
        return result;
    }
    
    /**
     * Problem: Given a number num keep adding the digits until there is only one digit left over.
     * @param num
     * @return new number
     * @url https://leetcode.com/problems/add-digits/
     */
    public int addDigits(int num) {
        if (num < 10) {
            return num;
        }
        String numS = Integer.toString(num);
        int sumDigits = 0;
        for (int i = 0; i < numS.length(); i++) {
            sumDigits += (int)(numS.charAt(i) - '0');
        }
        return addDigits(sumDigits);
    }
    
    // Helper function for topKFrequent
    Map<Integer, Integer> initNumToCount(int[] nums) {
        Map<Integer, Integer> initMap = new HashMap<>();
        for (int num : nums) {
            if (initMap.containsKey(num)) {
                continue;
            }
            initMap.put(num, 0);
        }
        return initMap;
    }
    
    // Helper function for topKFrequent
    Map<Integer, Set<Integer>> initCountToNums (int size) {
        Map<Integer, Set<Integer>> initMap = new HashMap<>();
        for (int i = 1; i <= size; i++) {
            initMap.put(i, new HashSet<>());
        }
        return initMap;
    }
    
    /**
     * Problem: Find the k most frequent numbers in an array.
     * @param nums
     * @param k
     * @return the k most frequent numbers in a list
     * @url https://leetcode.com/problems/top-k-frequent-elements/
     */
    public List<Integer> topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> numToCount = initNumToCount(nums);
        Map<Integer, Set<Integer>> countToNums = initCountToNums(nums.length);
        for (int i = 0; i < nums.length; i++) {
            int currCount = numToCount.get(nums[i]);
            if (currCount > 0) {
                countToNums.get(currCount).remove(nums[i]);
            }
            countToNums.get(currCount + 1).add(nums[i]);
            numToCount.put(nums[i],currCount+1);
        }
        List<Integer> result = new ArrayList<>();
        outer:
        for (int j = nums.length; j >= 1; j--) {
            for (Integer num : countToNums.get(j)) {
                if (k == 0) {
                    break outer;
                }
                result.add(num);
                k--;
            }
        }
        return result;
    }
    
    // Coordinate class for triangle min path question.
    class coor {
        int x;
        int y;
        public coor (int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        @Override
        public boolean equals(Object obj) {
             return Objects.equals(((coor)obj).x, x)
                && Objects.equals(((coor)obj).y, y);
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
    
    // Helper function for minimumTotal
    int findMinPathDFS(List<List<Integer>> triangle, Map<coor, Set<coor>> graph, coor node, int[][] dpMem) {
        if (dpMem[node.x][node.y] != 0) {
            return dpMem[node.x][node.y];
        }
        if (!graph.containsKey(node)) {
            return triangle.get(node.x).get(node.y);
        }
        int min = Integer.MAX_VALUE - 1;
        for (coor neighbor : graph.get(node)) {
            min = Math.min(min, triangle.get(node.x).get(node.y) + findMinPathDFS(triangle,graph,neighbor,dpMem));
        }
        dpMem[node.x][node.y] = min;
        return min;
    }
    
    /**
     * Given a triangle, find the minimum sum path from tip to base.
     * @param triangle
     * @return the sum of the minimum sum path.
     * @url https://leetcode.com/problems/triangle/
     */
    public int minimumTotal(List<List<Integer>> triangle) {
        if (triangle.size() == 1) {
            return triangle.get(0).get(0);
        }
        Map<coor, Set<coor>> triangleGraph = new HashMap<>();
        for (int i = 0; i < triangle.size() - 1; i++) {
            List<Integer> currRow = triangle.get(i);
            for (int j = 0; j < currRow.size(); j++) {
                coor currNode = new coor(i,j);
                triangleGraph.put(currNode, new HashSet<>());
                for (int k = j; k < j + 2; k++) {
                    coor nextNode = new coor(i+1,k);
                    triangleGraph.get(currNode).add(nextNode);
                }
            }
        }
        int[][] dpMem = new int[triangle.size()+1][triangle.size()+1];
        return findMinPathDFS(triangle, triangleGraph, new coor(0,0), dpMem);
    }
    
    /**
     * Problem: Return the index of needle in haystack
     * @param haystack
     * @param needle
     * @return the index
     * @url https://leetcode.com/problems/implement-strstr/
     */
    public int strStr(String haystack, String needle) {
        return haystack.indexOf(needle);
    }
    
    // Helper function for numDecodings
    public int numDecodingsHelper(String s, int currIndex, int[] dpMem) {
        if (dpMem[currIndex] != 0) {
            return dpMem[currIndex];
        }
        if (currIndex == s.length()) {
            return 1;
        }
        int result = 0;
        if (s.charAt(currIndex) != '0') {
            result += numDecodingsHelper(s, currIndex + 1, dpMem);
        }
        if (currIndex <= s.length() - 2 && 
            Integer.parseInt(s.substring(currIndex,currIndex+2)) <= 26 && 
            Integer.parseInt(s.substring(currIndex,currIndex+2)) > 9) {
            result += numDecodingsHelper(s, currIndex + 2, dpMem);
        }
        dpMem[currIndex] = result;
        return result;
    }
    
    /**
     * Given that a number can map to a letter, 1->A, 26->Z, given a sequence of numbers return the number of ways to decode it into letters
     * @param s
     * @return number of ways
     * @url https://leetcode.com/problems/decode-ways/
     */
    public int numDecodings(String s) {
        if (s.isEmpty()) {
            return 0;
        }
        int[] dpMem = new int[s.length() + 1];
        return numDecodingsHelper(s, 0, dpMem);
    }
    
    /**
     * Definition for singly-linked list.
     * public class ListNode {
     *     int val;
     *     ListNode next;
     *     ListNode(int x) { val = x; }
     * }
     */
    /**
     * Problem: Given a pointer to a node in a singly linked list, delete it.
     * @param node
     * @url https://leetcode.com/problems/delete-node-in-a-linked-list/
     */
    public void deleteNode(ListNode node) {
        node.val = node.next.val;
        node.next = node.next.next;
    }
    
    // Helper function for levelOrderBottom
    void levelOrderBottomHelper(TreeNode node, int currDepth, Map<Integer, List<Integer>> depthMap) {
        if (!depthMap.containsKey(currDepth)) {
            depthMap.put(currDepth, new ArrayList<>());
        }
        depthMap.get(currDepth).add(node.val);
        if (node.left != null) {
            levelOrderBottomHelper(node.left, currDepth+1, depthMap);
        }
        if (node.right != null) {
            levelOrderBottomHelper(node.right, currDepth+1, depthMap);
        }
    }
    
    /**
     * Problem: Return the bottom up left to right representation of a tree.
     * @param root
     * @return list of lists ordered from bottom to up inside each list from left to right.
     * @url https://leetcode.com/problems/binary-tree-level-order-traversal-ii/
     */
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Map<Integer, List<Integer>> depthMap = new HashMap<>();
        levelOrderBottomHelper(root, 0, depthMap);
        List<Integer> depths = new ArrayList<>(depthMap.keySet());
        Collections.sort(depths);
        Collections.reverse(depths);
        for (Integer depth: depths) {
            result.add(depthMap.get(depth));
        }
        return result;
    }
    
    // Helper function for isPalindrome that reverses a singly linked list.
    ListNode reverseLinkedList(ListNode head) {
        ListNode curr = null;
        ListNode currNode = head;
        while (currNode != null) {
            ListNode temp = currNode.next;
            currNode.next = curr;
            curr = currNode;
            currNode = temp;
        }
        return curr;
    }
    
    /**
     * Problem: Given a singly linked list, check if it is a palindrome in O(n) time and O(1) memory.
     * @param head
     * @return true if is palindrome, false otherwise.
     * @url https://leetcode.com/problems/palindrome-linked-list/
     */
    public boolean isPalindrome(ListNode head) {
        int size = 0;
        ListNode curr = head;
        while (curr != null) {
            size++;
            curr = curr.next;
        }
        int mid = size / 2;
        ListNode right = head;
        while (mid > 0) {
            right = right.next;
            mid--;
        }
        if (size % 2 != 0) {
            right = right.next;
        }
        right = reverseLinkedList(right);
        int halfSize = size / 2;
        boolean ans = true;
        while (halfSize > 0) {
            ans = ans && head.val == right.val;
            head = head.next;
            right = right.next;
            halfSize--;
        }
        return ans;
    }
    
    /**
     * Problem: Given an array, write a function to able to return range sum queries from it. The array is immutable.
     * @author George
     * @url https://leetcode.com/problems/range-sum-query-immutable/
     */
    public class NumArray {

        int[] nums;
        Map<Integer, Integer> memory;
        
        public NumArray(int[] nums) {
            this.nums = nums;
            this.memory = new HashMap<>();
            int res = 0;
            for (int i = 0; i < nums.length; i++) {
                res += nums[i];
                memory.put(i,res);
            }
        }

        public int sumRange(int i, int j) {
            if (i == 0) {
                return memory.get(j);
            }
            int left = i-1;
            int right = j;
            return memory.get(right) - memory.get(left);
        }
    }

	public static void main(String[] args) {
		/**
		 * LeetCode handles all of the testing for me. So no
		 * final functions will be tested here. Only helper functions
		 * will be tested.
		 */
	}
}
