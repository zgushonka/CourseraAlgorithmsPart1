import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

// Unions in virtual N x N space.
// With start(top row) and finish(bottom row).
public class PercolationUnions {
    private WeightedQuickUnionUF unions;
    private int n;

    public PercolationUnions(int n) {
        unions = new WeightedQuickUnionUF(n * n + 1 + 1);
        this.n = n;
    }

    public void union(int lhsRow, int lhsCol, int rhsRow, int rhsCol) {
        int p = unionIndex(lhsRow, lhsCol);
        int q = unionIndex(rhsRow, rhsCol);
        unions.union(p, q);
    }

    public boolean percolates() {
        return unions.connected(unionStartIndex(), unionFinishIndex());
    }

    public boolean isFull(int row, int col) {
        int index = unionIndex(row, col);
        return unions.connected(unionStartIndex(), index);
    }

    public void checkConnectionWithStartAndFinish(int row, int col) {
        int index = unionIndex(row, col);
        if (row == 1) {
            unions.union(unionStartIndex(), index);
        }
        if (row == n) {
            unions.union(unionFinishIndex(), index);
        }
    }

    private int unionIndex(int row, int col) {
        int rowIndex = row - 1;
        int colIndex = col - 1;
        return rowIndex * n + colIndex;
    }

    private int unionStartIndex() {
        return n * n;
    }

    private int unionFinishIndex() {
        return (n * n) + 1;
    }

    // test client (optional) --------------------------------------------------
    public static void main(String[] args) {
        StdOut.println("PercolationUnions main");

        int n = 5;
        if (args.length == 1) n = Integer.parseInt(args[0]);

        PercolationUnions sut = new PercolationUnions(n);
        sut.tests();
    }

    private void tests() {
        StdOut.println("PercolationUnions tests >");
        assert (!percolates());
        assert (!isFull(1, 1));
        checkConnectionWithStartAndFinish(1, 1);
        assert (isFull(1, 1));

        testIndex();
        StdOut.println("PercolationUnions tests <");
    }

    private void testIndex() {
        StdOut.println("PercolationUnions testIndex");
        int exN = n;
        n = 4;
        assert (unionIndex(1, 1) == 0);
        assert (unionIndex(2, 3) == 6);
        assert (unionIndex(3, 2) == 9);
        assert (unionIndex(4, 4) == 15);
        assert (unionStartIndex() == 16);
        assert (unionFinishIndex() == 17);

        n = 5;
        assert (unionIndex(1, 1) == 0);
        assert (unionIndex(2, 3) == 7);
        assert (unionIndex(3, 2) == 11);
        assert (unionIndex(4, 4) == 18);
        assert (unionIndex(5, 5) == 24);
        assert (unionStartIndex() == 25);
        assert (unionFinishIndex() == 26);
        n = exN;
    }
}
