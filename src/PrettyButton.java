import java.awt.*;

import javax.swing.*;
import javax.swing.event.*;

public class PrettyButton extends JButton implements ChangeListener
{
    public PrettyButton(String text)
    {
        super(text);

        primaryColor = Color.black;
        hoverColor = new Color(255, 213, 0);

        super.setBorderPainted(false);
        super.setContentAreaFilled(false);
        super.setFont(FontManager.GetMeanwhile(12));
        super.setForeground(primaryColor);

        super.getModel().addChangeListener(this);
    }

    @Override
    public void stateChanged(ChangeEvent e)
    {
        if (getModel().isRollover())
        {
            super.setForeground(hoverColor);
        }
        else
        {
            super.setForeground(primaryColor);
        }
    }

    private Color primaryColor;
    private Color hoverColor;
}
