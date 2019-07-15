/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.QuickFindUF;
import edu.princeton.cs.algs4.StdOut;

public class PercolationQuickFind {
    private boolean[][] grid;
    private int numOpenSites;
    private QuickFindUF underlyingArray;
    private int size;

    // creates n-by-n grid, with all sites initially blocked
    public PercolationQuickFind(int n) {
        if (n <= 0) throw new IllegalArgumentException("n should be positive integer!");
        size = n;
        grid = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = false;
            }
        }
        numOpenSites = 0;
        underlyingArray = new QuickFindUF(n * n + 2);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        argumentRangeCheck(row, col);
        if (!isOpen(row, col)) {
            grid[row - 1][col - 1] = true;
            numOpenSites += 1;

            if (row > 1) {
                if (isOpen(row - 1, col)) {
                    underlyingArray.union((row - 2) * size + col - 1, (row - 1) * size + col - 1);
                }
            }
            else {
                underlyingArray.union(size * size, (row - 1) * size + col - 1);
            }
            if (row < size) {
                if (isOpen(row + 1, col)) {
                    underlyingArray.union(row * size + col - 1, (row - 1) * size + col - 1);
                }
            }
            else {
                underlyingArray.union(size * size + 1, (row - 1) * size + col - 1);
            }
            if (col > 1) {
                if (isOpen(row, col - 1)) {
                    underlyingArray.union((row - 1) * size + col - 2, (row - 1) * size + col - 1);
                }
            }
            if (col < size) {
                if (isOpen(row, col + 1)) {
                    underlyingArray.union((row - 1) * size + col, (row - 1) * size + col - 1);
                }
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        argumentRangeCheck(row, col);
        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        // for (int j = 0; j < size; j++) {
        //     if (isOpen(0, j) && underlyingArray.connected(j, row * size + col)) {
        //         return true;
        //     }
        // }
        // return false;
        argumentRangeCheck(row, col);
        return (isOpen(row, col) && underlyingArray
                .connected(size * size, (row - 1) * size + col - 1));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        // for (int j = 0; j < size; j++) {
        //     if (isOpen(size - 1, j) && isFull(size - 1, j)) {
        //         return true;
        //     }
        // }
        // return false;
        return underlyingArray.connected(size * size, size * size + 1);
    }

    private void argumentRangeCheck(int row, int col) {
        if (row < 1 || row > size || col < 1 || col > size)
            throw new IllegalArgumentException("index out of range!");
    }

    // test client (optional)
    public static void main(String[] args) {
        // while (!StdIn.isEmpty()) {
        //     int value = StdIn.readInt();
        //     StdOut.println(value);
        // }
        //
        PercolationQuickFind perc = new PercolationQuickFind(2);
        StdOut.println(perc.size);
        StdOut.println(perc.numOpenSites);
        StdOut.println(perc.isOpen(1, 1));
        perc.open(1, 1);
        StdOut.println(perc.isOpen(1, 1));
        StdOut.println(perc.isFull(1, 1));
        StdOut.println(perc.isFull(2, 1));
        perc.open(2, 1);
        StdOut.println(perc.numOpenSites);
        StdOut.println(perc.isFull(2, 1));
        StdOut.println(perc.isFull(2, 2));
        StdOut.println(perc.percolates());

    }
}
