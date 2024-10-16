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

    public float[] GetSingleSolution()
    {
        float[] solutions = new float[matrix.GetColumnCount() - 1];
        int varOffset = -1;

        for (int i = 0; i < equalities.length; i++)
        {
            varOffset = -1;

            for (int j = 0; j < equalities[i].length - 1; j++)
            {
                if ((varOffset == -1) && (equalities[i][j] == 1))
                {
                    varOffset = j;
                }
                else if ((varOffset != -1) && (equalities[i][j] != 0))
                {
                    solutions[varOffset] = Float.NaN;
                    varOffset = -1;
                    break;
                }
            }

            if (varOffset != -1)
            {
                solutions[varOffset] = equalities[i][equalities[i].length - 1];
            }
        }

        return solutions;
    }

    public GaussSolver(Matrix m)
    {
        equalities = new float[m.GetRowCount()][m.GetColumnCount()];
        matrix = (Matrix) m.clone();
    }

    private Matrix matrix;
    private float[][] equalities;
}
