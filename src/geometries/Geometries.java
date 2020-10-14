package geometries;

import java.awt.Composite;
import java.util.ArrayList;
import java.util.List;

import primitives.BoundingBox;
import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
 

import static primitives.Util.*;

/**
 * this class represents all the geometries of a scene
 * this class implements the Intersectable interface
 * @author chetrit
 *
 */
public class Geometries implements Intersectable 
{
	/**
	 * a list of geometries that are interectable
	 */
	private List<Intersectable> _geometries;
	
	/**
	 * every  list of intersectable geometries needs to have 
	 * a bounding box with x,y,z minimum and maximum limits
	 */
	private BoundingBox Bounding_Box;
	
	/**
	 * default constructor
	 */
	public Geometries()
	{
		Bounding_Box = new BoundingBox();
		
		_geometries = new ArrayList<Intersectable>();
		// run time of insert and remove is quick because we can use an index
		
		setBoundingBox(); // in order to create the bounding box to all of the geometries in the list
	}
	
	/**
	 * constructor that receives intersectable geometries
	 * @param geometries - list of intersectable geometries
	 */
	public Geometries(Intersectable... geometries)
	{
		Bounding_Box = new BoundingBox();
		
		_geometries = new ArrayList<Intersectable>();
		
		for(int i = 0 ; i< geometries.length ; i++)
		{		 		
			_geometries.add(i, geometries[i]);
		}
		setBoundingBox(); // in order to create the bounding box to all of the geometries in the list
	}
		
	/** 
	 * a function which adds geometries to the list of geometries
	 * @param geometries - list of intersectable geometries
	 */
	public void add(Intersectable... geometries)
	{
		for(Intersectable geometry :geometries )
		{
			_geometries.add(geometry);
			 
		}			
		setBoundingBox(); // setting the bounding box's limits values
	}
	
	/**
	 * a function that finds intersections of ray with all of the geometries that are given in the list
	 * @param Ray - a ray that we need to find intersections with
	 * @return List<Point3D> of intersections - list of intersection points
	 */
	public List<GeoPoint> findIntersections(Ray ray)
	{
		if(!Bounding_Box.Intersects(ray))
			return null;
						
		List<GeoPoint> intersections = null;
		
		for(Intersectable geometry: _geometries)
		{
			List<GeoPoint> tempIntersections = geometry.findIntersections(ray);
						
			if(tempIntersections != null)
			{
				if(intersections == null)
					intersections = new ArrayList<GeoPoint>();
				intersections.addAll(tempIntersections);
			}			
		}
		return intersections;
	}
		
		public int getNumberOfGeometries()
		{
			return _geometries.size();
		} 
		
		/**
		 * a function that every intersectable geometry- should have in order to create 
		 * a bounding box
		 */
		public void setBoundingBox()
		{
			double xMinLimit = this.Bounding_Box.get_X_min(); // going in the negative direction of x
			double xMaxLimit = this.Bounding_Box.get_X_max(); // going in the positive direction of x
			 
			double yMinLimit = this.Bounding_Box.get_Y_min(); // going in the negative direction of y
			double yMaxLimit = this.Bounding_Box.get_Y_max(); // going in the positive direction of y
			 
			double zMinLimit = this.Bounding_Box.get_Z_min(); // going in the negative direction of z
			double zMaxLimit = this.Bounding_Box.get_Z_max(); // going in the positive direction of z
				    				
			 for(Object geometry : _geometries)
			 {
				 if(geometry instanceof Geometry)
				 {					 					 
					 Geometry geo = ((Geometry)geometry);
					 					 				 					 					 
					// setting x limits--------------------------
					 if(geo.Bounding_Box.get_X_min() < xMinLimit)
						 xMinLimit = geo.Bounding_Box.get_X_min();				 
					 			 			 				 		 
					 if(xMaxLimit < geo.Bounding_Box.get_X_max())
						 xMaxLimit = geo.Bounding_Box.get_X_max(); 	
				 			 
				      // setting y limits-------------------------			 
					 if(geo.Bounding_Box.get_Y_min()< yMinLimit)
						 yMinLimit = geo.Bounding_Box.get_Y_min();					 
					 			 			 
					 if(yMaxLimit < geo.Bounding_Box.get_Y_max())
						 yMaxLimit = geo.Bounding_Box.get_Y_max();	
					 				 
				     // setting z limits -----------------------
			 		if(geo.Bounding_Box.get_Z_min() < zMinLimit)
						 zMinLimit = geo.Bounding_Box.get_Z_min();	
			 		
					 if(zMaxLimit < geo.Bounding_Box.get_Z_max())
						 zMaxLimit = geo.Bounding_Box.get_Z_max();											 					 
					 //------------------------------------------
				 }	
				 
				 else
				 {
					 Geometries geo = ((Geometries)geometry); // in case there's an object of type Geometries- because of the hierarchy
	 				
					// setting x limits--------------------------
					 if(geo.Bounding_Box.get_X_min() < xMinLimit)
						 xMinLimit = geo.Bounding_Box.get_X_min();				 
					 			 			 				 		 
					 if(xMaxLimit < geo.Bounding_Box.get_X_max())
						 xMaxLimit = geo.Bounding_Box.get_X_max(); 	
				 			 
				      // setting y limits-------------------------			 
					 if(geo.Bounding_Box.get_Y_min()< yMinLimit)
						 yMinLimit = geo.Bounding_Box.get_Y_min();					 
					 			 			 
					 if(yMaxLimit < geo.Bounding_Box.get_Y_max())
						 yMaxLimit = geo.Bounding_Box.get_Y_max();	
					 				 
				     // setting z limits -----------------------
			 		if(geo.Bounding_Box.get_Z_min() < zMinLimit)
						 zMinLimit = geo.Bounding_Box.get_Z_min();	
			 		
					 if(zMaxLimit < geo.Bounding_Box.get_Z_max())
						 zMaxLimit = geo.Bounding_Box.get_Z_max();											 					 
					 //------------------------------------------
						
				 }
			 }				
			 
			 this.Bounding_Box = new BoundingBox(xMinLimit, xMaxLimit, yMinLimit, yMaxLimit, zMinLimit, zMaxLimit);
			 
			 //------------------------------------
			 //System.out.println("Xmin = " + this.Bounding_Box.get_X_min() + " Xmax = " + this.Bounding_Box.get_X_max());
		     //System.out.println("Ymin = " + this.Bounding_Box.get_Y_min() +  " Ymax = " + this.Bounding_Box.get_Y_max()); 
		     //System.out.println("Zmin = " + this.Bounding_Box.get_Z_min() +  " Zmax = " + this.Bounding_Box.get_Z_max()); 
		     //-------------------------------------
		}
		
};		
	


















		
		
		
		
		
		// My Version----------------------
		
		/*if(Util.isZero(_geometries.size()))
			throw new IllegalArgumentException("There are no geometries, therefore there are no intersection points to find");
					
		ArrayList intersectionPoints = new ArrayList<Point3D>();
		for(Intersectable geometry :_geometries)
		{			
			if(geometry.findIntersections(ray) != null)
			{
				List <Point3D> geometryIntersections = geometry.findIntersections(ray);
				
				for(int i = 0; i <geometryIntersections.size() ; i++)
				{
					intersectionPoints.add(geometryIntersections.get(i));
				}			
			}	 
		}
			
		if(intersectionPoints == null)
			return null;
		else
			return intersectionPoints;*/
	
	
	
	
	

	

