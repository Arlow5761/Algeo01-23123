import java.awt.*;
import java.text.NumberFormat;

import javax.swing.*;

public class PrettyTable extends JScrollPane
{
    public int GetCols() { return nCols; }

    public int GetRows() { return nRows; }

    public int GetCellSize() { return cellSize; }

    public int GetFontSize() { return fontSize; }

    public void SetCols(int nCols)
    {
        if (nCols < 1) return;

        JFormattedTextField[][] newFields = new JFormattedTextField[nRows][nCols];

        for (int i = 0; i < nRows; i++)
        {
            for (int j = 0; (j < nCols) && (j < this.nCols); j++)
            {
                newFields[i][j] = inputFields[i][j];
            }
        }
        
        if (nCols < this.nCols)
        {
            for (int i = 0; i < nRows; i++)
            {
                for (int j = this.nCols - 1; j >= nCols; j--)
                {
                    tableArea.remove(inputFields[i][j]);
                }
            }
        }
        else
        {
            GridBagConstraints gbc = new GridBagConstraints();

            for (int i = 0; i < nRows; i++)
            {
                for (int j = this.nCols; j < nCols; j++)
                {
                    newFields[i][j] = new JFormattedTextField(NumberFormat.getNumberInstance());
                    newFields[i][j].setPreferredSize(new Dimension(cellSize, cellSize));
                    newFields[i][j].setOpaque(false);
                    newFields[i][j].setHorizontalAlignment(JFormattedTextField.CENTER);
                    newFields[i][j].setFont(FontManager.GetMeanwhile(12));
                    newFields[i][j].setEditable(enabled);
                    newFields[i][j].setValue(0);
                    gbc.gridy = i;
                    gbc.gridx = j;
                    tableArea.add(newFields[i][j], gbc);
                }
            }
        }

        inputFields = newFields;

        for (int i = 0; i < this.nCols && columnLabels != null; i++)
        {
            topLabelArea.remove(columnLabels[i]);
        }

        this.nCols = nCols;

        if (colLabelGenerator == null) return;

        columnLabels = new JLabel[nCols];

        GridBagConstraints gbc = new GridBagConstraints();

        for (int i = 0; i < nCols; i++)
        {
            columnLabels[i] = new JLabel(colLabelGenerator.GetLabel(i));
            columnLabels[i].setPreferredSize(new Dimension(cellSize, cellSize));
            columnLabels[i].setFont(FontManager.GetMeanwhile(12));
            columnLabels[i].setOpaque(false);
            columnLabels[i].setHorizontalAlignment(JLabel.CENTER);
            gbc.gridx = i;
            gbc.gridy = 0;

            topLabelArea.add(columnLabels[i], gbc);
        }
    }

    public void SetRows(int nRows)
    {
        if (nRows < 1) return;

        JFormattedTextField[][] newFields = new JFormattedTextField[nRows][nCols];

        for (int i = 0; (i < nRows) && (i < this.nRows); i++)
        {
            for (int j = 0; j < nCols; j++)
            {
                newFields[i][j] = inputFields[i][j];
            }
        }

        if (nRows < this.nRows)
        {
            for (int i = this.nRows - 1; i >= nRows; i--)
            {
                for (int j = 0; j < nCols; j++)
                {
                    tableArea.remove(inputFields[i][j]);
                }
            }
        }
        else
        {
            GridBagConstraints gbc = new GridBagConstraints();

            for (int i = this.nRows; i < nRows; i++)
            {
                for (int j = 0; j < nCols; j++)
                {
                    newFields[i][j] = new JFormattedTextField(NumberFormat.getNumberInstance());
                    newFields[i][j].setPreferredSize(new Dimension(cellSize, cellSize));
                    newFields[i][j].setOpaque(false);
                    newFields[i][j].setHorizontalAlignment(JFormattedTextField.CENTER);
                    newFields[i][j].setFont(FontManager.GetMeanwhile(12));
                    newFields[i][j].setEditable(enabled);
                    newFields[i][j].setValue(0);
                    gbc.gridy = i;
                    gbc.gridx = j;
                    tableArea.add(newFields[i][j], gbc);
                }
            }
        }

        inputFields = newFields;

        for (int i = 0; i < this.nRows && rowLabels != null; i++)
        {
            sideLabelArea.remove(rowLabels[i]);
        }

        this.nRows = nRows;

        if (rowLabelGenerator == null) return;

        rowLabels = new JLabel[nRows];

        GridBagConstraints gbc = new GridBagConstraints();

        for (int i = 0; i < nRows; i++)
        {
            rowLabels[i] = new JLabel(rowLabelGenerator.GetLabel(i));
            rowLabels[i].setPreferredSize(new Dimension(cellSize, cellSize));
            rowLabels[i].setFont(FontManager.GetMeanwhile(12));
            rowLabels[i].setOpaque(false);
            rowLabels[i].setHorizontalAlignment(JLabel.CENTER);
            gbc.gridx = 0;
            gbc.gridy = i;

            sideLabelArea.add(rowLabels[i], gbc);
        }
    }

