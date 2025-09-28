// MergeSort: reusable buffer + insertion cutoff; early-exit if a[m] <= a[m+1]

import java.util.Arrays;

public class MergeSort {
    private static final int CUTOFF = 15;
    private int maxDepth;

    public void sort(int[] a) {
        maxDepth = 0;
        int[] buf = new int[a.length];
        sort(a, 0, a.length - 1, buf, 0);
    }

    private void sort(int[] a, int l, int r, int[] buf, int depth) {
        maxDepth = Math.max(maxDepth, depth);
        if (r <= l) return;
        if (r - l <= CUTOFF) { insertion(a, l, r); return; }
        int m = (l + r) >>> 1;
        sort(a, l, m, buf, depth + 1);
        sort(a, m + 1, r, buf, depth + 1);
        if (a[m] <= a[m + 1]) return;      // already ordered at the border
        merge(a, l, m, r, buf);
    }

    private void merge(int[] a, int l, int m, int r, int[] buf) {
        System.arraycopy(a, l, buf, l, r - l + 1);
        int i = l, j = m + 1;
        for (int k = l; k <= r; k++) {
            if (i > m) a[k] = buf[j++];
            else if (j > r) a[k] = buf[i++];
            else if (buf[i] <= buf[j]) a[k] = buf[i++];
            else a[k] = buf[j++];
        }
    }

    private void insertion(int[] a, int l, int r) {
        for (int i = l + 1; i <= r; i++) {
            int x = a[i], j = i - 1;
            while (j >= l && a[j] > x) { a[j + 1] = a[j]; j--; }
            a[j + 1] = x;
        }
    }

    public int getMaxDepth() { return maxDepth; }
}

