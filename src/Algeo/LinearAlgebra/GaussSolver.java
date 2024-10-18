package Algeo.LinearAlgebra;

import Algeo.Matrix;
import Algeo.OBE.OBEScaler;
import Algeo.OBE.OBESwitcher;
import Algeo.OBE.OBEAdder;

public class GaussSolver {

    private Matrix matrix;

    public GaussSolver(Matrix m) {
        matrix = (Matrix) m.clone();
    }

    public GaussSolver Solve() {
        int n = matrix.GetRowCount();
        int m = matrix.GetColumnCount();

        int lead = 0;

        for (int r = 0; r < n; r++) {
            if (lead >= m) {
                break;
            }
            int i = r;
            while (matrix.Get(i, lead) == 0) {
                i++;
                if (i == n) {
                    i = r;
                    lead++;
                    if (lead == m) {
                        return this;
                    }
                }
            }
            if (i != r) {
                // Switch
                matrix = OBESwitcher.Switch(matrix, i, r);
            }

            // Scale
            double leadVal = matrix.Get(r, lead);
            if (leadVal != 1.0) {
                matrix = OBEScaler.Scale(matrix, r, 1.0 / leadVal);
            }

            // Add
            for (int j = 0; j < n; j++) {
                if (j != r) {
                    double factor = -matrix.Get(j, lead);
                    if (factor != 0) {
                        matrix = OBEAdder.Add(matrix, j, r, factor);
                    }
                }
            }
            lead++;
        }

        return this;
    }

    public double[] GetSingleSolution() {
        int n = matrix.GetRowCount();
        int m = matrix.GetColumnCount();
        double[] solutions = new double[m - 1];

        for (int i = 0; i < n; i++) {
            boolean allZeros = true;
            int pivotCol = -1;

            for (int j = 0; j < m - 1; j++) {
                double val = matrix.Get(i, j);
                if (val != 0) {
                    allZeros = false;
                    if (pivotCol == -1) {
                        pivotCol = j;
                    } else {
                        return null;
                    }
                }
            }

            if (!allZeros) {
                solutions[pivotCol] = matrix.Get(i, m - 1);
            }
        }

        return solutions;
    }

    public Matrix GetMatrix() {
        return matrix;
    }
}
