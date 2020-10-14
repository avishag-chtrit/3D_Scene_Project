package UnitTests;

import static org.junit.Assert.*;
import org.junit.Test;
import primitives.*;

public class Point3DTests 
{

	@Test
	public void testEqualsObject() 
	{
        Coordinate c1 = new Coordinate(1.0);
		
		Point3D p1 = new Point3D(c1,c1,c1);
		Point3D p2 = new Point3D(c1,c1,c1);
		assertEquals(p1, p2);
	}

	@Test
	public void testSubtract() 
	{
        Coordinate c1 = new Coordinate(1.0);
		
		Coordinate c2 = new Coordinate(2.0);
		
		Point3D p1 = new Point3D(c2,c2,c2); //(2,2,2)
		Point3D p2 = new Point3D(c1,c1,c1); //(1,1,1)
		Vector res = p1.subtract(p2);//p1 = (1,1,1)
		Point3D p3 = new Point3D(c1,c1,c1); //(1,1,1)
		Point3D p4 = res.getHead();
		assertEquals(p3, p4);
	}

	@Test
	public void testAdd() 
	{
		Coordinate c1 = new Coordinate(1.0);
		Coordinate c2 = new Coordinate(2.0);
		
		Point3D p1 = new Point3D(c1,c1,c1); //(1,1,1)
		Vector v = new Vector(c1,c1,c1); //(1,1,1)
		Vector res = new Vector(p1.add(v)); //v=(2,2,2)
		
		Point3D p2 = new Point3D(c2,c2,c2); //(2,2,2)
		Point3D p3 = res.getHead();
		assertEquals(p2, p3);
	}

}
