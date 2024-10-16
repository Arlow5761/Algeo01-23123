package Algeo.Regression;

import Algeo.LinearAlgebra.GaussJordanSolver;
import Algeo.Matrix;

public class LinearRegressor {
    public static float[] solve(float[][] input) {
        float[] result;
        Matrix m = new Matrix(input);
        Matrix matrix1s = new Matrix(m.GetRowCount(), 1);
        Matrix matrixYs = new Matrix(m.GetRowCount(), 1);

        // Matrix Column 1
        for (int i = 0; i < m.GetRowCount(); i++) {
            matrix1s.Set(i, 0, 1);
        }

        // Matrix Y
        for (int i = 0; i < m.GetRowCount(); i++) {
            matrixYs.Set(i, 0, m.Get(i, m.GetColumnCount() - 1));
        }

        Matrix X = Matrix.Append(matrix1s, deleteLastColumn(m));

        Matrix XT = Matrix.Transpose(X);
        Matrix augmented = Matrix.Append(Matrix.Multiply(XT, X), Matrix.Multiply(XT, matrixYs));

        result = GaussJordanSolver.Solve(augmented).GetSingleSolution();

        return result;
    }

    private static Matrix deleteLastColumn(Matrix m) {
        Matrix slicedMatrix = new Matrix(m.GetRowCount(), m.GetColumnCount() - 1);
        for (int i = 0; i < slicedMatrix.GetColumnCount(); i++) {
            slicedMatrix.SetColumn(i, m.GetColumn(i));
        }
        return slicedMatrix;
    }
}