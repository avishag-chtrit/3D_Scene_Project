package elements;

import primitives.Color;

/**
 * this is an abstract class of light 
 * that has a color - every type of light has color
 * that is why- all classes that extends this class must have a color
 * @author chetrit
 */
abstract class Light
{
	/**
	 * every type of light must have a color
	 */
	protected Color _intensity;

	/**
	 * constructor that initializes the color field
	 * @param color - the color of the light the user wants to set
	 */
	public Light(Color color)
	{
		_intensity = new Color(color);
	}
	
	/**
	 * gets the intensity of the light
	 * @return Color - the intensity of the light is returned
	 */
	public Color getIntensity()
	{
		return new Color(_intensity);
	}
}
