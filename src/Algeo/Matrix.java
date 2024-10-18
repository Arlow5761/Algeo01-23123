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
        if (A.GetRowCount() != B.GetRowCount()) return null;

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

        double[][] matrixData = new double[A.GetRowCount()][B.GetColumnCount()];

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

        double[][] matrixData = new double[A.GetRowCount()][B.GetColumnCount()];

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

        double[][] matrixData = new double[A.GetRowCount()][B.GetColumnCount()];

        for (int i = 0; i < A.GetRowCount(); i++)
        {
            for (int j = 0; j < B.GetColumnCount(); j++)
            {
                double sum = 0;

                for (int k = 0; k < A.GetColumnCount(); k++)
                {
                    sum += A.Get(i, k) * B.Get(k, j);
                }

                matrixData[i][j] = sum;
            }
        }

        return new Matrix(matrixData);
    }

    static public Matrix Multiply(double k, Matrix m)
    {
        Matrix result = new Matrix(m.GetRowCount(), m.GetColumnCount());

        for (int i = 0; i < result.GetRowCount(); i++)
        {
            for (int j = 0; j < result.GetColumnCount(); j++)
            {
                result.Set(i, j, m.Get(i, j) * k);
            }
        }

        return result;
    }

    static public Matrix Transpose(Matrix m)
    {
        double[][] matrixData = new double[m.GetColumnCount()][m.GetRowCount()];

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

    public double Get(int i, int j)
    {
        return this.data[i][j];
    }

    public void Set(int i, int j, double v)
    {
        this.data[i][j] = v;
    }

    public double[] GetRow(int i)
    {
        return data[i];
    }

    public double[] GetColumn(int j)
    {
        double[] columnData = new double[this.data.length];
        for (int i = 0; i < this.data.length; i++)
        {
            columnData[i] = this.data[i][j];
        }

        return columnData;
    }

    public void SetRow(int i, double[] rowData)
    {
        data[i] = rowData.clone();
    }

    public void SetColumn(int j, double[] columnData)
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

    public int size()
    {
        int count = 0;
        for( int i = 0 ; i < this.GetRowCount(); i++){
            for (int j = 0 ; j < this.GetColumnCount(); j++){
                count++;
            }
        }
        return count;
    }

    // CONSTRUCTORS DESTRUCTORS //

    public Matrix(int nRows, int nColumns)
    {
        this.data = new double[nRows][nColumns];
        for (int i = 0; i < nRows; i++)
        {
            for (int j = 0; j < nColumns; j++)
            {
                data[i][j] = 0;
            }
        }
    }

    public Matrix(double[][] data)
    {
        this.data = data.clone();
    }

    public Object clone()
    {
        double[][] copiedData = new double[data.length][data[0].length];
        for (int i = 0; i < data.length; i++)
        {
            for (int j = 0; j < data[i].length; j++)
            {
                copiedData[i][j] = data[i][j];
            }
        }

        return new Matrix(copiedData);
    }

    private double[][] data;
}