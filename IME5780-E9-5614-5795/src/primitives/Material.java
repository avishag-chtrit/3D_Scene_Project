package primitives;

/**
 * a class that contains fields of data of the 
 * material of an object
 * @author chetrit
 *
 */
public class Material 
{
	/**
	 * the diffusive factor
	 */
	private double _kD;
	
	/**
	 * the specular factor
	 */
	private double _kS;
	
	/**
	 * the shininess factor
	 */
	private int _nShininess;
	
	/**
	 * the transparency factor
	 */
	private double _kT; // transparent
	
	/**
	 * the reflectancy factor 
	 */
	private double _kR;  // reflectance
	
	/**
	 * constructor that sets the fields of the material
	 * @param kd - diffusive factor
	 * @param ks - specular factor
	 * @param nShine - shininess factor
	 */
	public Material(double kd, double ks, int nShine)
	{
		this(kd, ks, nShine, 0, 0);	
	}
	
	/**
	 * constructor that sets the fields of the material
	 * @param double kd - diffusive factor
	 * @param double ks - specular factor
	 * @param int nShine - shininess factor
	 * @param double - the transparency factor
	 * @param double kr- the reflectecy factor
	 */
	public Material(double kd, double ks, int nShine, double kt, double kr)
	{
		_kD = kd;
		_kS = ks; 
		_kT = kt;
		_kR = kr;
		
		_nShininess = nShine;		
	}
	
	/**
	 * copy constructor that copies a material
	 * @param material - the material we want to copy
	 */
	public Material(Material material) 
	{
        this(material._kD, material._kS, material._nShininess, material._kT, material._kR);
    }
	
	
	// getters------------------------------------	
 
	/**
	 * gets _kD
	 * @return double kd- the diffusive factor
	 */
	public double get_kD()
	{
		return _kD;
	}
	
	/**
	 * gets _kS
	 * @return double ks- the specular factor 
	 */
	public double get_kS()
	{
		return _kS;
	}
	
	/**
	 * gets _nShininess
	 * @return int shininess - the shininess factor
	 */
	public int get_nShininess()
	{
		return _nShininess;
	}
	
	/**
	 * gets _kT
	 * @return double kt - the transparency factor
	 */
	public double get_kT()
	{
		return _kT;
	}
	
	/**
	 * gets _kR
	 * @return double kr - the reflectency factor
	 */
	public double get_kR()
	{
		return _kR;
	}
	
	
	
	

}
