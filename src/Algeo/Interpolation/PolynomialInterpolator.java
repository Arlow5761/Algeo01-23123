package Algeo.Interpolation;

import Algeo.Matrix;
import Algeo.LinearAlgebra.GaussSolver;

public class PolynomialInterpolator {

    public static void main(String args[]){
        double mewing = 8.3f;
        double[][] skibidi = {{8.0f, 2.0794f}, {9.0f,2.1972f},{9.5f, 2.2513f}};
        Matrix ligma = new Matrix(skibidi);

        System.out.println(interpolate(ligma,mewing));

    }

    public static double interpolate(Matrix m, double x) {//input m size = nx2
        int n = m.GetRowCount(); //n berisikan jumlah baris matrix m
        Matrix A = new Matrix(n, n); // A berisikan matrix dengan size nxn
        Matrix b = new Matrix(n, 1); // b berisikan matrix dengan size nx1

        for (int i = 0; i < n; i++) {
            double xi = m.Get(i, 0);//xi = x pada elemen matrix input m baris ke i
            double yi = m.Get(i, 1);//yi = y pada elemen matrix input m baris ke i
            b.Set(i, 0, yi);// b[i][0] = y pada elemen matrix input m;

            // Vandermonde 
            for (int j = 0; j < n; j++) { // matrix A[i][j] = xi ** j;
                A.Set(i, j, (double) Math.pow(xi, j));
            }
        }

        Matrix augmentedMatrix = augmentMatrices(A, b);

        // Eleminasi Gauss
        GaussSolver solver = new GaussSolver(augmentedMatrix);
        solver.Solve();//matrix obe
        double[] coeffs = solver.GetSingleSolution();// matrix nx1

        if (coeffs == null) {
            System.out.println("Tidak ada solusi unik");
            return Float.NaN;
        }

        double y = 0.0f;
        for (int i = 0; i < n; i++) {
            y += coeffs[i] * (double) Math.pow(x, i);// x pangkat i sampe n * dari 0 sampe dibawah n * koefisien dari single solutionnya gauss sum each i = yi
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

        return augmented;//gabung matrix a & matrix b
    }
}
