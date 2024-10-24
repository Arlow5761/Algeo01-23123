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
        else if (e.getSource() == gauss)
        {
            currentPage.Unload();
            Page.Load(new SPLGaussPage());
        }
        else if (e.getSource() == gaussjordan)
        {
            currentPage.Unload();
            Page.Load(new SPLGaussJordanPage());
        }
        else if (e.getSource() == cramer)
        {
            currentPage.Unload();
            Page.Load(new SPLCramerPage());
        }
        else if (e.getSource() == inversem)
        {
            currentPage.Unload();
            Page.Load(new SPLInversePage());
        }
        else if (e.getSource() == polynomial)
        {
            currentPage.Unload();
            Page.Load(new InterpolationPolynomialPage());
        }
        else if (e.getSource() == quadratic)
        {
            currentPage.Unload();
            Page.Load(new RegressionQuadraticPage());
        }
        else if (e.getSource() == linear)
        {
            currentPage.Unload();
            Page.Load(new RegressionLinearPage());
        }
        else if (e.getSource() == bicubic)
        {
            currentPage.Unload();
            Page.Load(new InterpolationBicubicSplinePage());
        }
        else if (e.getSource() == resizeImage)
        {
            currentPage.Unload();
            Page.Load(new ImageResizePage());
        }
        else if (e.getSource() == save)
        {
            if (TerminalBuffer.ReadBuffer() == null)
            {
                JOptionPane.showMessageDialog(null, "There's nothing to save!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                TerminalBuffer.SaveBuffer();
            }
        }
    }

    public LinearAlgebraCalculator()
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e)
        {
            System.out.println("Look and feel not applied due to error.");
        }

        window = new JFrame("Linear Algebra Calculator");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setSize(716, 987);
        window.setLayout(null);

        menuBar = new JMenuBar();
        
        spl = new JMenu("Linear Systems");

        gauss = new JMenuItem("Gauss' Method");
        gauss.addActionListener(this);

        gaussjordan = new JMenuItem("Gauss-Jordan Method");
        gaussjordan.addActionListener(this);

        cramer = new JMenuItem("Cramer's Method");
        cramer.addActionListener(this);

        inversem = new JMenuItem("Inverse Method");
        inversem.addActionListener(this);

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
        polynomial.addActionListener(this);

        bicubic = new JMenuItem("Bicubic Spline Interpolation");
        bicubic.addActionListener(this);

        interpolation.add(polynomial);
        interpolation.add(bicubic);

        regression = new JMenu("Regression");

        linear = new JMenuItem("Linear Regression");
        linear.addActionListener(this);

        quadratic = new JMenuItem("Quadratic Regression");
        quadratic.addActionListener(this);

        regression.add(linear);
        regression.add(quadratic);

        utility = new JMenu("Utility");

        resizeImage = new JMenuItem("Resize Image");
        resizeImage.addActionListener(this);
        
        utility.add(resizeImage);

        tools = new JMenu("Tools");

        save = new JMenuItem("Save");
        save.addActionListener(this);

        tools.add(save);

        menuBar.add(spl);
        menuBar.add(determinant);
        menuBar.add(inverse);
        menuBar.add(interpolation);
        menuBar.add(regression);
        menuBar.add(utility);
        menuBar.add(tools);

        window.setJMenuBar(menuBar);

        window.setVisible(true);
    }

    private JMenuBar menuBar;

    private JMenu spl;
    private JMenu determinant;
    private JMenu inverse;
    private JMenu interpolation;
    private JMenu regression;
    private JMenu utility;
    private JMenu tools;

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

    private JMenuItem resizeImage;

    private JMenuItem save;
}
