package Algeo.Inverse;

import Algeo.Matrix;

public class InverseOBESolver
{
    static public Matrix Solve(Matrix m)
    {
        return new InverseOBESolver(m).Solve();
    }

    public Matrix Solve()
    {
        do
        {
            Step();
        }
        while (!solved);

        return GetSolution();
    }

    public void Step()
    {

    }

    public Matrix GetMatrix()
    {
        return matrix;
    }

    public Matrix GetSolution()
    {
        return GetMatrix();
    }

    public boolean IsSolved()
    {
        return solved;
    }

    public InverseOBESolver(Matrix m)
    {
        solved = false;
        matrix = m;
    }

    private boolean solved;

    private Matrix matrix;
}