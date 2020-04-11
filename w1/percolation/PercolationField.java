import edu.princeton.cs.algs4.StdOut;

// N x N field populated with 0 after creation.
public class PercolationField {
    private int openSites = 0;
    private int[][] space;
    private int n;

    public PercolationField(int n) {
        this.n = n;

        space = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                space[i][j] = 0;
            }
        }
    }

    public void setOpen(int row, int col) {
        if (!isOpen(row, col)) {
            setItem(row, col);
            openSites++;
        }
    }

    public boolean isOpen(int row, int col) {
        if (!isCoordinatesValid(row, col)) {
            return false;
        }

        return getItem(row, col) == 1;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    private boolean isCoordinatesValid(int row, int col) {
        boolean isRowValid = 1 <= row && row <= n;
        boolean isColValid = 1 <= col && col <= n;
        return isRowValid && isColValid;
    }

    private int getItem(int row, int col) {
        int rowIndex = row - 1;
        int colIndex = col - 1;
        return space[rowIndex][colIndex];
    }

    private void setItem(int row, int col) {
        int rowIndex = row - 1;
        int colIndex = col - 1;
        space[rowIndex][colIndex] = 1;
    }

    // test client (optional) --------------------------------------------------
    public static void main(String[] args) {
        StdOut.println("PercolationField main");
        int n = 5;
        if (args.length == 1) n = Integer.parseInt(args[0]);

        PercolationField sut = new PercolationField(n);
        sut.tests();
    }

    private void tests() {
        StdOut.println("PercolationField test >");
        assert (openSites == 0);
        assert (!this.isOpen(1, 1));
        this.setOpen(1, 1);
        assert (openSites == 1);
        assert (this.isOpen(1, 1));
        StdOut.println("PercolationField test <");
    }
}
