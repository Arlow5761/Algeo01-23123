import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import javax.swing.*;
import javax.swing.JSpinner.NumberEditor;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.NumberFormatter;

import java.text.*;
import Algeo.*;
import Algeo.Determinant.DeterminantOBESolver;
import Algeo.Inverse.InverseNormalSolver;
import Algeo.Inverse.InverseOBESolver;

public class InverseOBEPage extends Page
{
    public InverseOBEPage()
    {
        page.setLayout(null);

        JLabel backImage = new JLabel(new ImageIcon("../assets/inverse-obe.png"));
        backImage.setBounds(0, 0, 700, 925);

        JPanel matrixArea = new JPanel(new GridBagLayout());
        matrixArea.setBounds(200, 470, 300, 300);
        matrixArea.setOpaque(false);

        PrettyTable matrixInput = new PrettyTable(3, 3,
        new LabelGenerator()
        {
            @Override
            public String GetLabel(int index) {
                return Integer.toString(index);
            }
        },
        new LabelGenerator()
        {
            @Override
            public String GetLabel(int index) {
                return Integer.toString(index);
            }
        });
        matrixInput.SetCellSize(30);
        matrixInput.SetFontSize(12);
        matrixInput.setMaximumSize(matrixArea.getSize());

        JLabel sizeLabel = new JLabel("Size :");
        sizeLabel.setHorizontalAlignment(JLabel.RIGHT);
        sizeLabel.setBounds(295, 435, 50 ,30);
        sizeLabel.setFont(FontManager.GetMeanwhile(12));

        JSpinner sizeField = new JSpinner(new SpinnerNumberModel(3, 1, 100, 1));
        sizeField.setBounds(355, 435, 50, 30);
        sizeField.setFont(FontManager.GetMeanwhile(12));

        NumberEditor numberEditor = new JSpinner.NumberEditor(sizeField);
        ((NumberFormatter) numberEditor.getTextField().getFormatter()).setAllowsInvalid(false);

        PrettyButton invertButton = new PrettyButton("Invert");
        invertButton.setBounds(80, 575, 100, 30);

        PrettyButton importButton = new PrettyButton("Import");
        importButton.setBounds(530, 575, 100, 30);

        sizeField.setEditor(numberEditor);

        matrixArea.add(matrixInput);
        page.add(matrixArea);
        page.add(sizeLabel);
        page.add(sizeField);
        page.add(invertButton);
        page.add(importButton);
        
        page.add(backImage);

        page.revalidate();
        page.repaint();

        sizeField.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                int size = ((Number) sizeField.getValue()).intValue();

                matrixInput.SetCols(size);
                matrixInput.SetRows(size);

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

                if (importedMatrix.GetColumnCount() != importedMatrix.GetRowCount())
                {
                    JOptionPane.showMessageDialog(null, "Error : Matrix not square!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                sizeField.setValue(importedMatrix.GetRowCount());

                MatrixIO.ImportToTable(matrixInput, importedMatrix);

                page.revalidate();
                page.repaint();
            }
        });

        invertButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Matrix input = MatrixIO.ExtractFromTable(matrixInput);

                if (DeterminantOBESolver.Solve(input) == 0)
                {
                    JOptionPane.showMessageDialog(null, "This matrix has no inverse!", "Operation Failed", JOptionPane.ERROR_MESSAGE);
                }

                Matrix result = InverseNormalSolver.Solve(input);

                MatrixIO.ImportToTable(matrixInput, result);
            }
        });
    }
}
