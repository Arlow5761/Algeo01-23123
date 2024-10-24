package Algeo.Interpolation;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

public class ImageResizer {

    public static void main(String[] args) {
        
        String inputImagePath = args[0];
        String outputImagePath = args[1];
        double scaleWidth = Double.parseDouble(args[2]);
        double scaleHeight = Double.parseDouble(args[3]);

        try {
            BufferedImage inputImage = ImageIO.read(new File(inputImagePath));

            int originalWidth = inputImage.getWidth();
            int originalHeight = inputImage.getHeight();

            int newWidth = (int) (originalWidth * scaleWidth);
            int newHeight = (int) (originalHeight * scaleHeight);

            BufferedImage outputImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);

            // Read pixel data
            double[][][] pixelData = new double[originalHeight][originalWidth][3]; // RGB image
            for (int y = 0; y < originalHeight; y++) {
                for (int x = 0; x < originalWidth; x++) {
                    int rgb = inputImage.getRGB(x, y);
                    int r = (rgb >> 16) & 0xFF;
                    int g = (rgb >> 8) & 0xFF;
                    int b = rgb & 0xFF;
                    pixelData[y][x][0] = r;
                    pixelData[y][x][1] = g;
                    pixelData[y][x][2] = b;
                }
            }

            // Resize
            for (int y = 0; y < newHeight; y++) {
                double srcY = y / scaleHeight;
                int y0 = (int) Math.floor(srcY);
                double dy = srcY - y0;

                for (int x = 0; x < newWidth; x++) {
                    double srcX = x / scaleWidth;
                    int x0 = (int) Math.floor(srcX);
                    double dx = srcX - x0;

                    //bicubic interpolate rgb
                    int[] rgb = BicubicSplineInterpolator.bicubicInterpolateRGB(pixelData, x0, y0, dx, dy);
                    int r = rgb[0];
                    int g = rgb[1];
                    int b = rgb[2];
                    int rgbValue = (r << 16) | (g << 8) | b;
                    outputImage.setRGB(x, y, rgbValue);
                }
            }

            // Save
            ImageIO.write(outputImage, "png", new File(outputImagePath));
            System.out.println("Image resized successfully.");
        
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

}
