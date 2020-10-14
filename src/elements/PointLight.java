package elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * class of a light of type -point light,
 * a light that has only a location but does not have a direction
 * this class extends Light class, and implements LightSource interface
 * @author chetrit
 *
 */
public class PointLight extends Light implements LightSource
{
	/**
	 * the point of the position of the light
	 */
	protected Point3D _position;
	
	/**
	 * kc- this values is made to help in calculation of the intensity 
	 */
	protected double _kC;
	
	/**
	 * kl- this values is made to help in calculation of the intensity 
	 */
	protected double _kL;
	
	/**
	 * kq- this values is made to help in calculation of the intensity 
	 */ 
	protected double _kQ;

	/**
	 * constructor that calls the base class's constructor in order to initialize all fields
	 * @param color - the color of the point light
	 * @param point - the lighted point
	 * @param double - _kC helps in calculation of the intensity 
	 * @param double - _kL helps in calculation of the intensity 
	 * @param double - _kQ helps in calculation of the intensity 
	 */
	public PointLight(Color color, Point3D point, double _kC, double _kL, double _kQ) 
	{
		super(color);
			
		_position = new Point3D(point);
		this._kC = _kC;
	    this._kL = _kL;
	    this._kQ = _kQ;
		
	}

	/**
	 * calculates the intensity of a point light
	 * @return Color - the intensity of the light in the specific given point
	 * this function overrides the function getIntensity of class Light 
	 */
	@Override
	public Color getIntensity(Point3D p)
	{
		double dsquared = p.distanceSquared(_position);
        double d = p.distance(_position);

        Color IL = _intensity.reduce(_kC + d*_kL +dsquared* _kQ);

        return IL;
	}

	/**
	 * calculates the vector from the position point to the intersection point
	 * @param Point3D - the lighted point
	 * @return Vector - direction from light to given point
	 * this function overrides the getL functon of LightSource interface
	 * 
	 */
	@Override
	public Vector getL(Point3D p) 
	{
		if (p.equals(_position)) 
		{
            return null;
        } 
		else
        {
            return p.subtract(_position).normalize();
        }		
		
	}
		

	@Override 
	public double getDistance(Point3D p)
	{
		return _position.distance(p);
		//return this.getL(p).getHead().distance(p);
		
		
	}
	
	/**
	 * a function that creates a beam of rays that will be sent to the geometry
	 * @param GeoPoint - the GeoPoint has 2 fields- point on the geometry, and the geometry itself
	 * @return List<Ray> - a list of rays from light to the specific given point
	 */
	public List<Ray> beamOfRays(GeoPoint point_of_geometry, int numOfRays)
	{
		Vector lightDirection = getL(point_of_geometry.getPoint());
		
		List<Ray> raysList = new ArrayList();
		
		Point3D resPoint = new Point3D(_position); // initialization
		Ray ray = new Ray(resPoint,lightDirection);	
		
		raysList.add(ray);
		
		double x = 0;
		double y = 0;	
		
		Random r1 = new Random();
		double randomXValue = -1.0 + (1.0 - (-1.0)) * r1.nextDouble(); // x will be in range: -1 < x < 1 and will have double values
		
		Random r2 = new Random();
		double randomRadiusValue = 3.0 + (6.0 - 3.0) * r2.nextDouble(); // the radius will be in range: 3 < r < 6, and will have double values
					
		
		int i = 0;
		while(i < numOfRays)
		{
			x = randomXValue;
			y = randomRadiusValue * Math.sqrt(1-x*x);
			x = randomRadiusValue * x; 
			
			resPoint = new Point3D((_position.getX().get() + x), (_position.getY().get() + y), _position.getZ().get());
			
			lightDirection = (point_of_geometry.getPoint()).subtract(resPoint);
			 			
			Ray ray1 = new Ray(resPoint,lightDirection);
			raysList.add(ray1);
						
			i++;
			randomXValue = -1.0 + (1.0 - (-1.0)) * r1.nextDouble();// x will be in range: -1 < x < 1 and will have double values
			randomRadiusValue = 3.0 + (6.0 - 3.0) * r2.nextDouble(); // the radius will be in range: 3 < r < 6, and will have double values			
		}		
		    	
		return raysList;
	}
}


































/*
        Vector lightDirection = getL(point_of_geometry.getPoint());	 		
		List<Ray> raysList = new ArrayList();
		
		Point3D resPoint = new Point3D(_position); // initialization
		Ray ray = new Ray(resPoint,lightDirection);			
		raysList.add(ray);
		
		double x = 0;
		double y = 0;		
		double PI = Math.PI;
				
		Random r1 = new Random();
		double randomXThetaValue = 0 + (2.0 - (0)) * r1.nextDouble(); // x will be in range: -1 < x < 1 and will have double values
		
		Random r2 = new Random();
		double randomRadiusValue = 3.0 + (6.0 - 3.0) * r2.nextDouble(); // the radius will be in range: 3 < r < 6, and will have double values
		                                                                            //and r^2 will be in range: 9 < r < 36
				
		int i = 0;
		while(i < 60)
		{
			x = randomRadiusValue * Math.cos(PI *randomXThetaValue);
			y = randomRadiusValue * Math.sin(PI *randomXThetaValue);
			
			resPoint = new Point3D((_position.getX().get() + x),(_position.getY().get() + y), _position.getZ().get());
			lightDirection = (point_of_geometry.getPoint()).subtract(resPoint);
			Ray ray2 = new Ray(resPoint,lightDirection);
			raysList.add(ray2);
			
			randomXThetaValue = 0 + (2.0 - (0)) * r1.nextDouble(); // x will be in range: -1 < x < 1 and will have double values
			randomRadiusValue = 3.0 + (6.0 - 3.0) * r2.nextDouble(); // the radius will be in range: 3 < r < 6, and will have double values, 											
		}                                                                          //and r^2 will be in range: 9 < r < 36
					 
	 
		    	
		return raysList;
	}
}*/





