import javax.swing.*;

public class InterpolationPolynomialPage extends Page
{
    public InterpolationPolynomialPage()
    {
        image = new JLabel(new ImageIcon("../assets/interpolation-polynomial.png"));
        image.setBounds(0, 0, 700, 925);

        page.add(image);
    }

    private JLabel image;
}
