package Algeo.LinearAlgebra;

import Algeo.Matrix;
import Algeo.Determinant.*;

public class CramerSolver
{
    static public Matrix[] GetSubmatrices(Matrix equationSpace, Matrix solutionSpace)
    {
        return new Matrix[0];
    }
    
    static public double[] Solve(Matrix equationSpace, Matrix solutionSpace)
    {
        double[] result = new double[equationSpace.GetColumnCount()];
        double detM = DeterminantCofactorSolver.determinant(equationSpace);
        for (int i=0;i<solutionSpace.GetRowCount();i++) {
            result[i] = DeterminantCofactorSolver.determinant(sliceAndInsert(equationSpace,solutionSpace,i))/detM;
        }

        return result;
    }

    static private Matrix sliceAndInsert(Matrix m1, Matrix m2, int excludeCol) {
        Matrix result = new Matrix(m1.GetRowCount(), m1.GetColumnCount());
        for (int i = 0; i < result.GetColumnCount(); i++) {
            if (i == excludeCol) {
                result.SetColumn(i, m2.GetColumn(0));
            } else {
                result.SetColumn(i, m1.GetColumn(i));
            }
        }
        return result;
    }
}
