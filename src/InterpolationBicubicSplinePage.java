import java.text.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import Algeo.Matrix;
import Algeo.Interpolation.BicubicSplineInterpolator;

public class InterpolationBicubicSplinePage extends Page
{
    public InterpolationBicubicSplinePage()
    {
        image = new JLabel(new ImageIcon("../assets/interpolation-bicubicspline.png"));
        image.setBounds(0, 0, 700, 925);

        PrettyTable functionProperties = new PrettyTable(4, 4, 
        new LabelGenerator()
        {
           @Override
           public String GetLabel(int index)
           {
                switch (index)
                {
                    case 0:
                        return "(0,0)";
                    case 1:
                        return "(0,1)";
                    case 2:
                        return "(0,1)";
                    case 3:
                        return "(1,1)";
                }

                return "";
           } 
        },
        new LabelGenerator()
        {
            @Override
            public String GetLabel(int index)
            {
                 switch (index)
                 {
                     case 0:
                         return "f";
                     case 1:
                         return "fx";
                     case 2:
                         return "fy";
                     case 3:
                         return "fxy";
                 }

                 return "";
            }
        });
        functionProperties.SetCellSize(50);
        functionProperties.setBounds(150, 385, 250, 250);

        JLabel aLabel = new JLabel("A :");
        aLabel.setFont(FontManager.GetMeanwhile(14));
        aLabel.setBounds(300, 700, 30, 30);
        aLabel.setHorizontalAlignment(JLabel.RIGHT);

        JFormattedTextField aField = new JFormattedTextField(NumberFormat.getNumberInstance());
        aField.setFont(FontManager.GetMeanwhile(14));
        aField.setBounds(340, 700, 70, 30);

        JLabel bLabel = new JLabel("B :");
        bLabel.setFont(FontManager.GetMeanwhile(14));
        bLabel.setBounds(300, 750, 30, 30);
        bLabel.setHorizontalAlignment(JLabel.RIGHT);

        JFormattedTextField bField = new JFormattedTextField(NumberFormat.getNumberInstance());
        bField.setFont(FontManager.GetMeanwhile(14));
        bField.setBounds(340, 750, 70, 30);

        JLabel fLabel = new JLabel("F(a,b) :");
        fLabel.setFont(FontManager.GetMeanwhile(14));
        fLabel.setBounds(270, 800, 60, 30);
        fLabel.setHorizontalAlignment(JLabel.RIGHT);

        JFormattedTextField fField = new JFormattedTextField(NumberFormat.getNumberInstance());
        fField.setFont(FontManager.GetMeanwhile(14));
        fField.setBounds(340, 800, 70, 30);
        fField.setEditable(false);

        PrettyButton calculateButton = new PrettyButton("Calculate");
        calculateButton.setBounds(45, 725, 130, 30);

        PrettyButton importButton = new PrettyButton("Import");
        importButton.setBounds(45, 825, 130, 30);

        page.add(functionProperties);
        page.add(aLabel);
        page.add(aField);
        page.add(bLabel);
        page.add(bField);
        page.add(fLabel);
        page.add(fField);
        page.add(calculateButton);
        page.add(importButton);

        page.add(image);

        page.revalidate();
        page.repaint();

        importButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Matrix imported = MatrixIO.OpenImperfectMatrix();

                if (imported == null)
                {
                    JOptionPane.showMessageDialog(null, "Error importing matrix!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (imported.GetColumnCount() != 4 || imported.GetRowCount() != 5)
                {
                    JOptionPane.showMessageDialog(null, "Matrix size not correct!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Matrix bicubicParams = new Matrix(4, 4);

                for (int i = 0; i < 4; i++)
                {
                    bicubicParams.SetRow(i, imported.GetRow(i));
                }

                MatrixIO.ImportToTable(functionProperties, bicubicParams);
                aField.setValue(imported.Get(4, 0));
                bField.setValue(imported.Get(4, 1));

                page.revalidate();
                page.repaint();
            }
        });

        calculateButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Matrix bicubicMatrix = MatrixIO.ExtractFromTable(functionProperties);

                double a = ((Number) aField.getValue()).doubleValue();
                double b = ((Number) bField.getValue()).doubleValue();

                double res = BicubicSplineInterpolator.bicubicInterpolate(bicubicMatrix, a, b);

                fField.setValue(res);

                page.revalidate();
                page.repaint();
            }
        });
    }

    private JLabel image;
}
