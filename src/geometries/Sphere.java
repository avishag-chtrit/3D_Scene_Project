package geometries;

import java.util.ArrayList;
import java.util.List;

import geometries.Intersectable.GeoPoint;
import primitives.*;

/**
 * this class represents a sphere
 * and extends the radial geometry class 
 * @author chetrit
 *
 */
public class Sphere extends RadialGeometry 
{
	/**
	 * presents the center point of the sphere
	 */
	private Point3D _center;
	
	/**
	 * constructor which calls base's constructor
	 * @param c - center point of thee sphere
	 * @param radius - the radius of the sphere
	 */
	/*public Sphere(Point3D c, double radius)
	{
		super(radius);
		_center = new Point3D(c);
	}*/
	
	/**
	 * constructor that sets the values of the fields
	 * @param c - the radius of the radial geometry
	 * @param radius - the radius of the sphere
	 */
	public Sphere(Color emission, Material material, Point3D c, double radius) 
	{
		super(emission, radius);
		
		Bounding_Box = new BoundingBox();
		
		this._material = material;			
		_center = new Point3D(c);
		
		this.setBoundingBox();		
	}
	
	/**
	 * constructor that sets a default color to geometry
	 * @param c - the radius of the radial geometry
	 * @param radius - the radius of the sphere
	 */
	public Sphere(Point3D c, double radius) 
	{
		this(Color.BLACK, new Material(0,0,0), c, radius);	 
	}
	
	/**
	 * constructor that sets a default Material to geometry
	 * @param emission - of the sphere
	 * @param c - the radius of the radial geometry
	 * @param radius - the radius of the sphere
	 */
	public Sphere(Color emission, Point3D c, double radius)
	{
		this(emission, new Material(0, 0, 0), c, radius);
	}
	

	/**
	 * calculates the normal of a sphere
	 * @param Point3D - a point on the sphere
	 * @return Vector - normal to the spesific point on the sphere
	 * this function overrides the function of class Geometry
	 */
	@Override
	public Vector getNormal(Point3D a)
	{	
		Vector normal = new Vector(a.subtract(_center));
		normal.normalize();
		return normal;
	}
	
	/**
	 * this function finds intersections with a ray 
	 * @param ray - the ray we want to find intersections with
	 * @return a list of intersection points with sphere
	 */
	public List<GeoPoint> findIntersections(Ray ray)
	{
		if(!this.Bounding_Box.Intersects(ray)) // if the ray does not intersect the bounding box - then we won't 
			return null;  // calculate intersection points with sphere 
		
		ArrayList <GeoPoint> intersection = null;
		Vector u = new Vector(1,1,1); // temp initialization
		 	
		try
		{
			// center - start_of_ray
			u = _center.subtract(ray.get_Point()); 
		}
		catch(IllegalArgumentException e)
		{
			//after refactoring
			Point3D p = ray.getPoint(this._radius);  

			intersection = new ArrayList<GeoPoint>();		 	
			GeoPoint geop = new GeoPoint(this, p);
					
			intersection.add(geop);
			return intersection;				
		}
		double t_m = u.dotProduct(ray.getDirection());
		double d = Math.sqrt(u.dotProduct(u)-t_m*t_m);
		
		
		if(d > _radius) // there are no intersections
			return null;
		 
		double t_h = Math.pow((Math.pow(_radius, 2)-(Math.pow(d,2))),0.5);
		double t1 = Util.alignZero(t_m-t_h);//2-1=1
		double t2 = Util.alignZero(t_m+t_h);//2+1=3
		
		
		if(t1 > 0)
		{
			intersection = new ArrayList<GeoPoint>();
			
			//after refactoring
			Point3D p1 = ray.getPoint(t1);
			GeoPoint geop = new GeoPoint(this, p1);
				
			intersection.add(geop);			
		}
		
		if(t2 > 0)
		{	
			Point3D p2 = ray.getPoint(t2);
			GeoPoint geop = new GeoPoint(this, p2);
						
			if(intersection == null)
				intersection = new ArrayList<GeoPoint>();
			
			intersection.add(geop);
		}
			
		return intersection;

	}

	/**
	 * this function sets the values of the bounding box of the sphere
	 * this function overrides the function of class Geometry
	 */
	@Override
	public void setBoundingBox() 
	{
		double radius = this._radius;
		
		 double xMinLimit = this._center.getX().get() - radius; // going in the negative direction of x
		 double xMaxLimit = this._center.getX().get() + radius; // going in the positive direction of x
		 
		 double yMinLimit = this._center.getY().get() - radius; // going in the negative direction of y
		 double yMaxLimit = this._center.getY().get() + radius; // going in the positive direction of y
		 
		 double zMinLimit = this._center.getZ().get() - radius; // going in the negative direction of z
		 double zMaxLimit = this._center.getZ().get() + radius; // going in the positive direction of z
		 
		 this.Bounding_Box = new BoundingBox(xMinLimit, xMaxLimit, yMinLimit, yMaxLimit, zMinLimit, zMaxLimit);
		 
		//------------------------------------
		 //System.out.println("Xmin = " + Bounding_Box.get_X_min() + " Xmax = " + Bounding_Box.get_X_max());
	     //System.out.println("Ymin = " + Bounding_Box.get_Y_min() +  " Ymax = " + Bounding_Box.get_Y_max()); 
	     //System.out.println("Zmin = " + Bounding_Box.get_Z_min() +  " Zmax = " + Bounding_Box.get_Z_max()); 
	     //-------------------------------------
	}

}
