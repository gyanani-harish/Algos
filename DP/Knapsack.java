

public class Knapsack {

    // A utility function that returns maximum of two integers
    static int max(int a, int b) {
        return (a > b) ? a : b;
    }

    // Returns the maximum value that can be put in a knapsack of capacity W
    static int knapSack(int actualBagCapacity, int wt[], int val[], int nofOfValues) {
        int i, currentBagCapacity;
        int K[][] = new int[nofOfValues + 1][actualBagCapacity + 1];

        // Build table K[][] in bottom up manner
        for (i = 0; i <= nofOfValues; i++) {
            for (currentBagCapacity = 0; currentBagCapacity <= actualBagCapacity; currentBagCapacity++) {
                if (i == 0 || currentBagCapacity == 0)
                    K[i][currentBagCapacity] = 0;
                else if (canHoldCurrentItemInBag(wt, i, currentBagCapacity)) {
                    System.out.print("K[" + i + "][" + currentBagCapacity + "] = max(val[" + (i - 1) + "]+K[" + (i - 1) + "][" + currentBagCapacity + "-" + wt[i - 1] + "], K[" + (i - 1) + "][" + currentBagCapacity + "]");
                    System.out.print(") =>max(" + currentProfit(val, i) + "+" + K[i - 1][currentBagCapacity - wt[i - 1]] + "," + K[i - 1][currentBagCapacity] + ")");
                    System.out.println("      where i=" + i + " w=" + currentBagCapacity + " val[" + (i - 1) + "]=" + val[i - 1] + ", " + "K[" + (i - 1) + "][" + currentBagCapacity + "-" + wt[i - 1] + "]=" + K[i - 1][currentBagCapacity - wt[i - 1]] + ", K[" + (i - 1) + "][" + currentBagCapacity + "]=" + K[i - 1][currentBagCapacity]);
                    K[i][currentBagCapacity] = max(currentProfit(val, i) + upperRowExcludingCurrentProfit(K, i, currentBagCapacity, wt), upperRowLastProfit(K, i, currentBagCapacity));
                } else
                    K[i][currentBagCapacity] = upperRowLastProfit(K, i, currentBagCapacity);
            }
        }

        return K[nofOfValues][actualBagCapacity];
    }

    /**
     *
     * @param wt - weight array
     * @param i - current row
     * @param bagCapacity - bagCapacity of that column
     * @return true if current item can be fit into bag, in other words bag can hold this item
     */
    private static boolean canHoldCurrentItemInBag(int[] wt, int i, int bagCapacity) {
        return wt[i-1] <= bagCapacity;
    }

    /**
     * @param K- 2d matrix or table
     * @param i  - current row
     * @param w  - current column
     * @param wt - weight array
     * @return return that cell where which has highest profit after considering current profit inclusion,
     * example - if the bag has 7 weight capability and current weight is 4, then it will find highest valued item
     * which fits in bag capability of 3.
     */
    private static int upperRowExcludingCurrentProfit(int[][] K, int i, int w, int wt[]) {
        return K[lastRow(i)][w - wt[lastRow(i)]];
    }

    private static int lastRow(int i) {
        return i - 1;
    }

    /**
     * @param K - 2d matrix or table
     * @param i - current row
     * @param w - current column
     * @return last row profit, example if our current position is 5,7 then it gives value of 4,7
     */
    private static int upperRowLastProfit(int[][] K, int i, int w) {
        return K[lastRow(i)][w];
    }

    /**
     * @param val - profit array
     * @param i   - current row
     * @return current row profit
     */
    private static int currentProfit(int[] val, int i) {
        return val[i-1];
    }


    // Driver program to test above function
    public static void main(String args[]) {
        int val[] = new int[]{101, 100, 120};
        int wt[] = new int[]{1, 2, 3};
        int W = 5;
        int n = val.length;
        System.out.println(knapSack(W, wt, val, n));
    }
}
