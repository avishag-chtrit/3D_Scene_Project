/**
 * 
 */
package UnitTests;

import primitives.Util.*;

import static java.lang.System.out;
import static org.junit.Assert.*;
import static primitives.Util.isZero;

import org.junit.Test;

import primitives.Point3D;
import primitives.Vector;

/**
 *  Unit test for primitives.Vector class
 * @author chetrit
 */
public class VectorTests 
{

	/**
	 * Test method for {@link primitives.Vector#subtract(primitives.Vector)}.
	 */
	@Test
	public void testSubtract() 
	{	
		Vector v1 = new Vector(2, 4, 6);
        Vector v2 = new Vector(1, 2, 3);
        
        // ============ Equivalence Partitions Tests ==============
        // checks whether or not the result of subtraction works
        Vector v3 = new Vector(1, 2, 3);
        Vector vr = v1.subtract(v2);
        assertEquals("Vector subtruction wrong result ", vr, v3);
         
        //=============== Boundary Values Tests ==================
        // checks if when direction of vector equals to zero - an exception is thrown
        try
        {
        	v2.subtract(v2);
        	fail("Didn't throw direction of vector cannot be zero exception!");
        }
        catch(IllegalArgumentException e) 
        {
        	assertTrue(true);
        }                               
	}

	/**
	 * Test method for {@link primitives.Vector#add(primitives.Vector)}.
	 */
	@Test
	public void testAdd() 
	{
		Vector v1 = new Vector(2, 4, 6);
        Vector v2 = new Vector(1, 2, 3);
        
        // ============ Equivalence Partitions Tests ==============        
        Vector v3 = new Vector(3, 6, 9);
        Vector vr = v1.add(v2);
        assertEquals("Vector addition wrong result ", vr, v3);
        
        //=============== Boundary Values Tests ==================
        // checks if when direction of vector equals to zero - an exception is thrown 
        try
        {
        	Vector v4 = new Vector(-3, -6, -9);
        	v3.add(v4);
        	fail("Didn't throw direction of vector cannot be zero exception!");
        }
        catch(IllegalArgumentException e) 
        {
        	assertTrue(true);
        }       
	}

	/**
	 * Test method for {@link primitives.Vector#scale(double)}.
	 */
	@Test
	public void testScale() 
	{
		Vector v1 = new Vector(1, 2, 3);
        double num = 2; 
		 
	    // ============ Equivalence Partitions Tests ==============
        // check if vector scaling works
        Vector v2 = new Vector(2, 4, 6);
        Vector v3 = v1.scale(num);
        
        assertEquals("Vector scale() wrong result", v2, v3);
        
        //=============== Boundary Values Tests ==================
        // checks if when scaling by zero an exception is thrown
        try
        {
        	v1.scale(0);
        	fail("Didn't throw can not scale by zero Exception");
        }
        catch(IllegalArgumentException ex)
        {
        	assertTrue(true);
        }
	}

	/**
	 * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
	 */	 
	@Test
	public void testDotProduct() 
	{
		Vector v1 = new Vector(2, 4, 6);
        Vector v2 = new Vector(1, 2, 3);
        
        Vector v3 = new Vector(0, 1, 0);
        Vector v4 = new Vector(1, 0, 0);
        
        // ============ Equivalence Partitions Tests ==============
        // checks if dot product works
        double check1 = 28;
        double dr1 = v1.dotProduct(v2);
        
        double check2 = 0;
        double dr2 = v3.dotProduct(v4);
           
        assertEquals("Vector DotProduct() wrong result ", dr1, check1, 0.00001);
        assertEquals("Vector DotProduct() wrong result - orthogonal vectors dot product doesn't return 0 ", dr2, check2, 0.00001);     
	}

	/**
	 * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
	 */
	@Test
	public void testCrossProduct() 
	{
		Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);

        // ============ Equivalence Partitions Tests ==============
        Vector v3 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v3);

        // Test that length of cross-product is proper (orthogonal vectors taken for simplicity)
        assertEquals("crossProduct() wrong result length", v1.length() * v3.length(), vr.length(), 0.00001);

        // Test cross-product result orthogonality to its operands
        assertTrue("crossProduct() result is not orthogonal to 1st operand", primitives.Util.isZero(vr.dotProduct(v1)));
        assertTrue("crossProduct() result is not orthogonal to 2nd operand", primitives.Util.isZero(vr.dotProduct(v3)));

        // =============== Boundary Values Tests ==================
        // test zero vector from cross-product of co-lined vectors
        try 
        {
            v1.crossProduct(v2);
            fail("crossProduct() for parallel vectors does not throw an exception");
            
        }
        catch(IllegalArgumentException ex)
        {
        	assertTrue(true);          	
        }
	}

	/**
	 * Test method for {@link primitives.Vector#lengthSquared()}.
	 */
	@Test
	public void testLengthSquared() 
	{
		Vector v1 = new Vector(1, 1, 1);		
		 
		// ============ Equivalence Partitions Tests ==============
		double num = 3;
		double res = v1.lengthSquared();
		assertEquals("LengthSquared() wrong result", num, res, 0.00001);
		
		 // =============== Boundary Values Tests ==================
		// checks if an exception is thrown when squared length is zero  
		 try 
	       { 
			 (new Vector(0, 0, 0)).lengthSquared();
			 fail("LengthSquared() does not throw an exception when length is 0");  
	       }
		 catch(IllegalArgumentException ex)
	       {
	          assertTrue(true);          	
	       }
	}

	/**
	 * Test method for {@link primitives.Vector#length()}.
	 */
	@Test
	public void testLength() 
	{
		Vector v1 = new Vector(1, 2, 2);		
		 
		// ============ Equivalence Partitions Tests ==============
		double num = 3;
		double res = v1.length();
		assertEquals("Length() wrong result", num, res, 0.00001);
		
		 // =============== Boundary Values Tests ==================
		// checks if an exception is thrown when length is zero  
		 try 
	       { 
			 (new Vector(0, 0, 0)).length();
			 fail("Length() does not throw an exception when length is 0");  
	       }
		 catch(IllegalArgumentException ex)
	       {
	          assertTrue(true);          	
	       }
	}

	/**
	 * Test method for {@link primitives.Vector#normalize()}.
	 */
	@Test
	public void testNormalize() 
	{
		
		Vector v = new Vector(4, 0, 0);
	    Vector vCopy = new Vector(v);
	    Vector vCopyNormalize = vCopy.normalize();
	      
	    //============ Equivalence Partitions Tests ==============
	    // check if the original vector has been normalized	    
	    double num = 1;
	    
	    assertEquals("ERROR: normalize() function creates a new vector",vCopy, vCopyNormalize);
	    assertEquals("ERROR: normalize() result is not a unit vector",vCopyNormalize.length(), num,0.00001 );   		
	}

	/**
	 * Test method for {@link primitives.Vector#normalized()}.
	 */
	@Test
	public void testNormalized()
	{				
        Vector v1 = new Vector(4, 0, 0);		
		
		//============ Equivalence Partitions Tests ==============
		// check if the original vector has been normalized    
	 
		Vector v2 = v1.normalized();
		Vector v3 = new Vector(new Point3D(1, 0, 0));
		assertEquals("ERROR: normalizated() function does not create a new vector", v2, v3);		
	}

}
