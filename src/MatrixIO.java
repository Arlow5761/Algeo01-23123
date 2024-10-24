import Algeo.Matrix;

import java.io.File;

import java.util.Scanner;

import javax.swing.*;

public class MatrixIO
{
    static public Matrix OpenMatrix()
    {
        JFileChooser fileChooser = new JFileChooser("../test");

        int res = fileChooser.showOpenDialog(null);

        if (res != JFileChooser.APPROVE_OPTION)
        {
            return null;
        }

        File file = fileChooser.getSelectedFile();

        try
        {
            Scanner fileReader = new Scanner(file);

            String[] rowStringData = fileReader.nextLine().strip().split(" ");

            double[] rowData = new double[rowStringData.length];

            for (int i = 0; i < rowData.length; i++)
            {
                rowData[i] = Double.parseDouble(rowStringData[i]);
            }

            double[][] matrixData = {rowData};

            while (fileReader.hasNextLine())
            {
                rowStringData = fileReader.nextLine().strip().split(" ");
                rowData = new double[matrixData[0].length];

                if (matrixData[0].length != rowStringData.length)
                {
                    fileReader.close();
                    return null;
                }

                for (int i = 0; i < rowData.length; i++)
                {
                    rowData[i] = Double.parseDouble(rowStringData[i]);
                }

                double[][] newMatrixData = new double[matrixData.length + 1][rowData.length];

                for (int i = 0; i < matrixData.length; i++)
                {
                    newMatrixData[i] = matrixData[i];
                }

                newMatrixData[matrixData.length] = rowData;

                matrixData = newMatrixData;
            }

            fileReader.close();

            return new Matrix(matrixData);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    static public Matrix OpenImperfectMatrix()
    {
        JFileChooser fileChooser = new JFileChooser("../test");

        int res = fileChooser.showOpenDialog(null);

        if (res != JFileChooser.APPROVE_OPTION)
        {
            return null;
        }

        File file = fileChooser.getSelectedFile();

        try
        {
            Scanner fileReader = new Scanner(file);

            String[] rowStringData = fileReader.nextLine().strip().split(" ");

            int maxRowSize = rowStringData.length;

            double[] rowData = new double[maxRowSize];

            for (int i = 0; i < rowData.length; i++)
            {
                rowData[i] = Double.parseDouble(rowStringData[i]);
            }

            double[][] matrixData = {rowData};

            while (fileReader.hasNextLine())
            {
                rowStringData = fileReader.nextLine().strip().split(" ");

                if (rowStringData.length > maxRowSize)
                {
                    maxRowSize = rowStringData.length;
                }

                rowData = new double[maxRowSize];

                for (int i = 0; i < rowStringData.length; i++)
                {
                    rowData[i] = Double.parseDouble(rowStringData[i]);
                }
                for (int i = rowStringData.length; i < rowData.length; i++)
                {
                    rowData[i] = 0;
                }

                double[][] newMatrixData = new double[matrixData.length + 1][maxRowSize];

                for (int i = 0; i < matrixData.length; i++)
                {
                    double[] newRowData = new double[maxRowSize];

                    for (int j = 0; j < matrixData[i].length; j++)
                    {
                        newRowData[j] = rowData[j];
                    }

                    for (int j = matrixData[i].length; j < maxRowSize; j++)
                    {
                        newRowData[j] = 0;
                    }

                    newMatrixData[i] = matrixData[i];
                }

                newMatrixData[matrixData.length] = rowData;

                matrixData = newMatrixData;
            }

            fileReader.close();

            return new Matrix(matrixData);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    static public Matrix ExtractFromTable(PrettyTable table)
    {
        Matrix res = new Matrix(table.GetRows(), table.GetCols());

        for (int i = 0; i < table.GetRows(); i++)
        {
            for (int j = 0; j < table.GetCols(); j++)
            {
                res.Set(i, j, table.GetValue(i, j));
            }
        }

        return res;
    }

    static public void ImportToTable(PrettyTable table, Matrix matrix)
    {
        table.SetRows(matrix.GetRowCount());
        table.SetCols(matrix.GetColumnCount());

        for (int i = 0; i < matrix.GetRowCount(); i++)
        {
            for (int j = 0; j < matrix.GetColumnCount(); j++)
            {
                table.SetValue(i, j, matrix.Get(i, j));
            }
        }
    }
}
