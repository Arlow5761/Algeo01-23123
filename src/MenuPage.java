import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MenuPage extends Page implements ActionListener
{
    public MenuPage()
    {
        page.setLayout(null);

        JLabel title = new JLabel("Table of Contents", JLabel.CENTER);
        title.setFont(FontManager.GetMeanwhile(48));
        title.setBounds(0, 100, 700, 50);

        Font superFont = FontManager.GetMeanwhile(24);
        Font font = FontManager.GetMeanwhile(18);

        JPanel area = new JPanel();
        area.setOpaque(false);
        area.setLayout(new BoxLayout(area, BoxLayout.Y_AXIS));
        area.setBounds(100, 180, 500, 500);

        JLabel spl = new JLabel("Linear Systems of Equations");
        spl.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        spl.setFont(superFont);

        JLabel det = new JLabel("Determinants of Matrixes");
        det.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        det.setFont(superFont);

        JLabel inv = new JLabel("Inverse of Matrixes");
        inv.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        inv.setFont(superFont);

        JLabel itp = new JLabel("Interpolation of Points");
        itp.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        itp.setFont(superFont);

        JLabel reg = new JLabel("Regression of Points");
        reg.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        reg.setFont(superFont);

        JLabel uti = new JLabel("Practical Uses");
        uti.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        uti.setFont(superFont);

        gauss = new PrettyButton("Gaussian Elimination");
        gauss.setFont(font);
        gauss.setBorderPainted(false);
        gauss.setContentAreaFilled(false);
        gauss.addActionListener(this);
        gauss.setAlignmentX(JButton.CENTER_ALIGNMENT);

        gaussjordan = new PrettyButton("Gauss-Jordan Elimination");
        gaussjordan.setFont(font);
        gaussjordan.setBorderPainted(false);
        gaussjordan.setContentAreaFilled(false);
        gaussjordan.addActionListener(this);
        gaussjordan.setAlignmentX(JButton.CENTER_ALIGNMENT);

        cramer = new PrettyButton("Cramer Method");
        cramer.setFont(font);
        cramer.setBorderPainted(false);
        cramer.setContentAreaFilled(false);
        cramer.addActionListener(this);
        cramer.setAlignmentX(JButton.CENTER_ALIGNMENT);

        inversem = new PrettyButton("Inverse Method");
        inversem.setFont(font);
        inversem.setBorderPainted(false);
        inversem.setContentAreaFilled(false);
        inversem.addActionListener(this);
        inversem.setAlignmentX(JButton.CENTER_ALIGNMENT);

        obedet = new PrettyButton("Row Operations");
        obedet.setFont(font);
        obedet.setBorderPainted(false);
        obedet.setContentAreaFilled(false);
        obedet.addActionListener(this);
        obedet.setAlignmentX(JButton.CENTER_ALIGNMENT);

        cofactor = new PrettyButton("Cofactor");
        cofactor.setFont(font);
        cofactor.setBorderPainted(false);
        cofactor.setContentAreaFilled(false);
        cofactor.addActionListener(this);
        cofactor.setAlignmentX(JButton.CENTER_ALIGNMENT);

        obeinv = new PrettyButton("Row Operations");
        obeinv.setFont(font);
        obeinv.setBorderPainted(false);
        obeinv.setContentAreaFilled(false);
        obeinv.addActionListener(this);
        obeinv.setAlignmentX(JButton.CENTER_ALIGNMENT);

        standard = new PrettyButton("Standard Equation");
        standard.setFont(font);
        standard.setBorderPainted(false);
        standard.setContentAreaFilled(false);
        standard.addActionListener(this);
        standard.setAlignmentX(JButton.CENTER_ALIGNMENT);

        polynomial = new PrettyButton("Polynomial");
        polynomial.setFont(font);
        polynomial.setBorderPainted(false);
        polynomial.setContentAreaFilled(false);
        polynomial.addActionListener(this);
        polynomial.setAlignmentX(JButton.CENTER_ALIGNMENT);

        bicubic = new PrettyButton("Bicubic Spline");
        bicubic.setFont(font);
        bicubic.setBorderPainted(false);
        bicubic.setContentAreaFilled(false);
        bicubic.addActionListener(this);
        bicubic.setAlignmentX(JButton.CENTER_ALIGNMENT);

        linear = new PrettyButton("Multiple Linear");
        linear.setFont(font);
        linear.setBorderPainted(false);
        linear.setContentAreaFilled(false);
        linear.addActionListener(this);
        linear.setAlignmentX(JButton.CENTER_ALIGNMENT);

        quadratic = new PrettyButton("Multiple Quadratic");
        quadratic.setFont(font);
        quadratic.setBorderPainted(false);
        quadratic.setContentAreaFilled(false);
        quadratic.addActionListener(this);
        quadratic.setAlignmentX(JButton.CENTER_ALIGNMENT);

        imageresize = new PrettyButton("Resize Images");
        imageresize.setFont(font);
        imageresize.setBorderPainted(false);
        imageresize.setContentAreaFilled(false);
        imageresize.addActionListener(this);
        imageresize.setAlignmentX(JButton.CENTER_ALIGNMENT);

        woawgirl = new JLabel(new ImageIcon(new ImageIcon("../assets/woaw.png").getImage().getScaledInstance(150, 315, Image.SCALE_SMOOTH)));
        woawgirl.setBounds(500, 600, 150, 300);

        area.add(spl);

        area.add(gauss);
        area.add(gaussjordan);
        area.add(cofactor);
        area.add(inversem);

        area.add(det);

        area.add(obedet);
        area.add(cofactor);

        area.add(inv);

        area.add(obeinv);
        area.add(standard);

        area.add(itp);

        area.add(polynomial);
        area.add(bicubic);

        area.add(reg);

        area.add(linear);
        area.add(quadratic);

        area.add(uti);

        area.add(imageresize);

        page.add(title);
        page.add(area);
        page.add(woawgirl);

        page.revalidate();
        page.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == cofactor)
        {
            LinearAlgebraCalculator.instance.currentPage.Unload();
            Page.Load(new DeterminantCofactorPage());
        }
        else if (e.getSource() == obedet)
        {
            LinearAlgebraCalculator.instance.currentPage.Unload();
            Page.Load(new DeterminantOBEPage());
        }
        else if (e.getSource() == obeinv)
        {
            LinearAlgebraCalculator.instance.currentPage.Unload();
            Page.Load(new InverseOBEPage());
        }
        else if (e.getSource() == standard)
        {
            LinearAlgebraCalculator.instance.currentPage.Unload();
            Page.Load(new InverseNormalPage());
        }
        else if (e.getSource() == gauss)
        {
            LinearAlgebraCalculator.instance.currentPage.Unload();
            Page.Load(new SPLGaussPage());
        }
        else if (e.getSource() == gaussjordan)
        {
            LinearAlgebraCalculator.instance.currentPage.Unload();
            Page.Load(new SPLGaussJordanPage());
        }
        else if (e.getSource() == cramer)
        {
            LinearAlgebraCalculator.instance.currentPage.Unload();
            Page.Load(new SPLCramerPage());
        }
        else if (e.getSource() == inversem)
        {
            LinearAlgebraCalculator.instance.currentPage.Unload();
            Page.Load(new SPLInversePage());
        }
        else if (e.getSource() == polynomial)
        {
            LinearAlgebraCalculator.instance.currentPage.Unload();
            Page.Load(new InterpolationPolynomialPage());
        }
        else if (e.getSource() == quadratic)
        {
            LinearAlgebraCalculator.instance.currentPage.Unload();
            Page.Load(new RegressionQuadraticPage());
        }
        else if (e.getSource() == linear)
        {
            LinearAlgebraCalculator.instance.currentPage.Unload();
            Page.Load(new RegressionLinearPage());
        }
        else if (e.getSource() == bicubic)
        {
            LinearAlgebraCalculator.instance.currentPage.Unload();
            Page.Load(new InterpolationBicubicSplinePage());
        }
        else if (e.getSource() == imageresize)
        {
            LinearAlgebraCalculator.instance.currentPage.Unload();
            Page.Load(new ImageResizerPage());
        }
    }

    private JLabel woawgirl;

    private PrettyButton gauss;
    private PrettyButton gaussjordan;
    private PrettyButton cramer;
    private PrettyButton inversem;

    private PrettyButton obedet;
    private PrettyButton cofactor;

    private PrettyButton obeinv;
    private PrettyButton standard;

    private PrettyButton polynomial;
    private PrettyButton bicubic;

    private PrettyButton linear;
    private PrettyButton quadratic;

    private PrettyButton imageresize;
}
