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
    }

    public static double bicubic_interpolate(Matrix m , double a, double b){

        //convert matrix 16x1
        Matrix function = new Matrix(m.size(), 1); 
        for ( int i = 0 ; i < m.GetRowCount(); i++){
            for ( int j = 0 ; j < m.GetColumnCount(); j++){
                function.Set((4 * i) + j, 0, m.Get(i,j));
            }
        }


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