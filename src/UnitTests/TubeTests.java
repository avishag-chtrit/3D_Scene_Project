package UnitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import geometries.Cylinder;
import geometries.Tube;
import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

public class TubeTests 
{

	@Test
	public void testGetNormal() 
	{
		Point3D center = new Point3D(0,0,0);
		Vector direction = new Vector(0,0,1);
		Ray ray = new Ray(center, direction);
		Tube tube = new Tube(ray, 25);
		
		Point3D p = new Point3D(25, 0, 1);
	 
        double t = p.subtract(center).dotProduct(direction);
        
        Point3D o=null;    
        if (!Util.isZero(t))
        	 o = center.add(direction.scale(t));
        
        //============ Equivalence Partitions Tests ==============
        Vector n = (p.subtract(o)).normalize();
        Vector exRes = new Vector(1, 0, 0);
		
        assertEquals("ERROR, Tube's GetNormal() - wrong result ", exRes, n);
				
	}

}
