import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import javax.swing.*;
import java.text.*;
import Algeo.*;
import Algeo.Determinant.DeterminantCofactorSolver;

public class DeterminantCofactorPage extends Page
{
    public DeterminantCofactorPage()
    {
        page.setLayout(null);

        JLabel backImage = new JLabel(new ImageIcon("../assets/determinant-cofactor.png"));
        backImage.setBounds(0, 0, 700, 925);

        page.add(backImage);
        page.revalidate();

        page.repaint();
    }
}
