package Algeo.OBE;

import Algeo.Matrix;

public class OBESwitcher
{
    static public Matrix Switch(Matrix m, int rowA, int rowB)
    {
        Matrix cola = new Matrix(m.GetRowCount(),m.GetColumnCount());
        //Copy m to new Matrix cola
        for (int i=0;i<cola.GetRowCount();i++) {
            for (int j=0;j<cola.GetColumnCount();j++)
                cola.Set(i,j,m.Get(i,j));
        }

        double[] temp = cola.GetRow(rowA);

        cola.SetRow(rowA, cola.GetRow(rowB));
        cola.SetRow(rowB, temp);

        return cola;
    }
}
