package geometries;

import java.util.ArrayList;
import java.util.List;

import primitives.*;

/**
 * this class represents a triangle
 * and extends the polygon class because a triangle is kind of a polygon
 * only with 3 vertices
 * @author chetrit
 *
 */
public class Triangle extends Polygon
{
	
	/**
	 * constructor of triangle which activates the base's constructor
	 * @param emission - the emission of the triangle
	 * @param a - a vertice of the triangle
	 * @param b - a vertice of the triangle
	 * @param c - a vertice of the triangle
	 */
	public Triangle(Color emission, Material material,  Point3D a, Point3D b, Point3D c)
	{
		super(emission, material, a, b, c);
		this.setBoundingBox();
	}
	
	/**
	 * constructor that sets default color to geometry
	 * @param a - a vertice of the triangle
	 * @param b - a vertice of the triangle
	 * @param c - a vertice of the triangle
	 */ 
	public Triangle(Point3D a, Point3D b, Point3D c)
	{
		 super(a, b, c);
	}
	
	/**
	 * constructor that sets default material to geometry
	 * @param emission - the emission of the triangle
	 * @param a - a vertice of the triangle
	 * @param b - a vertice of the triangle
	 * @param c - a vertice of the triangle
	 */
	public Triangle(Color emission, Point3D a, Point3D b, Point3D c)
	{
		 super(emission, a, b, c);
	}
	
	/**
	 * a function that calculates the intersections
	 * @param Ray - the ray we want to find intersections with
	 * @return List<Point3D> of intersections with triangle
	 */
	@Override
	public List<GeoPoint> findIntersections(Ray ray)
	{
		if(!this.Bounding_Box.Intersects(ray)) // if the ray does not intersect the bounding box
			return null; // we will not calculate the intersection points with the triangle
		
		Point3D _p1 = this._vertices.get(0);
		Point3D _p2 = this._vertices.get(1);
		Point3D _p3 = this._vertices.get(2);
		
		Vector n = new Vector(getNormal(_p1));
		Plane plane = new Plane(_p1,n);	
		
		List<GeoPoint> intersection =  plane.findIntersections(ray);
		
		if(intersection == null)
			return intersection;
		
		//Vector po = new Vector(ray.get_Point());
		
		//Vector v1 = new Vector(_p1);
		//v1 = v1.subtract(po);
		//Vector v2=new Vector(_p2);
		//v2 = v2.subtract(po);
		//Vector v3=new Vector(_p3);
		//v3 = v3.subtract(po);
		
		Point3D po = new Point3D(ray.get_Point());
		Vector v = ray.getDirection();
		
		Vector v1 = _p1.subtract(po);
		Vector v2 = _p2.subtract(po);		
		Vector v3 = _p3.subtract(po);
						 	
		Vector n1 = new Vector(v1);
		n1 = n1.crossProduct(v2);
		n1.normalize();
		Vector n2 = new Vector(v2);
		n2 = n2.crossProduct(v3);
		n2.normalize();
		Vector n3 = new Vector(v3);
		n3 = n3.crossProduct(v1);
		n3.normalize();
		
		Vector p = new Vector((intersection.get(0)).point);
		p = (p.getHead()).subtract(po);

		double sign1 = Util.alignZero(p.dotProduct(n1));
		double sign2 = Util.alignZero(p.dotProduct(n2));
		double sign3 = Util.alignZero(p.dotProduct(n3));
		
		if((sign1> 0 && sign2> 0 && sign3>0 )|| (sign1 <0 && sign2<0 && sign3 <0))
		{
			//for GeoPoint
	        (intersection.get(0)).geometry = this;
	        return intersection;
		}
			
		else
		{
			intersection.clear();
			intersection = null;
		}
		
		
							
		return intersection;
	}
	
	/**
	 * overriding the toString function of Object class
	 */
	@Override
	public String toString()
	{
		return "\n" + "p0: " + (_vertices.get(0)).toString() + "\n" +  "p1: " + (_vertices.get(1)).toString() + "\n" +  "p1: " + (_vertices.get(2)).toString();
	}

}
