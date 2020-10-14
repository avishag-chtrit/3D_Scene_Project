package scene;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import elements.AmbientLight;
import elements.Camera;
import elements.LightSource;
import geometries.Geometries;
import primitives.Color;
import geometries.Intersectable;

/** 
 * a class that represents all of the 
 * objects that a scene has
 * @author chetrit
 *
 */
public class Scene 
{
	/**
	 * name of scene
	 */
	private String _name;
	
	/**
	 * background color
	 */
	private Color _background = new Color (0,0,0);
	
	/**
	 * ambient light in the scene
	 */
	private AmbientLight _ambientLight;
	
	/**
	 * list of tight sources that are being in the scene
	 */
	private List<LightSource> _lights;
	
	/**
	 * a scene has geometries in it
	 */
	private Geometries _geometries;
	
	/**
	 * the camera of the scene- view point
	 */
	private Camera _camera;
	
	/**
	 * the distance from camera to view plane
	 */
	private double _distance;
	

	/**
	 * constructor that sets the scene's name, the geometries, and the lights
	 * @param name - the scene's name
	 */
	public Scene(String name)
	{
		_name = name;
		_geometries = new Geometries();
		_lights = new LinkedList<LightSource>();
	}
	
	
	/** 
	 * copy constructor
	 * @param scene
	 * only after we wrote it we saw they asked to not write it....
	 */
	/*
	public Scene(Scene scene)
	{
		_name = scene._name;
		_background = scene._background;
		_ambientLight = scene._ambientLight;
		_geometries = scene._geometries;
		_camera = scene._camera;
		_distance = scene._distance;
	}
	*/
			
	// getters-------------------------
	
	/**
	 * gets the list of lights in one scene
	 * @return List<LightSource>  - the list of the light sources
	 */
	public List<LightSource> getLights()
	{
		List<LightSource> lights = new LinkedList<LightSource>();
		lights.addAll(_lights);
		
		return lights;
	}
	
	/**
	 * gets the name of scene
	 * @return String - the name of the scene
	 */
	public String getName()
	{
		return new String(_name);
	}
	
	/**
	 * gets the background color of scene
	 * @return Color - the background color of the scene
	 */
	public Color getBackground()
	{
		return new Color(_background);
	}
	
	/**
	 * gets the ambientLight of scene
	 * @return AmbientLight - the ambient light of the scene
	 */
	public AmbientLight getAmbientLight()
	{
		return _ambientLight;
	}
	
	/**
	 * gets the Geometries of scene
	 * @return Geometries- of the scene
	 */
	public Geometries getGeometries()
	{
		return new Geometries(_geometries);
	}
	
	/**
	 * gets the Camera of scene
	 * @return Camera - the view point of the scene
	 */
	public Camera getCamera()
	{
		return new Camera(_camera);
	}
	
	/**
	 * gets the distance from screen, of the scene
	 * @return double - the distance of camera from plane
	 */
	public double getDistance()
	{
		return _distance;
	}
	
	// setters----------------------
	/**
	 * sets the background color of scene
	 * @param background color of scene
	 */
	public void setBackground(Color background)
	{
		_background = new Color(background); 
	}
	
	/**
	 * sets the ambientLight of scene
	 * @param the ambient light of the scene
	 */
	public void setAmbientLight(AmbientLight ambLight)
	{
		_ambientLight = ambLight;
	}
	
	/**
	 * sets the Camera of scene
	 * @param camera - the view point of the scene
	 */
	public void setCamera(Camera camera) 
	{
		_camera = new Camera(camera);          
		                             
	}
	
	/**
	 * sets the distance from screen, of the scene
	 * @param distance - the distance of camera from plane
	 */
	public void setDistance(double d)
	{
		_distance = d;
	}
	
	// functions------------
	
	/**
	 * adds a collection of intersectable geometries into the scene's list of geometries
	 * @param geometries - the geometries of the scene
	 */
	public void addGeometries(Intersectable... geometries) 
	{
		_geometries.add(geometries);
	}

	/**
	 * adding more lights to the scene
	 * @param lights - list of the light sources
	 */
	public void addLights(LightSource... lights)
	{
		for(LightSource light :lights)
		{
			 _lights.add(light);
		}
	}
	
	
	
	
	
	
	
	
	
}
