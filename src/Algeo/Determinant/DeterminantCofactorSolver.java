package Algeo.Determinant;

import Algeo.Matrix;

public class DeterminantCofactorSolver {

    public static void main(String[] args) {
        double[][] skibidi = {{1.00,2.00},{3.00,4.00}
        };
        Matrix ligma = new Matrix(skibidi);
        System.out.println("Determinant: " + determinant(ligma));
    }

    public static double determinant(Matrix m) {
        int n = m.GetColumnCount();
        double det = 0;

        if (n == 1) {
            return m.Get(0, 0);
        }

        if (n == 2) {
            return (m.Get(0, 0) * m.Get(1, 1) - m.Get(0, 1) * m.Get(1, 0));
        }

        for (int j = 0; j < n; j++) {
            int sign = ((0 + j) % 2 == 0) ? 1 : -1;
            double cofactor = m.Get(0, j);
            Matrix minor = getMinor(m, 0, j);
            det += sign * cofactor * determinant(minor);
        }

        return det;
    }

    public static Matrix getMinor(Matrix m, int row, int column) {
        int n = m.GetColumnCount();
        Matrix minor = new Matrix(n - 1, n - 1);

        int minorRow = 0;

        for (int i = 0; i < n; i++) {
            if (i == row)
                continue;

            int minorCol = 0;

            for (int j = 0; j < n; j++) {
                if (j == column)
                    continue;

                minor.Set(minorRow, minorCol, m.Get(i, j));
                minorCol++;
            }
            minorRow++;
        }
        return minor;
    }
}
