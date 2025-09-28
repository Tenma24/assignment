// Closest Pair: D&C; maintain Py; strip check with ≤7 neighbors per point

import java.util.*;

public class ClosestPair {
    private int maxDepth;

    public static class Point {
        public final double x, y;
        public Point(double x, double y) { this.x = x; this.y = y; }
    }

    public double findClosestDistance(Point[] pts) {
        maxDepth = 0;
        if (pts.length < 2) return Double.MAX_VALUE;
        Point[] px = Arrays.copyOf(pts, pts.length);
        Point[] py = Arrays.copyOf(pts, pts.length);
        Arrays.sort(px, Comparator.comparingDouble(p -> p.x));
        Arrays.sort(py, Comparator.comparingDouble(p -> p.y));
        double bestSq = solve(px, py, 0, pts.length - 1, 0); // depth=0
        return Math.sqrt(bestSq);
    }

    private double solve(Point[] px, Point[] py, int l, int r, int depth) {
        if (depth > maxDepth) maxDepth = depth;
        int n = r - l + 1;
        if (n <= 3) return bruteSq(px, l, r);

        int m = (l + r) >>> 1;
        double midX = px[m].x;

        Point[] pyl = new Point[n], pyr = new Point[n]; int il = 0, ir = 0;
        for (Point p : py) { if (p.x <= midX) pyl[il++] = p; else pyr[ir++] = p; }
        pyl = Arrays.copyOf(pyl, il); pyr = Arrays.copyOf(pyr, ir);

        double dl = solve(px, pyl, l, m, depth + 1);
        double dr = solve(px, pyr, m + 1, r, depth + 1);
        double d = Math.min(dl, dr);

        double limit = Math.sqrt(d);
        ArrayList<Point> strip = new ArrayList<>();
        for (Point p : py) if (Math.abs(p.x - midX) < limit) strip.add(p);

        double dsq = d;
        for (int i = 0; i < strip.size(); i++)
            for (int j = i + 1; j < strip.size() && j <= i + 7; j++) {
                double cur = sq(strip.get(i), strip.get(j));
                if (cur < dsq) dsq = cur;
            }
        return dsq;
    }

    private double bruteSq(Point[] a, int l, int r) {
        double best = Double.POSITIVE_INFINITY;
        for (int i = l; i <= r; i++)
            for (int j = i + 1; j <= r; j++) {
                double s = sq(a[i], a[j]);
                if (s < best) best = s;
            }
        return best;
    }
    private static double sq(Point a, Point b) { double dx = a.x - b.x, dy = a.y - b.y; return dx*dx + dy*dy; }

    public int getMaxDepth() { return maxDepth; }
}

