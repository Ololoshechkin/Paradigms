public class BinarySearchSpan {

    // for each a.length > i >= j >= 0 : a[i] <= a[j]
    public static int lowerBoundIterative(int[] a, int x) {
        int left = -1, right = a.length, middle;
        // left = -1, right = a.length
        // Inv: a[left] > x, a[right] <= x (I assume a[-1] = infinity, a[a.lenght] = -infinity), -1 <= left < right <= a.length,
        // for each a.length > i >= j >= 0 : a[i] <= a[j]
        while (right != left + 1) {
            // Inv, left < right - 1
            middle = left + (right - left) / 2;
            // Inv, left < middle < right
            if (a[middle] <= x) {
                // Inv, a[middle] <= x
                right = middle;
                // a[left'] > x, a[righ't] <= x, -1 <= left = left' < right' = middle < right < a.length
            } else  {
                // Inv, a[middle] > x
                left = middle;
                // a[left'] > x, a[righ't] <= x, -1 <= left < left' = middle < right' = right < a.length
            }
            // Inv, right' - left' < right - left
        }
        // right = left - 1, -1 <= left < right <= a.length, Inv, a[i] >= a[j] for each i <= j
        return right;
    }

    // Inv: a[left] > x, a[right] < x (I assume a[-1] = infinity, a[a.lenght] = -infinity), -1 <= left < right <= a.length,
    // for each a.length > i >= j >= 0 : a[i] <= a[j]
    private static int upperBoundRecursiveWithParams(int[] a, int x, int left, int right) {
        if (right == left + 1) {
            return right; // right = left - 1, -1 <= left < right <= a.length, Inv
        }
        // Inv, left < right - 1
        int middle = left + (right - left) / 2;
        // Inv, left < middle < right
        if (a[middle] < x) {
            right = middle;
            // a[left'] > x, a[righ't] < x, -1 <= left = left' < right' = middle < right < a.length, right' - left' < right - left
            return upperBoundRecursiveWithParams(a, x, left, middle);
        } else {
            left = middle;
            // a[left'] > x, a[righ't] < x, -1 <= left < left' = middle < right' = right < a.length, right' - left' < right - left
            return upperBoundRecursiveWithParams(a, x, left, right);
        }
    }


    // for each a.length > i >= j >= 0 : a[i] <= a[j]
    public static int upperBoundRecursive(int[] a, int x) {
        return upperBoundRecursiveWithParams(a, x, -1, a.length);
    }

    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        int[] a = new int[args.length - 1];
        for (int i = 1; i < args.length; i++) {
            a[i - 1] = Integer.parseInt(args[i]);
        }
        int left = lowerBoundIterative(a, x), right = upperBoundRecursive(a, x);
        int length = right - left;
        System.out.println(left + " " + length);
    }

}
