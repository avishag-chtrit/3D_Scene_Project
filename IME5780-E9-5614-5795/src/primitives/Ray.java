package primitives;
import java.math.*;

/** 
 * a class of a ray that has both starting point, 
 * and a vector of direction
 * @author chetrit
 *
 */
public class Ray 
{
	/**
	 *  a ray is presented by a vector of direction
	 */
	private Vector _dir;
	
	
	/**
	 * and also is presented by a point of start
	 */
	private Point3D _p0;
	
	/**
	 * helps us calculate shadow rays
	 */
	private static final double DELTA = 0.1;
	
	/**
	 * constructor which normalizes the vector and adds it into the vector of the ray
	 * @param point - the starting point of the ray
	 * @param vec - the direction of the ray
	 */
	public Ray(Point3D point, Vector vec)
	{
		_p0 = point;		
		vec.normalize();	
		_dir = vec;
	}
	
	/**
	 * copy constructor - that copies a given ray
	 * @param ray  - the ray we need to copy
	 */
	public Ray(Ray ray)  
	{
		_p0 = ray._p0;  				
		_dir = ray._dir;
	}
	
	/**
	 * a constructor that adds a delta to the starting point of the ray
	 * @param point - the starting point of the ray
	 * @param direction - the direction vector of the ray
	 * @param normal - the normal to the starting point
	 */
	public Ray(Point3D point, Vector direction, Vector normal) 
	{
        //head+ normal.scale(±DELTA)
		_dir = new Vector(direction).normalized();

        double nv = normal.dotProduct(direction);

        Vector normalDelta = normal.scale((nv > 0 ? DELTA : -DELTA));
        _p0 = point.add(normalDelta);
    }
	
	/**
	 * @return Point3D - the starting point
	 */
	public Point3D get_Point()
	{
		return this._p0;
	}
	
	/**
	 * @return vector of direction
	 */
	public Vector getDirection()
	{
		return new Vector(this._dir);
	}
	
	/**
	 * checks whether there are two rays that are the same one
	 * @return boolean - true if they are equal, else - false
	 * this function overrides the function of class object
	 */
	@Override
	public boolean equals(Object obj)
	{
	      if (this == obj) return true;
	      if (obj == null) return false;
	      if (!(obj instanceof Ray)) return false;
	      Ray oth = (Ray)obj;
	      return _p0.equals(oth._p0) && _dir.equals(oth._dir);
	}
	
	/**
	 * prints the data of a ray
	 * @return  String - the data of the ray to print
	 * this function overrides the function of class object
	 */
	@Override
	public String toString()
	{
		return "starting point: " + _p0.toString() + "\n" + "Direction of Ray: " + _dir.toString();
		
	}
	
	/**
	 * returns the point on the ray after moving in the ray direction- t steps 
	 * @param t - the number of steps we move on the ray
	 * @return the point we got to after moving t steps on the ray's direction
	 */
	public Point3D getPoint(double t) // refactoring
	{
		// p = p0 + t*v
		Vector v = this.getDirection();
		Point3D p = new Point3D(this._p0);
		p = p.add(v.scale(t));
		
		return p;
	}

	
}
