import java.text.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Algeo.Matrix;
import Algeo.Interpolation.PolynomialInterpolator;

public class InterpolationPolynomialPage extends Page
{
    public InterpolationPolynomialPage()
    {
        image = new JLabel(new ImageIcon("../assets/interpolation-polynomial.png"));
        image.setBounds(0, 0, 700, 925);

        PrettyTable pointsInputTable = new PrettyTable(4, 2,
        new LabelGenerator()
        {
            @Override
            public String GetLabel(int index)
            {
                return Integer.toString(index);
            }
        },
        new LabelGenerator()
        {
            @Override
            public String GetLabel(int index)
            {
                if (index == 0) return "X";
                return "Y";
            }
        });

        JPanel pointsInputView = new JPanel(new GridBagLayout());
        pointsInputView.setOpaque(false);

        GridBagConstraints pointsTableConstraints = new GridBagConstraints();
        pointsTableConstraints.weighty = 1;
        pointsTableConstraints.anchor = GridBagConstraints.NORTH;

        JScrollPane pointsInputArea = new JScrollPane(pointsInputView);
        pointsInputArea.setBounds(150, 450, 400, 110);
        pointsInputArea.setBorder(BorderFactory.createEmptyBorder());
        pointsInputArea.setOpaque(false);
        pointsInputArea.getViewport().setOpaque(false);

        JLabel pointsCountLabel = new JLabel("Points :");
        pointsCountLabel.setFont(FontManager.GetMeanwhile(14));
        pointsCountLabel.setBounds(270, 580, 70, 30);

        JSpinner pointsCountField = new JSpinner(new SpinnerNumberModel(4, 1, 100, 1));
        pointsCountField.setBounds(360, 580, 70, 30);
        pointsCountField.setFont(FontManager.GetMeanwhile(14));

        PrettyButton calculateButton = new PrettyButton("Calculate");
        calculateButton.setBounds(45, 725, 130, 30);

        PrettyButton importButton = new PrettyButton("Import");
        importButton.setBounds(45, 825, 130, 30);

        JLabel testPointLabel = new JLabel("Test Point :");
        testPointLabel.setFont(FontManager.GetMeanwhile(14));
        testPointLabel.setBounds(280, 700, 130, 30);

        JFormattedTextField testPointField = new JFormattedTextField(NumberFormat.getNumberInstance());
        testPointField.setFont(FontManager.GetMeanwhile(14));
        testPointField.setBounds(400, 700, 50, 30);
        testPointField.setValue(0);

        JLabel functionResultLabel = new JLabel();
        functionResultLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));

        GridBagConstraints functionResultConstraints = new GridBagConstraints();
        functionResultConstraints.weighty = 1;
        functionResultConstraints.anchor = GridBagConstraints.NORTH;

        JPanel functionResultView = new JPanel(new GridBagLayout());
        functionResultView.setOpaque(false);

        JScrollPane functionResultArea = new JScrollPane(functionResultView);
        functionResultArea.setBounds(170, 750, 380, 50);
        functionResultArea.setBorder(BorderFactory.createEmptyBorder());
        functionResultArea.setOpaque(false);
        functionResultArea.getViewport().setOpaque(false);

        JLabel testPointResultLabel = new JLabel();
        testPointResultLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        testPointResultLabel.setBounds(250, 805, 220, 30);
        testPointResultLabel.setHorizontalTextPosition(JLabel.CENTER);
        testPointResultLabel.setHorizontalAlignment(JLabel.CENTER);
        
        pointsInputView.add(pointsInputTable, pointsTableConstraints);
        functionResultView.add(functionResultLabel, functionResultConstraints);
        page.add(pointsInputArea);
        page.add(pointsCountLabel);
        page.add(pointsCountField);
        page.add(testPointLabel);
        page.add(testPointField);
        page.add(functionResultArea);
        page.add(testPointResultLabel);
        page.add(calculateButton);
        page.add(importButton);

        page.add(image);

        page.revalidate();
        page.repaint();

        pointsCountField.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                pointsInputTable.SetCols(((Number) pointsCountField.getValue()).intValue());

                page.revalidate();
                page.repaint();
            }
        });

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

                if (imported.GetRowCount() < 2)
                {
                    JOptionPane.showMessageDialog(null, "Imported data does not have enough points!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (imported.GetColumnCount() != 2)
                {
                    JOptionPane.showMessageDialog(null, "Imported data must be 2 dimensional!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Matrix points = new Matrix(imported.GetRowCount() - 1, imported.GetColumnCount());

                for (int i = 0; i < points.GetRowCount(); i++)
                {
                    points.SetRow(i, imported.GetRow(i));
                }

                pointsCountField.setValue(imported.GetRowCount() - 1);

                MatrixIO.ImportToTable(pointsInputTable, Matrix.Transpose(points));
                testPointField.setValue(imported.Get(imported.GetRowCount() - 1, 0));

                page.revalidate();
                page.repaint();
            }
        });

        calculateButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Matrix pointsMatrix = Matrix.Transpose(MatrixIO.ExtractFromTable(pointsInputTable));

                double testPoint = ((Number) testPointField.getValue()).doubleValue();
                
                double[] interpolationResult = PolynomialInterpolator.interpolate(pointsMatrix, testPoint);

                if (interpolationResult == null)
                {
                    JOptionPane.showMessageDialog(null, "Unique polynomial function not found!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double result = interpolationResult[0];

                NumberFormat fmt = NumberFormat.getNumberInstance();
                fmt.setMaximumFractionDigits(3);

                String functionString = "f(x) = ".concat(fmt.format(interpolationResult[1]));

                for (int i = 2; i < interpolationResult.length; i++)
                {
                    if (interpolationResult[i] == 0) continue;

                    if (interpolationResult[i] > 0)
                    {
                        functionString = functionString.concat(" + ");
                    }
                    else
                    {
                        functionString = functionString.concat(" - ");
                    }

                    functionString = functionString.concat(fmt.format(Math.abs(interpolationResult[i]))).concat(" (x^");
                    functionString = functionString.concat(fmt.format(i - 1)).concat(")");
                }

                functionResultLabel.setText(functionString);

                String testPointResultString = "f(".concat(fmt.format(testPoint)).concat(") = ").concat(fmt.format(result));

                testPointResultLabel.setText(testPointResultString);

                page.revalidate();
                page.repaint();
            }
        });
    }

    private JLabel image;
}
