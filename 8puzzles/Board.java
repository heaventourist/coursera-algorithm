import java.util.Random;
import java.util.Queue;
import java.util.LinkedList;


public class Board{
	private final char[][] blocks;
    private final int d;
    private int hammingValue;
    private int manhattanValue;
    public Board(int[][] blocks)           // construct a board from an n-by-n array of blocks
    {
        d = blocks.length;
    	this.blocks = new char[d][d];
        manhattanValue = 0;
        hammingValue = 0;
    	for(int i=0; i<d; i++)
    	{
    		for(int j=0; j<d; j++)
    		{
    			this.blocks[i][j] = (char)blocks[i][j];
                if(blocks[i][j]!=0 && blocks[i][j]-1!=i*d+j)
                    hammingValue++;
                if(blocks[i][j] != 0)
                    manhattanValue += Math.abs((blocks[i][j]-1)/d-i) + Math.abs((blocks[i][j]-1)%d-j);
    		}
    	}
    }
                                           // (where blocks[i][j] = block in row i, column j)
    public int dimension()                 // board dimension n
    {
    	return d;
    }
    public int hamming()                   // number of blocks out of place
    {
        return hammingValue;
    }
    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
        return manhattanValue;
    }
    public boolean isGoal()                // is this board the goal board?
    {
    	for(int i=0; i<d; i++)
        {
            for(int j=0; j<d; j++)
            {
                if(blocks[i][j]!=0 && blocks[i][j]-1!=i*d+j)
                    return false;
            }
        }
        return true;
    }
    public Board twin()                    // a board that is obtained by exchanging any pair of blocks
    {
    	int[][] blocksCopy = new int[d][d];
    	int rowZero = -1, colZero = -1;       
    	for(int i=0; i<d; i++)
    	{
    		for(int j=0; j<d; j++)
    		{
    			blocksCopy[i][j] = blocks[i][j];
    			if(blocks[i][j] == 0)
    			{
    				rowZero = i;
    				colZero = j;
    			}
    		}
    	}
    	int tmp = blocksCopy[(rowZero+1)%d][colZero];
    	blocksCopy[(rowZero+1)%d][colZero] = blocksCopy[rowZero][(colZero+1)%d];
    	blocksCopy[rowZero][(colZero+1)%d] = tmp;

    	Board t = new Board(blocksCopy);
    	return t;
    }
    public boolean equals(Object y)        // does this board equal y?
    {
        if(y==null || y.getClass() != Board.class || d != ((Board)y).dimension())
            return false;
        if(y == this)
            return true;
    	for(int i=0; i<d; i++)
    	{
    		for(int j=0; j<d; j++)
    		{
    			if(this.blocks[i][j] != ((Board)y).blocks[i][j])
    				return false;
    		}
    	}
    	return true;
    }
    public Iterable<Board> neighbors()     // all neighboring boards
    {
    	Queue<Board> q = new LinkedList<>();

    	int[][] blocksCopy = new int[d][d];       
        int rowZero = -1, colZero = -1;
    	for(int i=0; i<d; i++)
    	{
    		for(int j=0; j<d; j++)
    		{
    			blocksCopy[i][j] = blocks[i][j];
    			if(blocks[i][j] == 0)
    			{
    				rowZero = i;
    				colZero = j;
    			}
    		}
    	}

    	if(rowZero-1>=0)
    	{
    		int tmp = blocksCopy[rowZero][colZero];
    		blocksCopy[rowZero][colZero] = blocksCopy[rowZero-1][colZero];
    		blocksCopy[rowZero-1][colZero] = tmp;           

            Board up = new Board(blocksCopy);
    		up.manhattanValue = manhattanValue-Math.abs((blocksCopy[rowZero][colZero]-1)/d-(rowZero-1))+
    	                        Math.abs((blocksCopy[rowZero][colZero]-1)/d-rowZero);
    	    up.hammingValue = hammingValue-(((blocksCopy[rowZero][colZero]-1)/d == rowZero-1 && 
    	    	                           (blocksCopy[rowZero][colZero]-1)%d == colZero)?0:1) +
    	                                   (((blocksCopy[rowZero][colZero]-1)/d == rowZero && 
    	    	                           (blocksCopy[rowZero][colZero]-1)%d == colZero)?0:1);
    		
    		q.add(up);
    		
    		tmp = blocksCopy[rowZero][colZero];
    		blocksCopy[rowZero][colZero] = blocksCopy[rowZero-1][colZero];
    		blocksCopy[rowZero-1][colZero] = tmp;
    	}

    	if(rowZero+1<d)
    	{
    		int tmp = blocksCopy[rowZero][colZero];
    		blocksCopy[rowZero][colZero] = blocksCopy[rowZero+1][colZero];
    		blocksCopy[rowZero+1][colZero] = tmp;

    		Board down = new Board(blocksCopy);
    		down.manhattanValue = manhattanValue-Math.abs((blocksCopy[rowZero][colZero]-1)/d-(rowZero+1))+
    	                          Math.abs((blocksCopy[rowZero][colZero]-1)/d-rowZero);
    	    down.hammingValue = hammingValue-(((blocksCopy[rowZero][colZero]-1)/d == rowZero+1 && 
    	    	                           (blocksCopy[rowZero][colZero]-1)%d == colZero)?0:1) +
    	                                   (((blocksCopy[rowZero][colZero]-1)/d == rowZero && 
    	    	                           (blocksCopy[rowZero][colZero]-1)%d == colZero)?0:1);
    		
    		q.add(down);

    		tmp = blocksCopy[rowZero][colZero];
    		blocksCopy[rowZero][colZero] = blocksCopy[rowZero+1][colZero];
    		blocksCopy[rowZero+1][colZero] = tmp;
    	}

    	if(colZero-1>=0)
    	{
    		int tmp = blocksCopy[rowZero][colZero];
    		blocksCopy[rowZero][colZero] = blocksCopy[rowZero][colZero-1];
    		blocksCopy[rowZero][colZero-1] = tmp;

            Board left = new Board(blocksCopy);
    		left.manhattanValue = manhattanValue-Math.abs((blocksCopy[rowZero][colZero]-1)%d-(colZero-1))+
    	                        Math.abs((blocksCopy[rowZero][colZero]-1)%d-colZero);
    	    left.hammingValue = hammingValue-(((blocksCopy[rowZero][colZero]-1)/d == rowZero && 
    	    	                           (blocksCopy[rowZero][colZero]-1)%d == colZero-1)?0:1) +
    	                                   (((blocksCopy[rowZero][colZero]-1)/d == rowZero && 
    	    	                           (blocksCopy[rowZero][colZero]-1)%d == colZero)?0:1);
    		
    		q.add(left);

    		tmp = blocksCopy[rowZero][colZero];
    		blocksCopy[rowZero][colZero] = blocksCopy[rowZero][colZero-1];
    		blocksCopy[rowZero][colZero-1] = tmp;
    	}

    	if(colZero+1<d)
    	{
    		int tmp = blocksCopy[rowZero][colZero];
    		blocksCopy[rowZero][colZero] = blocksCopy[rowZero][colZero+1];
    		blocksCopy[rowZero][colZero+1] = tmp;

            Board right = new Board(blocksCopy);
    		right.manhattanValue = manhattanValue-Math.abs((blocksCopy[rowZero][colZero]-1)%d-(colZero+1))+
    	                        Math.abs((blocksCopy[rowZero][colZero]-1)%d-colZero);
    	    right.hammingValue = hammingValue-(((blocksCopy[rowZero][colZero]-1)/d == rowZero && 
    	    	                           (blocksCopy[rowZero][colZero]-1)%d == colZero+1)?0:1) +
    	                                   (((blocksCopy[rowZero][colZero]-1)/d == rowZero && 
    	    	                           (blocksCopy[rowZero][colZero]-1)%d == colZero)?0:1);
    		
    		q.add(right);

    		tmp = blocksCopy[rowZero][colZero];
    		blocksCopy[rowZero][colZero] = blocksCopy[rowZero][colZero+1];
    		blocksCopy[rowZero][colZero+1] = tmp;
    	}
    	return q;
    }

    public String toString()               // string representation of this board (in the output format specified below)
    {
    	StringBuilder s = new StringBuilder();
    	s.append(d + "\n");
    	for (int i = 0; i < d; i++) {
        	for (int j = 0; j < d; j++) {
            	s.append(String.format("%2d ", (int)blocks[i][j]));
        	}
        	s.append("\n");
    	}
    	return s.toString();
    }

    public static void main(String[] args) // unit tests (not graded)
    {
    	int[][] array1 = {{1,5,3},{4,2,8},{7,6,0}};
    	Board test1 = new Board(array1);
    	System.out.println("dimension = " +test1.dimension());
    	System.out.println("hamming = " +test1.hamming());
    	System.out.println("manhattan = " +test1.manhattan());

    	int[][] array2 = {{1,7,0},{4,2,8},{5,6,3}};
    	int[][] array3 = {{1,2,3},{4,5,6},{7,8,0}};
    	Board test2 = new Board(array1);
    	Board test3 = new Board(array2);
    	Board test4 = new Board(array3);
    	if(test1.equals(test2))
    		System.out.println("test1 == test2");
    	else
    		System.out.println("test1 != test2");

    	if(test1.equals(test3))
    		System.out.println("test1 == test3");
    	else
    		System.out.println("test1 != test3");

    	if(test1.isGoal())
    		System.out.println("test1 is the goal!");
    	else
    		System.out.println("test1 is not the goal!");

    	if(test4.isGoal())
    		System.out.println("test4 is the goal!");
    	else
    		System.out.println("test4 is not the goal!");
        
        
    	Board tw = test3.twin();
    	System.out.println(tw);

    	Queue<Board> q = (Queue<Board>)test3.neighbors();
    	System.out.println("********************************");
    	while(q.peek()!=null)
    	{
    		System.out.println("dimension = " + q.peek().dimension());
    		System.out.println("hamming = " + q.peek().hamming());
    		System.out.println("manhattan = " + q.peek().manhattan());
    		System.out.println(q.poll());
    		System.out.println("********************************");
    	}


    }
}