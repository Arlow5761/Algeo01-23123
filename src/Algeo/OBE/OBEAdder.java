package Algeo.OBE;

import Algeo.Matrix;

public class OBEAdder
{
    static public Matrix Add(Matrix m, int targetRow, int sourceRow, double scale)
    {
        Matrix newMatrix = new Matrix(m.GetRowCount(), m.GetColumnCount());

        for (int i = 0; i < m.GetRowCount(); i++)
        {
            if (i == targetRow)
            {
                double[] newRowData = m.GetRow(sourceRow).clone();

                for (int j = 0; j < newRowData.length; j++)
                {
                    newRowData[j] = m.Get(targetRow, j) + newRowData[j] * scale;
                }

                newMatrix.SetRow(targetRow, newRowData);
            }
            else
            {
                newMatrix.SetRow(i, m.GetRow(i).clone());
            }
        }

        return newMatrix;
    }
}
