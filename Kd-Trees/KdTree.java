import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import java.util.ArrayList;
import java.lang.IllegalArgumentException;

public class KdTree {

	private static final int LEFT = 1;
	private static final int RIGHT = 2;
	private static final int ABOVE = 3;
	private static final int BELOW = 4;

	private Node root;
	private class Node
	{
		Point2D point;
		Node left, right;
		int N;
		int height;
		Node(Point2D point, int N, int height)
		{
			this.point = point;
			this.N = N;
			this.height = height;
			left = null;
			right = null;
		}
	}	

    public KdTree()                               // construct an empty set of points 
   	{
   		root = null;
   	}
   	public boolean isEmpty()                      // is the set empty? 
   	{
   		return root == null;
   	}
   	public int size()                         // number of points in the set 
   	{
   		return size(root);
   	}
   	private int size(Node x)
	{
		if(x == null) return 0;
		else return x.N;
	}
	public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
	{
		if(p == null)
			throw new IllegalArgumentException();
		if(!contains(p))
			root = insert(root, p, 0);
	}
	private Node insert(Node h, Point2D p, int height)
	{
		if(h == null)
	    	return new Node(p, 1, height);
		if(h.height%2 == 0)
		{
			if(h.point.x()>p.x())
				h.left = insert(h.left, p, height+1);
			else
				h.right = insert(h.right, p, height+1);
		}
		else
		{
			if(h.point.y()>p.y())
				h.left = insert(h.left, p, height+1);
			else
				h.right = insert(h.right, p, height+1);
		}

		h.N = size(h.left) + size(h.right) + 1;
		return h;

	}
	public boolean contains(Point2D p)            // does the set contain point p? 
	{
		if(p == null)
			throw new IllegalArgumentException();
		return search(root, p);
	}
	private boolean search(Node h, Point2D p)
	{
		if(h == null)
			return false;
		if(p.equals(h.point))
			return true;

		if(h.height%2 == 0)
		{
			if(h.point.x()>p.x())
				return search(h.left, p);
			else
				return search(h.right, p);
		}
		else
		{
			if(h.point.y()>p.y())
				return search(h.left, p);
			else
				return search(h.right, p);
		}
	}
	public void draw()                         // draw all points to standard draw
	{
		draw(root);
	} 
	private void draw(Node h)     // preorder
	{
		if(h != null)
		{
			h.point.draw();
			draw(h.left);
			draw(h.right);
		}
	}
	public Iterable<Point2D> range(RectHV rect)     // all points that are inside the rectangle (or on the boundary) 
	{
		if(rect == null)
			throw new IllegalArgumentException();
		ArrayList<Point2D> result = new ArrayList<>();
		rangeSearch(root, rect, result);

		return result;
	}
	private void rangeSearch(Node p, RectHV rect, ArrayList<Point2D> list)
	{
		if(p == null)
			return;
		if(rect.contains(p.point))
			list.add(p.point);
		if(p.height%2==0)
		{
			if(p.point.x()<rect.xmin())
				rangeSearch(p.right, rect, list);
			else if(p.point.x()>rect.xmax())
				rangeSearch(p.left, rect, list);
			else
			{
				rangeSearch(p.left, rect, list);
				rangeSearch(p.right, rect, list);
			}
		}
		else
		{
			if(p.point.y()<rect.ymin())
				rangeSearch(p.right, rect, list);
			else if(p.point.y()>rect.ymax())
				rangeSearch(p.left, rect, list);
			else
			{
				rangeSearch(p.left, rect, list);
				rangeSearch(p.right, rect, list);
			}
		}
		
	}
	public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty 
	{
		if(p == null)
			throw new IllegalArgumentException();
		Double nearestDistance = 0.0;
		return nearest(p, root, null, nearestDistance, null);
	}
	private Point2D nearest(Point2D target, Node p, Point2D nearest, Double nearestDistance, Orientation ori)
	{
		if(p == null)
			return nearest;
		if(nearest == null || nearest.distanceTo(target)>p.point.distanceTo(target))
		{
			nearest = new Point2D(p.point.x(), p.point.y());
			nearestDistance = p.point.distanceTo(target);
		}

		if(p.height%2 == 0)
		{
			RectHV rectLeft, rectRight;
			if(ori == null)
			{
				rectLeft = new RectHV(0.0, 0.0, p.point.x(), 1.0);
				rectRight = new RectHV(p.point.x(), 0.0, 1.0, 1.0);
			}
			else if(ori.direction == ABOVE)
			{
				rectLeft = new RectHV(0.0, ori.n.point.y(), p.point.x(), 1.0);
				rectRight = new RectHV(p.point.x(), ori.n.point.y(), 1.0, 1.0);
			}
			else
			{
				rectLeft = new RectHV(0.0, 0.0, p.point.x(), ori.n.point.y());
				rectRight = new RectHV(p.point.x(), 0.0, 1.0, ori.n.point.y());
			}
			
			if(p.point.x()<=target.x())
			{
				nearest = nearest(target, p.right, nearest, nearestDistance, new Orientation(p, RIGHT));
				if(rectLeft.distanceTo(target) > nearestDistance)
					return nearest;
				nearest = nearest(target, p.left, nearest, nearestDistance, new Orientation(p, LEFT));
			}
			else
			{
				nearest = nearest(target, p.left, nearest, nearestDistance, new Orientation(p, LEFT));
				if(rectRight.distanceTo(target) > nearestDistance)
					return nearest;
				nearest = nearest(target, p.right, nearest, nearestDistance, new Orientation(p, RIGHT));
			}
		}
		else
		{
			RectHV rectAbove, rectBelow;
			if(ori == null)
			{
				rectAbove = new RectHV(0.0, 0.0, 1.0, p.point.y());
			    rectBelow = new RectHV(0.0, p.point.y(), 1.0, 1.0);
			}
			else if(ori.direction == LEFT)
			{
				rectAbove = new RectHV(0.0, 0.0, ori.n.point.x(), p.point.y());
			    rectBelow = new RectHV(0.0, p.point.y(), ori.n.point.x(), 1.0);
			}
			else
			{
				rectAbove = new RectHV(ori.n.point.x(), 0.0, 1.0, p.point.y());
			    rectBelow = new RectHV(ori.n.point.x(), p.point.y(), 1.0, 1.0);
			}
				
			if(p.point.y()<=target.y())
			{
				nearest = nearest(target, p.right, nearest,nearestDistance, new Orientation(p, ABOVE));
				if(rectBelow.distanceTo(target) > nearestDistance)
					return nearest;
				nearest = nearest(target, p.left, nearest, nearestDistance, new Orientation(p, BELOW));
			}
			else
			{
				nearest = nearest(target, p.left, nearest, nearestDistance, new Orientation(p, BELOW));
				if(rectAbove.distanceTo(target) > nearestDistance)
					return nearest;
				nearest = nearest(target, p.right, nearest, nearestDistance, new Orientation(p, ABOVE));
			}
		}
		return nearest;		
	}

