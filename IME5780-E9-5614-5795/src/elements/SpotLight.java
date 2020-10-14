package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * this class extends the PointLight class
 * because it has a point of location but also has a direction.
 * @author chetrit
 *
 */
public class SpotLight extends PointLight
{
	/**
	 * the direction of the light
	 */
	private Vector _direction;

	/** 
	 * constructor that calls the base class's constructor in order to initialize all fields
	 * @param color - the color of the light
	 * @param d - the direction vector of the light
	 * @param point - the starting point of the spot light
	 */
	public SpotLight(Color color, Vector d, Point3D point, double _kC, double _kL, double _kQ) 
	{
		super(color, point, _kC,_kL,_kQ);
		_direction = new Vector(d).normalized();
	}

	/**
	 * calculates the intensity of a spotlight
	 * @return Color - the color of the spot light
	 * this function overrides the function getIntensity of PointLight class
	 */
	@Override
	public Color getIntensity(Point3D p) 
	{       
	    Color pointLightIntensity = super.getIntensity(p);
	    double projection = _direction.dotProduct(getL(p));

	    Color IL = pointLightIntensity.scale(Math.max(0,projection));
	    return IL;		 
	}

}	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * gets the direction of the light source
	 * @return Vector 
	 */
	/*
	@Override
	public Vector getL(Point3D p) 
	{
	    return _direction;
	}*/
	

