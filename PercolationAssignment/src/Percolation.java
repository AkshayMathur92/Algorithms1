import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final WeightedQuickUnionUF unionUF;
    private final int n;
    private boolean[][] grid;
    private final int source;
    private final int sink;
    private int numOfOpenSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n){
        if(n <= 0) throw new IllegalArgumentException();
        this.n = n;
        unionUF = new WeightedQuickUnionUF((n * n) + 2);
        source = (n * n);
        grid = new boolean[n][n];
        sink = (n * n) + 1;
    }

    // opens the site (row, col) if it is not open already
    public void open(int r, int c){
        int row = r - 1;
        int col = c - 1;
        if(row < 0 || col < 0 || row >= n || col >= n) throw new IllegalArgumentException();
        if(grid[row][col]) return;
        grid[row][col] = true;
        if(row == 0){
            // this is top row , connect to source
            unionUF.union(row * n + col , source);
        }
        if(row == n - 1){
            // this is bottom row , connect to sink
            unionUF.union(row * n + col , sink);
        }
        numOfOpenSites++;
        //for all valid open neighbours connect
        for(int node : getValidNeighbour(row, col)){
            if(node != -1 )
                unionUF.union(row * n + col, node);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int r, int c){
        int row = r - 1;
        int col = c - 1;
        if(row < 0 || col < 0 || row >= n || col >= n) throw new IllegalArgumentException();
        return grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int r, int c){
        int row = r - 1;
        int col = c - 1;
        if(row < 0 || col < 0 || row >= n || col >= n) throw new IllegalArgumentException();
        return grid[row][col] && connected(row * n + col, source);
    }

    private boolean connected(int p, int q) {
        return unionUF.find(p) == unionUF.find(q);
    }

    // returns the number of open sites
    public int numberOfOpenSites(){
        return numOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates(){
        return connected(source, sink);
    }

    private int[] getValidNeighbour(int row, int col){
        int[] neighbour = {-1, -1, -1, -1};
        if(row - 1 >= 0 && isOpen(row - 1 + 1, col + 1)) {
            neighbour[0] = (row - 1) * n + col;
        }
        if(row + 1 < n && isOpen(row + 1 + 1, col + 1)) {
            neighbour[1] = (row + 1) * n + col;
        }
        if(col - 1 >= 0 && isOpen(row + 1, col - 1 + 1)) {
            neighbour[2] = (row * n)  + col - 1;
        }
        if(col + 1 < n && isOpen(row + 1, col + 1 + 1)){
            neighbour[3] = (row * n)  + col + 1;
        }
        return neighbour;
    }

    // test client (optional)

    public static void main(String[] args){
        Percolation percolation = new Percolation(20);
        while (!percolation.percolates()) {
            int row = StdRandom.uniform(1, 21);
            int col = StdRandom.uniform(1, 21);
            percolation.open(row, col);
        }
        StdOut.println("percolation threshold = "+ percolation.numberOfOpenSites() / 400.0);

    }
}
