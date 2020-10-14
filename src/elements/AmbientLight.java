package elements;

import primitives.Color;

/**
 * this class specifies the ambient color of 
 * the scene - it's a simple and basic light
 * of our scene
 * @author chetrit
 */
public class AmbientLight extends Light
{		 	
	/**
	 * constructor that calls the base class's constructor
	 * @param kA -the intensity amount
	 * @param c the color of the ambient light
	 */
	public AmbientLight(double kA, Color c)
	{
		super(c.scale(kA));		
	}
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * copy constructor
	 * @param ambLight
	 * we found out we were told not to write copy-ctor after writing it...
	 */
	/*
	public AmbientLight(AmbientLight ambLight)
	{
		_intensity = ambLight._intensity;
	}
	*/
 

}
