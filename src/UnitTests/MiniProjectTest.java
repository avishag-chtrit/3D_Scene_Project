package UnitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import elements.AmbientLight;
import elements.Camera;
import elements.DirectionalLight;
import elements.PointLight;
import elements.SpotLight;
import geometries.Geometries;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

public class MiniProjectTest 
{


	@Test
	public void AngryBird() throws Exception
	{	
		//********************time measurements**************************//
		long startTime = System.currentTimeMillis();
		
		//********************scene settings**************************//
		Scene scene = new Scene("Angry Birds");
		scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(1000);
		scene.setBackground(new Color(183,216,249));
		scene.setAmbientLight(new AmbientLight(0 , new Color(255,0,0)));
		
		//******************creating the bird*************************//		
		Geometries geos1 = new Geometries(new Sphere(new Color(240, 20, 50) ,new Material(0.2,0.2,30,0,0), new Point3D(0,0,0), 40), // red sphere- bird's body
				new Sphere(Color.BLACK,new Material(0.2,0.2,30,0,0), new Point3D(0,0,60), 43), // black sphere- bird's body
				
				new Sphere(Color.BLACK,new Material(1,1,20,0,0), new Point3D(20, 0, -42),8), //right black sphere
				new Sphere(Color.BLACK, new Material(1,1,20,0,0),new Point3D(5, 0, -42), 8), //left black sphere
				
				new Sphere(new Color(255,255,255) ,new Material(1,1,20,0,0), new Point3D(20, 0, -50),7),//right white sphere
				new Sphere(new Color(255,255,255), new Material(1,1,20,0,0), new Point3D(5, 0, -50),7),//left white sphere
				
		        new Sphere(Color.BLACK,new Material(1,1,15,0.5,0), new Point3D(17, 1, -60),3), //right black little sphere -eye 
    	        new Sphere(Color.BLACK,new Material(1,1,15,0.5,0), new Point3D(2, 1, -60),3),//left black little sphere- eye 
    	        new Sphere(new Color(8,175,69),new Material(0.2,0.2,30,0,0), new Point3D(0,90,160),110), //green ground
    	        
    	        new Sphere(new Color(240, 20, 50) ,new Material(0.2,0.2,30,0,0), new Point3D(0, -45, 0),9), // right- red hair
    	        new Sphere(new Color(240, 20, 50) ,new Material(0.2,0.2,30,0,0), new Point3D(-14, -41, 22),7.5), // left- red hair
		
    	        new Sphere(Color.BLACK,new Material(0.2,0.2,30,0,0), new Point3D(0, -46, 20),10), // right- black hair
    	        new Sphere(Color.BLACK ,new Material(0.2,0.2,30,0,0), new Point3D(-14, -41, 24),8));// left- black hair
    	       	
		Triangle triangle1 = new Triangle(new Color(255, 148, 77),new Material(1,1,20,0,0), new Point3D(12,7,-60),new Point3D(-2,16,-60), new Point3D(32,16,-60));//up orange - mouth
		Triangle triangle2 = new Triangle(new Color(255, 133, 51),new Material(1,1,20,0,0), new Point3D(10,24,-60),new Point3D(21,17,-60), new Point3D(1,17,-60));//down orange - mouth
		
		Triangle triangle3 = new Triangle(Color.BLACK ,new Material(0.2,0.2,30,0,0), new Point3D(12,6,-60),new Point3D(-3,16,-60), new Point3D(33,16,-60));//up black - mouth
		Triangle triangle4 = new Triangle(Color.BLACK ,new Material(0.2,0.2,30,0,0), new Point3D(10,25,-60),new Point3D(25,15,-60), new Point3D(-2,15,-60));//down black - mouth
		
		Triangle triangle5 = new Triangle(Color.BLACK ,new Material(0.2,0.2,30,0,0), new Point3D(13,-5,-41),new Point3D(25,-17,-41), new Point3D(30,-15,-41));//right black - eyebrow
		Triangle triangle6 = new Triangle(Color.BLACK ,new Material(0.2,0.2,30,0,0), new Point3D(-6,-15,-41),new Point3D(-1,-17,-41), new Point3D(12,-5,-41));//left black - eyebrow
		
		Triangle triangle7 = new Triangle(Color.BLACK ,new Material(0.2,0.2,30,0,0), new Point3D(-38,0,0),new Point3D(-58,-14,3), new Point3D(-50,-16,3));//up black - tail
		Triangle triangle8 = new Triangle(Color.BLACK ,new Material(0.2,0.2,30,0,0), new Point3D(-38,0,0),new Point3D(-65,-10,5), new Point3D(-68,0,5));//middle black - tail
		Triangle triangle9 = new Triangle(Color.BLACK ,new Material(0.2,0.2,30,0,0), new Point3D(-38,0,0),new Point3D(-57,5,3), new Point3D(-55,14,3));//down black - tail
		
		
		Geometries geos2 = new Geometries(triangle1, triangle2, triangle3, triangle4, triangle5, triangle6, triangle7, triangle8 /*,triangle9*/);

		//****************creating Hierarchy*********************//
		Geometries geos3 = new Geometries(geos1, geos2, triangle9);
		scene.addGeometries(geos3);

		//****************adding the lights*********************//
		scene.addLights(new SpotLight(new Color(255, 204, 204), new Vector(new Point3D(1,3,0)), new Point3D(-160,-100,0),1, 0.0004,0.0000006),
				new SpotLight(new Color(255, 204, 204), new Vector(new Point3D(-110,0,100)), new Point3D(0,0,-1),1, 0.0004,0.0000006), // left up
				new PointLight(new Color(255, 168, 186), new Point3D(50, -50, 0),1, 0.004,0.00006), // right up
				new DirectionalLight(new Color(255, 255, 153), new Vector(new Point3D(0,0,-100))));  // middle toward
		

		//*********************imageWriter settings******************//
		ImageWriter imageWriter = new ImageWriter("Our Angry Bird Picture", 150, 150, 500, 500);
		
		Render render = new Render(imageWriter, scene, 130, true); // we want 130 shadow rays, and it's true that we want the improvement we worked on
		
		render.setMultithreading(3);
		render.setDebugPrint();
		
		render.renderImage();
		//render.printGrid(50);
		imageWriter.writeToImage();		
		
		long stopTime = System.currentTimeMillis();		
		long totalTime = stopTime - startTime;
		
		System.out.println(totalTime + " milliSeconds.");
	}

}
