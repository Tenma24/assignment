// Deterministic Select (MoM5): groups of 5, median-of-medians pivot, in-place partition

public class DeterministicSelect {
    private int maxDepth;

    public int select(int[] a, int k) {
        if (k < 0 || k >= a.length) throw new IllegalArgumentException("k out of bounds");
        maxDepth = 0;
        int l = 0, r = a.length - 1;
        while (true) {
            if (l == r) return a[l];
            int p = choosePivotIndex(a, l, r, 0);    // depth=0
            p = partition(a, l, r, p);
            if (k == p) return a[p];
            if (k < p) r = p - 1; else l = p + 1;
        }
    }

    // ---- depth-aware helpers ----
    private int choosePivotIndex(int[] a, int l, int r, int depth) {
        if (depth > maxDepth) maxDepth = depth;
        int n = r - l + 1;
        if (n <= 5) { insertion(a, l, r); return l + n / 2; }
        int groups = (n + 4) / 5;
        for (int g = 0; g < groups; g++) {
            int s = l + g * 5, e = Math.min(s + 4, r);
            insertion(a, s, e);
            int med = s + (e - s) / 2;
            swap(a, l + g, med);
        }
        int mid = l + groups / 2;
        return selectIndexInPlace(a, l, l + groups - 1, mid, depth + 1);
    }

    private int selectIndexInPlace(int[] a, int l, int r, int k, int depth) {
        if (depth > maxDepth) maxDepth = depth;
        while (true) {
            if (l == r) return l;
            int p = choosePivotIndex(a, l, r, depth + 1);
            p = partition(a, l, r, p);
            if (k == p) return k;
            if (k < p) r = p - 1; else l = p + 1;
        }
    }

    private int partition(int[] a, int l, int r, int p) {
        swap(a, p, r);
        int pv = a[r], i = l;
        for (int j = l; j < r; j++) if (a[j] <= pv) { swap(a, i, j); i++; }
        swap(a, i, r);
        return i;
    }

    private void insertion(int[] a, int l, int r) {
        for (int i = l + 1; i <= r; i++) {
            int x = a[i], j = i - 1;
            while (j >= l && a[j] > x) { a[j + 1] = a[j]; j--; }
            a[j + 1] = x;
        }
    }
    private static void swap(int[] a, int i, int j) { int t = a[i]; a[i] = a[j]; a[j] = t; }

    public int getMaxDepth() { return maxDepth; }
}

