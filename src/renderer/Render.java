package renderer;

import scene.Scene;
import renderer.ImageWriter;

import java.util.ArrayList;
import java.util.List;
import elements.Camera;
import elements.DirectionalLight;
import elements.LightSource;
import geometries.Geometries;
import geometries.Intersectable;
import primitives.*;
import geometries.Intersectable.GeoPoint;
import geometries.Triangle;

/** 
 * a class that renders our picture
 * by the fields of scene and the image writer
 * @author chetrit
 *
 */
public class Render 
{
	private Scene _scene;
	private ImageWriter _imageWriter;
	
	/**
	 * hleps us to know the number of shadow rays that the user wants
	 */
	private int NumOfShadowRays; 
	
	/**
	 * helps us to know whether the user wants to add the improvement or not 
	 */
	private boolean improvement;
	
	/**
	 * helps us calculate shadow rays
	 */
	private static final double DELTA = 0.1;
	
	/**
	 * the minimum and maximum level of the recursion
	 */
	private static final int MAX_CALC_COLOR_LEVEL = 10;
	private static final double MIN_CALC_COLOR_K = 0.001;
	
	//------------------------------------------------------
	// ...........
		private int _threads = 1;
		private final int SPARE_THREADS = 2;
		private boolean _print = false;

		/**
		 * Pixel is an internal helper class whose objects are associated with a Render object that
		 * they are generated in scope of. It is used for multithreading in the Renderer and for follow up
		 * its progress.<br/>
		 * There is a main follow up object and several secondary objects - one in each thread.
		 */
		private class Pixel 
		{
			private long _maxRows = 0;
			private long _maxCols = 0;
			private long _pixels = 0;
			public volatile int row = 0;
			public volatile int col = -1;
			private long _counter = 0;
			private int _percents = 0;
			private long _nextCounter = 0;

			/**
			 * The constructor for initializing the main follow up Pixel object
			 * @param maxRows the amount of pixel rows
			 * @param maxCols the amount of pixel columns
			 */
			public Pixel(int maxRows, int maxCols) 
			{
				_maxRows = maxRows;
				_maxCols = maxCols;
				_pixels = maxRows * maxCols;
				_nextCounter = _pixels / 100;
				if (Render.this._print) System.out.printf("\r %02d%%", _percents);
			}

			/**
			 *  Default constructor for secondary Pixel objects
			 */
			public Pixel() {}

			/**
			 * Internal function for thread-safe manipulating of main follow up Pixel object - this function is
			 * critical section for all the threads, and main Pixel object data is the shared data of this critical
			 * section.<br/>
			 * The function provides next pixel number each call.
			 * @param target target secondary Pixel object to copy the row/column of the next pixel 
			 * @return the progress percentage for follow up: if it is 0 - nothing to print, if it is -1 - the task is
			 * finished, any other value - the progress percentage (only when it changes)
			 */
			private synchronized int nextP(Pixel target) 
			{
				++col;
				++_counter;
				if (col < _maxCols) 
				{
					target.row = this.row;
					target.col = this.col;
					if (_counter == _nextCounter) 
					{
						++_percents;
						_nextCounter = _pixels * (_percents + 1) / 100;
						return _percents;
					}
					return 0;
				}
				++row;
				if (row < _maxRows) 
				{
					col = 0;
					if (_counter == _nextCounter) 
					{
						++_percents;
						_nextCounter = _pixels * (_percents + 1) / 100;
						return _percents;
					}
					return 0;
				}
				return -1;
			}

			/**
			 * Public function for getting next pixel number into secondary Pixel object.
			 * The function prints also progress percentage in the console window.
			 * @param target target secondary Pixel object to copy the row/column of the next pixel 
			 * @return true if the work still in progress, -1 if it's done
			 */
			public boolean nextPixel(Pixel target) 
			{
				int percents = nextP(target);
				if (percents > 0)
					if (Render.this._print) System.out.printf("\r %02d%%", percents);
				if (percents >= 0)
					return true;
				if (Render.this._print) System.out.printf("\r %02d%%", 100);
				return false;
			}
		}

