package Algeo.Interpolation;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

import Algeo.Matrix;

public class ImageResizer {

    public static void main(String[] args) {
        if(args.length < 3){
            System.out.println("Usage: java Algeo.Interpolation.ImageResizer <input_image> <output_image> <scale_factor>");
            return;
        }

        String inputImagePath = args[0];
        String outputImagePath = args[1];
        double scaleFactor = Double.parseDouble(args[2]);

        try {
            BufferedImage inputImage = ImageIO.read(new File(inputImagePath));

            int originalWidth = inputImage.getWidth();
            int originalHeight = inputImage.getHeight();

            int newWidth = (int)(originalWidth * scaleFactor);
            int newHeight = (int)(originalHeight * scaleFactor);

            BufferedImage outputImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);

            // Read pixel data from input image
            double[][][] pixelData = new double[originalHeight][originalWidth][3]; // Assuming RGB image
            for(int y = 0; y < originalHeight; y++){
                for(int x = 0; x < originalWidth; x++){
                    int rgb = inputImage.getRGB(x, y);
                    int r = (rgb >> 16) & 0xFF;
                    int g = (rgb >> 8) & 0xFF;
                    int b = rgb & 0xFF;
                    pixelData[y][x][0] = r;
                    pixelData[y][x][1] = g;
                    pixelData[y][x][2] = b;
                }
            }

            // Resize using bicubic interpolation
            for(int y = 0; y < newHeight; y++){
                double srcY = y / scaleFactor;
                for(int x = 0; x < newWidth; x++){
                    double srcX = x / scaleFactor;
                    int[] rgb = bicubicInterpolate(pixelData, srcX, srcY);
                    int r = rgb[0];
                    int g = rgb[1];
                    int b = rgb[2];
                    int rgbValue = (r << 16) | (g << 8) | b;
                    outputImage.setRGB(x, y, rgbValue);
                }
            }

            // Save the output image
            ImageIO.write(outputImage, "png", new File(outputImagePath));
            System.out.println("Image resized successfully.");

        } catch(IOException e){
            System.out.println("Error: " + e.getMessage());
        }

    }

    // Bicubic interpolation for RGB images using BicubicSplineInterpolator
    public static int[] bicubicInterpolate(double[][][] pixels, double x, double y){
        int height = pixels.length;
        int width = pixels[0].length;

        int x0 = (int)Math.floor(x);
        int y0 = (int)Math.floor(y);

        double[] rgb = new double[3];

        for(int c = 0; c < 3; c++){ // For each color channel
            double[][] values = new double[4][4];
            for(int m = -1; m <= 2; m++){
                for(int n = -1; n <= 2; n++){
                    int xi = clamp(x0 + m, 0, width - 1);
                    int yi = clamp(y0 + n, 0, height - 1);
                    values[n + 1][m + 1] = pixels[yi][xi][c]; // Note the order
                }
            }
            Matrix m = new Matrix(values);
            double a = x - (x0);
            double b = y - (y0);
            rgb[c] = BicubicSplineInterpolator.bicubic_interpolate(m, a, b);
            rgb[c] = clamp(rgb[c], 0, 255);
        }

        return new int[]{(int)Math.round(rgb[0]), (int)Math.round(rgb[1]), (int)Math.round(rgb[2])};
    }

    public static int clamp(int val, int min, int max){
        return Math.max(min, Math.min(max, val));
    }

    public static double clamp(double val, double min, double max){
        return Math.max(min, Math.min(max, val));
    }
}



