package primitives;
import static primitives.Util.*;
import java.math.*;

/**
 * Class Coordinate is the basic class representing a coordinate for Cartesian
 * coordinate system. The class is based on Util controlling the accuracy.
*/

public final class Coordinate 
{
	/**
     * Coordinate value, intentionally "package-friendly" due to performance
     * constraints
     */
    final double _coord;

    /**
     * Coordinate constructor receiving a coordinate value
     * 
     * @param coord coordinate value
     */
    public Coordinate(double coord)
    {
        // if it too close to zero make it zero
        _coord = alignZero(coord);
    }

    /**
     * Copy constructor for coordinate
     * 
     * @param other - the coordinate we should copy
     */
    public Coordinate(Coordinate other) 
    {
        _coord = other._coord;
    }

    /**
     * Coordinate value getter
     * 
     * @return Coordinate - the coordinate value
     */
    public double get()
    {
        return _coord;
    }

    /*************** Admin *****************/
    /**
     * this function overrides the function equals of Object class
     * and checks if 2 coordinates are equal- by their values
     */
    @Override
    public boolean equals(Object obj) 
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Coordinate)) return false;
        return isZero(_coord - ((Coordinate)obj)._coord);
    }

    /**
     * this function overrides the function toString of Object class
     * and prints the coordinate's value whenever it's called
     */
    @Override
    public String toString() 
    {
        return "" + _coord;
    }
    
    //------Functions
    /**
     * 
     * @param c - a coordinate to multiply with
     * @return Coordinate - the result of the multiplication
     */
    public Coordinate multiply(Coordinate c)
    {
    	double tmp = this._coord * c.get();
    	Coordinate resC = new Coordinate(tmp);
    	return resC;
    }
}