		/**
		 * This function renders image's pixel color map from the scene included with
		 * the Renderer object
		 */
		public void renderImage() 
		{
			final int nX = _imageWriter.getNx();
			final int nY = _imageWriter.getNy();
			final double dist = _scene.getDistance();
			final double width = _imageWriter.getWidth();
			final double height = _imageWriter.getHeight();
			final Camera camera = _scene.getCamera();
			java.awt.Color background = _scene.getBackground().getColor();

			final Pixel thePixel = new Pixel(nY, nX);

			// Generate threads
			Thread[] threads = new Thread[_threads];
			for (int i = _threads - 1; i >= 0; --i) 
			{
				threads[i] = new Thread(() ->
				{
					Pixel pixel = new Pixel();
					while (thePixel.nextPixel(pixel)) 
					{
						Ray ray = camera.constructRayThroughPixel(nX, nY, pixel.col, pixel.row, //
								dist, width, height);
						
						 GeoPoint closestPoint = findClosestIntersection(ray);
		            	 
		            	 if (closestPoint == null) 
		                     _imageWriter.writePixel(pixel.col, pixel.row, background);
		            	 else 
		                     _imageWriter.writePixel(pixel.col, pixel.row, calcColor(closestPoint, ray).getColor());									 
					}
				});
			}

			// Start threads
			for (Thread thread : threads) thread.start();

			// Wait for all threads to finish
			for (Thread thread : threads) try { thread.join(); } catch (Exception e) {}
			if (_print) System.out.printf("\r100%%\n");
		}

		/**
		 * Set multithreading <br>
		 * - if the parameter is 0 - number of coress less 2 is taken
		 * 
		 * @param threads number of threads
		 * @return the Render object itself
		 */
		public Render setMultithreading(int threads) 
		{
			if (threads < 0)
				throw new IllegalArgumentException("Multithreading patameter must be 0 or higher");
			if (threads != 0)
				_threads = threads;
			else {
				int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
				if (cores <= 2)
					_threads = 1;
				else
					_threads = cores;
			}
			return this;
		}

		/**
		 * Set debug printing on
		 * 
		 * @return the Render object itself
		 */
		public Render setDebugPrint() 
		{
			_print = true;
			return this;
		}

	//------------------------------------------------------

	/**
	 * constructor that initializes the fields
	 * @param iw - the image writer
	 * @param scene - the given scene
	 */
	public Render(ImageWriter iw, Scene scene)
	{
		this(iw, scene, 100); // default value of shadow rays 
	}
	
	/**
	 *  constructor that initializes the scene field
	 * @param scene- the given scene
	 */
	public Render(Scene _scene) 
	{
		this(_scene, 100);// default value of number of shadow rays        
    }
	//----------------------------------------------------------------------------
	/**
	 * constructor that sets the wanted number of shadow rays
	 * @param _scene - the given scene
	 * @param numOfShadowRays - the number of the shadow rays that the user wants
	 */
	public Render(Scene _scene, int numOfShadowRays) 
	{           
         this(_scene, numOfShadowRays, false); // Default value of improvement
    }
	
	/**
	 * constructor that sets the wanted number of shadow rays
	 * @param iw - the image writer
	 * @param scene - the given scene
	 * @param numOfShadowRays - the number of the shadow rays that the user wants
	 */
	public Render(ImageWriter iw, Scene scene, int numOfShadowRays) 
	{ 
		this(iw, scene, numOfShadowRays, false); // Default value of improvement
    }
	
	/**
	 * constructor that also sets the boolean - improvement value
	 * @param iw - the image writer
	 * @param scene - the given scene
	 * @param numOfShadowRays - the number of the shadow rays that the user wants
	 * @param improve - if the user wants to improve the picture by adding shadow rays or not
	 */
	public Render(ImageWriter iw, Scene scene, int numOfShadowRays ,boolean improve)
	{
		this._scene = scene;
		_imageWriter = iw;
        
        if(numOfShadowRays > 0)
        	this.NumOfShadowRays = numOfShadowRays;        
        else
        	throw new IllegalArgumentException("number of shadow rays can not be negative or zero!");     
        
		this.improvement = improve;
	}
	
	/**
	 * constructor that also sets the boolean - improvement value
	 * @param scene - the given scene
	 * @param numOfShadowRays - the number of the shadow rays that the user wants
	 * @param improve- if the user wants to improve the picture by adding shadow rays or not
	 */
	public Render(Scene scene, int numOfShadowRays ,boolean improve)
	{
		this._scene = scene;
        
        if(numOfShadowRays > 0)
        	this.NumOfShadowRays = numOfShadowRays;        
        else
        	throw new IllegalArgumentException("number of shadow rays can not be negative or zero!");     
        
		this.improvement = improve;
	}
		
