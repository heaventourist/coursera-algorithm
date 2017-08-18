
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> 
{
    private Node first, last;
    private int count;

	private class Node
	{
		private Item item;
		private Node next, prev;

		Node(Item i, Node n, Node p)
		{
			item = i;
			next = n;
			prev = p;
		}
	}

    public Deque()                           // construct an empty deque
    {
    	first = null;
    	last = null;
    	count = 0;
    }
    public boolean isEmpty()                 // is the deque empty?
    {
    	return count == 0;
    }
    public int size()                        // return the number of items on the deque
    {
    	return count;
    }
    public void addFirst(Item item)          // add the item to the front
    {
    	if(item == null) 
    		throw new IllegalArgumentException();
    	if(count == 0) 
    	{
    		last = new Node(item, null, null);
            first = last;
    	}
    	else
    	{
            Node old = first;
    		first = new Node(item, first, null);
            old.prev = first;
    	}
    	count++;   	
    }
    public void addLast(Item item)           // add the item to the end
    {
    	if(item == null) 
    		throw new IllegalArgumentException();
    	if(count == 0)
    	{
    		last = new Node(item, null, null);
            first = last;
    	}
    	else
    	{
    		last.next = new Node(item, null, last);
    		last = last.next;
    	}
    	count++;
    }
    public Item removeFirst()                // remove and return the item from the front
    {
    	if(count == 0)
    		throw new NoSuchElementException();
    	Item tmp = first.item;
        if(count == 1)
        {
            first = null;
            last = null;
        }
        else
        {
            Node old = first;
            first = first.next;
            first.prev = null;
            old.next = null;
        }
    	count--;
    	return tmp;
    }
    public Item removeLast()                 // remove and return the item from the end
    {
    	if(count == 0)
    		throw new NoSuchElementException();
    	Item tmp = last.item;
        if(count == 1)
        {
            first = null;
            last = null;
        }
        else
        {
            Node old = last;
            last = last.prev;
            last.next = null;
            old.prev = null;
        }
    	count--;
    	return tmp;
    }
    public Iterator<Item> iterator()         // return an iterator over items in order from front to end
    {
    	return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item>
    {
    	Node current = first;
    	public boolean hasNext()
    	{
            return current != null;
    	}
    	public Item next()
    	{
    		if(!hasNext())
    			throw new NoSuchElementException();
    		Item tmp = current.item;
    		current = current.next;
    		return tmp;
    	}
    	public void remove()
    	{
    		throw new UnsupportedOperationException();
    	}
    }
    public static void main(String[] args)   // unit testing (optional)
    {
        Deque<Integer> dq = new Deque<>();
        dq.addFirst(0);
        dq.addFirst(1);
        dq.addFirst(2);
        dq.addFirst(3);
        dq.addFirst(4);
        dq.addFirst(5);
        dq.addFirst(6);
        dq.removeFirst();
        dq.removeLast();
        Iterator<Integer> it = dq.iterator();
        while(it.hasNext())
            StdOut.println(it.next());

    }
}