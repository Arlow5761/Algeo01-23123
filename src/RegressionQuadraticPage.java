import javax.swing.*;

public class RegressionQuadraticPage extends Page
{
    public RegressionQuadraticPage()
    {
        image = new JLabel(new ImageIcon("../assets/regression-quadratic.png"));
        image.setBounds(0, 0, 700, 925);

        page.add(image);
    }

    private JLabel image;
}
