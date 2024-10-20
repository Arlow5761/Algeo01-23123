import javax.swing.*;

public class RegressionLinearPage extends Page
{
    public RegressionLinearPage()
    {
        image = new JLabel(new ImageIcon("../assets/regression-linear.png"));
        image.setBounds(0, 0, 700, 925);

        page.add(image);
    }

    private JLabel image;
}
