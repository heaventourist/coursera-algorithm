import edu.princeton.cs.algs4.MinPQ;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Comparator;
import java.util.Stack;
import java.util.ArrayList;


public class Solver {
	private boolean solvable;
	private SearchNode goal;
	private Queue<Board> boards;
    public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
    {
    	if(initial == null)
    		throw new IllegalArgumentException();
    	MinPQ<SearchNode> pq = new MinPQ<>(new SearchNodeComparator());
        
    	pq.insert(new SearchNode(initial,0,null,1));
    	pq.insert(new SearchNode(initial.twin(),0,null,2));
    	while(true)
    	{
    		goal = pq.delMin();

    		if(goal.board.isGoal())
    		{
    			if(goal.label == 1)
    				solvable = true;
    			else if(goal.label == 2)
    				solvable = false;
    			break;
    		}

            Iterable<Board> neighbors = goal.board.neighbors();

    		for(Board e: neighbors)
    		{ 
    			if(goal.prev == null || !goal.prev.board.equals(e))  			
    				pq.insert(new SearchNode(e,goal.moves+1,goal,goal.label));
    		}
    	}

    	Stack<Board> tmp = new Stack<>();
    	tmp.add(goal.board);
    	while(goal.prev != null)
    	{
    		tmp.add(goal.prev.board);
    		goal = goal.prev;
    	}
    	boards = new LinkedList<>();
    	while(!tmp.isEmpty())
    		boards.add(tmp.pop());
    }

    private class SearchNodeComparator implements Comparator<SearchNode>
    {
    	public int compare(SearchNode S1, SearchNode S2)
    	{
    		return S1.priority() > S2.priority() ? 1:(S1.priority()==S2.priority()?0:-1);
    	}
    }

    private class SearchNode
    {
    	Board board;
    	int moves;
    	SearchNode prev;
    	int label;

    	private SearchNode(Board board, int moves, SearchNode prev, int label)
    	{
    		this.board = board;
    		this.moves = moves;
    		this.prev = prev;
    		this.label = label;
    	}

    	private int priority() 
    	{
            return moves + board.manhattan();
        }
    }
    public boolean isSolvable()            // is the initial board solvable?
    {
    	return solvable;
    }
    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    {
    	return isSolvable()?boards.size()-1: -1;
    }
    public Iterable<Board> solution()      // sequence of boards in a shorgoal solution; null if unsolvable
    {
    	if(!isSolvable())
    		return null;
    	else
    		return boards;
    }
    public static void main(String[] args) // solve a slider puzzle (given below)
    {
    	int[][] array = {{0,1,3},{4,2,5},{7,8,6}};
    	int[][] array2 = {{1,2,3},{4,5,6},{8,7,0}};
    	int[][] array1 = {{1,3},{2,0}};
    	Board test = new Board(array2);
    	Solver solve = new Solver(test);
    	if(solve.isSolvable())
    		System.out.println("solvable");
    	else
    		System.out.println("unsolvable");

    	System.out.println("need "+solve.moves()+" steps to reach the goal");
    	System.out.println(solve.solution());
    }
}