package Algeo.Interpolation;

import Algeo.Matrix;
import Algeo.LinearAlgebra.GaussSolver;

public class PolynomialInterpolator {

    public static float interpolate(Matrix m, float x) {
        int n = m.GetRowCount();
        Matrix A = new Matrix(n, n);
        Matrix b = new Matrix(n, 1);

        for (int i = 0; i < n; i++) {
            float xi = m.Get(i, 0);
            float yi = m.Get(i, 1);
            b.Set(i, 0, yi);

            // Vandermonde 
            for (int j = 0; j < n; j++) {
                A.Set(i, j, (float) Math.pow(xi, j));
            }
        }

        Matrix augmentedMatrix = augmentMatrices(A, b);

        // Eleminasi Gauss
        GaussSolver solver = new GaussSolver(augmentedMatrix);
        solver.Solve();
        float[] coeffs = solver.GetSingleSolution();

        if (coeffs == null) {
            System.out.println("Tidak ada solusi unik");
            return Float.NaN;
        }

        float y = 0.0f;
        for (int i = 0; i < n; i++) {
            y += coeffs[i] * (float) Math.pow(x, i);
        }

        return y;
    }

    //Augmented Matrix
    private static Matrix augmentMatrices(Matrix A, Matrix b) {
        int rows = A.GetRowCount();
        int colsA = A.GetColumnCount();

        Matrix augmented = new Matrix(rows, colsA + 1);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < colsA; j++) {
                augmented.Set(i, j, A.Get(i, j));
            }
            augmented.Set(i, colsA, b.Get(i, 0));
        }

        return augmented;
    }
}
