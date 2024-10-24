import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JSpinner.NumberEditor;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.NumberFormatter;

import java.text.*;
import Algeo.*;
import Algeo.Determinant.DeterminantOBESolver;

public class DeterminantOBEPage extends Page
{
    public DeterminantOBEPage()
    {
        page.setLayout(null);

        JLabel backImage = new JLabel(new ImageIcon("../assets/determinant-obe.png"));
        backImage.setBounds(0, 0, 700, 925);

        JPanel matrixArea = new JPanel(new GridBagLayout());
        matrixArea.setBounds(200, 375, 300, 225);
        matrixArea.setOpaque(false);

        JLabel sizeLabel = new JLabel("Size :");
        sizeLabel.setFont(FontManager.GetMeanwhile(14));
        sizeLabel.setBounds(80, 475, 50, 30);

        JSpinner sizeField = new JSpinner(new SpinnerNumberModel(3, 1, 100, 1));
        sizeField.setBounds(150, 475, 80, 30);
        sizeField.setFont(FontManager.GetMeanwhile(12));

        NumberEditor numberEditor = new JSpinner.NumberEditor(sizeField);
        ((NumberFormatter) numberEditor.getTextField().getFormatter()).setAllowsInvalid(false);

        PrettyButton importButton = new PrettyButton("Import");
        importButton.setBounds(542, 715, 100, 30);

        PrettyButton calcButton = new PrettyButton("Calculate");
        calcButton.setBounds(532, 802, 120, 30);

        JLabel answerLabel = new JLabel();
        answerLabel.setHorizontalAlignment(JLabel.CENTER);
        answerLabel.setBackground(Color.blue);
        answerLabel.setFont(FontManager.GetMeanwhile(20));
        answerLabel.setBounds(14, 730, 200, 75);

        PrettyTable matrixInput = new PrettyTable(3, 3, 
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
                return Integer.toString(index);
            }
        });
        matrixInput.setMaximumSize(new Dimension(225, 225));

        matrixArea.add(matrixInput);

        page.add(matrixArea);
        page.add(sizeLabel);
        page.add(sizeField);
        page.add(importButton);
        page.add(calcButton);
        page.add(answerLabel);

        page.add(backImage);

        page.revalidate();
        page.repaint();

        sizeField.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e) {
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

        calcButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Matrix matrix = MatrixIO.ExtractFromTable(matrixInput);

                double det = DeterminantOBESolver.Solve(matrix);

                NumberFormat printFormat = NumberFormat.getNumberInstance();
                printFormat.setMaximumFractionDigits(4);

                answerLabel.setText(printFormat.format(det));

                TerminalBuffer.WriteBuffer(Double.toString(det));

                page.revalidate();
                page.repaint();
            }
        });
    }
}
