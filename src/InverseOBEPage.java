import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import javax.swing.*;
import java.text.*;
import Algeo.*;
import Algeo.Inverse.InverseOBESolver;

public class InverseOBEPage extends Page
{
    public InverseOBEPage()
    {
        page.setLayout(null);

        title = new JLabel("Determinant Calculation with Cofactors");
        title.setBounds(0, 0, 700, 200);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(new Font("Comic Sans MS", Font.PLAIN, 32));

        page.add(title);

        sizeLabel = new JLabel("Matrix Size:");
        sizeLabel.setBounds(150, 200, 198, 50);
        sizeLabel.setHorizontalAlignment(JLabel.RIGHT);
        sizeLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));

        page.add(sizeLabel);

        sizeField = new JFormattedTextField(NumberFormat.getIntegerInstance());
        sizeField.setValue(3);
        sizeField.setBounds(352, 200, 98, 50);
        sizeField.setHorizontalAlignment(JFormattedTextField.LEFT);
        sizeField.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));

        sizeField.addPropertyChangeListener("value", new PropertyChangeListener()
        {
            @Override
            public void propertyChange(PropertyChangeEvent e)
            {
                matrixField.SetCols(((Number) sizeField.getValue()).intValue());
                matrixField.SetRows(((Number) sizeField.getValue()).intValue());

                resultMatrix.SetCols(((Number) sizeField.getValue()).intValue());
                resultMatrix.SetRows(((Number) sizeField.getValue()).intValue());

                page.revalidate();
                page.repaint();
            }
        });

        page.add(sizeField);

        matrixArea = new JPanel(new GridBagLayout());
        matrixArea.setBounds(0, 275, 700, 320);
        matrixArea.setOpaque(false);

        GridBagConstraints g = new GridBagConstraints();

        matrixField = new PrettyTable(3, 3,
        new LabelGenerator() { public String GetLabel(int index) { return Integer.toString(index); } },
        new LabelGenerator() { public String GetLabel(int index) { return Integer.toString(index); } });
        matrixField.setMaximumSize(new Dimension(320, 320));

        g.gridx = 0;
        g.gridy = 0;

        matrixArea.add(matrixField, g);

        resultMatrix = new PrettyTable(3, 3,
        new LabelGenerator() { public String GetLabel(int index) { return Integer.toString(index); } },
        new LabelGenerator() { public String GetLabel(int index) { return Integer.toString(index); } });
        resultMatrix.setMaximumSize(new Dimension(320, 320));
        resultMatrix.SetEditable(false);

        g.gridx = 2;
        g.gridy = 0;

        matrixArea.add(resultMatrix, g);

        page.add(matrixArea);

        calcButton = new JButton("Calculate");
        calcButton.setBounds(450, 800, 200, 50);
        calcButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));

        calcButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int size = ((Number) sizeField.getValue()).intValue();
                Matrix matrix = new Matrix(size, size);

                for (int i = 0; i < size; i++)
                {
                    for (int j = 0; j < size; j++)
                    {
                        double val = matrixField.GetValue(i, j);

                        if (Double.isNaN(val))
                        {
                            // Shout at user

                            return;
                        }

                        matrix.Set(i, j, val);
                    }
                }

                Matrix inverse = InverseOBESolver.Solve(matrix);
                
                for (int i = 0; i < size; i++)
                {
                    for (int j = 0; j < size; j++)
                    {
                        resultMatrix.SetValue(i, j, inverse.Get(i, j));
                    }
                }
            }
        });

        page.add(calcButton);

        importButton = new JButton("Import");
        importButton.setBounds(50, 800, 200, 50);
        importButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));

        page.add(importButton);

        page.revalidate();
        page.repaint();
    }

    private JLabel title;
    private JPanel matrixArea;
    private PrettyTable matrixField;
    private JLabel sizeLabel;
    private JFormattedTextField sizeField;
    private PrettyTable resultMatrix;
    private JButton calcButton;
    private JButton importButton;
}
