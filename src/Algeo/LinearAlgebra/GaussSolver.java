package Algeo.LinearAlgebra;

import Algeo.Matrix;
import Algeo.OBE.OBEScaler;
import Algeo.OBE.OBESwitcher;
import Algeo.OBE.OBEAdder;

public class GaussSolver
{
    static public GaussSolver Solve(Matrix m)
    {
        return new GaussSolver(m).Solve();
    }

    public GaussSolver Solve()
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

        // Backwards Substitution

        for (int i = 0; i < equalities.length; i++)
        {
            int mainVarPos = -1;

            for (int j = 0; j < equalities[i].length; j++)
            {
                equalities[i][j] = matrix.Get(i, j);

                if ((equalities[i][j] == 1) && (mainVarPos) == -1)
                {
                    mainVarPos = j;
                }
                else if ((mainVarPos != -1) && (i > 0))
                {
                    int k = i;

                    for (; (k >= 0) && (equalities[k][j] == 0); k--);

                    for (int l = j; j < equalities[i].length; j++)
                    {
                        equalities[i][l] -= equalities[i][j] * equalities[k][l];
                    }
                }
            }
        }

        return this;
    }

    public Matrix GetMatrix()
    {
        return matrix;
    }

    public float[][] GetSolution()
    {
        return equalities;
    }

    public GaussSolver(Matrix m)
    {
        equalities = new float[m.GetRowCount()][m.GetColumnCount()];
        matrix = (Matrix) m.clone();
    }

    private Matrix matrix;
    private float[][] equalities;
}
