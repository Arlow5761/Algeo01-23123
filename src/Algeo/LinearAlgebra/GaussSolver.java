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
            while (Math.abs(matrix.Get(i, lead)) < 1e-8) {
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
                //switch
                matrix = OBESwitcher.Switch(matrix, i, r);
            }

            // Scale
            float leadVal = matrix.Get(r, lead);
            if (Math.abs(leadVal - 1.0f) > 1e-8) {
                matrix = OBEScaler.Scale(matrix, r, 1.0f / leadVal);
            }

            // Add
            for (int j = 0; j < n; j++) {
                if (j != r) {
                    float factor = -matrix.Get(j, lead);
                    if (Math.abs(factor) > 1e-8) {
                        matrix = OBEAdder.Add(matrix, j, r, factor);
                    }
                }
            }
            lead++;
        }

        return this;
    }

    public float[] GetSingleSolution() {
        int n = matrix.GetRowCount();
        int m = matrix.GetColumnCount();
        float[] solutions = new float[m - 1];

        for (int i = 0; i < n; i++) {
            boolean allZeros = true;
            int pivotCol = -1;

            for (int j = 0; j < m - 1; j++) {
                float val = matrix.Get(i, j);
                if (Math.abs(val) > 1e-8) {
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