	/**
	 * returns the scene
	 * @return Scene - our scene
	 */
	public Scene get_scene() 
	 {
		 return _scene;	        
	 }
	
	/**
	 * sets the number of shadow rays that the user wants
	 * @param numOfShadowRays - the number of the shadow rays that the user wants
	 */
	public void set_numOfShadowRays(int numOfShadowRays) 
	 {
		if(numOfShadowRays > 0)
        	this.NumOfShadowRays = numOfShadowRays;
        
        else
        	throw new IllegalArgumentException("number of shadow rays can not be negative or zero!"); 
	 }
	
	/**
	 * sets the boolean value of improvement in order for us to know
	 * whether or not the user wants the improvement
	 * @param improve- if the user wants to improve the picture by adding shadow rays or not
	 */
	public void set_improvement(boolean improve)
	{
		this.improvement = improve;
	}
 
	
	// RENDERIMAGE BEFORE THREADS -------------------------------------------------------------------
	/**
     * Filling the buffer according to the geometries that are in the scene.
     * This function does not creating the picture file, but create the buffer pf pixels
     */
	
	/* public void renderImage() 
	 {
	        java.awt.Color background = _scene.getBackground().getColor();
	        Camera camera = _scene.getCamera();
	        Intersectable geometries = _scene.getGeometries();
	        double  distance = _scene.getDistance();

	        //width and height are the number of pixels in the rows
	        //and columns of the view plane
	        int width = (int) _imageWriter.getWidth();
	        int height = (int) _imageWriter.getHeight();

	        //Nx and Ny are the width and height of the image.
	        int Nx = _imageWriter.getNx(); //columns
	        int Ny = _imageWriter.getNy();//rows
	        Ray ray;
	        
	        for (int row = 0; row < Ny; ++row)  
	        {
	            for (int column = 0; column < Nx; ++column)  
	            {	
	            	
	            	ray = camera.constructRayThroughPixel(Nx, Ny, column, row, distance, width, height);	            	 
	            	
	            	
	            	// BEFORE REFACTORING----------------------------------------
	                //List<GeoPoint> intersectionPoints = _scene.getGeometries().findIntersections(ray);             
	                //if (intersectionPoints == null) 
	                //{
	                //    _imageWriter.writePixel(column, row, background);
	                //} 
	                //else 
	                //{
	                //	GeoPoint closestPoint = getClosestPoint(intersectionPoints);
	                //	java.awt.Color pixelColor = calcColor(closestPoint).getColor();
	                //    _imageWriter.writePixel(column, row, pixelColor);	                	                    
	                //}
	                //--------------------------------
	            	
	            	 GeoPoint closestPoint = findClosestIntersection(ray);
	            	 
	            	 if (closestPoint == null) 
	                     _imageWriter.writePixel(column, row, background);
	            	 else 
	                     _imageWriter.writePixel(column, row, calcColor(closestPoint, ray).getColor());
	            }
	        }	        
	 }	*/ 
    //-------------------------------------------------------------------------
	
     /**
      * Finding the closest point to the P0 of the camera.
      * @param  intersectionPoints list of points, the function should find from
      * this list the closet point to P0 of the camera in the scene.
      * @return  the closest point to the camera
      */

     public GeoPoint getClosestPoint(List<GeoPoint> intersectionPoints) 
     {
    	 GeoPoint result = null;
         double mindist = Double.MAX_VALUE;

         Point3D p0 = this._scene.getCamera().get_p0();

         for (GeoPoint geo: intersectionPoints) 
         {
        	 Point3D pt = geo.getPoint();     	 
             double distance = p0.distance(pt);
             
             if(distance < mindist)
             {
                 mindist = distance;
                 result = geo;
             }
         }
         return  result;
     }
     
     
     /**
      * shell function of calcColor function - that calls the calcColor function - for the first time
      * @param geoPoint - the point (and the geometry of it) that we want to caculate its color
      * @param inRay - the ray from the light to the point
      * @return color - the color of the point
      */
     private Color calcColor(GeoPoint geoPoint, Ray inRay)
     {
    	 Color color = calcColor(geoPoint, inRay, MAX_CALC_COLOR_LEVEL, 1.0);
         color = color.add(_scene.getAmbientLight().getIntensity());
                 
         //------------------------------------
         //if(geoPoint.getGeometry() instanceof Triangle)
        	// System.out.println("calculating triangle colors");
         
         //------------------------------------
         
         return color;
    	 
     }
     	 
