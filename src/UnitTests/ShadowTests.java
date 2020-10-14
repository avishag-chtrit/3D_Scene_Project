package UnitTests;

import static org.junit.Assert.*;
import org.junit.Test;
import elements.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;


/**
 * Testing basic shadows
 */
public class ShadowTests 
{
	/**
	 * Produce a picture of a sphere and triangle with point light and shade
	 */
	@Test
	public void SphereTriangleInitial() 
	{
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(0, Color.BLACK));

		scene.addGeometries(new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), //
				new Point3D(0, 0, 200), 60), //
				new Triangle(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), //
						new Point3D(-70, 40, 0), new Point3D(-40, 70, 0), new Point3D(-68, 68, 4)));

		scene.addLights(new SpotLight(new Color(400, 240, 0), //
				new Vector(1, -1, 3), new Point3D(-100, 100, -200), 1, 1E-5, 1.5E-7));

		ImageWriter imageWriter = new ImageWriter("sphereTriangleInitial", 200, 200, 400, 400);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Sphere-Triangle shading - move triangle up-right
	 */
	@Test
	public void SphereTriangleMove1() 
	{
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(0, Color.BLACK));

		scene.addGeometries(new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), 
				new Point3D(0, 0, 200), 60), //
				new Triangle(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30),// in order to move up- we needed to move in the negative direction of y
						new Point3D(-62,35,0), new Point3D(-32,65,0), new Point3D(-61,63,4))); // in order to move right- we needed to move in the positive direction of x

		scene.addLights(new SpotLight(new Color(400, 240, 0), 
				new Vector(1, -1, 3), new Point3D(-100, 100, -200), 1, 1E-5, 1.5E-7));

		ImageWriter imageWriter = new ImageWriter("sphereTriangleMove1", 200, 200, 400, 400);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}
	
	/**
	 * Sphere-Triangle shading - move triangle upper-righter
	 */
	@Test
	public void SphereTriangleMove2() 
	{
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(0, Color.BLACK));

		scene.addGeometries(new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), 
				new Point3D(0, 0, 200), 60), 
				new Triangle(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), // in order to move up- we needed to move in the negative direction of y
						new Point3D(-49,21,0), new Point3D(-19,51,0), new Point3D(-48,51,4)));// in order to move right- we needed to move in the positive direction of x
                                                                                                
		scene.addLights(new SpotLight(new Color(400, 240, 0), 
				new Vector(1, -1, 3), new Point3D(-100, 100, -200), 1, 1E-5, 1.5E-7));

		ImageWriter imageWriter = new ImageWriter("sphereTriangleMove2", 200, 200, 400, 400);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Sphere-Triangle shading - move spot closer
	 */
	@Test
	public void SphereTriangleSpot1() 
	{
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(0, Color.BLACK));

		scene.addGeometries(new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), 
				new Point3D(0, 0, 200), 60), 
				new Triangle(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), 
						new Point3D(-70, 40, 0), new Point3D(-40, 70, 0), new Point3D(-68, 68, 4)));
		                                                       //we needed to move in the negative direction of y
		scene.addLights(new SpotLight(new Color(400, 240, 0), // in order to move closer- we moved in the positive direction of z- in order to get closer to the sphere
				new Vector(1, -1, 3), new Point3D(-85, 85, -130), 1, 1E-5, 1.5E-7)); // in order to move right- we needed to move in the positive direction of x
                                                                                   
		ImageWriter imageWriter = new ImageWriter("sphereTriangleSpot1", 200, 200, 400, 400);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}	
	
	/**
	 * Sphere-Triangle shading - move spot even more close
	 */
	@Test
	public void SphereTriangleSpot2() 
	{
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(0, Color.BLACK));

		scene.addGeometries(new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), 
				new Point3D(0, 0, 200), 60), 
				new Triangle(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), //we needed to move in the negative direction of y
						new Point3D(-70, 40, 0), new Point3D(-40, 70, 0), new Point3D(-68, 68, 4)));// in order to move closer- we moved in the positive direction of z- in order to get closer to the sphere
	                                                                                               	// in order to move right- we needed to move in the positive direction of x
		scene.addLights(new SpotLight(new Color(400, 240, 0), //
				new Vector(1, -1, 3), new Point3D(-75, 75, -70), 1, 1E-5, 1.5E-7));

		ImageWriter imageWriter = new ImageWriter("sphereTriangleSpot2", 200, 200, 400, 400);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}	
	
	/**
	 * Produce a picture of a two triangles lighted by a spot light with a Sphere producing a shading
	 */
	@Test
	public void trianglesSphere() 
	{
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(0.15, new Color(java.awt.Color.WHITE)));

		scene.addGeometries( //
				new Triangle(Color.BLACK, new Material(0, 0.8, 60), //
						new Point3D(-150, 150, 115), new Point3D(150, 150, 135), new Point3D(75, -75, 150)), //
				new Triangle(Color.BLACK, new Material(0, 0.8, 60), //
						new Point3D(-150, 150, 115), new Point3D(-70, -70, 140), new Point3D(75, -75, 150)), //
				new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), // )
						new Point3D(0, 0, 115), 30));

		scene.addLights(new SpotLight(new Color(700, 400, 400), //
				new Vector(-1, 1, 4), new Point3D(40, -40, -115), 1, 4E-4, 2E-5));

		ImageWriter imageWriter = new ImageWriter("trianglesSphere", 200, 200, 600, 600);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}


}
