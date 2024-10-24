import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.*;
import javax.swing.JSpinner.NumberEditor;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.NumberFormatter;

import Algeo.Matrix;
import Algeo.Regression.LinearRegressor;

public class RegressionLinearPage extends Page
{
    public RegressionLinearPage()
    {
        image = new JLabel(new ImageIcon("../assets/regression-linear.png"));
        image.setBounds(0, 0, 700, 925);

        JLabel dimensionLabel = new JLabel("Dimensions :");
        dimensionLabel.setFont(FontManager.GetMeanwhile(12));
        dimensionLabel.setBounds(345, 185, 100, 20);

        JSpinner dimensionField = new JSpinner(new SpinnerNumberModel(2, 2, 100, 1));
        dimensionField.setBounds(345, 205, 50, 20);
        dimensionField.setFont(FontManager.GetMeanwhile(12));

        NumberEditor dimensionNumberEditor = new JSpinner.NumberEditor(dimensionField);
        ((NumberFormatter) dimensionNumberEditor.getTextField().getFormatter()).setAllowsInvalid(false);

        JLabel pointsLabel = new JLabel("Points :");
        pointsLabel.setFont(FontManager.GetMeanwhile(12));
        pointsLabel.setBounds(345, 225, 100, 20);

        JSpinner pointsField = new JSpinner(new SpinnerNumberModel(2, 2, 100, 1));
        pointsField.setBounds(345, 245, 50, 20);
        pointsField.setFont(FontManager.GetMeanwhile(12));

        NumberEditor pointsNumberEditor = new JSpinner.NumberEditor(dimensionField);
        ((NumberFormatter) pointsNumberEditor.getTextField().getFormatter()).setAllowsInvalid(false);

        JPanel pointsArea = new JPanel(new GridBagLayout());
        pointsArea.setOpaque(false);
        pointsArea.setBounds(400, 185, 185, 185);

        PrettyTable pointsTable = new PrettyTable(2, 2, 
        new LabelGenerator()
        {
            @Override
            public String GetLabel(int index)
            {
                if (index == ((Number) dimensionField.getValue()).intValue() - 1)
                {
                    return "Y";
                }

                return "X".concat(Integer.toString(index));
            }
        },
        new LabelGenerator()
        {
            @Override
            public String GetLabel(int index)
            {
                return Integer.toString(index);
            }
        });
        pointsTable.setMaximumSize(new Dimension(185, 185));

        JLabel testPointLabel = new JLabel("Test Point :");
        testPointLabel.setHorizontalAlignment(JLabel.CENTER);
        testPointLabel.setBounds(270, 515, 180, 30);
        testPointLabel.setFont(FontManager.GetMeanwhile(14));

        JPanel testPointView = new JPanel(new GridBagLayout());
        testPointView.setOpaque(false);

        PrettyTable testPoint = new PrettyTable(1, 1, null, null);

        GridBagConstraints testPointConstraint = new GridBagConstraints();
        testPointConstraint.weighty = 1;
        testPointConstraint.anchor = GridBagConstraints.NORTH;

        JScrollPane testPointArea = new JScrollPane(testPointView);
        testPointArea.setBounds(270, 550, 180, 50);
        testPointArea.setBorder(BorderFactory.createEmptyBorder());
        testPointArea.setOpaque(false);
        testPointArea.getViewport().setOpaque(false);

        JPanel functionResultView = new JPanel(new GridBagLayout());
        functionResultView.setOpaque(false);

        JLabel functionResult = new JLabel("");
        functionResult.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));

        GridBagConstraints functionResultConstraint = new GridBagConstraints();
        functionResultConstraint.weighty = 1;
        functionResultConstraint.anchor = GridBagConstraints.NORTH;

        JScrollPane functionResultArea = new JScrollPane(functionResultView);
        functionResultArea.setBounds(210, 610, 300, 60);
        functionResultArea.setBorder(BorderFactory.createEmptyBorder());
        functionResultArea.setOpaque(false);
        functionResultArea.getViewport().setOpaque(false);

        JPanel testResultView = new JPanel(new GridBagLayout());
        testResultView.setOpaque(false);

        JLabel testResult = new JLabel("");
        testResult.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));

        GridBagConstraints testResultConstraint = new GridBagConstraints();
        testResultConstraint.weighty = 1;
        testResultConstraint.anchor = GridBagConstraints.NORTH;

        JScrollPane testResultArea = new JScrollPane(testResultView);
        testResultArea.setBounds(210, 680, 300, 60);
        testResultArea.setBorder(BorderFactory.createEmptyBorder());
        testResultArea.setOpaque(false);
        testResultArea.getViewport().setOpaque(false);

        PrettyButton calculateButton = new PrettyButton("Calculate");
        calculateButton.setBounds(160, 780, 130, 30);

        PrettyButton importButton = new PrettyButton("Import");
        importButton.setBounds(455, 780, 100, 30);

        pointsArea.add(pointsTable);
        testPointView.add(testPoint, testPointConstraint);
        functionResultView.add(functionResult, functionResultConstraint);
        testResultView.add(testResult, testResultConstraint);
        page.add(pointsArea);
        page.add(dimensionLabel);
        page.add(dimensionField);
        page.add(pointsLabel);
        page.add(pointsField);
        page.add(testPointLabel);
        page.add(testPointArea);
        page.add(functionResultArea);
        page.add(testResultArea);
        page.add(calculateButton);
        page.add(importButton);

        page.add(image);

        page.revalidate();
        page.repaint();

        dimensionField.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                int size = ((Number) dimensionField.getValue()).intValue();

                pointsTable.SetCols(size);
                testPoint.SetCols(size - 1);

                page.revalidate();
                page.repaint();
            }
        });

        pointsField.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                int size = ((Number) pointsField.getValue()).intValue();

                pointsTable.SetRows(size);

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

                if (imported.GetRowCount() < 3)
                {
                    JOptionPane.showMessageDialog(null, "Imported data does not have enough points!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (imported.GetColumnCount() < 2)
                {
                    JOptionPane.showMessageDialog(null, "Imported data must be at least 2 dimensional!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Matrix points = new Matrix(imported.GetRowCount() - 1, imported.GetColumnCount());

                Matrix inputPoint = new Matrix(1, imported.GetColumnCount() - 1);

                for (int i = 0; i < imported.GetRowCount() - 1; i++)
                {
                    points.SetRow(i, imported.GetRow(i));
                }

                for (int j = 0; j < imported.GetColumnCount() - 1; j++)
                {
                    inputPoint.Set(0, j, imported.Get(imported.GetRowCount() - 1, j));
                }

                dimensionField.setValue(points.GetColumnCount());
                pointsField.setValue(points.GetRowCount());

                MatrixIO.ImportToTable(pointsTable, points);
                MatrixIO.ImportToTable(testPoint, inputPoint);

                page.revalidate();
                page.repaint();
            }
        });

        calculateButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Matrix pointsMatrix = MatrixIO.ExtractFromTable(pointsTable);
                double[][] pointsData = new double[pointsMatrix.GetRowCount()][pointsMatrix.GetColumnCount()];

                for (int i = 0; i < pointsMatrix.GetRowCount(); i++)
                {
                    pointsData[i] = pointsMatrix.GetRow(i);
                }

                double[] constants = LinearRegressor.solve(pointsData);

                NumberFormat fmt = NumberFormat.getNumberInstance();
                fmt.setMaximumFractionDigits(3);

                String functionString = "f(p) = ".concat(fmt.format(constants[0]));                
                
                for (int i = 1; i < constants.length; i++)
                {
                    if (constants[i] == 0) continue;

                    if (constants[i] > 0)
                    {
                        functionString = functionString.concat(" + ");
                    }
                    else
                    {
                        functionString = functionString.concat(" - ");
                    }

                    functionString = functionString.concat(fmt.format(Math.abs(constants[i])));

                    functionString = functionString.concat("(X").concat(Integer.toString(i - 1)).concat(")");
                }

                functionResult.setText(functionString);

                Matrix constantsMatrix = new Matrix(new double[][]{constants});

                Matrix testPointMatrix = Matrix.Transpose(Matrix.Append(new Matrix(new double[][]{{1}}), MatrixIO.ExtractFromTable(testPoint)));

                Matrix testResultMatrix = Matrix.Multiply(constantsMatrix, testPointMatrix);

                double testResultValue = testResultMatrix.Get(0, 0);

                String testResultString = "f(".concat(fmt.format(testPointMatrix.Get(1, 0)));

                for (int i = 2; i < testPointMatrix.GetRowCount(); i++)
                {
                    testResultString = testResultString.concat(", ").concat(fmt.format(testPointMatrix.Get(0, i)));
                }

                testResultString = testResultString.concat(") = ").concat(fmt.format(testResultValue));
                
                testResult.setText(testResultString);

                TerminalBuffer.WriteBuffer(functionString + "\n" + testResultString);

                page.revalidate();
                page.repaint();
            }
        });
    }

    private JLabel image;
}
