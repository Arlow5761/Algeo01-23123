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
        int leadOneRow = 0;
        int leadOneCol = 0;

        for (int j = 0; (j < matrix.GetColumnCount()) && (leadOneCol < matrix.GetColumnCount()) && (leadOneRow < matrix.GetRowCount()); j++) {
            boolean zeroCol = false;

            for (int i = 0; (i < matrix.GetRowCount()) && !zeroCol; i++) {

                if ((i == leadOneRow) && (j == leadOneCol)) {
                    if (Math.abs(matrix.Get(i, j)) <= 1e-8) {
                        zeroCol = true;

                        for (int k = i + 1; k < matrix.GetRowCount(); k++) {
                            if (Math.abs(matrix.Get(k, j)) <= 1e-8) continue;
                            
                            matrix = OBESwitcher.Switch(matrix, leadOneRow, k);
                            zeroCol = false;
                            break;
                        }

                        if (zeroCol) {
                            leadOneCol++;
                            continue;
                        }
                    }
                    
                    if (Math.abs(matrix.Get(i, j) - 1) > 1e-8) {
                        matrix = OBEScaler.Scale(matrix, leadOneRow, 1/matrix.Get(i, j));
                        matrix.Set(i, j, 1);
                    }

                    leadOneRow++;
                    leadOneCol++;
                }
                else if ((i >= leadOneRow) && (Math.abs(matrix.Get(i, j)) > 1e-8)) {
                    matrix = OBEAdder.Add(matrix, i, leadOneRow - 1, -matrix.Get(i, j));
                    matrix.Set(i, j, 0);
                }
            }
        }

        return this;
    }

    public double[] GetSingleSolution() {
        for (int i = matrix.GetRowCount() - 1; i >= 0; i--) {
            if (Math.abs(matrix.Get(i, matrix.GetColumnCount() - 1)) > 1e-8) {
                boolean filled = false;

                for (int j = matrix.GetColumnCount() - 2; j >= 0; j--) {
                    if (Math.abs(matrix.Get(i, j)) > 1e-8) {
                        filled = true;
                        break;
                    }
                }

                if (!filled) return null;

                break;
            }
        }

        double[] solution = new double[matrix.GetColumnCount() - 1];

        for (int j = matrix.GetColumnCount() - 2; j >= 0; j--)
        {
            double lastVal = 0;
            int i = matrix.GetRowCount() - 1;

            for (; (i >= 0) && (Math.abs(lastVal) < 1e-8); i--) {
                lastVal = matrix.Get(i, j);
            }

            i++;

            if (Math.abs(lastVal - 1) < 1e-8) {
                solution[j] = 0;

                for (int k = j - 1; k >= 0; k--) {
                    if (Math.abs(matrix.Get(i, k)) > 1e-8) return null;
                }

                for (int k = j + 1; k < matrix.GetColumnCount() - 1; k++) {
                    solution[j] -= solution[k] * matrix.Get(i, k);
                }

                solution[j] += matrix.Get(i, matrix.GetColumnCount() - 1);
            } else {
                return null;
            }
        }

        return solution;
    }

    public double[][] GetMultipleSolution() {
        for (int i = matrix.GetRowCount() - 1; i >= 0; i--) {
            if (Math.abs(matrix.Get(i, matrix.GetColumnCount() - 1)) > 1e-8) {
                boolean filled = false;

                for (int j = matrix.GetColumnCount() - 2; j >= 0; j--) {
                    if (Math.abs(matrix.Get(i, j)) > 1e-8) {
                        filled = true;
                        break;
                    }
                }

                if (!filled) return null;

                break;
            }
        }

        double[][] solutions = new double[matrix.GetColumnCount() - 1][];

        for (int j = matrix.GetColumnCount() - 2; j >= 0; j--) {
            double lastVal = 0;
            int i = matrix.GetRowCount() - 1;

            for (; (i >= 0) && (Math.abs(lastVal) < 1e-8); i--) {
                lastVal = matrix.Get(i, j);
            }

            i++;

            if (Math.abs(lastVal - 1) < 1e-8) {
                double[] solution = matrix.GetRow(i).clone();

                solution[j] = 0;

                solutions[j] = solution;

                for (int k = j - 1; k >= 0; k--) {
                    if (Math.abs(matrix.Get(i, k)) > 1e-8) {
                        solutions[j] = null;
                        break;
                    }
                }

                if (solutions[j] == null) continue;

                for (int k = j + 1; k < solution.length - 1; k++) {
                    solution[k] = -solution[k];
                }

                for (int k = j + 1; k < solution.length - 1; k++) {
                    if (solutions[k] != null) {
                        for (int l = k + 1; l < solutions[k].length; l++) {
                            solution[l] += solutions[k][l] * solution[k];
                        }

                        solution[k] = 0;
                    }
                }

                solutions[j] = solution;
            } else {
                solutions[j] = null;
            }
        }

        return solutions;
    }

    public Matrix GetMatrix() {
        return matrix;
    }
}
