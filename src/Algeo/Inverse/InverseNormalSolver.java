package Algeo.Inverse;

import Algeo.Matrix;
import Algeo.Determinant.DeterminantCofactorSolver;

public class InverseNormalSolver
{
    static public Matrix Solve(Matrix m)
    {
        if (m.GetRowCount() != m.GetColumnCount()) return null;

        double determinant = DeterminantCofactorSolver.determinant(m);

        if (determinant == 0) return null;

        Matrix adjoint = new Matrix(m.GetRowCount(), m.GetColumnCount());

        if (m.GetRowCount() > 2)
        {
            for (int i = 0; i < adjoint.GetRowCount(); i++)
            {
                for (int j = 0; j < adjoint.GetColumnCount(); j++)
                {
                    if (((i + j) % 2) == 0)
                    {
                        adjoint.Set(i, j, DeterminantCofactorSolver.determinant(m.GetMinor(i, j)));
                    }
                    else
                    {
                        adjoint.Set(i, j, -DeterminantCofactorSolver.determinant(m.GetMinor(i, j)));
                    }
                }
            }

            adjoint = Matrix.Transpose(adjoint);
        }
        else
        {
            adjoint.Set(0, 0, m.Get(1, 1));
            adjoint.Set(0, 1, -m.Get(0, 1));
            adjoint.Set(1, 0, -m.Get(1, 0));
            adjoint.Set(1, 1, m.Get(0, 0));
        }

        return Matrix.Multiply(1 / determinant, adjoint);
    }
}
