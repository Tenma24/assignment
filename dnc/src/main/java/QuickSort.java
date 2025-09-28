// QuickSort: Hoare partition, randomized pivot, recurse smaller-first (iterate larger)

import java.util.Random;

public class QuickSort {
    private static final Random RNG = new Random();
    private int maxDepth;

    public void sort(int[] a) {
        maxDepth = 0;
        sort(a, 0, a.length - 1, 0);
    }

    private void sort(int[] a, int l, int r, int depth) {
        while (l < r) {
            maxDepth = Math.max(maxDepth, depth);
            int p = hoare(a, l, r);
            if (p - l < r - p) { // recurse into smaller side
                sort(a, l, p, depth + 1);
                l = p + 1;       // iterate the larger side
            } else {
                sort(a, p + 1, r, depth + 1);
                r = p;
            }
        }
    }

    private int hoare(int[] a, int l, int r) {
        int pivot = a[l + RNG.nextInt(r - l + 1)];
        int i = l - 1, j = r + 1;
        while (true) {
            do { i++; } while (a[i] < pivot);
            do { j--; } while (a[j] > pivot);
            if (i >= j) return j;
            int t = a[i]; a[i] = a[j]; a[j] = t;
        }
    }

    public int getMaxDepth() { return maxDepth; }
}

