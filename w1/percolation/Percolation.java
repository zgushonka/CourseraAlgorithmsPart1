import edu.princeton.cs.algs4.StdOut;

public class Percolation {
    private PercolationField field;
    private PercolationUnions unions;
    private int n;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Error: N < 1");
        }

        field = new PercolationField(n);
        unions = new PercolationUnions(n);
        this.n = n;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            field.setOpen(row, col);
            unions.checkConnectionWithStartAndFinish(row, col);
            connectNeighbours(row, col);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return field.isOpen(row, col);
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return unions.isFull(row, col);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return field.numberOfOpenSites();
    }

    // does the system percolate?
    public boolean percolates() {
        return unions.percolates();
    }

    private void connectNeighbours(int row, int col) {
        int leftRow = row - 1;
        connect(row, col, leftRow, col);
        int rightRow = row + 1;
        connect(row, col, rightRow, col);
        int upCol = col - 1;
        connect(row, col, row, upCol);
        int downCol = col + 1;
        connect(row, col, row, downCol);
    }

    private void connect(int lhsRow, int lhsCol, int rhsRow, int rhsCol) {
        if (isOpen(rhsRow, rhsCol)) {
            unions.union(lhsRow, lhsCol, rhsRow, rhsCol);
        }
    }

    // test client (optional) --------------------------------------------------
    public static void main(String[] args) {
        StdOut.println("Percolation main");
        int n = 5;
        if (args.length == 1) n = Integer.parseInt(args[0]);

        Percolation sut = new Percolation(n);
        sut.tests();
    }

    private void tests() {
        StdOut.println("Percolation tests >");
        assert (!percolates());
        int col = 1;
        for (int row = 1; row <= n; row++) {
            assert (!isOpen(row, col));
            open(row, col);
            assert (isOpen(row, col));
        }
        assert (percolates());
        StdOut.println("Percolation tests <");
    }
}
