package geometries;
import java.util.ArrayList;
import java.util.List;

import primitives.BoundingBox;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

public class Plane extends Geometry
{
	
	private Point3D _p;
	private Vector _normal;
	
	
	//----------------------------------second type of c-tors
	/**
	 * constructor that sets the fields
	 * @param color - the color of the plane
	 * @param material - he material of the plane
	 * @param a - a point on the plane
	 * @param b - a point on the plane
	 * @param c - a point on the plane
	 */
	public Plane(Color color,  Material material, Point3D a, Point3D b, Point3D c)
	{
		super(color, material);
		
		Bounding_Box = new BoundingBox();
		
		_p = a;		
		Vector vec1 = new Vector(b.subtract(a));
		Vector vec2 = new Vector(c.subtract(a));
		
		Vector vec3 = vec1.crossProduct(vec2);		
		_normal = vec3.normalize();
				
	}
	
	/**
	 * constructor that adds a default color if the color of the geometry is not given
	 * @param a - a point on the plane
	 * @param b - a point on the plane
	 * @param c - a point on the plane
	 */
	public Plane(Point3D a, Point3D b, Point3D c)
	{
		this(Color.BLACK, a, b, c);
	}
	
	/**
	 * constructor that adds a default material if the material of the geometry is not given
	 * @param color - the color of the plane
	 * @param a - a point on the plane
	 * @param b - a point on the plane
	 * @param c - a point on the plane
	 */
	public Plane(Color color, Point3D a, Point3D b, Point3D c)
	{
		this(color, new Material(0d, 0d, 0) , a, b, c);
	}
	//----------------------------------second type of c-tors
	
	
	//-------------------------------------------------- one type of c-tors
	/**
	 * constructor that sets the values of the plane
	 * @param point - a point on the plane
	 * @param normal - normal to the plane
	 */
	public Plane(Color color, Material material ,Point3D point, Vector normal)
	{
		super(color, material);
		
		Bounding_Box = new BoundingBox();
		
		_p = point;	                    
		_normal = normal.normalize(); 				
    }	
	
	/**
	 * constructor that sets the values of the plane
	 * @param color - the color of the plane
	 * @param point - a point on the plane
	 * @param normal - normal to the plane
	 */
	public Plane(Color color,Point3D point, Vector normal)
	{
		this(color, new Material(0d, 0d, 0), point, normal);
	}
	
	/**
	 * constructor that adds a default color if the color of the geometry is not given
	 * @param point - a point on the plane
	 * @param normal - normal to the plane
	 */
	public Plane(Point3D point, Vector normal)
	{
		this(Color.BLACK, point, normal); 			
    }
	//-------------------------------------------------- one type of c-tors
	
	/**
	 * gets the normal of the plane
	 * @return Vector - the normal to the plane
	 */
	public Vector getNormal()
	{
		return _normal;
	}

	/**
	 * gets the normal of the plane
	 * @return Vector - the normal to the plane
	 */
	@Override
	public Vector getNormal(Point3D point)
	{
		return _normal;
	}
	
	/**
	 * a function that calculates the intersections
	 * @param Ray - ray we want to find intersections with
	 * @return List<Point3D> of intersections with plane
	 */
	public List<GeoPoint> findIntersections(Ray ray)
	{
		if(!Bounding_Box.Intersects(ray))
			return null;
		
		ArrayList<GeoPoint> intersectionPoint = null;
		Vector n = new Vector(_normal);
		Point3D tmp_pq = new Point3D(_p);
		Vector pq = new Vector(0,0,1); //temp initialization
		
		try
		{			
			pq = new Vector(tmp_pq.subtract(ray.get_Point()));
			
		}
		catch(IllegalArgumentException e) // this means that the ray starts at the exact point that resembles the plane and that is why we'll get ZERO vector from subtraction
		{
			
			return intersectionPoint;
		}
	 
		double mone = n.dotProduct(pq);
		double mechane = n.dotProduct(new Vector(ray.getDirection()));
		if(Util.isZero(mechane))
			return intersectionPoint;
		double t = Util.alignZero(mone/mechane);
		 	
		if( t > 0)
		{
			intersectionPoint = new ArrayList<GeoPoint>();
			
			//Vector vec = new Vector(ray.getDirection());
			//vec = vec.scale(t);//t1*vec
			//Point3D p = new Point3D(ray.get_Point());
			//p = p.add(vec);
			
			//refactor
			Point3D p = ray.getPoint(t);	
			GeoPoint geop = new GeoPoint(this, p);
			
			intersectionPoint.add(geop);
			return intersectionPoint;
		}
		
		return intersectionPoint;
	}
	
	/**
	 * this function sets the values of the bounding box of the sphere
	 */
	@Override
	public void setBoundingBox() 
	{	
		  // we did not implement because it was a bonus to write a function that finds 
		  // intersectons- and we did not write it!!
	}

}
