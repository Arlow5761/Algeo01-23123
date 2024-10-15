package Algeo.Determinant;

import Algeo.Matrix;

public class DeterminantCofactorSolver
{
    public static float determinant(Matrix m){
        float n = m.GetColumnCount();
        float det = 0;

        if (n == 1){
            return m.Get(0,0);
        }

        if ( n == 2){
            return (m.Get(0,0) * m.Get(1,1) - m.Get(1,0) * m.Get(0,1));
        }

        for (int j = 0 ; j < n ; j++){
            det += Math.pow(-1,j) * m.Get(0,j) * determinant(m.GetMinor(0,j));
        }   

        return det;
    }
}




