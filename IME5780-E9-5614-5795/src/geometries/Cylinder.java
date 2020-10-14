package geometries;
import java.util.List;

import primitives.*;

/**
 * this class extends the tube class
 * this geometry is finite
 * @author chetrit
 *
 */
public class Cylinder extends Tube
{
	/**
	 * cylinder has height, radius, and a ray
	 */
	private double _height;
	
	/**
	 * constructor that sets the values of the fields
	 * @param h - the height of the cylinder
	 * @param radius - the radius of the cylinder
	 * @param ray - a ray that defines the location of the cylinder
	 */
	public Cylinder(Color color, Material material ,double h, double radius, Ray ray)
	{
		super(ray, radius);
		
		this._material = material;
		this._emission = color;
		
		try
		{		
			if(h > 0)
				_height = h;
			else
				throw new IllegalArgumentException("the height of a Cylinder cannot be zero or nagitive");
		}
		catch(IllegalArgumentException ex)
		{
			System.out.println(ex);
		}					
	}
	
	/**
	 * constructor that adds a default color if a color is not given
	 * @param h - the height of the cylinder
	 * @param radius - the radius of the cylinder
	 * @param ray - a ray that defines the location of the cylinder
	 */
	public Cylinder(double h, double radius, Ray ray)
	{	
		this(Color.BLACK, h, radius, ray);				
	}
	
	/**
	 * constructor that adds a default material if a material is not given
	 * @param h - the height of the cylinder
	 * @param radius - the radius of the cylinder
	 * @param ray - a ray that defines the location of the cylinder
	 */
	public Cylinder(Color color,double h, double radius, Ray ray)
	{
		this(color, new Material(0d, 0d, 0), h, radius, ray);
	}
		
	/**
	 * this function returns the normal to the cylinder in a specific point
	 * @param Point3D - the given point on the cylinder
	 * @return Vector - the normal to the point
	 */
	public Vector getNormal(Point3D a)
	{
		Point3D center = this._axisRay.get_Point();
		Coordinate newZ = new Coordinate(center.getZ().get() + a.getZ().get());
		Point3D highCenterPoint = new Point3D(center.getX(), center.getY(), newZ);
		
		Vector normal = new Vector(a.subtract(highCenterPoint));
		normal.normalize();
		
		return normal;
	}
	
	/**
	 * funtion that calculates the intersection points with cylinder
	 * @return List<Point3D> of intersection points with cylinder
	 * this function overrides the function of tube class
	 */
	@Override
	public List<GeoPoint> findIntersections(Ray ray)
	{
		return null;
	}
}

