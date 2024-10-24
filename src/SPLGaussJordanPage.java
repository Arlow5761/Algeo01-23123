import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSpinner.NumberEditor;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.NumberFormatter;

import Algeo.Matrix;
import Algeo.LinearAlgebra.GaussJordanSolver;
import Algeo.LinearAlgebra.GaussSolver;

public class SPLGaussJordanPage extends Page
{
    public SPLGaussJordanPage()
    {
        JLabel image = new JLabel(new ImageIcon("../assets/spl-gaussjordan.png"));
        image.setBounds(0, 0, 700, 925);

        JPanel solutionView = new JPanel();
        solutionView.setOpaque(false);
        solutionView.setLayout(new BoxLayout(solutionView, BoxLayout.Y_AXIS));

        JScrollPane solutionArea = new JScrollPane(solutionView);
        solutionArea.setBounds(200, 575, 300, 275);
        solutionArea.setOpaque(false);
        solutionArea.setBorder(BorderFactory.createEmptyBorder());
        solutionArea.getViewport().setOpaque(false);

        JLabel colSizeLabel = new JLabel("Cols :");
        colSizeLabel.setFont(FontManager.GetMeanwhile(12));
        colSizeLabel.setHorizontalAlignment(JLabel.RIGHT);
        colSizeLabel.setBounds(360, 460, 50, 30);

        JLabel rowSizeLabel = new JLabel("Rows :");
        rowSizeLabel.setFont(FontManager.GetMeanwhile(12));
        rowSizeLabel.setHorizontalAlignment(JLabel.RIGHT);
        rowSizeLabel.setBounds(480, 460, 50, 30);

        JSpinner colSizeField = new JSpinner(new SpinnerNumberModel(4, 1, 100, 1));
        colSizeField.setBounds(415, 460, 50, 30);
        colSizeField.setFont(FontManager.GetMeanwhile(12));

        NumberEditor colNumberEditor = new JSpinner.NumberEditor(colSizeField);
        ((NumberFormatter) colNumberEditor.getTextField().getFormatter()).setAllowsInvalid(false);

        JSpinner rowSizeField = new JSpinner(new SpinnerNumberModel(3, 1, 100, 1));
        rowSizeField.setBounds(535, 460, 50, 30);
        rowSizeField.setFont(FontManager.GetMeanwhile(12));

        NumberEditor rowNumberEditor = new JSpinner.NumberEditor(rowSizeField);
        ((NumberFormatter) rowNumberEditor.getTextField().getFormatter()).setAllowsInvalid(false);

        JPanel matrixArea = new JPanel(new GridBagLayout());
        matrixArea.setOpaque(false);
        matrixArea.setBounds(330, 150, 300, 300);

        PrettyTable matrixInput = new PrettyTable(4, 3, 
        new LabelGenerator()
        {
            @Override
            public String GetLabel(int index)
            {
                if (index == ((Number) colSizeField.getValue()).intValue() - 1)
                {
                    return "Y";
                }

                return "X".concat(Integer.toString(index));
            }
        },
        new LabelGenerator()
        {
            @Override
            public String GetLabel(int index) {
                return Integer.toString(index);
            } 
        });
        matrixInput.setMaximumSize(new Dimension(300, 300));

        PrettyButton calculateButton = new PrettyButton("Calculate");
        calculateButton.setBounds(530, 805, 130, 30);

        PrettyButton importButton = new PrettyButton("Import");
        importButton.setBounds(545, 695, 100, 30);

        matrixArea.add(matrixInput);
        page.add(solutionArea);
        page.add(matrixArea);
        page.add(rowSizeLabel);
        page.add(colSizeLabel);
        page.add(rowSizeField);
        page.add(colSizeField);
        page.add(importButton);
        page.add(calculateButton);

        page.add(image);

        page.revalidate();
        page.repaint();

        colSizeField.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                matrixInput.SetCols(((Number) colSizeField.getValue()).intValue());

                page.revalidate();
                page.repaint();
            }
        });

        rowSizeField.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                matrixInput.SetRows(((Number) rowSizeField.getValue()).intValue());

                page.revalidate();
                page.repaint();
            }
        });

        importButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Matrix importedMatrix = MatrixIO.OpenMatrix();

                if (importedMatrix == null)
                {
                    JOptionPane.showMessageDialog(null, "Error : Import Failed!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                colSizeField.setValue(importedMatrix.GetColumnCount());
                rowSizeField.setValue(importedMatrix.GetRowCount());

                MatrixIO.ImportToTable(matrixInput, importedMatrix);

                page.revalidate();
                page.repaint();
            }
        });

        calculateButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                solutionView.removeAll();

                TerminalBuffer.WriteBuffer("");

                Matrix input = MatrixIO.ExtractFromTable(matrixInput);

                GaussJordanSolver solver = new GaussJordanSolver(input).Solve();

                MatrixIO.ImportToTable(matrixInput, solver.GetSolution());

                double[] singleSolution = solver.GetSingleSolution();

                if (singleSolution != null)
                {
                    for (int i = 0; i < singleSolution.length; i++)
                    {
                        JLabel solLabel = new JLabel("X".concat(Integer.toString(i)).concat(" = ").concat(Double.toString(singleSolution[i])));
                        solLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
                        solLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                        
                        solutionView.add(solLabel);

                        TerminalBuffer.WriteBuffer(TerminalBuffer.ReadBuffer().concat(solLabel.getText()).concat("\n"));
                    }

                    page.revalidate();
                    page.repaint();

                    return;
                }

                double[][] multiSolution = solver.GetMultipleSolution();

                if (multiSolution != null)
                {
                    for (int i = 0; i < multiSolution.length; i++)
                    {
                        JLabel solLabel = new JLabel("X".concat(Integer.toString(i)).concat(" = "));
                        solLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
                        solLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                        if (multiSolution[i] == null)
                        {
                            solLabel.setText(solLabel.getText().concat("t").concat(Integer.toString(i)));
                        }
                        else
                        {
                            NumberFormat formatter = NumberFormat.getNumberInstance();
                            formatter.setMaximumFractionDigits(3);
                        
                            boolean first = true;

                            for (int j = 0; j < multiSolution[i].length - 1; j++)
                            {
                                if (multiSolution[i][j] == 0) continue;

                                if (multiSolution[i][j] < 0)
                                {
                                    double val = Math.abs(multiSolution[i][j]);
                                    String out = formatter.format(val);
                                    solLabel.setText(solLabel.getText().concat(" - ").concat(out));
                                }
                                else if (first)
                                {
                                    double val = Math.abs(multiSolution[i][j]);
                                    String out = formatter.format(val);
                                    solLabel.setText(solLabel.getText().concat(out));
                                }
                                else
                                {
                                    double val = Math.abs(multiSolution[i][j]);
                                    String out = formatter.format(val);
                                    solLabel.setText(solLabel.getText().concat(" + ").concat(out));
                                }

                                solLabel.setText(solLabel.getText().concat("(t").concat(Integer.toString(j)).concat(")"));
                            }

                            if (multiSolution[i][multiSolution.length - 1] < 0)
                            {
                                double val = Math.abs(multiSolution[i][multiSolution.length - 1]);
                                String out = formatter.format(val);
                                solLabel.setText(solLabel.getText().concat(" - ").concat(out));
                            }
                            else if (multiSolution[i][multiSolution.length - 1] > 0)
                            {
                                double val = Math.abs(multiSolution[i][multiSolution.length - 1]);
                                String out = formatter.format(val);
                                solLabel.setText(solLabel.getText().concat(" + ").concat(out));
                            }
                        }
                        
                        solutionView.add(solLabel);

                        TerminalBuffer.WriteBuffer(TerminalBuffer.ReadBuffer().concat(solLabel.getText()).concat("\n"));
                    }

                    page.revalidate();
                    page.repaint();

                    return;
                }

                JLabel noSolLabel = new JLabel("No solutions exist!");
                noSolLabel.setFont(FontManager.GetMeanwhile(14));
                noSolLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                solutionView.add(noSolLabel);

                TerminalBuffer.WriteBuffer(TerminalBuffer.ReadBuffer().concat(noSolLabel.getText()).concat("\n"));

                page.revalidate();
                page.repaint();
            }
        });
    }    
}
