import java.util.ArrayList;
import java.util.Arrays;


public class FastCollinearPoints {
    
	private final LineSegment[] lines;
    public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
    {
        if(points == null)
            throw new IllegalArgumentException();
        for(Point element:points)
            if(element == null)
                throw new IllegalArgumentException();
        for(int i=0; i<points.length-1; i++)
        {
            for(int j=i+1; j<points.length; j++)
            {
                if(points[i].compareTo(points[j])==0)
                    throw new IllegalArgumentException();
            }
        }
    	ArrayList<String> keyList = new ArrayList<>();
        ArrayList<LineSegment> valueList = new ArrayList<>();
    	Point[] pointsCopy = Arrays.copyOf(points, points.length);
    	for(int p=0; p<points.length; p++)
    	{
    		Arrays.sort(pointsCopy, points[p].slopeOrder());

    		int start = 1;
    		for(int end=2; end<=pointsCopy.length; end++)
    		{
    			if(end != pointsCopy.length && 
                    (points[p].slopeTo(pointsCopy[end]) == points[p].slopeTo(pointsCopy[start])))
    				continue;
    			else
    			{
                    if(end-start < 3)
                    {
                        start = end;
                    }
                    else
                    {
                        Arrays.sort(pointsCopy,start,end);
                        Point min = points[p].compareTo(pointsCopy[start])<0?points[p]:pointsCopy[start];
                        Point max = points[p].compareTo(pointsCopy[end-1])>0?points[p]:pointsCopy[end-1];
                        LineSegment value = new LineSegment(min, max);
                        String key = min+"->"+max;
                        if(!keyList.contains(key))
                        {
                            keyList.add(key);
                            valueList.add(value);
                        }
                        start = end;
                    }   				
    			}
    		}
    	}
    	lines = valueList.toArray(new LineSegment[valueList.size()]);
    }
    public int numberOfSegments()        // the number of line segments
    {
    	return lines.length;
    }
    public LineSegment[] segments()                // the line segments
    {
    	return Arrays.copyOf(lines, lines.length);
    }
}