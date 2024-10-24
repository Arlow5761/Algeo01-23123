package Algeo.Interpolation;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

public class ImageResizer {

    public static void main(String[] args) {

        String inputImagePath = args[0];
        String outputImagePath = args[1];
        double scaleFactor = Double.parseDouble(args[2]);

        try {
            BufferedImage inputImage = ImageIO.read(new File(inputImagePath));

            int originalWidth = inputImage.getWidth();
            int originalHeight = inputImage.getHeight();

            int newWidth = (int) (originalWidth * scaleFactor);
            int newHeight = (int) (originalHeight * scaleFactor);

            BufferedImage outputImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);

            // Read pixel data from input image
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
                double srcY = y / scaleFactor;
                int y0 = (int) Math.floor(srcY);
                double dy = srcY - y0;

                for (int x = 0; x < newWidth; x++) {
                    double srcX = x / scaleFactor;
                    int x0 = (int) Math.floor(srcX);
                    double dx = srcX - x0;

                    int[] rgb = bicubicInterpolateRGB(pixelData, x0, y0, dx, dy);
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

    // Bicubic interpolation for RGB
    public static int[] bicubicInterpolateRGB(double[][][] pixels, int x0, int y0, double dx, double dy) {
        int height = pixels.length;
        int width = pixels[0].length;

        double[] rgb = new double[3];

        for (int c = 0; c < 3; c++) { 
            double[][] matrix = new double[4][4];

            double[][] f = new double[2][2];
            double[][] fx = new double[2][2];
            double[][] fy = new double[2][2];
            double[][] fxy = new double[2][2];

            for (int i = 0; i < 2; i++) { 
                for (int j = 0; j < 2; j++) {
                    int xi = x0 + i;
                    int yj = y0 + j;

                    // Clamp 
                    xi = clamp(xi, 0, width - 1);
                    yj = clamp(yj, 0, height - 1);


                    double fValue = getPixelValue(pixels, xi, yj, c);
                    f[i][j] = fValue;

                    double fxValue = (getPixelValue(pixels, xi + 1, yj, c) - getPixelValue(pixels, xi - 1, yj, c)) / 2.0;
                    fx[i][j] = fxValue;

                    double fyValue = (getPixelValue(pixels, xi, yj + 1, c) - getPixelValue(pixels, xi, yj - 1, c)) / 2.0;
                    fy[i][j] = fyValue;

                    double fxyValue = (getPixelValue(pixels, xi + 1, yj + 1, c) - getPixelValue(pixels, xi - 1, yj + 1, c)
                            - getPixelValue(pixels, xi + 1, yj - 1, c) + getPixelValue(pixels, xi - 1, yj - 1, c)) / 4.0;
                    fxy[i][j] = fxyValue;
                }
            }

            for (int k = 0; k < 4; k++) {
                int i = k % 2;
                int j = k / 2;
                matrix[0][k] = f[i][j];
                matrix[1][k] = fx[i][j];
                matrix[2][k] = fy[i][j];
                matrix[3][k] = fxy[i][j];
            }

            double value = BicubicSplineInterpolator.bicubicInterpolate(matrix, dx, dy);
            rgb[c] = clamp(value, 0, 255);
        }

        return new int[]{(int) Math.round(rgb[0]), (int) Math.round(rgb[1]), (int) Math.round(rgb[2])};
    }

    public static double getPixelValue(double[][][] pixels, int x, int y, int c) {
        int height = pixels.length;
        int width = pixels[0].length;

        x = clamp(x, 0, width - 1);
        y = clamp(y, 0, height - 1);

        return pixels[y][x][c];
    }

    public static int clamp(int val, int min, int max) {
        return Math.max(min, Math.min(max, val));
    }

    public static double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }
}
