package elements;

import java.util.List;

import geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.Point3D;
import primitives.Vector;
import primitives.Ray;

/**
 * the light source is an interface the defines the attributes, 
 * that all kinds of light sources, must have
 * @author chetrit
 */
public interface LightSource 
{
	/**
	 * returns the intensity of the light
	 * @param p - a specific point which we want to get
	 * the intensity of light in.
	 * @return the intensity of the light source in the specific given point
	 */
	public Color getIntensity(Point3D p);
	
	/**
	 * returns the normalized vector of direction of the light source
	 * @param p the lighted point
	 * @return Vector, a vector of direction from light to the lighted point
	 */
	public Vector getL(Point3D p);

	/**\
	 * function that returns the distance from the light to the intersection point
	 * @param Point3D - given lighted point
	 * @return double - the distance from the light source to the point
	 */
	public double getDistance(Point3D p);

	/**
	 * a function that creates a beam of rays that will be sent to the geometry
	 * @param lightDirection
	 * @param number of rays that the user want to create
	 * @return List<Ray> returns the list of the rays from light to point
	 */
	public List<Ray> beamOfRays(GeoPoint point_of_geometry, int numOfRays);
	
	
	
	
	
	
	
	
	
	
	
	
	///**
		 //* all lights has a radius in order to calculate random points on the light- in order to start the rays
		// */
		//public static double radius = 2;
	
}
