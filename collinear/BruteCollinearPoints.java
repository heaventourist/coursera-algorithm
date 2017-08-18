
import java.util.Arrays;
import java.util.ArrayList;



public class BruteCollinearPoints {
   private final LineSegment[] lines;
  
   public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
   {
   	if(points == null)
   		throw new IllegalArgumentException();
      for(Point element:points)
            if(element == null)
                throw new IllegalArgumentException();
      for(int i=0; i<points.length-1; i++)
      {
         for(int j=i+1; j<points.length; j++)
            if(points[i].compareTo(points[j])==0)
               throw new IllegalArgumentException();
      }
   	ArrayList<Double> keyList = new ArrayList<>();
      ArrayList<ArrayList<Point>> valueList = new ArrayList<>();
      ArrayList<LineSegment> lineList = new ArrayList<>();
   	Point[] pointsCopy = Arrays.copyOf(points, points.length);

   	for(int p=0; p<pointsCopy.length; p++)
   	{
   		for(int q=0; q<pointsCopy.length; q++)
   		{
   		   if(p==q)
   		   	continue;
   		   for(int r=0; r<pointsCopy.length; r++)
   		   {
   		      if(p==r || r==q)
   		       	continue;
   		      for(int s=0; s<pointsCopy.length; s++)
   		      {
   		       	if(p==s || s==q || s==r)
   		       	   continue;

                  double key1 = pointsCopy[p].slopeTo(pointsCopy[q]);
                  double key2 = pointsCopy[p].slopeTo(pointsCopy[r]);
                  double key3 = pointsCopy[p].slopeTo(pointsCopy[s]);
   		       	if(key1 == key2 && key1 == key3)
                  {
                     if(!keyList.contains(key1))
                     {
                        ArrayList<Point> tmp = new ArrayList<>();
                        tmp.add(pointsCopy[p]);
                        tmp.add(pointsCopy[q]);
                        tmp.add(pointsCopy[r]);
                        tmp.add(pointsCopy[s]);

                        valueList.add(tmp);
                        keyList.add(key1);                        
                     }
                     else
                     {
                        boolean found = false;
                        for(int i=0; i<keyList.size(); i++)
                        {
                           if(keyList.get(i).doubleValue() == key1)
                           {
                              ArrayList<Point> tmp = valueList.get(i);

                              if(!tmp.contains(pointsCopy[p]) &&
                                 !tmp.contains(pointsCopy[q]) &&
                                 !tmp.contains(pointsCopy[r]) &&
                                 !tmp.contains(pointsCopy[s]))
                                 continue;

                              if(!tmp.contains(pointsCopy[p]))
                                 tmp.add(pointsCopy[p]);
                              if(!tmp.contains(pointsCopy[q]))
                                 tmp.add(pointsCopy[q]);
                              if(!tmp.contains(pointsCopy[r]))
                                 tmp.add(pointsCopy[r]);
                              if(!tmp.contains(pointsCopy[s]))
                                 tmp.add(pointsCopy[s]);
                              found = true;
                              valueList.set(i,tmp);
                              break;
                           }
                        }
                        if(!found)
                        {
                           ArrayList<Point> tmp = new ArrayList<>();
                           tmp.add(pointsCopy[p]);
                           tmp.add(pointsCopy[q]);
                           tmp.add(pointsCopy[r]);
                           tmp.add(pointsCopy[s]);

                           valueList.add(tmp);
                           keyList.add(key1); 
                        }
                     }                    
                  }
   		      }
   		   }
   		}
      }
      
      for(ArrayList<Point> element: valueList)
      {
         Point[] tmp = element.toArray(new Point[element.size()]);
         Arrays.sort(tmp);
         lineList.add(new LineSegment(tmp[0], tmp[tmp.length-1]));
      }
       
      lines = lineList.toArray(new LineSegment[valueList.size()]);
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