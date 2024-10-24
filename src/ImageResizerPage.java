import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.NumberFormat;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import Algeo.Interpolation.BicubicSplineInterpolator;

public class ImageResizerPage extends Page
{
    public ImageResizerPage()
    {
        JLabel image = new JLabel(new ImageIcon("../assets/imageresizing.png"));
        image.setBounds(0, 0, 700, 925);

        JPanel placeholder = new JPanel(new GridBagLayout());
        placeholder.setBounds(225, 200, 250, 250);

        ImageIcon imagePreview = new ImageIcon();

        JLabel imageContainer = new JLabel(imagePreview);
        
        JLabel widthLabel = new JLabel("Width :");
        widthLabel.setFont(FontManager.GetMeanwhile(14));
        widthLabel.setBounds(270, 460, 70, 30);
        widthLabel.setHorizontalAlignment(JLabel.RIGHT);

        JFormattedTextField widthField = new JFormattedTextField(NumberFormat.getIntegerInstance());
        widthField.setFont(FontManager.GetMeanwhile(14));
        widthField.setBounds(350, 460, 70, 30);

        JLabel heightLabel = new JLabel("Height :");
        heightLabel.setFont(FontManager.GetMeanwhile(14));
        heightLabel.setBounds(270, 500, 70, 30);
        heightLabel.setHorizontalAlignment(JLabel.RIGHT);

        JFormattedTextField heightField = new JFormattedTextField(NumberFormat.getIntegerInstance());
        heightField.setFont(FontManager.GetMeanwhile(14));
        heightField.setBounds(350, 500, 70, 30);

        PrettyButton saveButton = new PrettyButton("Save");
        saveButton.setBounds(535, 395, 130, 30);

        PrettyButton openButton = new PrettyButton("Open");
        openButton.setBounds(45, 395, 130, 30);

        placeholder.add(imageContainer);

        page.add(placeholder);
        page.add(widthLabel);
        page.add(widthField);
        page.add(heightLabel);
        page.add(heightField);
        page.add(saveButton);
        page.add(openButton);

        page.add(image);

        page.revalidate();
        page.repaint();

        openButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes()));

                int status = fileChooser.showOpenDialog(null);

                if (status != JFileChooser.APPROVE_OPTION) return;

                try
                {
                    File file = fileChooser.getSelectedFile();

                    selectedImage = ImageIO.read(file);

                    float scaler = Math.max(selectedImage.getWidth(), selectedImage.getHeight());

                    imagePreview.setImage(selectedImage.getScaledInstance((int) (selectedImage.getWidth() / scaler * 250), (int) (selectedImage.getHeight() / scaler * 250), Image.SCALE_SMOOTH));
                    imageContainer.revalidate();
                    imageContainer.repaint();

                    widthField.setValue(selectedImage.getWidth());
                    heightField.setValue(selectedImage.getHeight());

                    page.revalidate();
                    page.repaint();
                }
                catch (Exception err)
                {
                    JOptionPane.showMessageDialog(null, "An error occured when trying to open the file.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        });

        saveButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter("PNG files", "png"));

                int status = fileChooser.showSaveDialog(null);

                if (status != JFileChooser.APPROVE_OPTION) return;

                JOptionPane pane = new JOptionPane("Resizing image...");
                JDialog dialog = pane.createDialog(null, "Loading");
                dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                dialog.setVisible(true);

                try
                {
                    File file = fileChooser.getSelectedFile();

                    BufferedImage inputImage = selectedImage;

                    int originalWidth = inputImage.getWidth();
                    int originalHeight = inputImage.getHeight();

                    int newWidth = ((Number) widthField.getValue()).intValue();
                    int newHeight = ((Number) heightField.getValue()).intValue();

                    double scaleWidth = (double) newWidth / (double) originalWidth;
                    double scaleHeight = (double) newHeight / (double) originalHeight;

                    BufferedImage outputImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);

                    // Read pixel data
                    double[][][] pixelData = new double[originalHeight][originalWidth][3]; // RGB image
                    for (int y = 0; y < originalHeight; y++) {
                        for (int x = 0; x < originalWidth; x++) {
                            int rgb = inputImage.getRGB(x, y);
                            int r = (rgb >> 16) & 0xFF;
                            int g = (rgb >> 8) & 0xFF;
                            int b = rgb & 0xFF;
                            pixelData[y][x][0] = r;
                            pixelData[y][x][1] = g;
                            pixelData[y][x][2] = b;
                        }
                    }

                    // Resize
                    for (int y = 0; y < newHeight; y++) {
                        double srcY = y / scaleHeight;
                        int y0 = (int) Math.floor(srcY);
                        double dy = srcY - y0;

                        for (int x = 0; x < newWidth; x++) {
                            double srcX = x / scaleWidth;
                            int x0 = (int) Math.floor(srcX);
                            double dx = srcX - x0;

                            //bicubic interpolate rgb
                            int[] rgb = BicubicSplineInterpolator.bicubicInterpolateRGB(pixelData, x0, y0, dx, dy);
                            int r = rgb[0];
                            int g = rgb[1];
                            int b = rgb[2];
                            int rgbValue = (r << 16) | (g << 8) | b;
                            outputImage.setRGB(x, y, rgbValue);
                        }
                    }

                    // Save
                    ImageIO.write(outputImage, "png", file);
                }
                catch (Exception err)
                {
                    dialog.dispose();
                    JOptionPane.showMessageDialog(null, "Resize Failed!", "Error", JOptionPane.ERROR_MESSAGE);
                }

                dialog.dispose();
            }
        });
    }

    private BufferedImage selectedImage;
}
