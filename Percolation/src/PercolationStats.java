
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {
    private final int trials;
    private final double[] thresholds;  // percolation threshold for each trial

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0){
            throw new IllegalArgumentException("n and trials must be greater than 0");
        }
            
        this.trials = trials;
        this.thresholds = new double[trials];

        for(int i = 0; i < trials; i++){

            Percolation percolation = new Percolation(n);
            while(!percolation.percolates()){
                int row = StdRandom.uniformInt(1, n+1);
                int col = StdRandom.uniformInt(1, n+1);

                if(!percolation.isOpen(row, col)){
                    percolation.open(row, col);
                }
            }

            thresholds[i] = (double) percolation.numberOfOpenSites() * 1.0 / (n*n);
        }
    }

    public double mean() {
        return StdStats.mean(thresholds);
    }

    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    public double confidenceLo() {
        return mean() - (1.96 * stddev() / Math.sqrt(trials));
    }

    public double confidenceHi() {
        return mean() + (1.96 * stddev() / Math.sqrt(trials));
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Two arguments required: n and trials");
        }
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(n, trials);
        StdOut.println("mean                    = " + stats.mean());
        StdOut.println("stddev                  = " + stats.stddev());
        StdOut.println("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
    }
}