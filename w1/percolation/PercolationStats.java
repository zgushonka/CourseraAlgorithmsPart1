import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] trialsResult;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Error: N < 1");
        }

        trialsResult = new double[trials];
        for (int i = 0; i < trials; i++) trialsResult[i] = 0;
        performTrials(n, trials);
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(trialsResult);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(trialsResult);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.96 * Math.sqrt(stddev()) / (Math.sqrt(trialsResult.length)));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96 * Math.sqrt(stddev()) / (Math.sqrt(trialsResult.length)));
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
