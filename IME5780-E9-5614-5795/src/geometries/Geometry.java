package geometries;
import primitives.Vector;
import primitives.BoundingBox;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;

/**
 * 
 * @author chetrit
 * an abstract class that implements the interface 'Intersectable'
 */
public abstract class Geometry implements Intersectable
{
	/**
	 * all geometries has a color and material type
	 */
	protected Color _emission; 
	protected Material _material;
	
	/**
	 * every intersectable geometry needs to have 
	 * a bounding box with x,y,z minimum and maximum limits
	 */
	public BoundingBox Bounding_Box;
	
	/**
	 * constructor that sets the emission color
	 * @param emission - the color of emission light
	 */
	public Geometry(Color emission)
	{
		this(emission, new Material(0d, 0d, 0));		
	}
	
	/**
	 * constructor that sets the fields
	 * @param emission - the color of emission light
	 * @param material - the material of the geometry
	 */
	public Geometry(Color emission, Material material)
	{
		_emission = new Color(emission);
		_material = material;		
	}
	
	/**
	 * a copy constructor that copies the given geometry
	 * @param geo - the given geometry we need to copy
	 */
	public Geometry(Geometry geo)
	{
		_emission = geo._emission;
		_material = geo._material;
		Bounding_Box = new BoundingBox(geo.Bounding_Box);
	}
	
	/**
	 * default constructor
	 */
	public Geometry()
	{
		this(Color.BLACK);	 		
	}
	
	/**
	 * all geometries has to implement a function that returns the normal to the geometry
	 * @param point - given point
	 * @return normal vector to the point
	 */
	public abstract Vector getNormal(Point3D point);
	
	/**
	 * returns a duplicate color of emission of the geometry
	 * @return Color - of emission light
	 */
	public Color getEmission()
	{
		return new Color(_emission);
	}
	
	/**
	 * returns the material of the geometry
	 * @return Material - of the geometry
	 */
	public Material getMaterial()
	{
		return _material;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}