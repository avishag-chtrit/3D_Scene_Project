package elements;
import primitives. *;

/**
 * @author chetrit
 * a class which implements a camera
 * that we will use in our scene -point ov view
 */
public class Camera 
{
	/**
	 * starting point of the camera
	 */
	private Point3D _p0;

	/**
	 * vector that shows us the 'up' direction from the camera
	 */
	private Vector _vUp;
	
	/**
	 * vector that shows us the 'toward' direction from the camera
	 */
	private Vector _vTo;
	
	/**
	 * vector that shows us the 'right' direction from the camera
	 */
	private Vector _vRight;

	/**
	 * a constructor which initializes the values of the camera's fields
	 * @param point - starting point of the camera
	 * @param vUp - the 'up' direction from camera
	 * @param vTo - the 'toward' direction from camera
	 */
	public Camera(Point3D point, Vector vTo, Vector vUp)
	{
		if(!Util.isZero(vUp.dotProduct(vTo)))
			throw new NumberFormatException("the vectors must be orthogonals!");
		
		_vRight = vTo.crossProduct(vUp);
		_vUp = vUp;
		_vTo = vTo;
		
		_vRight.normalize();
		_vUp.normalize();
		_vTo.normalize();	
		_p0 = new Point3D(point);
	}
	
	
	/**
	 * copy constructor which initializes the values of the camera's fields
	 * @param cmra - the given camera that we need to copy
	 */
	public Camera(Camera cmra)
	{
		_p0 = new Point3D(cmra._p0);
		_vUp = new Vector(cmra._vUp);
		_vTo = new Vector(cmra._vTo);
		_vRight = new Vector(cmra._vRight);
	}
	 
	
	/**
	 * 
	 * @param nX = number of pixels on x
	 * @param nY = number of pixels on y
	 * @param j = the y coordinate of the given pixel
	 * @param i = the x coordinate of the given pixel
	 * @param screenDistance = distance of camera from screen
	 * @param screenWidth - width of screen
	 * @param screenHeight - height of screen
	 * @return Ray - the ray we constructed from the camera
	 */
	public Ray constructRayThroughPixel(int nX, int nY,
            int j, int i, double screenDistance,
            double screenWidth, double screenHeight)
	{ 	
		        //Image center
				Point3D Pc = new Point3D(_p0); 
				Vector To = new Vector(_vTo); 
				To = To.scale(screenDistance);
				Pc = Pc.add(To);
				
				double Rx = screenWidth/nX;		//Ratio (pixel width)
				double Ry = screenHeight/nY;	//Ratio (pixel height)

				
				Vector Right = new Vector(_vRight);
				Vector Up = new Vector(_vUp); 
				
				double xj = (j + ((-1)*(double)nX/2))*Rx + (Rx/2);
				double yi = (i + ((-1)*(double)nY/2))*Ry + (Ry/2);
				
				Point3D finalPoint = Point3D.ZERO;
							

				if(!Util.isZero(xj) && !Util.isZero(yi))
				{
					 if((Right.scale(xj)).equals(Up.scale(yi)))
						 finalPoint = new Point3D(Pc);
					 else
					 {
							Right = Right.scale(xj);
							Up = Up.scale(yi);
							Vector res = Right.subtract(Up);
							finalPoint = Pc.add(res);
					 }										
				}
				else
					if(Util.isZero(xj) && !Util.isZero(yi))
					{
						yi = yi * (-1);
						Up = Up.scale(yi);
						finalPoint = new Point3D(Pc.add(Up));										
					}						
					if(!Util.isZero(xj) && Util.isZero(yi))
					{
						Right = Right.scale(xj);
						finalPoint = Pc;
						finalPoint = finalPoint.add(Right);
					}
					if((Util.isZero(xj) && Util.isZero(yi)))         
					{
						finalPoint = new Point3D(Pc);			 
					}				
														
				Vector v = new Vector(finalPoint.subtract(_p0));//the ray's vector			 
				v.normalize();	
				
				Ray ray = new Ray(_p0, v);
				return ray;
	}  
	
	/**
	 * 
	 * @return Vector _vUp - the 'up' direction from camera
	 */
	public Vector get_vUp()
	{
		return new Vector(_vUp);
	}
	
	/**
	 * 
	 * @return Vector _vTo -the 'toward' direction from camera
	 */
	public Vector get_vTo()
	{
		return new Vector(_vTo);
	}
	
	/**
	 * 
	 * @return Vector _vRight - the 'right' direction from camera
	 */
	public Vector get_vRight()
	{
		return new Vector(_vRight);
	}
	 
	/**
	 * 
	 * @return Point3D _p0 - camera's starting point
	 */
	public Point3D get_p0()
	{
		return new Point3D(_p0);
	}
}

