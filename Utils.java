public class Utils {
    public static int binarySearch(long arr[], long key) {
        return binarySearchUtil(arr, 0, arr.length - 1, key);
    }

    private static int getMid(int ss, int se) {
        return ss + (se - ss) / 2;
    }

    private static int binarySearchUtil(long arr[], int ss, int se, long key) {
        if (se >= ss) {
            int mid = getMid(ss, se);
            if (arr[mid] == key) {
                return mid + 1;
            } else if (key > arr[mid]) {
                return binarySearchUtil(arr, mid + 1, se, key);
            } else {
                return binarySearchUtil(arr, ss, mid - 1, key);
            }
        }
        return -1;//element not found
    }
}
