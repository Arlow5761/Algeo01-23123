import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import javax.swing.*;
import java.text.*;
import Algeo.*;
import Algeo.Determinant.DeterminantCofactorSolver;

public class DeterminantCofactorPage extends Page
{
    public DeterminantCofactorPage()
    {
        super();

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

                page.revalidate();
                page.repaint();
            }
        });

        page.add(sizeField);

        matrixArea = new JPanel(new GridBagLayout());
        matrixArea.setBounds(0, 275, 700, 320);
        matrixArea.setOpaque(false);

        matrixField = new PrettyTable(3, 3,
        new LabelGenerator() { public String GetLabel(int index) { return Integer.toString(index); } },
        new LabelGenerator() { public String GetLabel(int index) { return Integer.toString(index); } });
        matrixField.setMaximumSize(new Dimension(320, 320));

        matrixArea.add(matrixField);

        page.add(matrixArea);

        answerLabel = new JLabel("Determinant:");
        answerLabel.setBounds(150, 650, 198, 50);
        answerLabel.setHorizontalAlignment(JLabel.RIGHT);
        answerLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));

        page.add(answerLabel);

        answer = new JLabel("");
        answer.setBounds(352, 650, 198, 50);
        answer.setHorizontalAlignment(JLabel.LEFT);
        answer.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));

        page.add(answer);

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

                double determinant = DeterminantCofactorSolver.determinant(matrix);
                
                answer.setText(NumberFormat.getNumberInstance().format(determinant));
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
    private JLabel answerLabel;
    private JLabel answer;
    private JButton calcButton;
    private JButton importButton;
}
