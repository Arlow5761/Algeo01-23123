package Algeo.Interpolation;

import Algeo.LinearAlgebra.GaussJordanSolver;
import Algeo.Matrix;

public class BicubicSplineInterpolator {

    public static void main(String args[]){
        double a = 0.5;
        double b = 0.5;

        double[][] mewing = {{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,16}};
        System.out.println(bicubicInterpolate(mewing,a , b));

        int n = 20;
		for (int i = 0; i <= n ; i++) {
			for (int j = 0; j <= n ; j++) {
				double result = bicubicInterpolate(mewing, (double)i/n,(double) j/n);
				System.out.printf("%.0f ",result);
			}
			System.out.println("");
		}

    }

    public static double bicubicInterpolate(double[][] input, double a, double b) {
        // handle error
        if (input.length != 4 || input[0].length != 4) {
            throw new IllegalArgumentException("Input matrix tidak valid");
        }

        //input
        double[] F = new double[16];
        int idx = 0;
        for (int k = 0; k < 4; k++) {
            for (int l = 0; l < 4; l++) {
                F[idx++] = input[k][l];
            }
        }

        double[][] A = new double[16][16];
        double[] bVec = new double[16];

        idx = 0;
        double[] xCoords = {0, 1};
        double[] yCoords = {0, 1};

        for (int i = 0; i < 2; i++) { 
            for (int j = 0; j < 2; j++) { 
                double xi = xCoords[i];
                double yj = yCoords[j];

                // proses
                double[] basisF = new double[16];
                double[] basisFx = new double[16];
                double[] basisFy = new double[16];
                double[] basisFxy = new double[16];
                int count = 0;
                for (int m = 0; m < 4; m++) {
                    for (int n = 0; n < 4; n++) {
                        double xm = Math.pow(xi, m);
                        double yn = Math.pow(yj, n);
                        basisF[count] = xm * yn;
                        basisFx[count] = (m > 0 ? m * Math.pow(xi, m - 1) : 0) * yn;
                        basisFy[count] = xm * (n > 0 ? n * Math.pow(yj, n - 1) : 0);
                        basisFxy[count] = (m > 0 ? m * Math.pow(xi, m - 1) : 0) * (n > 0 ? n * Math.pow(yj, n - 1) : 0);
                        count++;
                    }
                }

                // f(xi, yj)
                A[idx] = basisF;
                bVec[idx] = F[idx];
                idx++;

                // fx(xi, yj)
                A[idx] = basisFx;
                bVec[idx] = F[idx];
                idx++;

                // fy(xi, yj)
                A[idx] = basisFy;
                bVec[idx] = F[idx];
                idx++;

                // fxy(xi, yj)
                A[idx] = basisFxy;
                bVec[idx] = F[idx];
                idx++;
            }
        }

        // Solve 
        Matrix AMatrix = new Matrix(A);
        Matrix bMatrix = new Matrix(bVec.length, 1);
        for (int i = 0; i < bVec.length; i++) {
            bMatrix.Set(i, 0, bVec[i]);
        }
        Matrix augmented = Matrix.Append(AMatrix, bMatrix);

        GaussJordanSolver solver = new GaussJordanSolver(augmented);
        solver.Solve();
        double[] coeffs = solver.GetSingleSolution();

        //result
        double result = 0.0;
        int count = 0;
        for (int m = 0; m < 4; m++) {
            double am = Math.pow(a, m);
            for (int n = 0; n < 4; n++) {
                double bn = Math.pow(b, n);
                result += coeffs[count++] * am * bn;
            }
        }

        return result;
    }
}
