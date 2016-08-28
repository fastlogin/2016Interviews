import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Questions acquired from Elements of Programming Interviews 
 * (https://www.amazon.com/Elements-Programming-Interviews-Insiders-Guide/dp/1479274836)
 */
public class EPI {

	/**
	 * Problem: Given an array of stocks find the max profit of a single buy and sell
	 * the array is ordered in prices of succeeding days.
	 * @param stocks
	 * @return max profit
	 */
	int findMaxProfit(int[] stocks) {
		int currMin = stocks[0];
		int currMaxProfit = -1;
		for (int stock : stocks) {
			if (stock - currMin > currMaxProfit) {
				currMaxProfit = stock - currMin;
			}
			if (stock < currMin) {
				currMin = stock;
			}
		}
		return currMaxProfit;
	}

	/**
	 * Problem: Reverse the digits of a number.
	 * @param n
	 * @return reversed n
	 */
	static int reverseDigits(int n) {
		if (Math.abs(n) < 10) {
			return n;
		}
		boolean isNeg = false;
		if (n < 0) {
			n = n * -1;
			isNeg = true;
		}
		int mod = 1;
		List<Integer> digits = new ArrayList<Integer>();
		int currNum = n;
		while (currNum > 0) {
			digits.add(currNum % 10);
			currNum = currNum / 10;
			mod = mod * 10;
		}
		int result = 0;
		mod = mod / 10;
		for (Integer digit : digits) {
			result += digit * mod;
			mod = mod / 10;
		}
		if (isNeg) {
			return result * -1;
		} else {
			return result;
		}
	}


	public static void main(String[] args) {
		// Test case for reverseDigits
		System.out.println(reverseDigits(-34879234));
	}
}
