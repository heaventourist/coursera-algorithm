
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;


public class PercolationStats
{
	private final double[] openedSites;

	// perform trials independent experiments on an n-by-n grid
	public PercolationStats(int n, int trials)
	{
		if(n<=0 || trials<=0)
			throw new IllegalArgumentException("Should satisfy: n>0 && trials>0");
		openedSites = new double[trials];
		for(int i=0; i<trials; i++)
		{
			Percolation test = new Percolation(n);
			
			while(!test.percolates())
		    {
		    	int x = StdRandom.uniform(n)+1;
			    int y = StdRandom.uniform(n)+1;

			    if(!test.isOpen(x,y))
			        test.open(x,y);
		    }
			openedSites[i] = (double)test.numberOfOpenSites()/(n*n);
		}	
	}

    // sample mean of percolation threshold
	public double mean()
	{
		return StdStats.mean(openedSites);
	}

	// sample standard deviation of percolation threshold
	public double stddev()
	{
		return StdStats.stddev(openedSites);
	}

	// low  endpoint of 95% confidence interval
	public double confidenceLo()
	{
		return StdStats.mean(openedSites) - 1.96*(StdStats.stddev(openedSites))/Math.sqrt(openedSites.length);
	}

	// high endpoint of 95% confidence interval
	public double confidenceHi()
	{
		return StdStats.mean(openedSites) + 1.96*(StdStats.stddev(openedSites))/Math.sqrt(openedSites.length);
	}

	// test client
	public static void main(String[] args)
	{
		if(args.length<2) 
		{
			StdOut.println("not enough arguments");
			return;
		}

		int arg1=Integer.parseInt(args[0]), arg2=Integer.parseInt(args[1]);
		PercolationStats test = new PercolationStats(arg1,arg2);

		StdOut.printf("mean	= %f\n",test.mean());
		StdOut.printf("stddev	= %f\n",test.stddev());
		StdOut.printf("95%% confidence interval	= [%f,%f]\n",test.confidenceLo(), test.confidenceHi());
	}
}