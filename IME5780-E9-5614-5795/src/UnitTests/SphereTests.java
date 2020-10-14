package UnitTests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import geometries.Sphere;
import geometries.Intersectable.GeoPoint;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
public class SphereTests 
{

	@Test
	public void testGetNormal() 
	{
		Point3D p0 = new Point3D(1, 2, 3);
		Point3D p = new Point3D(1+ 5, 2, 3);
		Sphere s = new Sphere(p0, 5);		
		
		//============ Equivalence Partitions Tests 
		Vector normal = new Vector(p.subtract(p0));
		normal.normalize();
		Vector ExpRes = new Vector(1, 0, 0);
		assertEquals("Error, Sphere - GetNormal(), wrong result", ExpRes, normal);			
	}
	
	@Test
	public void testFindIntersections()
	{
		Sphere sp = new Sphere(new Point3D(2, 2, 2), 2);
		
		//============ Equivalence Partitions Tests 
		
		Ray ray1 = new Ray(new Point3D(1, 0, 0), new Vector(1, 0, 0));
		List<GeoPoint> IntersectionPoints1 = sp.findIntersections(ray1);
		assertEquals("Error, there must not be intersetion points in this case", IntersectionPoints1, null);
		
		Ray ray2 = new Ray(new Point3D(0.5, 0.5, 1), new Vector(1.5, 1.5, 3));
		List<GeoPoint> IntersectionPoints2 = sp.findIntersections(ray2);
		assertEquals("ERROR, there must be 2 intersection points in this case", IntersectionPoints2.size(), 2 );
		
		Ray ray3 = new Ray(new Point3D(0.5, 0.5, 1), new Vector(-1, -1, -2));
		List<GeoPoint> IntersectionPoints3 = sp.findIntersections(ray3);
		assertEquals("ERROR, the ray must start from the oposite direction and have no intersection points", IntersectionPoints3, null);
		
		// the start of the ray is not included
		Ray ray4 = new Ray(new Point3D(1, 1, 2), new Vector(2, 2, 4));
		List<GeoPoint> IntersectionPoints4 = sp.findIntersections(ray4);
		assertEquals("ERROR, there must be not be an intersection point", IntersectionPoints4.size(), 1);
		
		// =============== Boundary Values Tests ==================
		
		try
		{
			//the ray starts in the center point
			Ray ray5 = new Ray(new Point3D(2, 2, 2), new Vector(4, 4, 4));
			List<GeoPoint> IntersectionPoints5 = sp.findIntersections(ray5);
			
			Ray ray6 = new Ray(new Point3D(3, 3, 3), new Vector(4, 4, 4));
			List<GeoPoint> IntersectionPoints6 = sp.findIntersections(ray6);
					
			Ray ray7 = new Ray(new Point3D(4, 3, 2), new Vector(0, -1, 0));
			List<GeoPoint> IntersectionPoints7 = sp.findIntersections(ray7);
			
			// the start of ray is not included
			Ray ray8 = new Ray(new Point3D(4, 2, 2), new Vector(-1, 0, 0));
			List<GeoPoint> IntersectionPoints8 = sp.findIntersections(ray8);
			
			Ray ray9 = new Ray(new Point3D(4, 2, 2), new Vector(-6, -3, -3));
			List<GeoPoint> IntersectionPoints9 = sp.findIntersections(ray9);
			
			
									
		}
		catch(Exception e)
		{
			fail("there must be 1 intersection point"); 				
		}
			 
		try
		{
			Ray ray10 = new Ray(new Point3D(0.5, 0.5, 0.5), new Vector(-4, -4, -4));
			List<GeoPoint> IntersectionPoints10 = sp.findIntersections(ray10);
			
			Ray ray11 = new Ray(new Point3D(4, 1, 2), new Vector(0, -1, 0));
			List<GeoPoint> IntersectionPoints11 = sp.findIntersections(ray11);
			
			//the point of the start of the ray
			Ray ray12 = new Ray(new Point3D(2, 2, 4), new Vector(2, 2, 4));
			List<GeoPoint> IntersectionPoints12 = sp.findIntersections(ray12);
			
			//the point of the start of the ray
			Ray ray13 = new Ray(new Point3D(4, 2, 2), new Vector(6, 3, 3));
			List<GeoPoint> IntersectionPoints13 = sp.findIntersections(ray13);
			
			//the point of the start of the ray
			Ray ray14 = new Ray(new Point3D(4, 2, 2), new Vector(0, -1, 0));
			List<GeoPoint> IntersectionPoints14 = sp.findIntersections(ray14);
		}
		catch(Exception e)
		{
			fail("there are no intersection points - therefpre it must return null!");
		}
		
		try
		{		
			Ray ray15 = new Ray(new Point3D(0.5, 0.5, 0.5), new Vector(4, 4, 4));
			List<GeoPoint> IntersectionPoints15 = sp.findIntersections(ray15);
						
		}
		catch(Exception e)
		{
			fail("there must be 2 intersection points");
		}
		
	 
		
	 
		
	 
		
		 
		
	 
		
	}
}
