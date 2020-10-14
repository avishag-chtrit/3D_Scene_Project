package primitives;

/** a class that represents a 3d vector 
 * 
 * @author chetrit
 *
 */
public class Vector 
{
	/**
	 *  vector is presented by a 3D point of direction - the head of the vector
	 */
	private Point3D _head;
	
	/**
	 * constructor that initializes the head point of the vector
	 * @param point - head of the vector
	 */
	public Vector(Point3D point)
	{
		if(point.equals(Point3D.ZERO))
			throw new IllegalArgumentException("The Direction of vector cannot be zero");
		
		this._head =  point;	 	
	}
	
	/**
	 * copy constructor that copies the values of the fields of a given vector
	 * @param vec - the vector we need to copy from
	 */
	public Vector(Vector vec)
	{
		if(Point3D.ZERO.equals(vec.getHead()))         //(vec._head.getX().get() == 0 && vec._head.getY().get() == 0 && vec._head.getZ().get() == 0)
			throw new NumberFormatException("The Direction of vector cannot be zero");
		
		this._head =  vec._head;
	}
	
	/** 
	 * constructor that receives coordinates
	 * @param x - the x coordinate of the head point
	 * @param y - the y coordinate of the head point
	 * @param z - the z coordinate of the head point
	 */
	public Vector(Coordinate x, Coordinate y, Coordinate z)
	{
		Point3D point  = new Point3D(x, y, z);
		
		if(point.equals(Point3D.ZERO))
			throw new IllegalArgumentException("The Direction of vector cannot be zero");
					
		this._head = point;
	}
	
	/**
	 * constructor that receives doubles
	 * @param x - the x coordinate value in double, of the head point
	 * @param y - the y coordinate value in double, of the head point
	 * @param z - the z coordinate value in double, of the head point
	 */
	public Vector(double x, double y, double z)
	{
		Point3D point  = new Point3D(x, y, z);
		 
		if(point.equals(Point3D.ZERO))
			throw new IllegalArgumentException("The Direction of vector cannot be zero");
		
		this._head = point;	
	}
	
	/**
	 * @return Point3D - the head of the vector
	 */
	public Point3D getHead()
	{
		return this._head; 
	}

	/**
	 * checks whether there are two vectors that are the same one
	 * @return boolean - true if they are equal, else - false
	 * this function overrides the function of class object
	 */
	@Override
	public boolean equals(Object obj) 
	{
	      if (this == obj) return true;
	      if (obj == null) return false;
	      if (!(obj instanceof Vector)) return false;
	      
	      Vector oth = (Vector)obj;
	      return _head.equals(oth._head);
	}
	
	/**
	 * this function overrides the function of class object and
	 * @return String - string of the vector's data
	 */
	@Override 
	public String toString()
	{
		return _head.toString();
	}

	//------------------Functions
	
	/**
	 * a function that subtracts two vectors
	 * @param vec - the vector we want to subtract from
	 * @return Vector - the vector that is created from the subtraction
	 */
	public Vector subtract(Vector vec)
	{
		Vector copiedVec = new Vector(vec);		
		return _head.subtract(copiedVec._head);
	}
	
	/**
	 * a function that adds two vectors
	 * @param vec - the vector we want to add  
	 * @return - Vector - the vector that is created from the addition
	 */
	public Vector add(Vector vec)
	{
		Vector copiedVec = new Vector(vec);
		
		Point3D newDirectionPoint = new Point3D(_head.add(copiedVec));
		Vector resVec = new Vector(newDirectionPoint);		

		return resVec;
	}
	
	/**
	 * multiplies a number vector
	 * @param a - the scalar we want to multiply in
	 * @return Vector - the vector that is created from the scaling
	 */
	public Vector scale(double a)
	{
		if( a == 0 )
			throw new IllegalArgumentException("can not scale the vector with a = 0");
		
		Vector copiedVec = new Vector(_head.getX().get()*a, _head.getY().get()*a, _head.getZ().get()*a);
		return copiedVec;
	}
	
	/**
	 * @param vec - the vector we want to - dot product with
	 * @return double - the number, created from the dot product
	 */
	public double dotProduct(Vector vec)
	{
		double sum = (_head.getX().multiply(vec._head.getX()).get()) + (_head.getY().multiply(vec._head.getY()).get())
				+ (_head.getZ().multiply(vec._head.getZ()).get());
		
		return sum;
	}
	
	/**
	 * @param vec - the vector we want to - cross product with
	 * @return Vector - - the vector, created from the cross product
	 */
	public Vector crossProduct(Vector vec) // vec = (v1, v2, v3)
	{                                       // this = (u1, u2, u3)
		double tmpx = (((_head.getY()).multiply(vec._head.getZ())).get() - (_head.getZ()).multiply(vec._head.getY()).get());
		double tmpy = (((_head.getZ()).multiply(vec._head.getX())).get() - (_head.getX()).multiply(vec._head.getZ()).get());
		double tmpz = (((_head.getX()).multiply(vec._head.getY())).get() - (_head.getY()).multiply(vec._head.getX()).get());
		
		Vector tmpVec = new Vector(tmpx, tmpy, tmpz);
		return tmpVec;
	}
	
	/**
	 * calculates the squared length of vector
	 * @param vec - we will find its squared length
	 * @return double the squared length
	 */
	public double lengthSquared()
	{
		Point3D point = new Point3D(this._head);
		return (point).distanceSquared(Point3D.ZERO);		
	}
	
	public double length()
	{
		return Math.sqrt(this.lengthSquared());
	}
	
	/**
	 * normalizes the original vector
	 * @return Vector - the original vector- after normalization
	 */
	public Vector normalize()
	{		
		double tmpX = _head.getX().get();
		double tmpY = _head.getY().get();
		double tmpZ = _head.getZ().get();
		
		double vecLen = Math.sqrt(tmpX*tmpX + tmpY*tmpY + tmpZ*tmpZ);
		
		Coordinate x = new Coordinate(tmpX/vecLen);
		Coordinate y = new Coordinate(tmpY/vecLen);
		Coordinate z = new Coordinate(tmpZ/vecLen);	 
		
		Point3D p = new Point3D(x, y, z);
		this._head = p;
		
		return this;
	}
	
	/**
	 * returns a copy of a vector after normalizing the copied vector- only
	 * @return Vector - the copied vector, after normalization
	 */
	public Vector normalized()
	{
		Vector vec = new Vector(this);
		vec.normalize();
		return vec;		
	}
	
	/**
	 * calculates the direction of the orthogonal vector
	 * @param lightDirection - the direction of the light
	 * @return Vector - the orthogonal vector
	 */
	public Vector Orthogonal()
	{ 
		double x_coordinate = this.getHead().getX().get();
		double y_coordinate = this.getHead().getY().get();
		double z_coordinate = this.getHead().getZ().get();
		
		Vector vec = new Vector((1/x_coordinate) , (-1)*(1 / y_coordinate), 0* z_coordinate);
		
		return vec;					 		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
