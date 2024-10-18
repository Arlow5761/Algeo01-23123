package Algeo.Inverse;

import Algeo.Matrix;
import Algeo.LinearAlgebra.GaussJordanSolver;

public class InverseOBESolver
{
    static public Matrix Solve(Matrix m)
    {
        return new InverseOBESolver(m).Solve();
    }

    public Matrix Solve()
    {
        int dimension = matrix.GetColumnCount();
        Matrix siInvers = new Matrix(dimension,dimension);
        Matrix siMerge = GaussJordanSolver.Solve(Matrix.Append(matrix, Matrix.GetIdentity(dimension))).GetSolution();
        for(int i=0;i<siMerge.GetRowCount();i++) {
            for (int j=dimension;j<siMerge.GetColumnCount();j++) {
                siInvers.Set(i, j-dimension, siMerge.Get(i, j));
            }
        }
        return siInvers;
    }


    public Matrix GetMatrix()
    {
        return matrix;
    }

    public InverseOBESolver(Matrix m)
    {
        matrix = (Matrix) m.clone();
    }

    private Matrix matrix;
}