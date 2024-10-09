package Algeo.LinearAlgebra;

import Algeo.Matrix;

public class GaussJordanSolver
{
    static public GaussJordanSolver Solve(Matrix m)
    {
        return new GaussJordanSolver(m).Solve();
    }

    public GaussJordanSolver Solve()
    {
        do
        {
            Step();
        }
        while (state == State.unfinished);

        return this;
    }

    public float GetSingleSolution()
    {
        return 0;
    }

    public void Step()
    {

    }

    public State GetState()
    {
        return state;
    }

    public GaussJordanSolver(Matrix m)
    {
        state = State.unfinished;
        matrix = m;
    }

    enum State
    {
        unfinished,
        hasOneSolution,
        hasMultipleSolutions
    }

    private State state;
    private Matrix matrix;
}
