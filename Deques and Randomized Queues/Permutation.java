import edu.princeton.cs.algs4.*;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Permutation {
   public static void main(String[] args)
   {
   	if(args.length<1)
   	{
   		throw new IllegalArgumentException("Need parameters!");
   	}
    RandomizedQueue<String> rq = new RandomizedQueue<>();
   	int k = Integer.parseInt(args[0]);
    while(!StdIn.isEmpty()) rq.enqueue(StdIn.readString());
    for (int i = 0; i < k; i++) StdOut.println(rq.dequeue());
   }
}