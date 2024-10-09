package Algeo.OBE;

import Algeo.Matrix;

public class OBEScaler
{
    static public Matrix Scale(Matrix m, int row, float scale)
    {
        Matrix scaledMatrix = new Matrix(m.GetRowCount(),m.GetColumnCount());

        for ( int i = 0 ; i < m.GetColumnCount(); i++){
            scaledMatrix.Set(row, i, scale* m.Get(row,i));
        }
        return scaledMatrix;
    }

}


