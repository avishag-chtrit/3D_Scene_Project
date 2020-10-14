package geometries;

import primitives.Color;
import primitives.Material;

/**
 * this class represents a radial geometry
 * this class extends the Geometry class
 * @author chetrit
 *
 */
public abstract class RadialGeometry extends Geometry
{
	/**
	 * every radial geometry has a radius
	 */
	protected double _radius;
	
	/**
	 * constructor that sets the radius
	 * @param radius - the radius of the radial geometry
	 * @param emissionLight - the emission light of the radial geometry
 	 */
	public RadialGeometry(Color emissionLight, double radius)
	{
		super(emissionLight);
		 
		if (primitives.Util.isZero(radius) || (radius < 0.0))
            throw new IllegalArgumentException("radius "+ _radius +" is not valid");
		
		this._radius = radius;		
	}
	
	/**
	 * constructor that sets the radius
	 * @param radius - the radius of the radial geometry
	 */
	public RadialGeometry(double radius)
	{
		if (primitives.Util.isZero(_radius) || (_radius < 0.0))
            throw new IllegalArgumentException("radius "+ _radius +" is not valid");
		
		this._radius = radius;
	}
	
	
	/**
	 * copy constructor that copies a radial geometry
	 * @param radGeo - that we need to copy
	 */
	public RadialGeometry(RadialGeometry radGeo)
	{
		this._radius = radGeo._radius;		
	}
	
	/**
	 * returns the radius of the radial geometry
	 * @return double - the radius of the radial geometry
	 */
	public double getRadius()
	{
		return this._radius;
	}

}
