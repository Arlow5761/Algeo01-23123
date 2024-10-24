package Algeo.Interpolation;

import Algeo.LinearAlgebra.GaussJordanSolver;
import Algeo.Matrix; 

public class BicubicSplineInterpolator
{

    public static void main(String args[]){
        double a, b;
        a = 0.75;
        b = 0.25;
        double[][] skibidi1 = {
            {21,98,125,153},
            {51,101,161,59},
            {0,42,72,210},
            {16,12,81,96},
        };
    
        Matrix mew_maxing = new Matrix(skibidi1);
        System.out.println(bicubic_interpolate(mew_maxing, a, b));
        int n = 20;
		for (int i = 0; i <= n ; i++) {
			for (int j = 0; j <= n ; j++) {
				double result = bicubic_interpolate(mew_maxing, (double)i/n , (double)j/n);
				System.out.printf("%.0f ",result);
			}
			System.out.println("");
		}

    }

    public static double bicubic_interpolate(Matrix m , double a, double b){

        //convert matrix 16x1
        Matrix function = new Matrix(m.size(), 1); 
        for ( int i = 0 ; i < m.GetRowCount(); i++){
            for ( int j = 0 ; j < m.GetColumnCount(); j++){
                function.Set((4 * i) + j, 0, m.Get(i,j));
            }
        }

         //matrix equation
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

        //to matrix
        Matrix element = new Matrix(equation);

        //augment both matrix
        Matrix augmented = Matrix.Append(element, function);

        //solve gaussjordan
        GaussJordanSolver solver = new GaussJordanSolver(augmented);
        solver.Solve();
        double[] coeffs = solver.GetSingleSolution();
            
        //solve final
        double result = 0.0;
        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j <= 3; j++) {
                int index = i * 4 + j; 
                result += coeffs[index] * Math.pow(a, i) * Math.pow(b, j);
            }
        }

        return result;
     }
}
  