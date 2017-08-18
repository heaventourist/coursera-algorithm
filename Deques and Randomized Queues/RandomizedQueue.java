import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Arrays;


public class RandomizedQueue<Item> implements Iterable<Item> 
{
	private Item[] queue;
	private int count;
	private static final int initialSize = 10;

    public RandomizedQueue()                 // construct an empty randomized queue
    {
    	queue = (Item[])new Object[initialSize];
    	count = 0;
    }
    public boolean isEmpty()                 // is the queue empty?
    {
    	return count == 0;
    }
    public int size()                        // return the number of items on the queue
    {
    	return count;
    }
    public void enqueue(Item item)           // add the item
    {
    	if(item == null)
    		throw new IllegalArgumentException();
    	ensureSize();
    	queue[count++] = item;
    }
    public Item dequeue()                    // remove and return a random item
    {
    	if(count == 0)
    		throw new NoSuchElementException();
    	ensureSize();
    	int index = StdRandom.uniform(count);
    	Item result = queue[index];
    	for(int i = index+1; i<count; i++)
    	{
    		queue[i-1] = queue[i];
    	}
    	queue[--count] = null;
    	return result;
    }
    public Item sample()                     // return (but do not remove) a random item
    {
    	if(count == 0)
    		throw new NoSuchElementException();
    	int index = StdRandom.uniform(count);
    	return queue[index];
    }
    public Iterator<Item> iterator()         // return an independent iterator over items in random order
    {
    	return new RandomizedQueueIterator();
    }
    private class RandomizedQueueIterator implements Iterator<Item>
    {
    	Item[] test = Arrays.copyOf(queue, count);
    	int size = count;
    	public boolean hasNext()
    	{
    		return size != 0;
    	}
    	public Item next()
    	{
    		if(!hasNext())
    			throw new NoSuchElementException();
    		int index = StdRandom.uniform(size);
            Item result = test[index];
            for(int i = index+1; i<size; i++)
    		{
    			test[i-1] = test[i];
    		}
    		test[--size] = null;
    		return result;
    	}
    	public void remove()
    	{
    		throw new UnsupportedOperationException();
    	}

    }
    private void ensureSize()
    {
    	if(count == queue.length)
    		queue = Arrays.copyOf(queue,2*queue.length);
    	else if(count == queue.length/4)
    		queue = Arrays.copyOf(queue, queue.length/2);
    }
    public static void main(String[] args)   // unit testing (optional)
    {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        rq.enqueue(4);
        rq.enqueue(5);
        rq.dequeue();
        rq.dequeue();
        rq.enqueue(6);
        rq.dequeue();
        Iterator<Integer> it = rq.iterator();
        while(it.hasNext())
            StdOut.println(it.next());
    }
}