     /**
      * Calculate the color intensity in a point
      * @param GeoPoint the point for which the color is required, and the geometry it stands on
      * @param inRay - the ray from the light to the point
      * @param int level - level of recursion
      * @param double k - reduces the transparency and reflectency in the recursion - the more we go deeper in the recursion - the more that transparency and reflectency will be reduced
      * @return the color intensity of the point
      */
     private Color calcColor(GeoPoint intersection, Ray inRay, int level, double k) 
     {  	
    	 if (level == 1 || k < MIN_CALC_COLOR_K) 
             return Color.BLACK;
    	    	          
         Color resultColor = intersection.getGeometry().getEmission();
         Point3D pointGeo = intersection.getPoint();
               
         Vector v = pointGeo.subtract(_scene.getCamera().get_p0()).normalize();
         Vector n = intersection.getGeometry().getNormal(pointGeo);
         
         Material material = intersection.getGeometry().getMaterial();

         int nShininess = material.get_nShininess();
         double kd = material.get_kD();
         double ks = material.get_kS();
         double kr = material.get_kR();
         double kt = material.get_kT();
         
         double kkr = k * kr;
         double kkt = k * kt;
         double ktr;
         
         List<LightSource> lights = _scene.getLights();

         if (lights != null)
         {     	 
             for (LightSource lightSource : lights) 
             {
                 Vector l = lightSource.getL(pointGeo);
                 double nl = primitives.Util.alignZero(n.dotProduct(l));
                 double nv = primitives.Util.alignZero(n.dotProduct(v));
              
                                  
                 if (sign(nl) == sign(nv)) 
                 {
                	 if(improvement)
                		 ktr = transparency2(lightSource, l, n, intersection); // function that calculates the transparency with a lot of shadow rays
                	 else
                		 ktr = transparency1(lightSource, l, n, intersection); // function that calculates the transparency with one shadow ray
                		                	                 	                	                                 	              	                	 
                	 //if(unshaded(lightSource, l, n, intersection))
                	 if(ktr* k > MIN_CALC_COLOR_K)
                	 {
                		 Color lightIntensity = lightSource.getIntensity(pointGeo).scale(ktr); 
                		 resultColor = resultColor.add(calcDiffusive(kd, l, n, lightIntensity), 
                				 calcSpecular(ks, l, n, v, nShininess, lightIntensity));                			                 			                		 
                		 
                	 }                       	             	 
                 }  
             }
         }
         
         if (kkr > MIN_CALC_COLOR_K) 
         {
             Ray reflectedRay = constructReflectedRay(pointGeo, inRay, n);
             GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
             
             if(reflectedPoint != null) 
            	 resultColor = resultColor.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr));
             
         }
         
         if (kkt > MIN_CALC_COLOR_K) 
         {
             Ray refractedRay = constructRefractedRay(pointGeo, inRay, n);
             GeoPoint refractedPoint = findClosestIntersection(refractedRay);
             
             if (refractedPoint != null) 
            	 resultColor = resultColor.add(calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(kt));
             
         }
              
         return resultColor;          	
     }
	 
     /**
      * calling the writeToImage function that the _imageWriter field, of render has
      */
     public void writeToImage() 
     {
         _imageWriter.writeToImage();
     }

     /**
      * Printing the grid with a fixed interval between lines
      * @param interval The interval between the lines.
      */
     public void printGrid(int interval, java.awt.Color colorsep) 
     {
         double rows = this._imageWriter.getNy();
         double collumns = _imageWriter.getNx();
         //Writing the lines.
         for (int row = 0; row < rows; ++row) 
         {
             for (int collumn = 0; collumn < collumns; ++collumn) 
             {
                 if (collumn % interval == 0 || row % interval == 0) 
                 {
                     _imageWriter.writePixel(collumn, row, colorsep);
                 }
             }
         }
     }
     // HELP FUNCTIONS---------------------------------------------
     
     /**
      * checks if number is positive
      * @param val - a value we want to check
      * @return boolean - whether the sign is equal or not
      */
     private boolean sign(double val) 
     {
         return (val > 0d);
     }
     
     /**
      * calculates the diffuse light 
      * @param kd - diffusive factor
      * @param l - the light direction vector
      * @param n - the normal to our point
      * @param lightIntensity - the intensity of the light
      * @return the diffused light of the point
      */
     private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) 
     {
  		 l = l.normalize();
 		 Vector normal = n.normalize();
 		 double k = normal.dotProduct(l);
 		 
 		 if(k < 0)
 			 k = k*(-1);
 			 	 
 		 double res = k * kd; 
  		 Color diffuse = lightIntensity.scale(res);  		  
  		 		
  		 return diffuse;  	 
     }
     
     /**
      * calculates the specular light 
      * @param ks - the specular factor
      * @param l - the light direction vector
      * @param n - the normal to our point
      * @param v - the direction vector from camera to point
      * @param nShininess - the shininess factor
      * @param lightIntensity - the intensity of the light
      * @return the specular light of the point
      */
     private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) 
     {  		
  		double R1 = l.dotProduct(n);
  		Vector R = l.add(n.scale(-2*R1));
  		double minusVR = -primitives.Util.alignZero(R.dotProduct(v));
  		if (minusVR <= 0)
  		{
            return Color.BLACK; // view from direction opposite to r vector
        }
  		
  		Color specular = lightIntensity.scale(ks * Math.pow(minusVR, nShininess));
  					
  		return specular;    	 
     }
     
     /**
      * this function checks if there is a geometry that shades the intersection point
      * @param l - the light direction vector
      * @param n - the normal to our point
      * @param gp - the point and the geometry it stands on
      * @return boolean - whether or not- the point is ushaded
      */
     private boolean unshaded(LightSource light, Ray lightRay, GeoPoint geopoint) 
     {      
         List<GeoPoint> intersections = _scene.getGeometries().findIntersections(lightRay);
                  
         if (intersections == null) 
             return true;
                                      
         double lightDistance = light.getDistance(geopoint.getPoint());
                 
         for (GeoPoint gp : intersections) 
         {
        	 double temp = gp.getPoint().distance(geopoint.getPoint()) - lightDistance;      
             if (primitives.Util.alignZero(temp) <= 0 )
            	 return false;  
         }
         return true;    
     }
     
     /**
      * this function calculates the transparency of the geometry that shadows the point, and returns the degree of transparency....
      * @param light - the light source that lights the point
      * @param l - the light direction vector
      * @param n - the normal to our point
      * @param geopoint - the point and the geometry it stands on
      * @return - the transparency 
      */
     private double transparency2(LightSource light, Vector l, Vector n, GeoPoint geopoint) 
     {   
    	 if(light instanceof DirectionalLight) // because directional light is in the infinity
   		  transparency1(light, l, n, geopoint); 
    	 
      	  double numberOfReachingRays = 0;  // number of ray that reached the light    	  
    	  double finalTransparencey = 0;
  
          List<Ray> raysList = light.beamOfRays(geopoint, this.NumOfShadowRays);
          List<Ray> Oposite_raysList = new ArrayList();
          int numberOfRays = raysList.size();
          
          double lightDistance = light.getDistance(geopoint.getPoint());
          double ktr = 1d;
               
          for(Ray ray:raysList)
          {
        	  Vector lightDirection = (ray.getDirection()).scale(-1); // from point to light source
        	  Ray OpositeRay = new Ray(geopoint.getPoint(), lightDirection, n); // goes to the c-tor that adds the delta value
        	  Oposite_raysList.add(OpositeRay);
          }             
          for(Ray Oposite_ray :Oposite_raysList)
          {
        	  List<GeoPoint> intersections = _scene.getGeometries().findIntersections(Oposite_ray);    	  
              if (intersections == null) 
            	  numberOfReachingRays ++;             
              else
              {          	 
                   for (GeoPoint gp : intersections)
                   {
                       if(primitives.Util.alignZero(gp.getPoint().distance(geopoint.getPoint()) - lightDistance) <= 0 && !Util.isZero(gp.getGeometry().getMaterial().get_kT()))
                    	   ktr *= gp.getGeometry().getMaterial().get_kT();                	                                                   
                       else
                	   {                   		 
                    	   ktr = 0;
                		   break;
                	   }                      
                   }                 
                   finalTransparencey += ktr;
                   ktr = 1d;                  
              }
          }             
          double res = ((double)numberOfReachingRays + (double)finalTransparencey)/ (double)numberOfRays; // example: (5 + 4(0.6) + 5(0.3))/14               
          return res;
     }
     
     /**
      * this function calculates the transparency of the geometry that shadows the point, and returns the degree of transparency....
      * @param light - the light source that lights the point
      * @param l - the light direction vector
      * @param n - the normal to our point
      * @param geopoint - the point and the geometry it stands on
      * @return the transparancy of the object that shadows our point
      */
     private double transparency1(LightSource light, Vector l, Vector n, GeoPoint geopoint)
     {
    	 Vector lightDirection = l.scale(-1); // from point to light source
	         Ray lightRay = new Ray(geopoint.getPoint(), lightDirection, n); // goes to the c-tor that adds the delta value
	         Point3D pointGeo = geopoint.getPoint();

	         List<GeoPoint> intersections = _scene.getGeometries().findIntersections(lightRay);
	         if (intersections == null) 
	             return 1d; // 1.0
	         
	         double lightDistance = light.getDistance(pointGeo);
	         double ktr = 1d;
	         for (GeoPoint gp : intersections)
	         {
	             if (primitives.Util.alignZero(gp.getPoint().distance(pointGeo) - lightDistance) <= 0 )
	             {
	                 ktr *= gp.getGeometry().getMaterial().get_kT();
	                 if (ktr < MIN_CALC_COLOR_K) 
	                     return 0.0;   	                 
	             }
	         }
	         return ktr;  
     }
     
     
     
    /**
      * this function sends 65 ray to the light and calculates the average of rays that got to the light - from all rays that were sent
      * @param light - light source
      * @param l - vector of direction from the light to the point 
      * @param n - vector that is normal to the point
      * @param geopoint - has the point and the geometry of the point
      * @return
      */
  /*   private double softShadows(LightSource light, Vector l, Vector n, GeoPoint geopoint)
     { 
    	 int counter = 0;
    	 Vector lightDirection = l.scale(-1); // from point to light source
    	 lightDirection.normalize();
    	 
    	 Point3D point = geopoint.getPoint();
    	 double x_Coordinate = lightDirection.getHead().getX().get();
    	 double y_Coordinate = lightDirection.getHead().getY().get();
    	 double z_Coordinate = lightDirection.getHead().getZ().get();
    	  	 
    	
    	 
         Ray OriginallightRay = new Ray(point, lightDirection, n); // goes to the c-tor that adds the delta value                
         if(unshaded(light, OriginallightRay, geopoint)) // 
        	 counter ++;
                  
         // moving +x right  
         int i = 1;
         while(i < 40)
         {
        	 Ray posXLightRay = new Ray(point, new Vector(x_Coordinate + 0.0005*i, y_Coordinate, z_Coordinate), n);
             if(unshaded(light, posXLightRay, geopoint)) // if the shading geometries are not shading- the ray can get to the light
            	 counter ++;
             i++;
         }
              
         //moving -x left
         i = 1;
         while (i < 40)
         {
        	    Ray negXLightRay = new Ray(point, new Vector(x_Coordinate - 0.0005*i, y_Coordinate, z_Coordinate), n);
                if(unshaded(light, negXLightRay, geopoint))
                	counter ++;
                i++;
         }
              
         // moving +y down
         i = 1;
         while (i < 40)
         {
        	 Ray posYLightRay = new Ray(point, new Vector(x_Coordinate, y_Coordinate + 0.0005*i, z_Coordinate), n);
             if(unshaded(light, posYLightRay, geopoint))
            	 counter ++;
             i++;
         }
                
         // moving -y up
         i = 1;
         while (i < 40)
         {
        	  Ray negYLightRay = new Ray(point, new Vector(x_Coordinate, y_Coordinate - 0.0005*i, z_Coordinate), n);
              if(unshaded(light, negYLightRay, geopoint))
            	  counter ++;
              i++;
         }
               
         // moving +x right, +y down 
         i = 1;
         while (i < 40)
         {
        	    Ray posX_posY_LightRay = new Ray(point, new Vector(x_Coordinate + 0.0005*i, y_Coordinate + 0.0005*i, z_Coordinate), n);
                if(unshaded(light, posX_posY_LightRay, geopoint))
                	counter ++;
                i++;
         }
              
         // moving +x right, -y up  
         i = 1;
         while (i < 40)
         {
        	  Ray posX_negY_LightRay = new Ray(point, new Vector(x_Coordinate + 0.0005*i, y_Coordinate - 0.0005*i, z_Coordinate), n);
              if(unshaded(light, posX_negY_LightRay, geopoint))
            	  counter ++;
              i++;
         }
         
      // moving -x left, +y down  
         i = 1;
         while (i < 40)
         {
        	  Ray negX_posY_LightRay = new Ray(point, new Vector(x_Coordinate - 0.0005*i, y_Coordinate + 0.0005*i, z_Coordinate), n);
              if(unshaded(light, negX_posY_LightRay, geopoint))
             	 counter ++;
              i++;
         }
           
         // moving -x left, -y up 
         i = 1;
         while (i < 40)
         {
        	  Ray negX_negY_LightRay = new Ray(point, new Vector(x_Coordinate - 0.0005*i, y_Coordinate - 0.0005*i, z_Coordinate), n);
              if(unshaded(light, negX_negY_LightRay, geopoint))
             	 counter ++;
              i++;
         }
         
         //-------------------------------------
         //System.out.println("number of rays that reached the light: " + counter);
         //-------------------------------
       
         double avg = (double)counter / 313.0; // we created 313 rays        
         return avg;                      // if avg = 1 => nothing is shaded, if avg = 0 => everything is shaded
     }
*/     
    
     /**
      * calculates the reflected ray 
      * @param n - vector that is normal to the point
      * @param geopoint - has the point and the geometry of the point
      * @param inRay - the ray from the light that intersects our point
      * @return reflected ray - the reflected ray
      */
     public Ray constructReflectedRay(Point3D geopoint, Ray inRay, Vector n)    
     {
    	//𝒓=𝒗−𝟐∙𝒗∙𝒏∙𝒏
    	 Vector normal = new Vector(n);
    	 normal.normalize();
    	 
    	 Vector direction = new Vector(inRay.getDirection());
    	 direction.normalize();
    	 
    	 if(direction.dotProduct(normal) == 0)
    		 return null;
    	 
    	 Vector r = normal.scale((-2)* direction.dotProduct(normal));
    	 r = r.add(direction);
    	 
    	 /*Vector delta = normal.scale(normal.dotProduct(direction) > 0 ? DELTA : - DELTA);
    	 Point3D point = geopoint.add(delta);
    	 Ray reflectedRay = new Ray(point, r);*/
    	 
    	 Ray reflectedRay = new Ray(geopoint, r, n);
    	
    	 return reflectedRay;  	   	  
     }
     
     /**
      * Find intersections of a ray with the scene geometries and get the
      * intersection point that is closest to the ray head. If there are no
      * intersections, null will be returned.
      *
      * @param ray intersecting the scene
      * @return the closest point
      */
     private GeoPoint findClosestIntersection(Ray ray) 
     {
         GeoPoint closestPoint = null;
         double closestDistance = Double.MAX_VALUE;
         Point3D ray_p0 = ray.get_Point();

         List<GeoPoint> intersections = _scene.getGeometries().findIntersections(ray);
         if (intersections == null)
             return null;

         for (GeoPoint geoPoint : intersections) 
         {
             double distance = ray_p0.distance(geoPoint.getPoint());
             if (distance < closestDistance) 
             {
                 closestPoint = geoPoint;
                 closestDistance = distance;
             }
         }
         return closestPoint;
     }  
     
     /**
      * calculates the refracted ray
      * @param n - the normal vector to the point
      * @param geopoint - has the point and the geometry of the point
      * @param inRay - the ray from the light that intersects our point
      * @return the refracted ray
      */
     public Ray constructRefractedRay(Point3D geopoint, Ray inRay, Vector n)
     {
    	 Vector normal = new Vector(n);
    	 normal.normalize();
    	 
    	 Vector direction = new Vector(inRay.getDirection());
    	 direction.normalize();
    	 
    	 /*
    	 Vector delta = normal.scale(normal.dotProduct(direction) > 0 ? DELTA : - DELTA);
    	 delta = delta.scale(-1);
    	 Point3D point = geopoint.add(delta);*/   	 
    	 
    	 Ray refractedRay = new Ray(geopoint, direction, normal);
    	 
    	 return refractedRay; 
    	 
    	 //return new Ray(pointGeo, inRay.getDirection(), n);
     }
  
}

	 
	 

	
	
	
	
	 
	 
	 
 
 
	

