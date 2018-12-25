import edu.princeton.cs.algs4.WeightedQuickUnionUF;
/**
 * @author Kverchi
 * Date: 10.8.2018
 *
 * Models a percolation system using an n-by-n grid of sites.
 * Each site is either open or blocked. A full site is an open site that can be connected to an open site
 * in the top row via a chain of neighboring (left, right, up, down) open sites.
 * The system percolates if there is a full site in the bottom row.
 */
public class Percolation {
    private int gridSize;
    private boolean[][] percolationSystem;
    private WeightedQuickUnionUF unionFindDatatype;
    private int openSitesCounter;
    private int virtualTopSiteIndex;
    private int virtualBottomSiteIndex;

    private final static int VIRTUAL_SITES_NUM = 2;
    private final static int GRID_INDEX_SHIFT = 1;

    /**
     * Class constructor creates n-by-n grid, with all sites blocked
     * @param n size of the grid
     */
    public Percolation(int n) {
        this.gridSize = n;
        this.virtualTopSiteIndex = 0;
        this.virtualBottomSiteIndex = gridSize * gridSize + 1;
        percolationSystem = new boolean[gridSize][gridSize];
        unionFindDatatype = new WeightedQuickUnionUF(gridSize * gridSize + VIRTUAL_SITES_NUM);
    }
    /** Opens site (row, col) if it is not open already
     *  @param  row  index of row in percolation system
     *  @param  col  index of column in percolation system
     */
    public void open(int row, int col) {
        //is site already open
        if (isOpen(row, col))
            return;
        //open site (set site in percolationSystem as true)
        percolationSystem[row - GRID_INDEX_SHIFT][col - GRID_INDEX_SHIFT] = true;
        openSitesCounter ++;
        //connect newly opened site (q) to all of its adjacent open sites (p)
        //convert 2d coords of percolationSystem to 1d coords of weighted union-find datatype
        int sitePos1d = xyTo1D(row, col);
        //union() up to x4
        //union() merges the component containing site p with the the component containing site q.
        connectToAdjacentSite(row - 1, col, sitePos1d);
        connectToAdjacentSite(row + 1, col, sitePos1d);
        connectToAdjacentSite(row, col + 1, sitePos1d);
        connectToAdjacentSite(row, col - 1, sitePos1d);
        //connect to virtual top (or bottom)
        if (row == 1)
            unionFindDatatype.union(sitePos1d, 0);
        if (row == gridSize) {
            unionFindDatatype.union(virtualBottomSiteIndex, sitePos1d);
        }
    }

    /**
     * Is site (row, col) open?
     * @param row index of row in percolation system
     * @param col index of column in percolation system
     * @return result if site is already open
     */
    public boolean isOpen(int row, int col) {
        //check bounderies
        checkBoundaries(row - GRID_INDEX_SHIFT, col - GRID_INDEX_SHIFT);
        //check if it is open
        return percolationSystem[row - GRID_INDEX_SHIFT][col - GRID_INDEX_SHIFT];
    }

    /**
     * Is site (row, col) full?
     * @param row index of row in percolation system
     * @param col index of column in percolation system
     * @return result if an open site can be connected to an open site
     * in the top row via a chain of neighboring (left, right, up, down) open sites
     */
    public boolean isFull(int row, int col) {
        //check bounderies
        checkBoundaries(row - GRID_INDEX_SHIFT, col - GRID_INDEX_SHIFT);
        //convert 2d coords of percolationSystem to 1d coords of weighted union-find datatype
        int sitePos1d = xyTo1D(row, col);
        //connected(int p, int q) returns true if the the two sites are in the same component.
        return unionFindDatatype.connected(0, sitePos1d);
    }

    /**
     * Number of open sites
     * @return number of open sites
     */
    public int numberOfOpenSites() {
        return openSitesCounter;
    }

    /**
     * Does the system percolate?
     * @return result if the system percolates
     */
    public boolean percolates() {
        boolean result;
        if (gridSize == 1) {
            result = percolationSystem[0][0];
        }
        //connected(int p, int q) returns true if the the two sites are in the same component.
        // p = 0, q = n * n + 1
        else {
            result = unionFindDatatype.connected(0, virtualBottomSiteIndex);
        }
        return result;
    }

    /**
     * Checks boundaries of the percolation system
     * @param i row index
     * @param j column index
     */
    private void checkBoundaries(int i, int j) {
        if (i >= gridSize || j >= gridSize || i < 0 || j < 0)
            throw new java.lang.IndexOutOfBoundsException(
                    "Index is out of bounds");
    }

    /**
     * Maps site from a 2-dimensional (row, column) pair to a 1-dimensional union find object index
     * @param i row
     * @param j column
     * @return index of site in 1-dimensional union find object
     */
    private int xyTo1D(int i, int j) {
        return gridSize * (i - 1) + j;
    }

    /**
     * Connects newly opened site to its adjacent open site
     * @param row index of row in percolation system
     * @param col index of column in percolation system
     * @param sitePos1d index of site in 1-dimensional union find object
     */
    private void connectToAdjacentSite(int row, int col, int sitePos1d) {
        try {
            if (isOpen(row, col)) {
                unionFindDatatype.union(sitePos1d, xyTo1D(row, col));
            }
        } catch (IndexOutOfBoundsException e) {

        }
    }

}
