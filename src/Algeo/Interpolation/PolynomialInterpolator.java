package Algeo.Interpolation;

import Algeo.Matrix;

public class PolynomialInterpolator {

    public static float interpolate(Matrix m, float x) {
        int n = m.GetRowCount();
        float[] coef = new float[n];
        float[] x_values = new float[n];

        //masukin x
        for (int i = 0; i < n; i++) {
            x_values[i] = m.Get(i, 0); 
        }

        float[][] dividedDiff = new float[n][n];

        //masukin y
        for (int i = 0; i < n; i++) {
            dividedDiff[i][0] = m.Get(i, 1); 
        }
  
        for (int j = 1; j < n; j++) {
            for (int i = 0; i < n - j; i++) {
                dividedDiff[i][j] = (dividedDiff[i + 1][j - 1] - dividedDiff[i][j - 1]) / (x_values[i + j] - x_values[i]);
            }
        }

        for (int i = 0; i < n; i++) {
            coef[i] = dividedDiff[0][i];
        }

        float y = coef[0];
        for (int i = 1; i < n; i++) {
            float term = coef[i];
            for (int j = 0; j < i; j++) {
                term *= (x - x_values[j]);
            }
            y += term;
        }

        return y; 
    }
}
