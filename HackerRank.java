import java.util.Arrays;

/**
 * Questions acquired from HackerRank (https://www.hackerrank.com/)
 */
public class HackerRank {

	/**
	 * Problem: Find the nth value in an array circularly rotated numRotations times.
	 * @param arr
	 * @param numRotations
	 * @param n
	 * @return the value
	 * @url https://www.hackerrank.com/contests/master/challenges/circular-array-rotation?h_r=internal-search
	 */
	static Integer nthValueCircRotation(int[] arr, int numRotations, int n) {
		int shiftedAmount = numRotations % arr.length;
		if (n > arr.length) {
			return null;
		}
		if (n > shiftedAmount) {
			return arr[n - shiftedAmount - 1];
		} else {
			return arr[arr.length - 1 - shiftedAmount + n];
		}
	}

	/**
	 * Problem: Find a grid rectangular pattern in a bigger matrix
	 * @param bigMatrix
	 * @param smallMatrix
	 * @return True if pattern exists, False otherwise
	 * @url https://www.hackerrank.com/challenges/the-grid-search
	 */
	static boolean findMatrixPattern(int[][] bigMatrix, int[][] smallMatrix) {
		int bigLen = bigMatrix.length;
		int bigWid = bigMatrix[0].length;
		int smallLen = smallMatrix.length;
		int smallWid = smallMatrix[0].length;
		if (smallLen > bigLen || smallWid > bigWid) {
			return false;
		}

		int i = 0;
		while (i + smallLen - 1 < bigLen) {
			int j = smallWid;	
			while (j - 1 < bigWid) {
				int k = i;
				while (k - i < smallLen) {
					if (!Arrays.equals(Arrays.copyOfRange(bigMatrix[k], j - smallWid, j),(smallMatrix[k]))) {	
						break;
					}
					k++;
				}
				if (k == smallLen) {
					return true;
				}
				j++;
			}
			i++;
		}
		return false;
	}

	public static void main(String[]  args) {
		int[] testFiveArray = new int[5];
		testFiveArray[0] = 1;
		testFiveArray[1] = 2;
		testFiveArray[2] = 3;
		testFiveArray[3] = 4;
		testFiveArray[4] = 5;

		int[] testThreeArray = new int[3];
		testThreeArray[0] = 1;
		testThreeArray[1] = 2;
		testThreeArray[2] = 3;

		System.out.println(nthValueCircRotation(testFiveArray, 2, 3));
		System.out.println(nthValueCircRotation(testFiveArray, 2, 1));
		System.out.println(nthValueCircRotation(testFiveArray, 2, 2));

		int[][] matrixBigGridTest = new int[5][5];
		matrixBigGridTest[0] = testFiveArray;
		matrixBigGridTest[1] = testFiveArray;
		matrixBigGridTest[2] = testFiveArray;
		matrixBigGridTest[3] = testFiveArray;
		matrixBigGridTest[4] = testFiveArray;

		int[][] matrixSmallGridTest = new int[3][3];
		matrixSmallGridTest[0] = testThreeArray;
		matrixSmallGridTest[1] = testThreeArray;
		matrixSmallGridTest[2] = testThreeArray;

		System.out.println(findMatrixPattern(matrixBigGridTest, matrixSmallGridTest));
	}
}
