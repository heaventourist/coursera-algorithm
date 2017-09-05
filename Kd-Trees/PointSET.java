import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import java.util.ArrayList;
import java.lang.IllegalArgumentException;

public class PointSET {
	private final SET<Point2D> setOfPoints;
   	public PointSET()                               // construct an empty set of points 
   	{
   		setOfPoints = new SET<Point2D>();
   	}
   	public boolean isEmpty()                      // is the set empty? 
   	{
   		return setOfPoints.isEmpty();
   	}
   	public int size()                         // number of points in the set 
   	{
   		return setOfPoints.size();
   	}
	public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
	{
		if(p == null)
			throw new IllegalArgumentException();
		setOfPoints.add(p);
	}
	public boolean contains(Point2D p)            // does the set contain point p? 
	{
		if(p == null)
			throw new IllegalArgumentException();
		return setOfPoints.contains(p);
	}
	public void draw()                         // draw all points to standard draw
	{
		for(Point2D e: setOfPoints)
		{
			e.draw();
		}
	} 
	public Iterable<Point2D> range(RectHV rect)     // all points that are inside the rectangle (or on the boundary) 
	{
		if(rect == null)
			throw new IllegalArgumentException();
		ArrayList<Point2D> result = new ArrayList<>();
		for(Point2D e: setOfPoints)
		{
			if(rect.contains(e))
				result.add(e);
		}
		return result;
	}
	public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty 
	{
		if(p == null)
			throw new IllegalArgumentException();
		Point2D nearest = null;
		for(Point2D e: setOfPoints)
		{
			if(nearest == null || nearest.distanceTo(p)>e.distanceTo(p))
				nearest = e;
		}
		return nearest;
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

		PointSET set = new PointSET();
		set.insert(point1);
		set.insert(point2);
		set.insert(point3);
		set.insert(point4);
		set.insert(point5);
		set.insert(point6);

		System.out.println("The size of the set = "+set.size());
		if(set.isEmpty())
			System.out.println("Set is empty");
		else
			System.out.println("Set is not empty");

		if(set.contains(point7))
			System.out.println("Set contains point7");
		else
			System.out.println("Set doesn't contain point7");

		set.draw();

		ArrayList<Point2D> tmp = new ArrayList<>();
		for(Point2D e: set.range(rect))
			tmp.add(e);
		System.out.println(tmp);

		Point2D neighbour = set.nearest(point7);
		System.out.println("The nearest point is "+neighbour);

	}
}