import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * @author Kverchi
 * Date: 10.8.2018
 *
 * Performs a series of computational experiment for percolation system.
 */
public class PercolationStats {
    private double[] trialsResults;
    private int trials;
    private int gridSize;

    /**
     * Class constructor performs trials independent experiments on an n-by-n grid
     * @param n percolation system grid size
     * @param trials number of trials that were performed
     */
    public PercolationStats(int n, int trials) {
        this.gridSize = n;
        this.trials = trials;
        if(gridSize < 1 || trials < 1) {
            throw new IllegalArgumentException("Grid size and number of trials should be bigger than 0");
        }

        trialsResults = new double[trials];

        //Repeating Monte Carlo simulation computation experiment
        Percolation percolation;
        for (int i = 0; i < trials; i++) {
            percolation = new Percolation(gridSize);
            int count = 0;
            do {
                int row = StdRandom.uniform(1, gridSize + 1);
                int col = StdRandom.uniform(1, gridSize + 1);
                percolation.open(row, col);
            } while (!percolation.percolates());
            count = percolation.numberOfOpenSites();
            trialsResults[i] = (double) count / (double) (gridSize * gridSize);
        }
    }

    /**
     * Sample mean of percolation threshold
     * @return sample mean
     */
    public double mean() {
        return StdStats.mean(trialsResults);
    }

    /**
     * Sample standard deviation of percolation threshold
     * @return sample standard
     */
    public double stddev() {
        return StdStats.stddev(trialsResults);
    }

    /**
     * Low endpoint of 95% confidence interval
     * @return low endpoint
     */
    public double confidenceLo() {
        return mean() - (1.96 * stddev() / Math.sqrt(trialsResults.length));
    }

    /**
     * High endpoint of 95% confidence interval
     * @return high endpoint
     */
    public double confidenceHi() {
        return mean() + (1.96 * stddev() / Math.sqrt(trialsResults.length));
    }

    /**
     * @param args args[0] - size of n-by-n grid, args[1] - number of trials
     */
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        Stopwatch sw = new Stopwatch();
        PercolationStats percolationStats = new PercolationStats(N, T);
        System.out.printf("mean                     = %f\n", percolationStats.mean());
        System.out.printf("stddev                   = %f\n", percolationStats.stddev());
        System.out.printf("95%% confidence interval = %f, %f\n",
                percolationStats.confidenceLo(), percolationStats.confidenceHi());

        System.out.println("------");
        System.out.printf("Total time: %f secs. (for N=%d, T=%d)",
                sw.elapsedTime(), N, T);
    }
}
