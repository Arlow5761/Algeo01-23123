package Algeo.LinearAlgebra;

import Algeo.Matrix;
import Algeo.Inverse.*;

public class InverseSolver
{
    static public double[] Solve(Matrix m, double[] b)
    {
        Matrix temp = new Matrix(b.length, 1);
        temp.SetColumn(0, b.clone());
        Matrix sum = Matrix.Multiply((InverseOBESolver.Solve(m)), temp);
        return sum.GetColumn(0);
    }
}
