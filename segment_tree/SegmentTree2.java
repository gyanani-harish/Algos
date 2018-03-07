package segment_tree;

public class SegmentTree2 {
	// Java Program to show segment tree operations like construction,
	// query and update
	int st[]; // The array that stores segment tree nodes
	private int n;
	private int mHeight;

	/*
	 * Constructor to construct segment tree from given array. This constructor
	 * allocates memory for segment tree and calls constructSTUtil() to fill the
	 * allocated memory
	 */
	SegmentTree2(int arr[]) {
		n = arr.length;
		// Allocate memory for segment tree
		// Height of segment tree
		mHeight = (int) (Math.ceil(Math.log(n) / Math.log(2)));

		// Maximum size of segment tree
		int max_size = 2 * (int) Math.pow(2, mHeight) - 1;

		st = new int[max_size]; // Memory allocation

		constructSTUtil(arr, 0, n - 1, 0);
	}

	// A utility function to get the middle index from corner indexes.
	int getMid(int s, int e) {
		return s + (e - s) / 2;
	}

	/*
	 * A recursive function to get the sum of values in given range of the array.
	 * The following are parameters for this function.
	 * 
	 * st --> Pointer to segment tree si --> Index of current node in the segment
	 * tree. Initially 0 is passed as root is always at index 0 ss & se --> Starting
	 * and ending indexes of the segment represented by current node, i.e., st[si]
	 * qs & qe --> Starting and ending indexes of query range
	 */
	int getSumUtil(int ss, int se, int qs, int qe, int si) {
		// If segment of this node is a part of given range, then return
		// the sum of the segment
		if (qs <= ss && qe >= se)
			return st[si];

		// If segment of this node is outside the given range
		if (se < qs || ss > qe)
			return 0;

		// If a part of this segment overlaps with the given range
		int mid = getMid(ss, se);
		return getSumUtil(ss, mid, qs, qe, 2 * si + 1) + getSumUtil(mid + 1, se, qs, qe, 2 * si + 2);
	}

	/*
	 * A recursive function to update the nodes which have the given index in their
	 * range. The following are parameters st, si, ss and se are same as
	 * getSumUtil() i --> index of the element to be updated. This index is in input
	 * array. diff --> Value to be added to all nodes which have i in range
	 */
	void updateValueUtil(int ss, int se, int i, int diff, int si) {
		// Base Case: If the input index lies outside the range of
		// this segment
		if (i < ss || i > se)
			return;

		// If the input index is in range of this node, then update the
		// value of the node and its children
		st[si] = st[si] + diff;
		if (se != ss) {
			int mid = getMid(ss, se);
			updateValueUtil(ss, mid, i, diff, 2 * si + 1);
			updateValueUtil(mid + 1, se, i, diff, 2 * si + 2);
		}
	}

	// The function to update a value in input array and segment tree.
	// It uses updateValueUtil() to update the value in segment tree
	void updateValue(int arr[], int n, int i, int new_val) {
		// Check for erroneous input index
		if (i < 0 || i > n - 1) {
			System.out.println("Invalid Input");
			return;
		}

		// Get the difference between new value and old value
		int diff = new_val - arr[i];

		// Update the value in array
		arr[i] = new_val;

		// Update the values of nodes in segment tree
		updateValueUtil(0, n - 1, i, diff, 0);
	}

	// Return sum of elements in range from index qs (quey start) to
	// qe (query end). It mainly uses getSumUtil()
	int getSum(int n, int startIndex, int endIndex) {
		// Check for erroneous input values
		if (startIndex < 0 || endIndex > n - 1 || startIndex > endIndex) {
			System.out.println("Invalid Input");
			return -1;
		}
		return getSumUtil(0, n - 1, startIndex, endIndex, 0);
	}

	// A recursive function that constructs Segment Tree for array[ss..se].
	// si is index of current node in segment tree st
	int constructSTUtil(int arr[], int ss, int se, int si) {
		// If there is one element in array, store it in current node of
		// segment tree and return
		if (isBaseConditionSatisfied(ss, se)) {
			st[si] = calculateValue(arr[ss]);
			return st[si];
		}

		// If there are more than one elements, then recur for left and
		// right subtrees and store the sum of values in this node
		int mid = getMid(ss, se);
		st[si] = calculateResult(arr, ss, mid, se, si);
		return st[si];
	}

	private int calculateValue(int i) {
		
		  int total = 0; for (int j = i - 4; j <= i; j++) { total += j; } return total;
		 
		//return i;
	}

	private int calculateResult(int[] arr, int ss, int mid, int se, int si) {
		return constructSTUtil(arr, ss, mid, si * 2 + 1) + constructSTUtil(arr, mid + 1, se, si * 2 + 2);
	}

	private boolean isBaseConditionSatisfied(int start, int end) {
		return start == end;
	}

	// Driver program to test above functions
	public static void main(String args[]) {
		int arr[] = { 500000, 500005, 500010};
		int n = arr.length;
		SegmentTree2 tree = new SegmentTree2(arr);
		tree.printLevels();
		// Build segment tree from given array

		// Print sum of values in array from index 1 to 3
		System.out.println("Sum of values in given range = " + tree.getSum(n, 0, 1));

		// Update: set arr[1] = 10 and update corresponding segment
		// tree nodes
		// tree.updateValue(arr, n, 1, 10);

		// Find sum after the value is updated
		// System.out.println("Updated sum of values in given range = " + tree.getSum(n,
		// 1, 3));
	}

	private void printLevels() {
		int j = 1;
		int height = getHeight(st.length);
		for (int l = height; l >=0; l--) {
			System.out.print("  ");
		}
		height--;
		for (int i = 0, k = 0; i < st.length; i++, k++) {
			
			System.out.print(st[i] + " ");
			if (j - 1 == k) {
				
				System.out.println();
				for (int l = height; l >=0; l--) {
					System.out.print("  ");
				}
				height--;
				j = j * 2;
				k = -1;
			}
		}

	}

	private int getHeight(int length) {
		return mHeight;
	}
}
