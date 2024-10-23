import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.JSpinner.*;
import javax.swing.event.*;

import Algeo.Matrix;
import Algeo.Determinant.DeterminantOBESolver;
import Algeo.LinearAlgebra.InverseSolver;

public class SPLInversePage extends Page
{
    public SPLInversePage()
    {
        JLabel image = new JLabel(new ImageIcon("../assets/spl-inverse.png"));
        image.setBounds(0, 0, 700, 925);

        JPanel solutionView = new JPanel();
        solutionView.setOpaque(false);
        solutionView.setLayout(new BoxLayout(solutionView, BoxLayout.Y_AXIS));

        JScrollPane solutionArea = new JScrollPane(solutionView);
        solutionArea.setBounds(200, 575, 300, 275);
        solutionArea.setOpaque(false);
        solutionArea.setBorder(BorderFactory.createEmptyBorder());
        solutionArea.getViewport().setOpaque(false);

        JLabel sizeLabel = new JLabel("Cols :");
        sizeLabel.setFont(FontManager.GetMeanwhile(12));
        sizeLabel.setHorizontalAlignment(JLabel.RIGHT);
        sizeLabel.setBounds(410, 460, 50, 30);

        JSpinner sizeField = new JSpinner(new SpinnerNumberModel(3, 1, 100, 1));
        sizeField.setBounds(465, 460, 50, 30);
        sizeField.setFont(FontManager.GetMeanwhile(12));

        NumberEditor sizeNumberEditor = new JSpinner.NumberEditor(sizeField);
        ((NumberFormatter) sizeNumberEditor.getTextField().getFormatter()).setAllowsInvalid(false);

        JPanel equationMatrixArea = new JPanel(new GridBagLayout());
        equationMatrixArea.setOpaque(false);
        equationMatrixArea.setBounds(330, 190, 180, 180);

        PrettyTable equationMatrixInput = new PrettyTable(3, 3, null, null);
        equationMatrixInput.setMaximumSize(new Dimension(180, 180));

        GridBagConstraints equationMatrixConstraints = new GridBagConstraints();
        equationMatrixConstraints.weightx = 1;
        equationMatrixConstraints.anchor = GridBagConstraints.EAST;

        JLabel parameterMatrixLabel = new JLabel("X  :");
        parameterMatrixLabel.setFont(FontManager.GetMeanwhile(16));
        parameterMatrixLabel.setHorizontalTextPosition(JLabel.CENTER);
        parameterMatrixLabel.setVerticalTextPosition(JLabel.CENTER);
        parameterMatrixLabel.setBounds(525, 190, 60, 180);

        JPanel solutionMatrixArea = new JPanel(new GridBagLayout());
        solutionMatrixArea.setOpaque(false);
        solutionMatrixArea.setBounds(565, 190, 30, 180);

        PrettyTable solutionMatrixInput = new PrettyTable(1, 3, null, null);
        solutionMatrixInput.setMaximumSize(new Dimension(30, 180));

        PrettyButton calculateButton = new PrettyButton("Calculate");
        calculateButton.setBounds(530, 805, 130, 30);

        PrettyButton importButton = new PrettyButton("Import");
        importButton.setBounds(545, 695, 100, 30);

        equationMatrixArea.add(equationMatrixInput, equationMatrixConstraints);
        solutionMatrixArea.add(solutionMatrixInput);

        page.add(solutionArea);
        page.add(equationMatrixArea);
        page.add(parameterMatrixLabel);
        page.add(solutionMatrixArea);
        page.add(sizeLabel);
        page.add(sizeField);
        page.add(importButton);
        page.add(calculateButton);

        page.add(image);

        page.revalidate();
        page.repaint();

        sizeField.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                int size = ((Number) sizeField.getValue()).intValue();

                equationMatrixInput.SetCols(size);
                equationMatrixInput.SetRows(size);

                solutionMatrixInput.SetRows(size);

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

                if (importedMatrix.GetColumnCount() != importedMatrix.GetRowCount() + 1)
                {
                    JOptionPane.showMessageDialog(null, "Error : Matrix Size Incompatible!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                sizeField.setValue(importedMatrix.GetRowCount());

                Matrix equationMatrix = new Matrix(importedMatrix.GetRowCount(), importedMatrix.GetRowCount());
                Matrix solutionMatrix = new Matrix(importedMatrix.GetRowCount(), 1);

                for (int i = 0; i < equationMatrix.GetColumnCount(); i++)
                {
                    equationMatrix.SetColumn(i, importedMatrix.GetColumn(i));
                }

                solutionMatrix.SetColumn(0, importedMatrix.GetColumn(importedMatrix.GetColumnCount() - 1));

                MatrixIO.ImportToTable(equationMatrixInput, equationMatrix);
                MatrixIO.ImportToTable(solutionMatrixInput, solutionMatrix);

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

                Matrix matrix = MatrixIO.ExtractFromTable(equationMatrixInput);

                double[] sols = MatrixIO.ExtractFromTable(solutionMatrixInput).GetColumn(0);

                if (DeterminantOBESolver.Solve(matrix) == 0)
                {
                    JLabel noSolLabel = new JLabel("No single solution exist!");
                    noSolLabel.setFont(FontManager.GetMeanwhile(14));
                    noSolLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                    solutionView.add(noSolLabel);

                    page.revalidate();
                    page.repaint();

                    return;
                }

                double[] solution = InverseSolver.Solve(matrix, sols);

                if (solution != null)
                {
                    for (int i = 0; i < solution.length; i++)
                    {
                        JLabel solLabel = new JLabel("X".concat(Integer.toString(i)).concat(" : ").concat(Double.toString(solution[i])));
                        solLabel.setFont(FontManager.GetMeanwhile(14));
                        solLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                        
                        solutionView.add(solLabel);
                    }

                    page.revalidate();
                    page.repaint();

                    return;
                }
            }
        });
    }
}
