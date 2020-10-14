package geometries;

 
import java.util.List;
import primitives.*;
import primitives.BoundingBox;

/**
 * 
 * @author chetrit
 * an interface that all geometries implements- all of them must have a function that calculates the intersection points with ray
 */
public interface Intersectable 
{
	List <GeoPoint> findIntersections(Ray ray);
	
	/**
	 * every intersectable geometry needs to have 
	 * a bounding box with x,y,z minimum and maximum limits
	 */
	//public BoundingBox Bounding_Box = new BoundingBox(1,1,1,1,1,1); // = new BoundingBox();
	
	/**
	 * a function that every intersectable geometry- should have in order to create 
	 * a bounding box
	 */
	public void setBoundingBox();
	
	/**
	 * an helper class thet will help us calculate the specific color of 
	 * a geometry based on the color of the geometry itself- fong model 
	 * @author chetrit
	 */
	public static class GeoPoint 
	{
		/**
		 * the geometry of the point
		 */
	    public Geometry geometry;
	    
	    /**
	     * thepoint of that is on a geometry
	     */
	    public Point3D point;
	    
	    /**
	     * helps to indicate the geometry of the intersection point
	     * @param Geometry - the geometry that the point is on
	     * @param Point3D - the point that is on the geometry
	     */
	    public GeoPoint(Geometry geo, Point3D _point)
	    {
	    	geometry = geo;
	    	point = new Point3D(_point);
	    }
	    
	    /**
	     * gets the geometry
	     * @return Geometry - the geometry that the point is on
	     */
	    public Geometry getGeometry()
	    {
	    	return geometry;
	    }
	    
	    /**
	     * gets the point
	     * @return Point3D - the point that is on the geometry
	     */
	    public Point3D getPoint()
	    {
	    	return point;
	    }
	    
	    
	    /**
	     * a function that checks the equality of two GeoPoint types
	     * @return boolean - whether or not the intersectable types are equal by value
	     * this class overrides the function of object class
	     */
	    @Override
		public boolean equals(Object obj)
		{
	    	if (this == obj) return true;
		      if (obj == null) return false;
		      if (!(obj instanceof GeoPoint)) return false;
		      GeoPoint oth = (GeoPoint)obj;
		      return geometry.equals(oth.geometry) && point.equals(oth.point);
		}     
	}

	
}
