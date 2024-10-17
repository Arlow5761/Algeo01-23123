package Algeo.Determinant;

import Algeo.Matrix;
import Algeo.OBE.OBEAdder;
import Algeo.OBE.OBEScaler;
import Algeo.OBE.OBESwitcher;

public class DeterminantOBESolver
{
    static public float Solve(Matrix m)
    {
        return new DeterminantOBESolver(m).Solve();
    }

    public float Solve()
    {
        solution = 1;

        float multiplier = 1;

        int leadOneRow = 0;
        int leadOneCol = 0;

        for (int j = 0; (j < matrix.GetColumnCount()) && (leadOneCol < matrix.GetColumnCount()) && (leadOneRow < matrix.GetRowCount()); j++)
        {
            boolean zeroCol = false;

            for (int i = 0; (i < matrix.GetRowCount()) && !zeroCol; i++)
            {

                if ((i == leadOneRow) && (j == leadOneCol))
                {
                    if (matrix.Get(i, j) == 0)
                    {
                        zeroCol = true;

                        for (int k = i + 1; k < matrix.GetRowCount(); k++)
                        {
                            if (matrix.Get(k, j) == 0) continue;
                            
                            multiplier = -multiplier;
                            matrix = OBESwitcher.Switch(matrix, leadOneRow, k);
                            zeroCol = false;
                            break;
                        }

                        if (zeroCol)
                        {
                            leadOneCol++;
                            continue;
                        }
                    }
                    
                    if (matrix.Get(i, j) != 1)
                    {
                        multiplier *= matrix.Get(i, j);
                        matrix = OBEScaler.Scale(matrix, leadOneRow, 1/matrix.Get(i, j));
                    }

                    leadOneRow++;
                    leadOneCol++;
                }
                else if ((i >= leadOneRow) && (matrix.Get(i, j) != 0))
                {
                    matrix = OBEAdder.Add(matrix, i, leadOneRow - 1, -matrix.Get(i, j));
                }
            }
        }

        for (int i = 0; i < matrix.GetColumnCount(); i++)
        {
            solution *= matrix.Get(i, i);
        }

        solution *= multiplier;

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
