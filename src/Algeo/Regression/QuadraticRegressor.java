package Algeo.Regression;

import Algeo.LinearAlgebra.GaussSolver;
import Algeo.Matrix;

public class QuadraticRegressor {
    public static double[] solve(double[][] input) {
        double[] result;
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

        Matrix linearTerms = deleteLastColumn(m);
        Matrix quadraticTerms = createQuadraticTerms(linearTerms);
        Matrix interactionTerms = createInteractionTerms(linearTerms);
        Matrix X = Matrix.Append(Matrix.Append(matrix1s, Matrix.Append(linearTerms, quadraticTerms)), interactionTerms);

        Matrix XT = Matrix.Transpose(X);
        Matrix augmented = Matrix.Append(Matrix.Multiply(XT, X), Matrix.Multiply(XT, matrixYs));

        result = GaussSolver.Solve(augmented).GetSingleSolution();

        return result;
    }

    private static Matrix deleteLastColumn(Matrix m) {
        Matrix slicedMatrix = new Matrix(m.GetRowCount(), m.GetColumnCount() - 1);
        for (int i = 0; i < slicedMatrix.GetColumnCount(); i++) {
            slicedMatrix.SetColumn(i, m.GetColumn(i));
        }
        return slicedMatrix;
    }

    private static Matrix createQuadraticTerms(Matrix linearTerms) {
        Matrix quadraticTerms = new Matrix(linearTerms.GetRowCount(), linearTerms.GetColumnCount());
        for (int i = 0; i < quadraticTerms.GetRowCount(); i++) {
            for (int j = 0; j < quadraticTerms.GetColumnCount(); j++) {
                quadraticTerms.Set(i, j, Math.pow(linearTerms.Get(i, j), 2));
            }
        }
        return quadraticTerms;
    }

    private static Matrix createInteractionTerms(Matrix linearTerms) {
        Matrix interactionTerms = new Matrix(linearTerms.GetRowCount(), (linearTerms.GetColumnCount() - 1) * linearTerms.GetColumnCount() / 2);
        int count = 0;
        for (int i = 0; i < linearTerms.GetColumnCount() - 1; i++) {
            for (int j = i + 1; j < linearTerms.GetColumnCount(); j++) {
                for (int r = 0; r < linearTerms.GetRowCount(); r++) {
                    interactionTerms.Set(r, count, linearTerms.Get(r, i) * linearTerms.Get(r, j));
                }
                count++;
            }
        }
        return interactionTerms;
    }
}
