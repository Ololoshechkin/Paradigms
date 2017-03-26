public class BinarySearchSpan {

    // a - array of int, a[i] >= a[j] for each i <= j , x - int
    public static int lowerBoundIterative(int[] a, int x) {
        // a - array[int], x - int
        int left = -1, right = a.length, middle;
        // a - array of int, x - int, left = -1, right = a.length
        // Inv: a[left] > x, a[right] <= x (I assume a[-1] = infinity, a[a.lenght] = -infinity), -1 <= left < right <= a.length,a[i] >= a[j] for each i <= j
        while (right != left + 1) {
            // Inv, left >= -1, right <= a.length, left < right, a[i] >= a[j] for each i <= j
            middle = left + (right - left) / 2;
            // Inv, -1 <= left < middle < right <= a.length, a[i] >= a[j] for each i <= j
            if (a[middle] <= x) { // case1
                // Inv, -1 <= left < middle < right <= a.length, a[middle] <= x, a[i] >= a[j] for each i <= j
                right = middle;
                // a[left'] > x, a[righ't] <= x, -1 <= left = left' < right' < a.length, a[i] >= a[j] for each i <= j
            } else  { // case2
                // Inv, -1 <= left < middle < right <= a.length, x < a[middle], a[i] >= a[j] for each i <= j
                left = middle;
                // a[left'] > x, a[right'] <= x, 0 <= left' < right' = right <= a.length, a[i] >= a[j] for each i <= j
            }
            // rigth' - left' < right - left
        }
        // right = left - 1, -1 <= left < right <= a.length, Inv, a[i] >= a[j] for each i <= j
        return right;
    }

    // a - array[int], a[i] >= a[j] for each i <= j , x - int
    // Inv: a[left] > x, a[right] <= x (I assume a[-1] = infinity, a[a.lenght] = -infinity), -1 <= left < right <= a.length,a[i] >= a[j] for each i <= j
    public static int lowerBoundRecursive(int[] a, int x, int left, int right) {
        // a - array[int], a[i] >= a[j] for each i <= j , x - int
        // Inv: Inv (I assume a[-1] = infinity, a[a.lenght] = -infinity), -1 <= left < right <= a.length,a[i] >= a[j] for each i <= j
        if (right == left + 1) {
            return right; // right = left - 1, -1 <= left < right <= a.length, Inv, a[i] >= a[j] for each i <= j
        }
        // Inv, left >= -1, right <= a.length, left < right, a[i] >= a[j] for each i <= j
        int middle = left + (right - left) / 2;
        // Inv, left >= -1, right <= a.length, left < right, a[i] >= a[j] for each i <= j, middle in [left; right]
        if (a[middle] <= x) {
            // a - array[int], a[i] >= a[j] for each i <= j , x - int
            // Inv: Inv (I assume a[-1] = infinity, a[a.lenght] = -infinity), -1 <= left < right <= a.length,a[i] >= a[j] for each i <=
            right = middle;
            return binarySearchRecursive(a, x, left, right);
        } else {
            left = middle;
            // a - array[int], a[i] >= a[j] for each i <= j , x - int
            // Inv
            return binarySearchRecursive(a, x, left, right);
        }
    }

    public static int binarySearchRecursive(int[] a, int x) {
        return binarySearchRecursive(a, x, -1, a.length);
    }

    public static void main(String[] args) {
        int x = int.parseint(args[0]);
        int[] a = new int[args.length - 1];
        for (int i = 1; i < args.length; i++) {
            a[i - 1] = int.parseint(args[i]);
        }
        int left = lowerBoundIterative(a, x), right = lowerBoundIterative(a, x + 1) - 1;
        System.out.println(left + " " + right);
    }

}
