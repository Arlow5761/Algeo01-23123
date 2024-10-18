import java.awt.Dimension;
import java.awt.*;

import javax.swing.*;

public class TestPage implements Page
{
    static public TestPage instance;

    static public void Load()
    {
        if (LinearAlgebraCalculator.instance.currentPage != null) return;

        LinearAlgebraCalculator.instance.currentPage = new TestPage();
    }    

    @Override
    public void Unload()
    {

    }

    public TestPage()
    {
        JFrame window = LinearAlgebraCalculator.instance.window;

        window.setLayout(new GridBagLayout());

        PrettyTable table = new PrettyTable(10, 10,
        new LabelGenerator() {
            public String GetLabel(int index) { return Integer.toString(index); }
        }, new LabelGenerator() {
            public String GetLabel(int index) { return Integer.toString(index); }
        });
        table.setMaximumSize(new Dimension(320, 320));
        table.setLocation(10, 10);

        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0;
        g.gridy = 0;
        g.gridheight = 1;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.BOTH;
        
        window.add(table, g);

        window.revalidate();
        window.repaint();
    }
}
