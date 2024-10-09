package Algeo.LinearAlgebra;

import Algeo.Matrix;

public class GaussSolver
{
    static public GaussSolver Solve(Matrix m)
    {
        return new GaussSolver(m).Solve();
    }

    public GaussSolver Solve()
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

    public GaussSolver(Matrix m)
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
