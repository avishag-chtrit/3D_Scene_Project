package UnitTests;

import org.junit.Test;

import elements.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Test rendering abasic image
 * 
 * @author Dan
 */
public class LightsTests 
{

    /**
     * Produce a picture of a sphere lighted by a directional light
     */
    @Test
    public void sphereDirectional() 
    {
    	   Scene scene = new Scene("Test scene");
           scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
           scene.setDistance(1000);
           scene.setBackground(Color.BLACK);
           scene.setAmbientLight(new AmbientLight(0, Color.BLACK));

           scene.addGeometries(
                   new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 100), new Point3D(0, 0, 50), 50));

           scene.addLights(new DirectionalLight(/*new Color(500, 250, 250)*/ new Color(500, 300, 0), new Vector(1, -1, 1)));

           ImageWriter imageWriter = new ImageWriter("sphereDirectional", 150, 150, 500, 500);
           Render render = new Render(imageWriter, scene);

           render.renderImage();
           render.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a point light
     */
    @Test
    public void spherePoint() 
    {
    	Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(0, Color.BLACK));

        scene.addGeometries(
                new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 100), new Point3D(0, 0, 50), 50));

        scene.addLights(new PointLight(/*new Color(500, 250, 250)*/ new Color(500, 300, 0), new Point3D(-50, 50, -50), 1, 0.00001, 0.000001));

        ImageWriter imageWriter = new ImageWriter("spherePoint", 150, 150, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void sphereSpot() 
    {
    	 Scene scene = new Scene("Test scene");
         scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
         scene.setDistance(1000);
         scene.setBackground(Color.BLACK);
         scene.setAmbientLight(new AmbientLight(0, Color.BLACK));

         scene.addGeometries(
                 new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 100), new Point3D(0, 0, 50), 50));

         scene.addLights(new SpotLight(/*new Color(500, 250, 250)*/ new Color(500, 300, 0), new Vector(1, -1, 2),new Point3D(-50, 50, -50), 1, 0.00001, 0.00000001));

         ImageWriter imageWriter = new ImageWriter("sphereSpot", 150, 150, 500, 500);
         Render render = new Render(imageWriter, scene);

         render.renderImage();
         render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a directional light
     */
    @Test
    public void trianglesDirectional() 
    {
    	Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(0.15, new Color(java.awt.Color.WHITE)));

        scene.addGeometries(
                new Triangle(Color.BLACK, new Material(0.8, 0.2, 300),
                        new Point3D(-150, 150, 150), new Point3D(150, 150, 150), new Point3D(75, -75, 150)),
                new Triangle(Color.BLACK, new Material(0.8, 0.2, 300),
                        new Point3D(-150, 150, 150), new Point3D(-70, -70, 50), new Point3D(75, -75, 150)));

        scene.addLights(new DirectionalLight(new Color(300, 150, 150), new Vector(0, 0, 1)));

        ImageWriter imageWriter = new ImageWriter("trianglesDirectional", 200, 200, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a point light
     */
    @Test
    public void trianglesPoint() 
    {
    	Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(0.15, new Color(java.awt.Color.WHITE)));

        scene.addGeometries(
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 300),
                        new Point3D(-150, 150, 150), new Point3D(150, 150, 150), new Point3D(75, -75, 150)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 300),
                        new Point3D(-150, 150, 150), new Point3D(-70, -70, 50), new Point3D(75, -75, 150)));

        scene.addLights(new PointLight(new Color(500, 250, 250), new Point3D(10, 10, 130), 1, 0.0005, 0.0005));

        ImageWriter imageWriter = new ImageWriter("trianglesPoint", 200, 200, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light
     */
    @Test
    public void trianglesSpot() 
    {
    	Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(0.15, new Color(java.awt.Color.WHITE)));

        scene.addGeometries(
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 300),
                        new Point3D(-150, 150, 150), new Point3D(150, 150, 150), new Point3D(75, -75, 150)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 300),
                        new Point3D(-150, 150, 150), new Point3D(-70, -70, 50), new Point3D(75, -75, 150)));
        
        SpotLight spot = new SpotLight(new Color(500, 250, 250), new Vector(-2, 2, 1), new Point3D(10, 10, 130), 1, 0.0001, 0.000005); 
        
        scene.addLights(spot);
       
       

        ImageWriter imageWriter = new ImageWriter("trianglesSpot", 200, 200, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }
    
    
    
    /**
     * Produce a picture of a sphere lighted by a directional, spot, and point light
     */
    @Test
    public void sphereLightsTest() 
    {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(0, Color.BLACK));

        scene.addGeometries(
                new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 100), new Point3D(0, 0, 50), 50d));
        
        //----------------lights
        scene.addLights(new DirectionalLight(new Color(java.awt.Color.cyan) , new Vector(3, -5, 7)));
        
        scene.addLights(new PointLight(new Color(java.awt.Color.cyan), new Point3D(-15,20,-80), 1, 0.001, 0.0001));
        
        scene.addLights(new SpotLight(new Color(java.awt.Color.cyan), new Vector(3, -5, 7),new Point3D(-30, 50, -70), 1, 0.001, 0.001));
        //--------------------------
        

        ImageWriter imageWriter = new ImageWriter("sphereLightsTest", 150, 150, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }
    
    
    /**
     * Produce a picture of a two triangles lighted by a directional, point, and spot light
     */
    @Test
    public void trianglesLightsTest() 
    {
    	Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(0.15, new Color(java.awt.Color.WHITE)));

        scene.addGeometries(
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 300),
                        new Point3D(-150, 150, 150), new Point3D(150, 150, 150), new Point3D(75, -75, 150)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 300),
                        new Point3D(-150, 150, 150), new Point3D(-70, -70, 50), new Point3D(75, -75, 150)));

        // LIGHTS----------------------------------------------  
        scene.addLights(new DirectionalLight(new Color(java.awt.Color.cyan), new Vector(-2, 2, 1)));
            
        scene.addLights(new PointLight(new Color(500, 500, 250), new Point3D(2, 10, 130), 1, 0.0001, 0.0002));  
        
        scene.addLights(new SpotLight(new Color(500, 250, 250), new Vector(-2, 2, 1), new Point3D(57, 10, 130), 1, 0.0003, 0.000003));
        
        // LIGHTS--------------------------------------------------
        
        ImageWriter imageWriter = new ImageWriter("trianglesLightsTest", 200, 200, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }
    
    
    
}
