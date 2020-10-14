package primitives;

/**
 * a class of the bounding box- that each and every intersectable type
 * must have. it has x's limits, y's limits, and z's limits
 * @author chetrit
 *
 */
public class BoundingBox 
{
	/**
	 * x, minimum and maximum limits - fields
	 */
	private double X_min;
	private double X_max;
	
	/**
	 * y, minimum and maximum limits - fields
	 */
	private double Y_min;
	private double Y_max;
	
	/**
	 * z, minimum and maximum limits - fields
	 */
	private double Z_min;
	private double Z_max;
	
	/**
	 * contructor that sets the bounding box's
	 * limits values
	 * @param X_min - the minimum x limit
	 * @param X_max - the maximum x limit
	 * @param Y_min - the minimum y limit
	 * @param Y_max - the maximum y limit
	 * @param Z_min - the minimum z limit
	 * @param Z_max - the maximum z limit
	 */
	public BoundingBox(double X_min, double X_max, double Y_min, double Y_max, double Z_min, double Z_max)
	{
		this.X_min = X_min;
		this.X_max = X_max;
		
		this.Y_min = Y_min;
		this.Y_max = Y_max;
		
		this.Z_min = Z_min;
		this.Z_max = Z_max;
	}
	
	/**
	 * default constructor that
	 * initializes the default values of the limits
	 */
	public BoundingBox()
	{
		this.X_min =  Double.POSITIVE_INFINITY; // going in the negative direction of x
		this.X_max =  Double.NEGATIVE_INFINITY; // going in the positive direction of x
		 
		this.Y_min = Double.POSITIVE_INFINITY; // going in the negative direction of y
		this.Y_max = Double.NEGATIVE_INFINITY; // going in the positive direction of y
		 
		this.Z_min =  Double.POSITIVE_INFINITY; // going in the negative direction of z
		this.Z_max = Double.NEGATIVE_INFINITY; // going in the positive direction of z
	}
	
	/**
	 * copy constructor that copies a bounding box
	 * @param BoundingBox - the bounding box we should copy
	 */
	public BoundingBox(BoundingBox bBox)
	{
		this.X_min = bBox.X_min;
		this.X_max = bBox.X_max;
		
		this.Y_min = bBox.Y_min;
		this.Y_max = bBox.Y_max;
		
		this.Z_min = bBox.Z_min;
		this.Z_max = bBox.Z_max;
	}
	
	// getters for y limits--------------
	
	/**
	 * @returns the minimum x limit
	 */
	public double get_X_min()
	{
		return X_min;
	}
	
	/**
	 * @returns the maximum x limit
	 */
	public double get_X_max()
	{
		return X_max;
	}
	
	// getters for y limits--------------
	
	/**
	 * @returns the minimum y limit
	 */
	public double get_Y_min()
	{
		return Y_min;
	}
	
	/**
	 * @returns the maximum y limit
	 */
	public double get_Y_max()
	{
		return Y_max;
	}
	
	// getters for z limits--------------
	
	/**
	 * @returns the minimum z limit
	 */
	public double get_Z_min()
	{
		return Z_min;
	}
	
	/**
	 * @returns the maximum z limit
	 */
	public double get_Z_max()
	{
		return Z_max;
	}
	
	/**
	 * 
	 * @param ray- ray from camera to geometry
	 * @returns boolean answer- whether the ray intersects the box or not
	 */
	public boolean Intersects(Ray ray)
	{		
		double tmpVal = 0;
		Point3D directionPoint = ray.getDirection().getHead();
		Point3D p0 = new Point3D(ray.get_Point());
		
		double tmin = (X_min - p0.getX().get()) /directionPoint.getX().get();// we assume that we can project the ray along its direction to reach the limits of the box - xminLimit = x0 +t*dx
	    double tmax = (X_max - p0.getX().get()) / directionPoint.getX().get(); // we assume that we can project the ray along its direction to reach the limits of the box - xmaxLimit = x0 +t*dx
	   // we are actually looking for the -t - if this t exists, we transfered some of the values in the equtetion in order to find t
	    
	    if (tmin > tmax) // swapping
	    {
	    	tmpVal = tmin;
	    	tmin = tmax;
	    	tmax = tmpVal;
	    }
	 
	    double tymin = (Y_min - p0.getY().get()) / directionPoint.getY().get(); // we assume that we can project the ray along its direction to reach the limits of the box - yminLimit = y0 +t*dy
	    double tymax = (Y_max - p0.getY().get()) /  directionPoint.getY().get();// we assume that we can project the ray along its direction to reach the limits of the box - ymaxLimit = y0 +t*dy
	    // we are actually looking for the -t - if this t exists, we transfered some of the values in the equtetion in order to find t
	    
	    if (tymin > tymax) // swapping
	    {
	    	tmpVal = tymin;
	    	tymin = tymax;
	    	tymax = tmpVal;
	    	
	    }	  
	    
	    //we can say that the ray intersects the node if there exists some t >= 0, such that
	    // tmin <= t < tmax  AND  tymin <= t < tymax
	 
	   // if something like the ifs- cases happens- that means that there are two ranges that t has to be within them but, they are two different ranges
	    // and t can not be within both of them at the same time - that is why there is no intersecton
	    if ((tmin > tymax) || (tymin > tmax)) 
	    { 
	    	return false;
	    }
	         	 
	    if (tymin > tmin) // the bigger minimum - we create the range that is the intersection range of the both ranges we found => [3,5] AND [3.5, 6] = [3.5, 5]
	        tmin = tymin; 
	 
	    if (tymax < tmax)  // the smaller maximum- we create the range that is the intersection range of the both ranges we found => [3,5] AND [3.5, 6] = [3.5, 5]
	        tmax = tymax; 
	 
	    double tzmin = (Z_min - p0.getZ().get()) /  directionPoint.getZ().get(); // we assume that we can project the ray along its direction to reach the limits of the box - zminLimit = z0 +t*dz
	    double tzmax = (Z_max - p0.getZ().get()) /  directionPoint.getZ().get();// we assume that we can project the ray along its direction to reach the limits of the box - zmaxLimit = z0 +t*dz
	    // we are actually looking for the -t - if this t exists, we transfered some of the values in the equtetion in order to find t
	    
	    if (tzmin > tzmax) //swapping
	    {
	    	tmpVal = tzmin;
	    	tzmin = tzmax;
	    	tzmax = tmpVal;
	    } 
	 
	    //we can say that the ray intersects the node if there exists some t >= 0, such that
	    // tmin <= t < tmax  AND  tzmin <= t < tzmax
	 
	   // if something like the ifs- cases happens- that means that there are two ranges that t has to be within them but, they are two different ranges
	    // and t can not be within both of them at the same time - that is why there is no intersecton
	    if ((tmin > tzmax) || (tzmin > tmax)) 
	        return false; 
	 
	    if (tzmin > tmin) // the bigger minimum- we create the range that is the intersection range of the both ranges we found => [3,5] AND [3.5, 6] = [3.5, 5]
	        tmin = tzmin; 
	 
	    if (tzmax < tmax) // the smaller maximum - // the smaller maximum- we create the range that is the intersection range of the both ranges we found => [3,5] AND [3.5, 6] = [3.5, 5]
	        tmax = tzmax; 
	     
	    return true; 	
	}

	
	
	
	
	

}
