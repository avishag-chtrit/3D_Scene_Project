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
 * class of directional light- a light that is so far
 * that the distance from its source equals infinity
 * this light does not have location because of its enormous distance,
 *  but does have direction. this class implements - LightSource interface
 * @author chetrit
 *
 */
public class DirectionalLight extends Light implements LightSource
{
	/**
	 * a vector that contains the direction of the light
	 */
	private Vector _direction;

	/**
	 * constructor that calls the base class's constructor in order to initialize all fields
	 * @param color - the color of the light, all lights are actually a color
	 * @param d - the direction vector
	 */
	public DirectionalLight(Color color, Vector d) 
	{
		super(color);
		_direction = d.normalized();
	}

	/**
     * @param p the lighted point is not used and is mentioned
     *          only for compatibility with LightSource
     * @return fixed intensity of the directionLight
     */
	@Override
	public Color getIntensity(Point3D p) 
	{
		return _intensity;	 
	}

	/**
	 * like a get function of direction
	 * @return Vector
	 */
	@Override
	public Vector getL(Point3D p) 
	{	
		return _direction;
	}
	
	@Override 
	public double getDistance(Point3D p)
	{
	    return Double.POSITIVE_INFINITY;
	}
	
	/**
	 * a function that creates a beam of rays that will be sent to the geometry
	 * @param lightDirection
	 * @return List<Ray>
	 */
	public List<Ray> beamOfRays(GeoPoint point_of_geometry, int numOfShadowRays)
	{
		
		return null;
	
	} 		
}

















    	 /*Vector lightDirection = this._direction.scale(-1); // from point to light source
    	 lightDirection.normalize();
    	 Vector n = point_of_geometry.getGeometry().getNormal(point_of_geometry.getPoint());
    	 n.normalize();
    	     	 
    	 Point3D point = point_of_geometry.getPoint();
    	 double x_Coordinate = lightDirection.getHead().getX().get();
    	 double y_Coordinate = lightDirection.getHead().getY().get();
    	 double z_Coordinate = lightDirection.getHead().getZ().get();
    	 
    	 List<Ray> listOfRays = new ArrayList();
    	  	   	 
         Ray OriginallightRay = new Ray(point, lightDirection, n); // goes to the c-tor that adds the delta value  
         listOfRays.add(OriginallightRay);
         
         // moving +x right  
         int i = 1;
         while(i < 40)
         {
        	 Ray posXLightRay = new Ray(point, new Vector(x_Coordinate + 0.0005*i, y_Coordinate, z_Coordinate), n);
        	 listOfRays.add(posXLightRay);
             
             i++;
         }
              
         //moving -x left
         i = 1;
         while (i < 40)
         {
        	    Ray negXLightRay = new Ray(point, new Vector(x_Coordinate - 0.0005*i, y_Coordinate, z_Coordinate), n);
        	    listOfRays.add(negXLightRay);
               
                i++;
         }
              
         // moving +y down
         i = 1;
         while (i < 40)
         {
        	 Ray posYLightRay = new Ray(point, new Vector(x_Coordinate, y_Coordinate + 0.0005*i, z_Coordinate), n);
        	 listOfRays.add(posYLightRay);
             i++;
         }
                
         // moving -y up
         i = 1;
         while (i < 40)
         {
        	  Ray negYLightRay = new Ray(point, new Vector(x_Coordinate, y_Coordinate - 0.0005*i, z_Coordinate), n);
        	  listOfRays.add(negYLightRay);
              i++;
         }
               
         // moving +x right, +y down 
         i = 1;
         while (i < 40)
         {
        	    Ray posX_posY_LightRay = new Ray(point, new Vector(x_Coordinate + 0.0005*i, y_Coordinate + 0.0005*i, z_Coordinate), n);
        	    listOfRays.add(posX_posY_LightRay);
                i++;
         }
              
         // moving +x right, -y up  
         i = 1;
         while (i < 40)
         {
        	  Ray posX_negY_LightRay = new Ray(point, new Vector(x_Coordinate + 0.0005*i, y_Coordinate - 0.0005*i, z_Coordinate), n);
        	  listOfRays.add(posX_negY_LightRay);
              i++;
         }
         
      // moving -x left, +y down  
         i = 1;
         while (i < 40)
         {
        	  Ray negX_posY_LightRay = new Ray(point, new Vector(x_Coordinate - 0.0005*i, y_Coordinate + 0.0005*i, z_Coordinate), n);
        	  listOfRays.add(negX_posY_LightRay);
              i++;
         }
           
         // moving -x left, -y up 
         i = 1;
         while (i < 40)
         {
        	  Ray negX_negY_LightRay = new Ray(point, new Vector(x_Coordinate - 0.0005*i, y_Coordinate - 0.0005*i, z_Coordinate), n);
        	  listOfRays.add(negX_negY_LightRay);
              i++;
         }
   
         return listOfRays;
         */
		
		




               
 
