import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class LinearAlgebraCalculator implements ActionListener
{
    static public LinearAlgebraCalculator instance;

    public JFrame window;

    public Page currentPage;

    static public void main(String[] args)
    {
        instance = new LinearAlgebraCalculator();

        Page.Load(new CoverPage());
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == cofactor)
        {
            currentPage.Unload();
            Page.Load(new DeterminantCofactorPage());
        }
        else if (e.getSource() == obedet)
        {
            currentPage.Unload();
            Page.Load(new DeterminantOBEPage());
        }
        else if (e.getSource() == obeinv)
        {
            currentPage.Unload();
            Page.Load(new InverseOBEPage());
        }
        else if (e.getSource() == standard)
        {
            currentPage.Unload();
            Page.Load(new InverseNormalPage());
        }
    }

    public LinearAlgebraCalculator()
    {
        window = new JFrame("Linear Algebra Calculator");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setSize(716, 987);
        window.setLayout(null);

        menuBar = new JMenuBar();
        
        spl = new JMenu("Linear Systems");

        gauss = new JMenuItem("Gauss' Method");

        gaussjordan = new JMenuItem("Gauss-Jordan Method");

        cramer = new JMenuItem("Cramer's Method");

        inversem = new JMenuItem("Inverse Method");

        spl.add(gauss);
        spl.add(gaussjordan);
        spl.add(cramer);
        spl.add(inversem);

        determinant = new JMenu("Determinant");

        obedet = new JMenuItem("Row Operations Method");
        obedet.addActionListener(this);

        cofactor = new JMenuItem("Cofactor Method");
        cofactor.addActionListener(this);

        determinant.add(obedet);
        determinant.add(cofactor);

        inverse = new JMenu("Inverse");

        obeinv = new JMenuItem("Row Operations Method");
        obeinv.addActionListener(this);

        standard = new JMenuItem("Equation Method");
        standard.addActionListener(this);

        inverse.add(obeinv);
        inverse.add(standard);

        interpolation = new JMenu("Interpolation");

        polynomial = new JMenuItem("Polynomial Interpolation");

        bicubic = new JMenuItem("Bicubic Spline Interpolation");

        interpolation.add(polynomial);
        interpolation.add(bicubic);

        regression = new JMenu("Regression");

        linear = new JMenuItem("Linear Regression");

        quadratic = new JMenuItem("Quadratic Regression");

        regression.add(linear);
        regression.add(quadratic);

        menuBar.add(spl);
        menuBar.add(determinant);
        menuBar.add(inverse);
        menuBar.add(interpolation);
        menuBar.add(regression);

        window.setJMenuBar(menuBar);

        window.setVisible(true);
    }

    private JMenuBar menuBar;

    private JMenu spl;
    private JMenu determinant;
    private JMenu inverse;
    private JMenu interpolation;
    private JMenu regression;

    private JMenuItem gauss;
    private JMenuItem gaussjordan;
    private JMenuItem cramer;
    private JMenuItem inversem;

    private JMenuItem obedet;
    private JMenuItem cofactor;

    private JMenuItem obeinv;
    private JMenuItem standard;

    private JMenuItem polynomial;
    private JMenuItem bicubic;

    private JMenuItem linear;
    private JMenuItem quadratic;
}
