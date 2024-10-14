package Algeo;

public class Matrix implements Cloneable
{
    // UTILITY OPERATIONS

    static public Matrix GetIdentity(int size)
    {
        Matrix identityMatrix = new Matrix(size, size);

        for (int i = 0; i < size; i++)
        {
            identityMatrix.Set(i, i, 1);
        }

        return identityMatrix;
    }

    static public Matrix Append(Matrix A, Matrix B)
    {
        if (A.GetRowCount() != B.GetColumnCount()) return null;

        Matrix newMatrix = new Matrix(A.GetRowCount(), A.GetColumnCount() + B.GetColumnCount());

        for (int i = 0; i < A.GetRowCount(); i++)
        {
            for (int j = 0; j < A.GetColumnCount(); j++)
            {
                newMatrix.Set(i, j, A.Get(i, j));
            }

            for (int j = 0; j < B.GetColumnCount(); j++)
            {
                newMatrix.Set(i, A.GetColumnCount() + j, B.Get(i, j));
            }
        }

        return newMatrix;
    }

    public Matrix GetMinor(int i, int j)
    {
        Matrix minor = new Matrix(GetRowCount() - 1, GetColumnCount() - 1);
        int n = 0;

        for (int a = 0; a < GetRowCount(); a++)
        {
            int m = 0;

            if (a == i) continue;

            for (int b = 0; b < GetColumnCount(); b++)
            {
                if (b == j) continue;

                minor.Set(n, m, Get(a, b));

                m++;
            }

            n++;
        }

        return minor;
    }

    // BASIC OPERATIONS //

    static public Matrix Add(Matrix A, Matrix B)
    {
        if ((A.GetRowCount() != B.GetRowCount()) || (A.GetColumnCount() != B.GetColumnCount()))
        {
            return null;
        }

        float[][] matrixData = new float[A.GetRowCount()][B.GetColumnCount()];

        for (int i = 0; i < A.GetRowCount(); i++)
        {
            for (int j = 0; j < B.GetColumnCount(); j++)
            {
                matrixData[i][j] = A.Get(i, j) + B.Get(i, j);
            }
        }

        return new Matrix(matrixData);
    }

    static public Matrix Subtract(Matrix A, Matrix B)
    {
        if ((A.GetRowCount() != B.GetRowCount()) || (A.GetColumnCount() != B.GetColumnCount()))
        {
            return null;
        }

        float[][] matrixData = new float[A.GetRowCount()][B.GetColumnCount()];

        for (int i = 0; i < A.GetRowCount(); i++)
        {
            for (int j = 0; j < B.GetColumnCount(); j++)
            {
                matrixData[i][j] = A.Get(i, j) - B.Get(i, j);
            }
        }

        return new Matrix(matrixData);
    }

    static public Matrix Multiply(Matrix A, Matrix B)
    {
        if ((A.GetColumnCount() != B.GetRowCount())) return null;

        float[][] matrixData = new float[A.GetRowCount()][B.GetColumnCount()];

        for (int i = 0; i < A.GetRowCount(); i++)
        {
            for (int j = 0; j < B.GetColumnCount(); j++)
            {
                float sum = 0;

                for (int k = 0; k < A.GetColumnCount(); k++)
                {
                    sum += A.Get(i, k) * B.Get(k, j);
                }

                matrixData[i][j] = sum;
            }
        }

        return new Matrix(matrixData);
    }

    static public Matrix Transpose(Matrix m)
    {
        float[][] matrixData = new float[m.GetColumnCount()][m.GetRowCount()];

        for (int i = 0; i < m.GetRowCount(); i++)
        {
            for (int j = 0; j < m.GetColumnCount(); j++)
            {
                matrixData[j][i] = m.Get(i, j);
            }
        }

        return new Matrix(matrixData);
    }

    // ACCESSORS //

    public float Get(int i, int j)
    {
        return this.data[i][j];
    }

    public void Set(int i, int j, float v)
    {
        this.data[i][j] = v;
    }

    public float[] GetRow(int i)
    {
        return data[i];
    }

    public float[] GetColumn(int j)
    {
        float[] columnData = new float[this.data.length];
        for (int i = 0; i < this.data.length; i++)
        {
            columnData[i] = this.data[i][j];
        }

        return columnData;
    }

    public void SetRow(int i, float[] rowData)
    {
        data[i] = rowData;
    }

    public void SetColumn(int j, float[] columnData)
    {
        for (int i = 0; i < this.data.length; i++)
        {
            this.data[i][j] = columnData[i];
        }
    }

    public int GetRowCount()
    {
        return this.data.length;
    }

    public int GetColumnCount()
    {
        return this.data[0].length;
    }

    // CONSTRUCTORS DESTRUCTORS //

    public Matrix(int nRows, int nColumns)
    {
        this.data = new float[nRows][nColumns];
        for (int i = 0; i < nRows; i++)
        {
            for (int j = 0; j < nColumns; j++)
            {
                data[i][j] = 0;
            }
        }
    }

    public Matrix(float[][] data)
    {
        this.data = data.clone();
    }

    public Object clone()
    {
        return new Matrix(data);
    }

    private float[][] data;
}