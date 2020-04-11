import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] trialsResult;

    private final int trialsValue;
    private double meanValue = 0;
    private double stddevValue = 0;
    private double confValue = 0;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        trialsValue = trials;
        trialsResult = new double[trials];
        for (int i = 0; i < trials; i++) trialsResult[i] = 0;
        performTrials(n, trials);
        calcStats();
    }

    // sample mean of percolation threshold
    public double mean() {
        return meanValue;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddevValue;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - confValue;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + confValue;
    }

    private void performTrials(int size, int trials) {
        for (int trial = 0; trial < trials; trial++) {
            Percolation percolation = new Percolation(size);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, size + 1);
                int col = StdRandom.uniform(1, size + 1);
                percolation.open(row, col);
            }
            trialsResult[trial] = percolation.numberOfOpenSites() / ((double) (size * size));
            percolation = null;
        }
    }

    private void calcStats() {
        meanValue = StdStats.mean(trialsResult);
        stddevValue = StdStats.stddev(trialsResult);
        confValue = 1.96 * stddev() / Math.sqrt(trialsValue);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = 0;
        int trials = 0;
        if (args.length == 2) {
            n = Integer.parseInt(args[0]);
            trials = Integer.parseInt(args[1]);
        }

        PercolationStats stats = new PercolationStats(n, trials);
        StdOut.println("mean\t\t\t\t\t= "
                               + stats.mean());

        StdOut.println("stddev\t\t\t\t\t= "
                               + stats.stddev());

        StdOut.println("95% confidence interval\t= ["
                               + stats.confidenceLo()
                               + ", "
                               + stats.confidenceHi()
                               + "]");
    }
}
