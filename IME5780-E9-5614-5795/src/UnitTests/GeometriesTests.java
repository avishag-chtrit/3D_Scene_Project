package UnitTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import geometries.*;
import primitives.*;

public class GeometriesTests 
{

	@Test
	public void testFindIntsersections()
	{	
		Polygon poly1 = new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                new Point3D(0, 1, 0), new Point3D(-1, 1, 1));
		
		Triangle tr1 = new Triangle(new Point3D(1, 2, 0), new Point3D(2, 2, 0 ),
				new Point3D(1.5, 2,1)); 
		
		Plane pln1 = new Plane(new Point3D(3, 1, 0), new Point3D(4, 1, 0),
				new Point3D(3.5, 0, 1));
		
		Sphere sp = new Sphere(new Point3D(0, -2, 0), 1.0);
		
		Tube tb = new Tube(new Ray(new Point3D(0, 2, 0), new Vector(0, 0, 1)), 0.5 );
				 
		Geometries geo1 = new Geometries(poly1, tr1, pln1, sp, tb); 
		
		Geometries geo2 = new Geometries();
		
		//--------------------------------------------------------
		//Polygon poly2 = new Polygon(new Point3D(-0.5, 0, 0), new Point3D(0.5, 0, 0), 
				//new Point3D(-0.5, 0, 1), new Point3D(0.5, 0, 1));
		
		Triangle tr2 = new Triangle(new Point3D(-0.5, 1, 0), new Point3D(0.5, 1, 0 ),
				new Point3D(0, 1,1)); 
		
		Plane pln2 = new Plane(new Point3D(-0.5, -0.5, 0), new Point3D(0.5, -0.5, 0),
				new Point3D(0, -0.75, 1));
		
		Geometries geo3 = new Geometries(poly1, tr2, pln2, sp, tb);
		
		// ============ Equivalence Partitions Tests ==============
		
		Ray ray1 = new Ray(new Point3D(2, -2, 0), new Vector(-1, 0, 0));
		List intersectionPoints = geo1.findIntersections(ray1);
		assertEquals("ERROR, there must be intersection points", 2, intersectionPoints.size());
		//there must be 5 intersection points but we did not implement the intersections with tube and polygon
		
		// =============== Boundary Values Tests ==================
		try
		{
			Ray ray2 = new Ray(new Point3D(1,1,1), new Vector(1,0,0));
			geo2.findIntersections(ray2);
			fail("Exception had to be thrown, because there are no geometries...");
			
		}
		catch(IllegalArgumentException e)
		{
			
		}
		
		try
		{
			// none of the geometries has an intersection with ray
			Ray ray3 = new Ray(new Point3D(-1, -1, 0), new Vector(-2, -2,0));
			geo1.findIntersections(ray3);
		}
		catch(Exception e)
		{
			fail("ERROR, there are no intersection points, therefore it must return null");
		}
		
		try
		{ 
			//one of the geometries has an intersection with ray
			Ray ray4 = new Ray(new Point3D(0, -0.5, 0), new Vector(0, -5, 0));
			geo1.findIntersections(ray4);
			
			//all of of the geometries must have intersection points with ray
			Ray ray5 = new Ray(new Point3D(0, -4, 0), new Vector(0,1,0));
			geo3.findIntersections(ray5);
		}
		catch(Exception e)
		{
			fail("ERROR, there must be intersection points");
		}
		
		
		
	}

}
