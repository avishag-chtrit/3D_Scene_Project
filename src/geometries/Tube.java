package geometries;

import java.util.List;

import primitives.*;

/**
 * this class represents a tube
 * and extends the radial geometry class
 * @author chetrit
 *
 */
public class Tube extends RadialGeometry 
{
	/**
	 * ray of the tube
	 */
	protected Ray _axisRay;
	
	/** 
	 * constructor that activates the base's constructor
	 * @param color - the color of the tube
	 * @param material - the material of the tube
	 * @param r - the ray of the tube
	 * @param radius - the radius of the tube
	 */
	public Tube(Color color, Material material, Ray r, double radius)
	{
		super(color, radius);
		
		this._material = material;
		
		_axisRay = new Ray(r);		
	}
	
	/**
	 * contructor that sets default color to geometry
	 * @param r - the ray of the tube
	 * @param radius - the radius of the tube
	 */
	public Tube(Ray r, double radius)
	{
		this(Color.BLACK, new Material(0, 0, 0), r, radius);
		
	}
	
	/**
	 * constructor that sets a default material
	 * @param emissionLight - the emissionLight of the tube
	 * @param _radius - the radius of the tube
	 * @param _ra y- the ray of the tube
	 */
	public Tube(Color emissionLight, double _radius, Ray _ray) 
	{
        this(emissionLight, new Material(0, 0, 0), _ray, _radius);
	}
	
	
	
	/**
	 * @param Point3D - the point on the tube
	 * @return Vector - the normal to thee point on the tube
	 */
	@Override
	public Vector getNormal(Point3D p)
	{
		 Point3D p0 = _axisRay.get_Point();
         Vector v = _axisRay.getDirection();
         double t = p.subtract(p0).dotProduct(v);
         Point3D o=null;
         
         if (!Util.isZero(t)) 
        	 o = p0.add(v.scale(t));
         
         Vector n = p.subtract(o).normalize();
         return n;
            	
		
		/*Point3D center = this._axisRay.getPoint();
		Coordinate newZ = new Coordinate(center.getZ().get() + a.getZ().get());
		Point3D highCenterPoint = new Point3D(center.getX(), center.getY(), newZ);
		
		Vector normal = new Vector(a.subtract(highCenterPoint));
		normal.normalize();
		
		return normal;*/
           
	}
	
	/**
	 * a function that calculates the intersections
	 * @param Ray - a ray we wnat to find intersections with 
	 * @return List<Point3D> of intersections with tube
	 */
	public List<GeoPoint> findIntersections(Ray ray)
	{
		return null;
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
