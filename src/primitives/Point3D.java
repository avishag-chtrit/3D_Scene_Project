package primitives;
import java.math.*;
/**
 *@author chetrit
 * class Point3D shows a point in 3D by 3 coordinates
 */
public class Point3D 
{	
	/**
	 * 3 coordinates which present the point in 3D
	 * a static variable of type Point3D
	 */
	private Coordinate _x;
	private Coordinate _y;
	private Coordinate _z;
	
	/**
	 * a Point3D that symbolizes the point (0,0,0) 
	 */
	public static Point3D ZERO = new Point3D(0, 0, 0);
	
	
	/**
	 * constructor that sets the coordinates of the point
	 * @param Coordinate x - the x coordinate
	 * @param Coordinate y - the y coordinate
	 * @param Coordinate z - the z coordinate
	 */
	public Point3D(Coordinate x, Coordinate y,Coordinate z)
	{
		_x = x;
		_y = y;
		_z = z;				
	}
	
	/**
	 * constructor that sets the coordinates of the point
	 * @param point- all coordinates of the given point are needed in order to set the coordinate values
	 */
	public Point3D(Point3D point)
	{
		this._x = point._x;
		this._y = point._y;
		this._z = point._z;
	}
	
	/**
	 * constructor that sets the coordinates of the point
	 * @param x - the x coordinate's value in double 
	 * @param y - the y coordinate's value in double 
	 * @param z - the z coordinate's value in double 
	 */
	public Point3D (double x, double y, double z)
	{
		Coordinate tmpX = new Coordinate(x);
		Coordinate tmpY = new Coordinate(y);
		Coordinate tmpZ = new Coordinate(z);
		this._x = tmpX;
		this._y = tmpY;
		this._z = tmpZ;
		
	}

	/**
	 * function that gets the x's value
	 * @return Coordinate x -the value of x coordinate
	 */
	public Coordinate getX()
	{
		return _x;
	}
	
	/**
	 * function that gets the y's value
	 * @return Coordinate y -the value of y coordinate
	 */
	public Coordinate getY()  
	{
		return _y;
	}
	

	/**
	 * function that gets the z's value
	 * @return Coordinate z -the value of z coordinate
	 */
	public Coordinate getZ()  
	{
		return _z;
	}
	
	/**
	 * this function overrides the equals function of object class
	 * and checks whether or not - two points are being equal
	 */
	@Override
	public boolean equals(Object obj) 
	{
	     if (this == obj) return true;
	     if (obj == null) return false;
	     if (!(obj instanceof Point3D)) return false;
	     Point3D oth = (Point3D)obj;
	     return (_x.equals(oth._x) && _y.equals(oth._y) && _z.equals(oth._z));
	}
	
	/**
	 * this function overrides the toString of object class
	 * and prints the point whenever it's called
	 */
	@Override
	public String toString()
	{
		return "(" + _x.toString() + ", " + _y.toString() + ", " + _z.toString() + " )"; 
	}
	
	//------------------------Functions
	
	/**
	 * a function which subtract a point from a point and returns the vector between them
	 * @param point - the point we want to subtract
	 * @return Vector - the vector that is created from the subtraction
	 */
	public Vector subtract(Point3D point)
	{
		Coordinate tmpx = new Coordinate(this.getX().get() - point.getX().get());
		Coordinate tmpy = new Coordinate(this.getY().get() - point.getY().get());
		Coordinate tmpz = new Coordinate(this.getZ().get() - point.getZ().get());
		
		Point3D tmpP = new Point3D(tmpx, tmpy, tmpz);
		Vector vec = new Vector(tmpP);
		return vec;
	}
	
	/**
	 * a function which adds two points and returns a vector
	 * @param point-  the point we want to add
	 * @return Point3D - the point that is created from the addition
	 */
	public Point3D add(Vector vec)
	{
		
		double tmpx = this.getX().get() + vec.getHead().getX().get();
		double tmpy = this.getY().get() + vec.getHead().getY().get();
		double tmpz = this.getZ().get() + vec.getHead().getZ().get();
		
		Point3D tmp_p = new Point3D(tmpx, tmpy, tmpz);
		return tmp_p;				
	}
	
	/**
	 * a function which calculates the squared distance
	 * @param point - the point we need to find the squared distance from
	 * @return double the squared distance
	 */
	public double distanceSquared(Point3D point)
	{	
		Vector vec = new Vector(this.subtract(point));
		
		double valX = vec.getHead().getX().get();
		double valY = vec.getHead().getY().get();
		double valZ = vec.getHead().getZ().get();
		
		double squareDis = valX*valX + valY*valY + valZ*valZ;
		return squareDis;
	}
	
	/**
	 * a function which calculates the distance
	 * @param point - the point we need to find the distance from
	 * @return double - the distance
	 */
	public double distance(Point3D point)
	{
		double squareDis = distanceSquared(point);
		double dis = Math.sqrt(squareDis);
		return dis;
	}
	
	
	 	
};