	private final class Orientation
	{
		Node n;
		int direction;

		Orientation(Node n, int direction)
		{
			this.n = n;
			this.direction = direction;
		}

	}

	public static void main(String[] args)                  // unit testing of the methods (optional) 
	{
		RectHV rect = new RectHV(0.5, 0.5, 0.7, 0.7);
		Point2D point1 = new Point2D(0.1, 0.1);
		Point2D point2 = new Point2D(0.2, 0.8);
		Point2D point3 = new Point2D(0.5, 0.5);
		Point2D point4 = new Point2D(0.6, 0.7);
		Point2D point5 = new Point2D(0.6, 0.6);
		Point2D point6 = new Point2D(0.55, 0.55);
		Point2D point7 = new Point2D(0.1, 0.9);
		Point2D point8 = new Point2D(0.1, 0.1);

		KdTree set = new KdTree();
		set.insert(point1);
		set.insert(point2);
		set.insert(point3);
		set.insert(point4);
		set.insert(point5);
		set.insert(point6);
		set.insert(point8);

		RectHV all = new RectHV(0.0,0.0,1.0,1.0);
		ArrayList<Point2D> tmp1 = new ArrayList<>();
		for(Point2D e: set.range(all))
			tmp1.add(e);
		System.out.println(tmp1);


		System.out.println("The size of the set = "+set.size());
		if(set.isEmpty())
			System.out.println("Set is empty");
		else
			System.out.println("Set is not empty");

		if(set.contains(point7))
			System.out.println("Set contains point7");
		else
			System.out.println("Set doesn't contain point7");

		//set.draw();

		ArrayList<Point2D> tmp = new ArrayList<>();
		for(Point2D e: set.range(rect))
			tmp.add(e);
		System.out.println(tmp);

		Point2D neighbour = set.nearest(point7);
		System.out.println("The nearest point is "+neighbour);

	}
}