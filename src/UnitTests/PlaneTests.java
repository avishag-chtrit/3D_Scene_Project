package UnitTests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import geometries.Intersectable.GeoPoint;
import geometries.Plane;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class PlaneTests 
{

	@Test
	public void testGetNormal() 
	{
		Point3D a = new Point3D(1, 0, 0);
		Point3D b = new Point3D(0, 1, 0);
		Point3D c = new Point3D(0, 0, 1);
		
		Plane pln = new Plane(a , b, c);
		Vector normal1 = pln.getNormal();
		Vector normal2 = pln.getNormal(a); // the function that does receive a point	 
		
		 
		// ============ Equivalence Partitions Tests ============== 
		Vector vec1 = new Vector(b.subtract(a));
		Vector vec2 = new Vector(c.subtract(a));
		
		Vector vec3 = vec1.crossProduct(vec2);
		Vector normalToPln = vec3.normalize();
		
		assertEquals("GetNormal() wrong result- this is not the normal", normalToPln, normal1);
		assertEquals("GetNormal() wrong result- this is not the normal", normalToPln, normal2);
	}
	
	@Test
	public void testFindIntersections()
	{
		Plane plane = new Plane(new Point3D(0.5, 0.5,0), new Vector(-1, -1, 0));
		
		// ============ Equivalence Partitions Tests ============== 
		
		Ray ray1 = new Ray(new Point3D(2, 2, 0), new Vector(-1, -2, 0));
		List<GeoPoint> intersectionPoints1 = plane.findIntersections(ray1);
		assertEquals("ERROR, there must be an intersection point", intersectionPoints1.size(), 1);
		
		Ray ray2 = new Ray(new Point3D(-1, -2, 0), new Vector(-1, -2, 0));
		List<GeoPoint> intersectionPoints2 = plane.findIntersections(ray2);
		assertEquals("ERROR, there must not be an intersection point", intersectionPoints2, null);
		
		// =============== Boundary Values Tests ==================
		try
		{
			Ray ray3 = new Ray(new Point3D(-1, 1, 0), new Vector(-2, 2, 0));
			List<GeoPoint> intersectionPoints3 = plane.findIntersections(ray3);			 
			
			Ray ray5 = new Ray(new Point3D(0.25, 0.25, 0), new Vector(-1, -1, 0));
			List<GeoPoint> intersectionPoints5 = plane.findIntersections(ray5);
			
			//the start of the ray is not included
			Ray ray6 = new Ray(new Point3D(0.5, 0.5, 0), new Vector(-1, -1, 0));
			List<GeoPoint> intersectionPoints6 = plane.findIntersections(ray6);
			
			//the start of the ray is not included
			Ray ray8 = new Ray(new Point3D(0.75, 0.5, 0), new Vector(1.5, 1, 0));
			List<GeoPoint> intersectionPoints8 = plane.findIntersections(ray8);
			
			//the ray is included in the plane
			Ray ray4 = new Ray(new Point3D(-1, 2, 0), new Vector(-2, 2, 0));
			List<GeoPoint> intersectionPoints4 = plane.findIntersections(ray4);
			 
		}
		catch(Exception e)
		{
			fail("ERROR, there are no intersection points, therefore it must return null");
		}
		 
		try
		{			
			Ray ray7 = new Ray(new Point3D(1, 1, 0), new Vector(-1, -1, 0));
			List<GeoPoint> intersectionPoints7 = plane.findIntersections(ray7);					
		}
		catch(Exception e)
		{
			fail("ERROR, there must be an intersection point");
		}
		
	}
}

	 