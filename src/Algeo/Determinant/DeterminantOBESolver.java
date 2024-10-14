package Algeo.Determinant;

import Algeo.Matrix;
import Algeo.LinearAlgebra.GaussSolver;

public class DeterminantOBESolver
{
    static public float Solve(Matrix m)
    {
        return new DeterminantOBESolver(m).Solve();
    }

    public float Solve()
    {
        solution = 0;

        matrix = GaussSolver.Solve(matrix).GetMatrix();

        for (int i = 0; i < matrix.GetColumnCount(); i++)
        {
            solution += matrix.Get(i, i);
        }

        return GetSolution();
    }

    public float GetSolution()
    {
        return solution;
    }

    public DeterminantOBESolver(Matrix m)
    {
        solution = 0;
        matrix = m;
    }

    private Matrix matrix;
    private float solution;
}
