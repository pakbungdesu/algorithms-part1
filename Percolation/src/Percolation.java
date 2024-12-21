import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int n;
    private final WeightedQuickUnionUF generalUF;
    private final WeightedQuickUnionUF fullUF;  // auxiliary UF to prevent backwash
    private final int topidx;
    private final int bttmidx;
    private final boolean[][] grid;
    private int openSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be greater than 0.");
        }

        this.n = n;
        this.generalUF = new WeightedQuickUnionUF(n * n + 2); // +2 for virtual top and bottom sites
        this.fullUF = new WeightedQuickUnionUF(n * n + 1); // +1 for virtual top site only
        this.topidx = 0;
        this.bttmidx = n * n + 1;
        this.grid = new boolean[n][n];
        this.openSites = 0;
    }


    private int xyTo1d(int row, int col) {
        return (row-1) * n + (col-1) + 1;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > n){
            throw new IllegalArgumentException("Row must be between 1 and " + n);
        }
        
        if (col < 1 || col > n){
            throw new IllegalArgumentException("Col must be between 1 and " + n);
        } 

        if (!isOpen(row, col)){
            grid[row-1][col-1] = true;
            openSites++;

            int curridx = xyTo1d(row, col);

            // union with the top virtual site if it's in the first row
            if (row == 1){
                generalUF.union(curridx, topidx);
                fullUF.union(curridx, topidx);
            } 

            // union with the bottom virtual site if it's in the last row
            if (row == n){
                generalUF.union(curridx, bttmidx);
            }

            // union with adjacent sites
            if ((row > 1) && isOpen(row - 1, col)) bothUnion(curridx, row - 1, col); // up
            if ((row < n) && isOpen(row + 1, col)) bothUnion(curridx, row + 1, col); // down
            if ((col > 1) && isOpen(row, col - 1)) bothUnion(curridx, row, col - 1); // left
            if ((col < n) && isOpen(row, col + 1)) bothUnion(curridx, row, col + 1); // right
        }
    }


    private void bothUnion(int curridx, int row, int col){
        generalUF.union(curridx, xyTo1d(row, col));
        fullUF.union(curridx, xyTo1d(row, col));
    }


    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > n){
            throw new IllegalArgumentException("Row must be between 1 and " + n);
        }
        
        if (col < 1 || col > n){
            throw new IllegalArgumentException("Col must be between 1 and " + n);
        } 
        return grid[row-1][col-1];
    }

    // is the site (row, col) full? (check if connected to top virtual site)
    public boolean isFull(int row, int col) {
        if (row < 1 || row > n){
            throw new IllegalArgumentException("Row must be between 1 and " + n);
        }
        
        if (col < 1 || col > n){
            throw new IllegalArgumentException("Col must be between 1 and " + n);
        } 
        return isOpen(row, col) && (fullUF.find(xyTo1d(row, col)) == fullUF.find(topidx));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate? (check if top is connected to bottom)
    public boolean percolates() {
        return generalUF.find(topidx) == generalUF.find(bttmidx);
    }
}
