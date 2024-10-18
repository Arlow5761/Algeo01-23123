package Algeo.Interpolation;

import Algeo.LinearAlgebra.GaussJordanSolver;
import Algeo.Matrix; 

public class BicubicSplineInterpolator
{

    public static void main(String args[]){
        double a, b;
        a = 0.5;
        b = 0.5;
        double[][] skibidi = {
            {1}, {2}, {3}, {4},
            {5}, {6}, {7}, {8},
            {9}, {10}, {11}, {12},
            {13}, {14}, {15}, {16}
        };
        
        Matrix mew_maxing = new Matrix(skibidi);
        System.out.println(bicubic_interpolate(mew_maxing, a, b));
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


    public static double bicubic_interpolate(Matrix m , double a, double b){

        //input matrix 16x1
        Matrix function = m;

        //input element value in matrix
        double[][] template ={
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},//input f(0,0)
            {1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0},//input f(1,0)
            {1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},//input f(0,1)
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},//input f(1,1)
            {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},//input fx(0,0)
            {0, 0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 0, 3, 0, 0, 0},//input fx(1,0)
            {0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0},//input fx(0,1)
            {0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3},//input fx(1,1)
            {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},//input fy(0,0)
            {0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0},//input fy(1,0)
            {0, 1, 2, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},//input fy(0,1)
            {0, 1, 2, 3, 0, 1, 2, 3, 0, 1, 2, 3, 0, 1, 2, 3},//input fy(1,1)
            {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},//input fxy(0,0)
            {0, 0, 0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 0, 3, 0, 0},//input fxy(1,0)
            {0, 0, 0, 0, 0, 1, 2, 3, 0, 0, 0, 0, 0, 0, 0, 0},//input fxy(0,1)
            {0, 0, 0, 0, 0, 1, 2, 3, 0, 2, 4, 6, 0, 3, 6, 9}//input fxy(1,1)
          };

        //to matrix
        Matrix element = new Matrix(template);
        
        //augment both matrix
        Matrix augmented = augmentMatrices(element,function);

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