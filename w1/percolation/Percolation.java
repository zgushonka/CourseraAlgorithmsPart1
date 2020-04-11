import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int openSites = 0;
    private boolean[][] space;
    private final PercolationUnions unionsFull;
    private final PercolationUnions unionsPercolate;
    private final int n;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        space = new boolean[n][n];
        unionsFull = new PercolationUnions(n);
        unionsPercolate = new PercolationUnions(n);
        this.n = n;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        if (!isOpen(row, col)) {
            setOpen(row, col);
            updateConnections(row, col);
            openSites++;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return space[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        return isOpen(row, col) && unionsFull.isFull(row, col);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return unionsPercolate.percolates();
    }

    //
    // Private
    //

    private void setOpen(int row, int col) {
        space[row - 1][col - 1] = true;
    }

    private void validate(int row, int col) {
        if (!isValid(row, col)) throw new IllegalArgumentException();
    }

    private boolean isValid(int row, int col) {
        return isValid(row) && isValid(col);
    }

    private boolean isValid(int coordinate) {
        return 1 <= coordinate && coordinate <= n;
    }

    private void updateConnections(int row, int col) {
        unionsFull.checkConnectionWithStart(row, col);
        unionsPercolate.checkConnectionWithStart(row, col);
        connectNeighbours(row, col);
        unionsPercolate.checkConnectionWithFinishFast(row, col);
    }

    private void connectNeighbours(int row, int col) {
        connect(row, col, row - 1, col);
        connect(row, col, row + 1, col);
        connect(row, col, row, col - 1);
        connect(row, col, row, col + 1);
    }

    private void connect(int lhsRow, int lhsCol, int rhsRow, int rhsCol) {
        if (isValid(rhsRow, rhsCol) && isOpen(rhsRow, rhsCol)) {
            unionsFull.union(lhsRow, lhsCol, rhsRow, rhsCol);
            unionsPercolate.union(lhsRow, lhsCol, rhsRow, rhsCol);
        }
    }

    //
    // Nested
    //

    private class PercolationUnions {
        private final WeightedQuickUnionUF unions;
        private final int n;
        private final int unionStartIndex;
        private final int unionFinishIndex;

        public PercolationUnions(int n) {
            unions = new WeightedQuickUnionUF(n * n + 1 + 1);
            this.n = n;
            unionStartIndex = n * n;
            unionFinishIndex = unionStartIndex + 1;
        }

        public boolean percolates() {
            return unions.connected(unionStartIndex, unionFinishIndex);
        }

        public boolean isFull(int row, int col) {
            return unions.connected(unionStartIndex, unionIndex(row, col));
        }

        public void union(int lhsRow, int lhsCol,
                          int rhsRow, int rhsCol) {
            int p = unionIndex(lhsRow, lhsCol);
            int q = unionIndex(rhsRow, rhsCol);
            unions.union(p, q);
        }

        public void checkConnectionWithStart(int row, int col) {
            if (row == 1) {
                unions.union(unionStartIndex, unionIndex(row, col));
            }
        }

        public void checkConnectionWithFinishFast(int row, int col) {
            if (row == n) {
                unions.union(unionFinishIndex, unionIndex(row, col));
            }
        }

        private int unionIndex(int row, int col) {
            int rowIndex = row - 1;
            int colIndex = col - 1;
            return rowIndex * n + colIndex;
        }
    }
}
