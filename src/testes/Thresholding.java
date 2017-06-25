package testes;

import org.opencv.core.Core;
import org.opencv.core.Mat;
//import org.opencv.highgui.Highgui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Thresholding {
   public static void main( String[] args ){
   
      try{

         System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
         Mat source = Imgcodecs.imread("img/digital_image_processing.jpg",  Imgcodecs.CV_LOAD_IMAGE_COLOR);
         Mat destination = new Mat(source.rows(),source.cols(),source.type());

         destination = source;
         //Imgproc.threshold(source,destination,127,255,Imgproc.THRESH_TOZERO);
         Imgproc.threshold(source, destination, 127, 255, Imgproc.THRESH_BINARY);
         Imgcodecs.imwrite("img/ThreshZero.jpg", destination);
         
      }catch (Exception e) {
         System.out.println("error: " + e.getMessage());
      }
   }
}