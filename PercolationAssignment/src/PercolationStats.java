import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {

    private double[] thresholds;
    private int trials_done = 0;
    private static final double CONFIDENCE_95 = 1.96;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){
        if(n <= 0 || trials <= 0)
            throw new IllegalArgumentException();
        thresholds = new double[trials];
        while(trials > 0) {
            trials--;
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                percolation.open(row, col);
            }
            addDataPoint(percolation.numberOfOpenSites()/ (double)(n*n));
        }
    }

    private void addDataPoint(double threshold){
        thresholds[trials_done] = threshold;
        trials_done++;
    }

    // sample mean of percolation threshold
    public double mean(){
        return StdStats.mean(thresholds, 0, trials_done);
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        return StdStats.stddev(thresholds, 0 , trials_done);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){
        return mean() - (CONFIDENCE_95 * stddev()) / Math.sqrt(trials_done);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        return mean() + (CONFIDENCE_95 * stddev()) / Math.sqrt(trials_done);
    }

    // test client (see below)
    public static void main(String[] args){
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, t);
        StdOut.println("mean = " + stats.mean());
        StdOut.println("stddev = " + stats.stddev());
        StdOut.printf("95% confidence interval = [%d, %d]", stats.confidenceLo(), stats.confidenceHi());
    }

}