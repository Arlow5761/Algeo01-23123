import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public abstract class Page
{
    static public void Load(Page page)
    {
        if (LinearAlgebraCalculator.instance.currentPage != null) LinearAlgebraCalculator.instance.currentPage.Unload();
        LinearAlgebraCalculator.instance.currentPage = page;

        LinearAlgebraCalculator.instance.window.revalidate();
        LinearAlgebraCalculator.instance.window.repaint();
    }

    public void Unload()
    {
        if (LinearAlgebraCalculator.instance.currentPage != this) return;

        Timer timer = new Timer(10, new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                viewport.setViewPosition(new Point(viewport.getViewPosition().x + 20, 0));

                if (viewport.getViewPosition().x >= 700)
                {
                    ((Timer) e.getSource()).stop();

                    LinearAlgebraCalculator.instance.window.remove(viewport);
                }

                LinearAlgebraCalculator.instance.window.revalidate();
                LinearAlgebraCalculator.instance.window.repaint();
            }
        });

        TerminalBuffer.ClearBuffer();

        timer.start();
    }

    public Page()
    {
        page = new JPanel(null);
        page.setBackground(Color.white);
        page.setBounds(0, 0, 716, 987);

        pagePanel = new JPanel(null);
        pagePanel.setOpaque(false);
        pagePanel.setBounds(0, 0, 1432, 987);

        viewport = new JViewport();
        viewport.setOpaque(false);
        viewport.setLayout(null);
        viewport.setBounds(0, 0, 716, 987);

        LinearAlgebraCalculator.instance.window.add(viewport);
        viewport.setView(pagePanel);
        pagePanel.add(page);
    }

    protected JPanel page;

    private JPanel pagePanel;
    private JViewport viewport;
}
