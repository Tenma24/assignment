import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

public class AlgorithmTester {
    private static final int[] SIZES = {100, 1000, 5000, 10000};
    private static final int TRIALS = 5;

    public static void main(String[] args) {
        Random rnd = new Random(42);

        try (PrintWriter pw = new PrintWriter(new FileWriter("metrics.csv"))) {
            // заголовок
            System.out.println("algorithm,n,time_ns,maxDepth");
            pw.println("algorithm,n,time_ns,maxDepth");

            for (int n : SIZES) {
                // ---- MergeSort ----
                long time = 0; long depth = 0;
                for (int t = 0; t < TRIALS; t++) {
                    int[] base = randArray(rnd, n, 1_000_000);
                    int[] arr  = Arrays.copyOf(base, base.length);
                    MergeSort ms = new MergeSort();
                    long st = System.nanoTime(); ms.sort(arr); long dt = System.nanoTime() - st;
                    time += dt; depth += ms.getMaxDepth();
                }
                String msLine = "MergeSort," + n + "," + (time / TRIALS) + "," + (depth / TRIALS);
                System.out.println(msLine); pw.println(msLine);

                // ---- QuickSort ----
                time = 0; depth = 0;
                for (int t = 0; t < TRIALS; t++) {
                    int[] base = randArray(rnd, n, 1_000_000);
                    int[] arr  = Arrays.copyOf(base, base.length);
                    QuickSort qs = new QuickSort();
                    long st = System.nanoTime(); qs.sort(arr); long dt = System.nanoTime() - st;
                    time += dt; depth += qs.getMaxDepth();
                }
                String qsLine = "QuickSort," + n + "," + (time / TRIALS) + "," + (depth / TRIALS);
                System.out.println(qsLine); pw.println(qsLine);

                // ---- DeterministicSelect ----
                time = 0; depth = 0;
                for (int t = 0; t < TRIALS; t++) {
                    int[] base = randArray(rnd, n, 1_000_000);
                    int[] arr  = Arrays.copyOf(base, base.length);
                    DeterministicSelect ds = new DeterministicSelect();
                    long st = System.nanoTime(); ds.select(arr, n / 2); long dt = System.nanoTime() - st;
                    time += dt; depth += ds.getMaxDepth();
                }
                String dsLine = "DeterministicSelect," + n + "," + (time / TRIALS) + "," + (depth / TRIALS);
                System.out.println(dsLine); pw.println(dsLine);

                // ---- ClosestPair ----
                time = 0; depth = 0;
                for (int t = 0; t < TRIALS; t++) {
                    ClosestPair.Point[] pts = randPoints(rnd, n);
                    ClosestPair cp = new ClosestPair();
                    long st = System.nanoTime(); cp.findClosestDistance(pts); long dt = System.nanoTime() - st;
                    time += dt; depth += cp.getMaxDepth();
                }
                String cpLine = "ClosestPair," + n + "," + (time / TRIALS) + "," + (depth / TRIALS);
                System.out.println(cpLine); pw.println(cpLine);
            }

            System.out.println("saved in metrics.csv");
        } catch (Exception e) { e.printStackTrace(); }
    }

    private static int[] randArray(Random r, int n, int bound) {
        int[] a = new int[n]; for (int i = 0; i < n; i++) a[i] = r.nextInt(bound); return a;
    }
    private static ClosestPair.Point[] randPoints(Random r, int n) {
        ClosestPair.Point[] p = new ClosestPair.Point[n];
        for (int i = 0; i < n; i++) p[i] = new ClosestPair.Point(r.nextDouble()*1000.0, r.nextDouble()*1000.0);
        return p;
    }
}
