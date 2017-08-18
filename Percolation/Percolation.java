

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;


public class Percolation
{
	private boolean[][] grid;
	private final WeightedQuickUnionUF qU;
	private int count;
	private final int dimension;
	private final int head;
	private final int tail;

	// create n-by-n grid, with all sites blocked
	public Percolation(int n)
	{
		if(n<=0)
			throw new IllegalArgumentException("The dimension should be larger than 0");
		dimension = n;
		grid = new boolean[n][n];
		qU = new WeightedQuickUnionUF(n*n+2);
		head = 0;
		tail = n*n+1;
		count = 0;
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{
				grid[i][j] = false;
			}
		}
	}

    // open site (row, col) if it is not open already
	public void open(int row, int col)
	{
		if(row<1 || row >dimension || col<1 || col>dimension)
			throw new IllegalArgumentException("Row and col should be larger than 0");
	
		if(!isOpen(row,col))
		{
			grid[row-1][col-1] = true;
			count++;
            
            if(row == 1)
            	qU.union(head, dimension*(row-1)+col);

            if(row == dimension)
                qU.union(tail, dimension*(row-1)+col);

			if(row>=2 && grid[row-2][col-1])
				qU.union(dimension*(row-1)+col, dimension*(row-2)+col);

			if(col<dimension && grid[row-1][col])
				qU.union(dimension*(row-1)+col, dimension*(row-1)+col+1);

			if(row<dimension && grid[row][col-1])
				qU.union(dimension*(row-1)+col, dimension*(row)+col);
			
			if(col>=2 && grid[row-1][col-2])
				qU.union(dimension*(row-1)+col, dimension*(row-1)+(col-1));

		}
	}

	// is site (row, col) open?
	public boolean isOpen(int row, int col)
	{
		if(row<1 || row >dimension || col<1 || col>dimension)
			throw new IllegalArgumentException("Row and col should be larger than 0");
		return grid[row-1][col-1];
	}

	// is site (row, col) full?
	public boolean isFull(int row, int col)
	{
		if(row<1 || row >dimension || col<1 || col>dimension)
			throw new IllegalArgumentException("Row and col should be larger than 0");
		return qU.connected(head,dimension*(row-1)+col);
	}

	// number of open sites
	public int numberOfOpenSites()
	{
		return count;
	}

	// does the system percolate?
	public boolean percolates()
	{
		return qU.connected(head,tail);
	}

	// test client
	public static void main(String[] args)
	{
		Percolation test = new Percolation(100);
		test.open(1,5);
		//test.open(2,2);
		if(test.percolates())
			StdOut.println("The matrix percolates");
		else
			StdOut.println("The matrix doesn't percolates");
	}

}