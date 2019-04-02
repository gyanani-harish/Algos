package segment_tree;


public class st {
    long tree[], arr[], lazy[];

    public st(long arr[]) {
        int n = arr.length;
        int max_size = getMaxSizeOfFullBinaryTree(n);
        this.arr = arr;
        tree = new long[max_size];
        lazy = new long[max_size];
        constructUtil(arr, 0, n - 1, 0);
    }

    /**
     * @param n - no of elements in array
     * @return max size of array to store full binary tree
     * difference between complete binary tree and full binary tree
     * https://stackoverflow.com/questions/12359660/difference-between-complete-binary-tree-strict-binary-tree-full-binary-tre#answer-32064101
     */
    private int getMaxSizeOfFullBinaryTree(int n) {
        int height = (int) Math.ceil(getLog2OfN(n));
        return (int) (2 * Math.pow(2, height) - 1);
    }

    private double getLog2OfN(int n) {
        return Math.log(n) / Math.log(2);
    }

    private long constructUtil(long[] arr, int ss, int se, int si) {
        if (isLeafNode(ss, se)) {
            tree[si] = arr[ss];
            return tree[si];
        }
        int mid = getMid(ss, se);
        tree[si] = constructUtil(arr, ss, mid, si * 2 + 1)
                + constructUtil(arr, mid + 1, se, si * 2 + 2);
        return tree[si];
    }

    private int getMid(int ss, int se) {
        return ss + (se - ss) / 2;
    }

    private boolean isInvalidInputForSum(int qs, int qe) {
        return qs < 0 || qe > arr.length - 1 || qs > qe;
    }

    public long getSum(int qs, int qe) {
        if (isInvalidInputForSum(qs, qe)) {
            return -1;
        }
        return getSumUtil(0, arr.length - 1, qs, qe, 0);
    }

    private long getSumUtil(int ss, int se, int qs, int qe, int si) {
        lazyUtil(ss, se, si);
        if (isInCompleteRange(ss, se, qs, qe)) {
            return tree[si];
        }
        if (isOutsideRange(ss, se, qs, qe)) {
            return 0;
        }

        int mid = getMid(ss, se);
        return getSumUtil(ss, mid, qs, qe, si * 2 + 1)
                + getSumUtil(mid + 1, se, qs, qe, si * 2 + 2);
    }

    private boolean isInCompleteRange(int ss, int se, int qs, int qe) {
        return ss >= qs && se <= qe;
    }

    private boolean isOutsideRange(int ss, int se, int qs, int qe) {
        return ss > se || qs > se || qe < ss;
    }

    private boolean isInvalidInputForUpdate(int i) {
        return i < 0 || i > arr.length - 1;
    }

    public void updateRange(int fromIndex, int toIndex, long diff) {
        updateRangeUtilLazy(0, arr.length - 1, diff, fromIndex, toIndex, 0);
    }

    void updateValue(int i, long new_value) {
        if (isInvalidInputForUpdate(i)) {
            return;
        }
        long diff = new_value - arr[i];
        arr[i] = new_value;
        updateValueUtil(0, arr.length - 1, diff, i, 0);
    }

    private void updateValueUtil(int ss, int se, long diff, int index, int si) {
        if (index > se || index < ss) {
            return;
        }
        tree[si] += diff;
        if (!isLeafNode(ss, se)) {
            int mid = getMid(ss, se);
            updateValueUtil(ss, mid, diff, index, si * 2 + 1);
            updateValueUtil(mid + 1, se, diff, index, si * 2 + 2);
        }
    }

    private void updateRangeUtil(int ss, int se, long diff, int fromIndex, int toIndex, int si) {
        if (fromIndex > toIndex || fromIndex > se || toIndex < ss || ss > se) {
            return;
        }
        //leaf node
        if (isLeafNode(ss, se)) {
            tree[si] += diff;
            return;
        }
        int mid = getMid(ss, se);
        updateRangeUtil(ss, mid, diff, fromIndex, toIndex, si * 2 + 1);
        updateRangeUtil(mid + 1, se, diff, fromIndex, toIndex, si * 2 + 2);

        tree[si] = tree[si * 2 + 1] + tree[si * 2 + 2];
    }

    private boolean isLeafNode(int ss, int se) {
        return ss == se;
    }

    private void lazyUtil(int ss, int se, int si) {
        if (lazy[si] != 0) {
            tree[si] = (se - ss + 1) * lazy[si];
            if (!isLeafNode(ss, se)) {
                lazy[si * 2 + 1] += lazy[si];
                lazy[si * 2 + 2] += lazy[si];
            }
            lazy[si] = 0;
        }
    }

    /**
     * 1. check any update pending in that range (check root has update pending or not) if yes update the root and make lazy update on children
     * 2. if current range (ss and se) are not in range of update, return
     * 3. if current range is within update range update root and lazy update children  (if children available)
     * 4. divide the tree and recursively do the same work in subtrees accordingly
     * 5. update parent whose children have been updated
     *
     * @param ss        - start index of tree
     * @param se        - end index of tree
     * @param diff      - difference to be added in leaf nodes
     * @param fromIndex - start index of update range
     * @param toIndex   - end index of update range
     * @param si        - index of current node
     */
    private void updateRangeUtilLazy(int ss, int se, long diff, int fromIndex, int toIndex, int si) {
        lazyUtil(ss, se, si);
        if (fromIndex > toIndex || fromIndex > se || toIndex < ss || ss > se) {
            return;
        }
        // Current segment is fully in range
        if (ss >= fromIndex && se <= toIndex) {
            // Add the difference to current node
            tree[si] += (se - ss + 1) * diff;

            if (!isLeafNode(ss, se)) {
                // This is where we store values in lazy nodes,
                // rather than updating the segment tree itelf
                // Since we don't need these updated values now
                // we postpone updates by storing values in lazy[]
                lazy[si * 2 + 1] += diff;
                lazy[si * 2 + 2] += diff;
            }
            return;
        }

        int mid = getMid(ss, se);
        updateRangeUtilLazy(ss, mid, diff, fromIndex, toIndex, si * 2 + 1);
        updateRangeUtilLazy(mid + 1, se, diff, fromIndex, toIndex, si * 2 + 2);

        //update those parent nodes whose children have been updated by above recursive methods
        tree[si] = tree[si * 2 + 1] + tree[si * 2 + 2];
    }

    // Driver program to test above functions
    public static void main(String args[]) {
        long arr[] = {1, 2, 3, 4, 5, 6, 7};
        st tree = new st(arr);


        // Add 10 to all nodes at indexes from 1 to 5.
        tree.updateRange(1, 4, 10);

        // Find sum after the value is updated
        System.out.println("Updated sum of values in given range = " +
                tree.getSum(0, 2));
    }
}
