package UnitTests;

import static org.junit.Assert.*;
import org.junit.Test;
import elements.Camera;
import primitives.*;
import geometries.*;
import java.util.List;
import geometries.Intersectable.GeoPoint;

public class CameraIntegrationTest 
{
	 Camera cam1 = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0));
	 Camera cam2 = new Camera(new Point3D(0, 0, -0.5), new Vector(0, 0, 1), new Vector(0, -1, 0));
	    
	 
	    /** Sphere TC:1
	    *  a function that checks if we can find intersection with sphere1
	    */
	    @Test
	    public void constructRayThroughPixelWithSphere1()
	    {
	    	 
	        Sphere sph =  new Sphere(new Point3D(0, 0, 3), 1);
	        //Ray ray = cam1.constructRayThroughPixel(3,3,0,0,1,3,3);
	        //List<Point3D> results =  sph.findIntersections(ray);
	       
	        List<GeoPoint> results;
	        
	        int count = 0;
	        int Nx = 3;
	        int Ny = 3;
	        
	        System.out.println("sphere1: ");
	        
	        for (int i = 0; i < Ny; i++) 
	        {
	            for (int j = 0; j < Nx; j++ ) 
	            {
	            	Ray ray = new Ray(cam1.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));	            		    		            	
	                results = sph.findIntersections(ray);	
	                
	                if (results != null)
	                    count += results.size();
	            }
	        }
	        
	        
	        assertEquals("ERROR, wrong number of intersections with sphere",2 , count);
	        System.out.println("count: "+ count);
	    }
	    
	    /** Sphere TC:2
		  * a function that checks if we can find intersection with sphere2
		  */
	    @Test
	    public void constructRayThroughPixelWithSphere2() 
	    {
	        Sphere sph =  new Sphere(new Point3D(0, 0, 2.5), 2.5);

	        List<GeoPoint> results;
	        int count = 0;
	         
	        int Nx = 3;
	        int Ny = 3;

	        
	        for (int i = 0; i < Ny; ++i) 
	        {
	            for (int j = 0; j < Nx; ++j) 
	            {
	            	Ray ray = cam2.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3);
	                results = sph.findIntersections(ray);
	                if (results != null)
	                    count += results.size();
	            }
	        }

	        assertEquals("ERROR, wrong number of intersections with sphere", 18, count);
	        System.out.println("count: "+ count);
	    }
	    
	    /** Sphere TC:3
		  * a function that checks if we can find intersection with sphere3
		  */
	    @Test
	    public void constructRayThroughPixelWithSphere3() 
	    {
	        Sphere sph =  new Sphere(new Point3D(0, 0, 2), 2);

	        List<GeoPoint> results;
	        int count = 0;
	         
	        int Nx = 3;
	        int Ny = 3;

	        
	        for (int i = 0; i < Ny; ++i) 
	        {
	            for (int j = 0; j < Nx; ++j) 
	            {
	            	Ray ray = cam2.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3);
	                results = sph.findIntersections(ray);
	                if (results != null)
	                    count += results.size();
	            }
	        }

	        assertEquals("ERROR, wrong number of intersections with sphere", 10, count);
	        System.out.println("count: "+ count);
	    }
	    
	    /** Sphere TC:4
		  * a function that checks if we can find intersection with sphere4
		  */
	    @Test
	    public void constructRayThroughPixelWithSphere4() 
	    {
	        Sphere sph =  new Sphere(new Point3D(0, 0, 0), 4);

	        List<GeoPoint> results;
	        int count = 0;
	         
	        int Nx = 3;
	        int Ny = 3;

	        
	        for (int i = 0; i < Ny; ++i) 
	        {
	            for (int j = 0; j < Nx; ++j) 
	            {
	            	Ray ray = cam1.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3);
	                results = sph.findIntersections(ray);
	                if (results != null)
	                    count += results.size();
	            }
	        }

	        assertEquals("ERROR, wrong number of intersections with sphere", 9, count);
	        System.out.println("count: "+ count);
	    }
	    
	    /** Sphere TC:5
		  * a function that checks if we can find intersection with sphere5
		  */
	    @Test
	    public void constructRayThroughPixelWithSphere5() 
	    {
	        Sphere sph =  new Sphere(new Point3D(0, 0, -1), 0.5);

	        List<GeoPoint> results;
	        int count = 0;
	         
	        int Nx = 3;
	        int Ny = 3;
	        
	        for (int i = 0; i < Ny; ++i) 
	        {
	            for (int j = 0; j < Nx; ++j) 
	            {
	            	Ray ray = cam1.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3);
	                results = sph.findIntersections(ray);
	                if (results != null)
	                    count += results.size();
	            }
	        }

	        assertEquals("ERROR, wrong number of intersections with sphere", 0, count);
	        System.out.println("count: "+ count);    
	    }
	    
	    /** Plane TC:1
		  * a function that checks if we can find intersection with plane1 which is orthogonal to view plane
		  */
	    @Test
	    public void constructRayThroughPixelWithPlane1()
	    {	    	
	    	 Plane plane =  new Plane(new Point3D(0, 0, 1.25), new Vector(0, 0, 1));

	         List<GeoPoint> results;
	         int count = 0;
	         
	         int Nx = 3;
	         int Ny = 3;

	         for (int i = 0; i < Ny; i++) 
		        {
		            for (int j = 0; j < Nx; j ++) 
		            {
		            	Ray ray = cam1.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3);
		            	results = plane.findIntersections(ray);
		                       
		                if (results != null)
		                    count += results.size();
		            }
		        }

		     assertEquals("ERROR, wrong number of intersections with plane", 9, count);
		     System.out.println("count: "+ count);
	    	
		     
		     // ax +by +cz +d = 0
		     // 0x +0y+1*z -2 = 0
	    }
	    
	    /** Plane TC:2
		  * a function that checks if we can find intersection with plane2
		  */
	    @Test
	    public void constructRayThroughPixelWithPlane2()
	    {
	    	 Plane plane =  new Plane(new Point3D(0, 0, 9), new Vector(0,-0.5, 1));

	         List<GeoPoint> results;
	         int count = 0;
	         
	         int Nx = 3;
	         int Ny = 3;

	         for (int i = 0; i < Ny; ++i) 
		        {
		            for (int j = 0; j < Nx; ++j) 
		            {
		            	Ray ray = cam1.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3);
		                results = plane.findIntersections(ray);
		                if (results != null)
		                    count += results.size();
		            }
		        }

		        assertEquals("ERROR, wrong number of intersections with plane", 9, count);
		        System.out.println("count: "+ count);
	    	
	    }
	    
	    /** Plane TC:3
		  * a function that checks if we can find intersection with plane3
		  */
	    @Test
	    public void constructRayThroughPixelWithPlane3()
	    {
	    	 Plane plane =  new Plane(new Point3D(0, 0, 8), new Vector(0,-3, 1));

	         List<GeoPoint> results;
	         int count = 0;
	         
	         int Nx = 3;
	         int Ny = 3;

	         for (int i = 0; i < Ny; ++i) 
		        {
		            for (int j = 0; j < Nx; ++j) 
		            {
		            	Ray ray = cam1.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3);
		                results = plane.findIntersections(ray);
		                if (results != null)
		                    count += results.size();
		            }
		        }

		        assertEquals("ERROR, wrong number of intersections with plane", 6, count);
		        System.out.println("count: "+ count);
	    }	  
	    
	    /** Triangle TC:1
		  * a function that checks if we can find intersection with triangle1
		  */
	    @Test
	    public void constructRayThroughPixelWithTriangle1()
	    {  	
	    	 Triangle triangle =  new Triangle(new Point3D(0, -1, 2), new Point3D(1,1, 2), new Point3D(-1,1,2));

	         List<GeoPoint> results;
	         int count = 0;
	         
	         int Nx = 3;
	         int Ny = 3;

	         for (int i = 0; i < Ny; ++i) 
		        {
		            for (int j = 0; j < Nx; ++j) 
		            {
		            	Ray ray = cam1.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3);
		                results = triangle.findIntersections(ray);
               
		                if (results != null)
		                    count += results.size();
		            }
		        }

		        assertEquals("ERROR, wrong number of intersections with triangle", 1, count);
		        System.out.println("count: "+ count);
	    }	
	    
	    /** Triangle TC:2
		  * a function that checks if we can find intersection with triangle2
		  */
	    @Test
	    public void constructRayThroughPixelWithTriangle2()
	    {
	    	 Triangle triangle =  new Triangle(new Point3D(1, 1, 2), new Point3D(-1,1, 2), new Point3D(0,-20,2));

	         List<GeoPoint> results;
	         int count = 0;
	         
	         int Nx = 3;
	         int Ny = 3;

	         for (int i = 0; i < Ny; ++i) 
		        {
		            for (int j = 0; j < Nx; ++j) 
		            {
		            	Ray ray = cam1.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3);
		                results = triangle.findIntersections(ray);
		                if (results != null)
		                    count += results.size();
		            }
		        }

		        assertEquals("ERROR, wrong number of intersections with triangle", 2, count);
		        System.out.println("count: "+ count);
	    }	    
}   


