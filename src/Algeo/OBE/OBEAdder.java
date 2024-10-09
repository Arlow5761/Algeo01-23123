package Algeo.OBE;

import Algeo.Matrix;

public class OBEAdder
{
    static public Matrix Add(Matrix m, int targetRow, int sourceRow, float scale)
    {
        Matrix newMatrix = new Matrix(m.GetRowCount(), m.GetColumnCount());

        for (int i = 0; i < m.GetRowCount(); i++)
        {
            if (i == targetRow)
            {
                float[] newRowData = m.GetRow(sourceRow).clone();

                for (int j = 0; j < newRowData.length; j++)
                {
                    newRowData[i] *= scale;
                    newRowData[i] += newMatrix.Get(targetRow, i);
                }

                newMatrix.SetRow(targetRow, newRowData);
            }
            else
            {
                newMatrix.SetRow(i, m.GetRow(i));
            }
        }

        return newMatrix;
    }
}