    public void SetCellSize(int size)
    {
        cellSize = size;

        for (int i = 0; i < inputFields.length; i++)
        {
            for (int j = 0; j < inputFields[i].length; j++)
            {
                inputFields[i][j].setPreferredSize(new Dimension(cellSize, cellSize));
            }
        }

        for (int i = 0; i < columnLabels.length; i++)
        {
            columnLabels[i].setPreferredSize(new Dimension(cellSize, cellSize));
        }

        for (int j = 0; j < rowLabels.length; j++)
        {
            rowLabels[j].setPreferredSize(new Dimension(cellSize, cellSize));
        }
    }

    public void SetFontSize(int size)
    {
        fontSize = size;

        for (int i = 0; i < inputFields.length; i++)
        {
            for (int j = 0; j < inputFields[i].length; j++)
            {
                inputFields[i][j].setFont(FontManager.GetMeanwhile(fontSize));
            }
        }

        for (int i = 0; i < columnLabels.length; i++)
        {
            columnLabels[i].setFont(FontManager.GetMeanwhile(fontSize));
        }

        for (int j = 0; j < rowLabels.length; j++)
        {
            rowLabels[j].setFont(FontManager.GetMeanwhile(fontSize));
        }
    }

    public void SetLabelsVisible(boolean visible)
    {
        if (visible)
        {
            super.setRowHeaderView(null);
            super.setColumnHeaderView(null);
        }
        else
        {
            super.setRowHeaderView(rowHeader);
            super.setColumnHeaderView(columnHeader);
        }
    }

    public void SetEditable(boolean editable)
    {
        enabled = editable;

        for (int i = 0; i < inputFields.length; i++)
        {
            for (int j = 0; j < inputFields[i].length; j++)
            {
                inputFields[i][j].setEditable(editable);
            }
        }
    }

    public double GetValue(int i, int j)
    {
        Object val = inputFields[i][j].getValue();

        return val == null ? Double.NaN : ((Number) val).doubleValue();
    }

    public void SetValue(int i, int j, double val)
    {
        inputFields[i][j].setValue(val);
    }

    public PrettyTable(int nCols, int nRows, LabelGenerator colGenerator, LabelGenerator rowGenerator)
    {
        super();

        enabled = true;

        this.nCols = 0;
        this.nRows = 0;

        colLabelGenerator = colGenerator;
        rowLabelGenerator = rowGenerator;

        topLabelArea = new JPanel(new GridBagLayout());
        topLabelArea.setOpaque(false);

        sideLabelArea = new JPanel(new GridBagLayout());
        sideLabelArea.setOpaque(false);

        tableArea = new JPanel(new GridBagLayout());
        tableArea.setOpaque(false);

        this.setViewportView(tableArea);
        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        this.setColumnHeaderView(topLabelArea);
        this.setRowHeaderView(sideLabelArea);

        this.setOpaque(false);
        this.getRowHeader().setOpaque(false);
        this.getColumnHeader().setOpaque(false);
        this.getViewport().setOpaque(false);

        this.setBorder(BorderFactory.createEmptyBorder());

        SetCols(nCols);
        SetRows(nRows);
    }

    private int nCols;
    private int nRows;

    private JPanel topLabelArea;
    private JPanel sideLabelArea;
    private JPanel tableArea;

    private JLabel[] columnLabels;
    private JLabel[] rowLabels;

    private LabelGenerator colLabelGenerator;
    private LabelGenerator rowLabelGenerator;

    private JFormattedTextField[][] inputFields;

    private boolean enabled;

    private int cellSize;
    private int fontSize;

    @Override
    public Dimension getPreferredSize()
    {
        Dimension preferredSize = super.getPreferredSize();

        int width = Math.min(preferredSize.width, getMaximumSize().width);
        int height = Math.min(preferredSize.height, getMaximumSize().height);

        return new Dimension(width, height);
    }
}
