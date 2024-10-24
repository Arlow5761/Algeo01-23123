package Algeo.Interpolation;

import Algeo.LinearAlgebra.GaussJordanSolver;
import Algeo.Matrix; 

public class BicubicSplineInterpolator
{

    private static Matrix convert_matrix_16(Matrix m){
        Matrix function = new Matrix(m.size(), 1);
        for ( int i = 0 ; i < m.GetRowCount(); i++){
            for ( int j = 0 ; j < m.GetColumnCount(); j++){
                function.Set((4 * i) + j, 0, m.Get(i,j));
            }
        }
        return function;
    }

    private static double count(double[] koef,double x,double y){
        double result = 0.0;
        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j <= 3; j++) {
                int index = i * 4 + j; 
                result += koef[index] * Math.pow(x, i) * Math.pow(y, j);
            }
        }
        return result;
     }



    public static double bicubicInterpolate(Matrix m , double a, double b){

        Matrix function = convert_matrix_16(m);

        // matrix equation
        double[][] equation = new double[16][16];

        for (int k = 0; k < 16; k++) {
            int pointIndex = k % 4;
            int derivativeIndex = k / 4;

            int x = (pointIndex == 0 || pointIndex == 2) ? 0 : 1;
            int y = (pointIndex == 0 || pointIndex == 1) ? 0 : 1;

            switch (derivativeIndex) {

                case 0: // f(x,y)
                    for (int i = 0; i <= 3; i++) {
                        for (int j = 0; j <= 3; j++) {
                            equation[k][4 * i + j] = Math.pow(x, i) * Math.pow(y, j);
                        }
                    }
                    break;

                case 1: // fx(x,y)
                    for (int i = 1; i <= 3; i++) {
                        for (int j = 0; j <= 3; j++) {
                            equation[k][4 * i + j] = i * Math.pow(x, i - 1) * Math.pow(y, j);
                        }
                    }
                    break;

                case 2: // fy(x,y)
                    for (int i = 0; i <= 3; i++) {
                        for (int j = 1; j <= 3; j++) {
                            equation[k][4 * i + j] = j * Math.pow(x, i) * Math.pow(y, j - 1);
                        }
                    }
                    break;

                case 3: // fxy(x,y)
                    for (int i = 1; i <= 3; i++) {
                        for (int j = 1; j <= 3; j++) {
                            equation[k][4 * i + j] = i * j * Math.pow(x, i - 1) * Math.pow(y, j - 1);
                        }
                    }
                    break;

            }
        }

        Matrix element = new Matrix(equation);

        Matrix augmented = Matrix.Append(element, function);

        GaussJordanSolver solver = new GaussJordanSolver(augmented);
        solver.Solve();
        double[] coeffs = solver.GetSingleSolution();

        double result = count(coeffs, a, b);

        return result;
    }

    

    public static int[] bicubicInterpolateRGB(double[][][] pixels, int x0, int y0, double dx, double dy) {
        int height = pixels.length;
        int width = pixels[0].length;

        double[] rgb = new double[3];

        for (int c = 0; c < 3; c++) {
            double[][] mValues = new double[4][4]; 

            int[] xs = { x0, x0 + 1 };
            int[] ys = { y0, y0 + 1 };

            int index = 0;

            for (int j = 0; j < ys.length; j++) {
                for (int i = 0; i < xs.length; i++) {
                    int x = xs[i];
                    int y = ys[j];

                    x = clamp(x, 0, width - 1);
                    y = clamp(y, 0, height - 1);

                    // f(x, y) = I(x, y)
                    double f = getPixelValue(pixels, x, y, c);

                    // f_x(x, y) = [I(x+1, y) - I(x-1, y)] / 2
                    double fx = (getPixelValue(pixels, x + 1, y, c) - getPixelValue(pixels, x - 1, y, c)) / 2.0;

                    // f_y(x, y) = [I(x, y+1) - I(x, y-1)] / 2
                    double fy = (getPixelValue(pixels, x, y + 1, c) - getPixelValue(pixels, x, y - 1, c)) / 2.0;

                    // f_xy(x, y) = [I(x+1, y+1) - I(x-1, y+1) - I(x+1, y-1) + I(x-1, y-1)] / 4
                    double fxy = (
                        getPixelValue(pixels, x + 1, y + 1, c)
                        - getPixelValue(pixels, x - 1, y + 1, c)
                        - getPixelValue(pixels, x + 1, y - 1, c)
                        + getPixelValue(pixels, x - 1, y - 1, c)
                    ) / 4.0;

                    mValues[0][index] = f;
                    mValues[1][index] = fx;
                    mValues[2][index] = fy;
                    mValues[3][index] = fxy;

                    index++;
                }
            }

            Matrix m = new Matrix(mValues);

            double value = BicubicSplineInterpolator.bicubicInterpolate(m, dx, dy);

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
