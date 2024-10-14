package Algeo.LinearAlgebra;

import Algeo.Matrix;
import Algeo.OBE.OBEAdder;
import Algeo.OBE.OBEScaler;
import Algeo.OBE.OBESwitcher;

public class GaussJordanSolver
{
    static public GaussJordanSolver Solve(Matrix m)
    {
        return new GaussJordanSolver(m).Solve();
    }

    public GaussJordanSolver Solve()
    {
        // OBE operations

        int leadOneRow = 0;
        int leadOneCol = 0;

        for (int j = 0; j < matrix.GetColumnCount(); j++)
        {
            boolean zeroCol = false;

            for (int i = 0; (i < matrix.GetRowCount()) && !zeroCol; i++)
            {
                float val = matrix.Get(i, j);

                if ((i == leadOneRow) && (j == leadOneCol))
                {
                    if (val == 0)
                    {
                        zeroCol = true;

                        for (int k = i + 1; k < matrix.GetRowCount(); k++)
                        {
                            if (matrix.Get(i, k) == 0) continue;
                            
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
                    
                    if (val != 1)
                    {
                        matrix = OBEScaler.Scale(matrix, leadOneRow, 1/val);
                        leadOneRow++;
                        leadOneCol++;
                    }
                }
                else if ((i >= leadOneRow) && (val != 0))
                {
                    matrix = OBEAdder.Add(matrix, i, leadOneRow - 1, -val);
                }
            }
        }

        // Jordan Part

        for (int i = matrix.GetRowCount() - 1; i > 0; i--)
        {
            int mainVarPos = 0;

            for (; (mainVarPos < matrix.GetColumnCount()) && (matrix.Get(i, mainVarPos) != 1); mainVarPos++);

            for (int j = i - 1; j >= 0; j--)
            {
                matrix = OBEAdder.Add(matrix, j, i, -matrix.Get(j, mainVarPos));
            }
        }

        return this;
    }

    public Matrix GetSolution()
    {
        return matrix;
    }

    public GaussJordanSolver(Matrix m)
    {
        matrix = m;
    }

    private Matrix matrix;
}
