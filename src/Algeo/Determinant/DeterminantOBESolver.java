package Algeo.Determinant;

import Algeo.Matrix;

public class DeterminantOBESolver
{
    static public float Solve(Matrix m)
    {
        return new DeterminantOBESolver(m).Solve();
    }

    public float Solve()
    {
        do
        {
            Step();
        }
        while (!solved);

        return GetSolution();
    }

    public float GetSolution()
    {
        // Get solution from finished state

        return 0;
    }

    public void Step()
    {
        // Update object state to get one step closer to finished state
    }

    public boolean IsSolved()
    {
        return solved;
    }

    public Matrix GetMatrix()
    {
        return matrix;
    }

    public DeterminantOBESolver(Matrix m)
    {
        solved = false;
        matrix = m;
    }

    private Matrix matrix;
    private boolean solved;
}
