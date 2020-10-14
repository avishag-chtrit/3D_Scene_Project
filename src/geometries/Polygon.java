package geometries;

import java.util.ArrayList;
import java.util.List;
import primitives.*;
import static primitives.Util.*;

/**
 * Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 * 
 * @author Dan
 */
public class Polygon extends Geometry 
{
    /**
     * List of polygon's vertices
     */
    protected List<Point3D> _vertices;
    /**
     * Associated plane in which the polygon lays
     */
    protected Plane _plane;

    /**
     * Polygon constructor based on vertices list. The list must be ordered by edge
     * path. The polygon must be convex.
     * 
     * @param vertices list of vertices according to their order by edge path
     * @throws IllegalArgumentException in any case of illegal combination of
     *                                  vertices:
     *                                  <ul>
     *                                  <li>Less than 3 vertices</li>
     *                                  <li>Consequent vertices are in the same
     *                                  point
     *                                  <li>The vertices are not in the same
     *                                  plane</li>
     *                                  <li>The order of vertices is not according
     *                                  to edge path</li>
     *                                  <li>Three consequent vertices lay in the
     *                                  same line (180&#176; angle between two
     *                                  consequent edges)
     *                                  <li>The polygon is concave (not convex></li>
     *                                  </ul>
     */
    public Polygon(Color color,  Material material, Point3D... vertices) 
    {
    	super(color, material); 
    	
    	Bounding_Box = new BoundingBox(); // initializing the bounding box's values
    	
        if (vertices.length < 3)
            throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
        _vertices = new ArrayList<Point3D>();
        // Generate the plane according to the first three vertices and associate the
        // polygon with this plane.
        // The plane holds the invariant normal (orthogonal unit) vector to the polygon
        _plane = new Plane(vertices[0], vertices[1], vertices[2]);
        
        if (vertices.length == 3)
        {      	
        	_vertices.add(vertices[0]);
        	_vertices.add(vertices[1]);
        	_vertices.add(vertices[2]);
        	
        	this.setBoundingBox();
        	
        	return; // no need for more tests for a Triangle      	
        }
        	

        Vector n = _plane.getNormal();

        // Subtracting any subsequent points will throw an IllegalArgumentException
        // because of Zero Vector if they are in the same point
        Vector edge1 = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
        Vector edge2 = vertices[0].subtract(vertices[vertices.length - 1]);

        // Cross Product of any subsequent edges will throw an IllegalArgumentException
        // because of Zero Vector if they connect three vertices that lay in the same
        // line.
        // Generate the direction of the polygon according to the angle between last and
        // first edge being less than 180 deg. It is hold by the sign of its dot product
        // with
        // the normal. If all the rest consequent edges will generate the same sign -
        // the
        // polygon is convex ("kamur" in Hebrew).
        boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
        for (int i = 1; i < vertices.length; ++i) {
            // Test that the point is in the same plane as calculated originally
            if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
                throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
            // Test the consequent edges have
            edge1 = edge2;
            edge2 = vertices[i].subtract(vertices[i - 1]);
            if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
                throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
            
            setBoundingBox();
        }
    }
    
    /**
     * constructor that sets a default color to the geometry
     */
    public Polygon(Point3D... vertices)
    {
    	this(Color.BLACK, vertices);
    }
    
    /**
     * constructor that sets a default material to the geometry
     * @param emissionLight
     * @param vertices
     */
    public Polygon(Color emissionLight, Point3D... vertices)
    {
        this(emissionLight,new Material(0,0,0),vertices);
    }

    /**
     * calculates the normal to polygon
     * @return Vector
     */
    @Override
    public Vector getNormal(Point3D point) 
    {
        return _plane.getNormal();
    }
    
    /**
     * a function that calculates the intersection points with the given polygon
     * @param Ray - ray we want to fid intersections with
     * @return List<Point3D> of intersection points
     */
    public List<GeoPoint> findIntersections(Ray ray)
	{
    	if(!this.Bounding_Box.Intersects(ray)) // if the ray does not intersect the bounding box - then we won't 
			return null;  // calculate intersection points with polygon 
    	
    	List<GeoPoint> intersections = _plane.findIntersections(ray);
        if (intersections == null)
            return null;

        Point3D p0 = ray.get_Point();
        Vector v = ray.getDirection();

        Vector v1  = _vertices.get(1).subtract(p0);
        Vector v2 = _vertices.get(0).subtract(p0);
        double sign = v.dotProduct(v1.crossProduct(v2));
        if (isZero(sign))
            return null;

        boolean positive = sign > 0;

        for (int i = _vertices.size() - 1; i > 0; --i) 
        {
            v1 = v2;
            v2 = _vertices.get(i).subtract(p0);
            sign = alignZero(v.dotProduct(v1.crossProduct(v2)));
            if (isZero(sign)) return null;
            if (positive != (sign >0)) return null;
        }

        intersections.get(0).geometry = this;

        return intersections;
	}
    
    /**
	 * this function sets the values of the bounding box of the sphere
	 */
	@Override
	public void setBoundingBox() 
	{	
		 double xMinLimit =  this.Bounding_Box.get_X_min(); // going in the negative direction of x
		 double xMaxLimit =  this.Bounding_Box.get_X_max(); // going in the positive direction of x
		 
		 double yMinLimit = this.Bounding_Box.get_Y_min(); // going in the negative direction of y
		 double yMaxLimit = this.Bounding_Box.get_Y_max(); // going in the positive direction of y
		 
		 double zMinLimit = _vertices.get(0).getZ().get() - 1; // going in the negative direction of z
		 double zMaxLimit = _vertices.get(0).getZ().get() + 1; // going in the positive direction of z
		 // because all vertices must lay on the same plane
		 
		 for(Point3D vertice : _vertices)
		 {
			 if(vertice.getX().get() < xMinLimit)
				 xMinLimit = vertice.getX().get();
			 
			 if(xMaxLimit < vertice.getX().get())
				 xMaxLimit = vertice.getX().get();
			 
			 if(vertice.getY().get() < yMinLimit)
				 yMinLimit = vertice.getY().get();
			 
			 if(yMaxLimit < vertice.getY().get())
				 yMaxLimit = vertice.getY().get();		 
		 }
		  
		 Bounding_Box = new BoundingBox(xMinLimit, xMaxLimit, yMinLimit, yMaxLimit, zMinLimit, zMaxLimit);
		 
			//------------------------------------
		 //System.out.println("Xmin = " + Bounding_Box.get_X_min() + " Xmax = " + Bounding_Box.get_X_max());
	     //System.out.println("Ymin = " + Bounding_Box.get_Y_min() +  " Ymax = " + Bounding_Box.get_Y_max()); 
	     //System.out.println("Zmin = " + Bounding_Box.get_Z_min() +  " Zmax = " + Bounding_Box.get_Z_max()); 
	     //-------------------------------------
		 
	}
}
