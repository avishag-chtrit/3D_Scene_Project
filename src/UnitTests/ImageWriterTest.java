package UnitTests;

import static org.junit.Assert.*;
import org.junit.Test;
import renderer.ImageWriter;
import java.awt.Color;

public class ImageWriterTest 
{

	/**
	 * testing the ImageWriter class and creating an image of 16x10 pixels
	 * each one is 10x10 as we were asked for...
	 */
	@Test
    public void writeToImage1() 
	{
        String imagename = "ImageWriter_Test1";
        int width = 1600;
        int height = 1000;
        int nx = 160;
        int ny = 100;
        ImageWriter imageWriter = new ImageWriter(imagename, width, height, nx, ny);
        for (int col = 0; col < ny; col ++) 
        {
            for (int row = 0; row < nx; row ++) 
            {
                if (col % 10 == 0 || row % 10 == 0) 
                {
                    imageWriter.writePixel(row, col, Color.WHITE);
                }
            }
        }
        imageWriter.writeToImage();
    }
	
	/**
	 * testing the ImageWriter class and creating an image of 5x8 pixels
	 * each one is 10x10 as we were asked for...
	 */
	@Test
    public void writeToImage2() 
	{
        String imagename = "ImageWriter_Test2";
        int width = 800;
        int height = 500;
        int nx = 80;
        int ny = 50;
        ImageWriter imageWriter = new ImageWriter(imagename, width, height, nx, ny);
        for (int col = 0; col < ny; col ++) 
        {
            for (int row = 0; row < nx; row ++) 
            {
                if (col % 10 == 0 || row % 10 == 0) 
                {
                    imageWriter.writePixel(row, col, Color.WHITE);
                }
            }
        }
        imageWriter.writeToImage();
    }
	
}
