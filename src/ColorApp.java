import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Robot;
import java.awt.AWTException;

import static java.awt.Font.*;

public class ColorApp extends JFrame implements ChangeListener {

    private JPanel contentPane;
    File targetFile, currentfile;
    BufferedImage targetImg, currentImg;
    public JPanel panel, panel2, panel_1, panel_2;
    private boolean hasfile;
    private static final int baseSize = 300;
    private JColorChooser jcc;
    private static final String basePath =
            "C:\\Documents and Settings\\Administrator\\Desktop\\Images";
    private JSlider redsl;
    private JSlider greensl;
    private JSlider bluesl;
    private JLabel redlb;
    private JLabel greenlb;
    private JLabel bluelb;
    private JLabel pos;
    final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ColorApp frame = new ColorApp();
                    frame.setTitle("Color App");
                    frame.setUndecorated(true);
                    frame.pack();
                    frame.setExtendedState(Frame.MAXIMIZED_BOTH);
                    frame.setVisible(true);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public ColorApp() {
        GridBagConstraints c = new GridBagConstraints();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        setContentPane(panel);
        panel.setLayout(new GridBagLayout());
        Color primary = new Color(250, 229, 223);
        Color secondary = new Color(237, 120, 102);
        Color third = new Color(245, 202, 194);
        Color fourth = new Color(48, 49, 121);
        Color fifth = new Color(20, 24, 80);

        panel.setBackground(primary);
        if (shouldFill) {
            c.fill = GridBagConstraints.BOTH;
        }

        // Gridbag Modifer
        c.fill = GridBagConstraints.BOTH;
        c.ipady = 0;       //reset to default
        c.weightx = 0;
        c.weighty = 0;   //request any extra vertical space
        c.insets = new Insets(0, 0, 0, 0);  //top padding
        c.ipadx = 600;
        c.ipady = 50;
        c.anchor = GridBagConstraints.LINE_START;

        // LEFT
        c.anchor = GridBagConstraints.LINE_START;
//        c.ipadx = 300;
//        c.ipady = 88;
        c.gridwidth = 1;
        c.gridheight = 5;

        // Button Browse
        JButton btnBrowse = new JButton("BROWSE");
        btnBrowse.setBackground(third);
        btnBrowse.setOpaque(true);
        btnBrowse.setBorderPainted(false);
        btnBrowse.setForeground(fourth);
        btnBrowse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnBrowse.setBackground(secondary);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnBrowse.setBackground(third);
            }
        });
        btnBrowse.addActionListener(new AbstractAction("browse") {
            public void actionPerformed(ActionEvent e) {
                browseButtonActionPerformed(e);
            }
        });
        c.gridx = 0;
        c.gridy = 0;
        panel.add(btnBrowse, c);

        // ColorChooser
        JButton btnjcc = new JButton("Advanced Color Options");
        btnjcc.setBackground(third);
        btnjcc.setOpaque(true);
        btnjcc.setBorderPainted(false);
        btnjcc.setForeground(fourth);
        btnjcc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnjcc.setBackground(secondary);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnjcc.setBackground(third);
            }
        });
        btnjcc.addActionListener(new AbstractAction("jcc") {
            public void actionPerformed(ActionEvent evt) {
                jccButtonActionPerformed(evt);
            }
        });
        c.gridx = 0;
        c.gridy = 5;
        panel.add(btnjcc, c);

        // Color Clicker
        MouseAdapter ma = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                mouseCkd(e);
            }
        };
        final int[] num = {0};
        num[0] = 0;
        JButton colclk = new JButton("Getting Color");
        colclk.setBackground(third);
        colclk.setOpaque(true);
        colclk.setForeground(fourth);
        colclk.setBorderPainted(false);
        colclk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                colclk.setBackground(secondary);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                colclk.setBackground(third);
            }
        });
        colclk.addActionListener(new AbstractAction("Get Pixel Color") {
            public void actionPerformed(ActionEvent e) {
                colclkButtonActionPerformed(e, num[0], ma);
                num[0] = num[0] + 1;
            }
        });
        c.gridx = 0;
        c.gridy = 10;
        panel.add(colclk, c);

        // Image Better Button
        JButton imgedit = new JButton("Filter Image");
        imgedit.setBackground(third);
        imgedit.setOpaque(true);
        imgedit.setBorderPainted(false);
        imgedit.setForeground(fourth);
        imgedit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                imgedit.setBackground(secondary);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                imgedit.setBackground(third);
            }
        });
        imgedit.addActionListener(new AbstractAction("Fiter Image") {
            public void actionPerformed(ActionEvent e) {
//                System.out.println("Filter Function Running");
                imgeditButtonActionPerformed(e, currentImg);
            }
        });
        c.gridx = 0;
        c.gridy = 15;
        panel.add(imgedit, c);

        // Image normal Button
        JButton imgenormal = new JButton("Return Normal");
        imgenormal.setBackground(third);
        imgenormal.setForeground(fourth);
        imgenormal.setOpaque(true);
        imgenormal.setBorderPainted(false);
        imgenormal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                imgenormal.setBackground(secondary);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                imgenormal.setBackground(third);
            }
        });
        imgenormal.addActionListener(new AbstractAction("Return Normal") {
            public void actionPerformed(ActionEvent evt) {
//                System.out.println("Normal Function Running");
                imgenmButtonActionPerformed(evt);
            }
        });
        c.gridx = 0;
        c.gridy = 20;
        panel.add(imgenormal, c);

        // Image better Button
        JButton imgebt = new JButton("Intensify Image");
        imgebt.setBackground(third);
        imgebt.setForeground(fourth);
        imgebt.setOpaque(true);
        imgebt.setBorderPainted(false);
        imgebt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                imgebt.setBackground(secondary);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                imgebt.setBackground(third);
            }
        });
        imgebt.addActionListener(new AbstractAction("Intensify Image") {
            public void actionPerformed(ActionEvent evt) {
//                System.out.println("Intensify Function Running");
                imgebtButtonActionPerformed(evt, currentImg);
            }
        });
        c.gridx = 0;
        c.gridy = 25;
        panel.add(imgebt, c);

        JButton generate_image = new JButton("Generate Image");
        generate_image.setBackground(third);
        generate_image.setForeground(fourth);
        generate_image.setOpaque(true);
        generate_image.setBorderPainted(false);
        generate_image.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                generate_image.setBackground(secondary);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                generate_image.setBackground(third);
            }
        });
        generate_image.addActionListener(new AbstractAction("Generate Image") {
            public void actionPerformed(ActionEvent evt) {
                imgmkButtonActionPerformed(evt, currentImg);
            }
        });
        c.gridx = 0;
        c.gridy = 30;
        panel.add(generate_image, c);

        JButton getColAvg = new JButton("Get Average Color");
        getColAvg.setBackground(third);
        getColAvg.setForeground(fourth);
        getColAvg.setOpaque(true);
        getColAvg.setBorderPainted(false);
        getColAvg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                getColAvg.setBackground(secondary);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                getColAvg.setBackground(third);
            }
        });
        getColAvg.addActionListener(new AbstractAction("getColAvg") {
            public void actionPerformed(ActionEvent evt) {
                getColAvgButtonActionPerformed(evt, currentImg);
            }
        });
        c.gridx = 0;
        c.gridy = 35;
        panel.add(getColAvg, c);

        // CENTER
        c.anchor = GridBagConstraints.CENTER;
        // Red Slider
        redsl = new JSlider(0, 255, 128);
        redsl.setBackground(primary);
        redsl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JOptionPane.showMessageDialog(redsl, redsl.getValue(), "Red Value" , JOptionPane.PLAIN_MESSAGE);
            }
        });

        // Red Slider
        redsl.setOpaque(true);
        redsl.addChangeListener(this);
        redsl.setToolTipText("Red Value: " + String.valueOf(redsl.getValue()));
        c.gridx = 1;
        c.gridy = 0;
        c.gridheight = 5;
        panel.add(redsl, c);

        // Green Slider
        greensl = new JSlider(0, 255, 128);
        greensl.setBackground(primary);
        greensl.setOpaque(true);
        greensl.addChangeListener(this);
        greensl.setToolTipText("Green Value: " + String.valueOf(greensl.getValue()));
        greensl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JOptionPane.showMessageDialog(greensl, greensl.getValue(), "Green Value" , JOptionPane.PLAIN_MESSAGE);
            }
        });
        c.gridx = 1;
        c.gridy = 5;
        c.gridheight = 5;
        panel.add(greensl, c);

        // Blue Slider
        bluesl = new JSlider(0, 255, 128);
        bluesl.setBackground(primary);
        bluesl.setOpaque(true);
        bluesl.addChangeListener(this);
        bluesl.setFont(new Font("Times New Roman", ITALIC, 10));
        bluesl.setToolTipText("Blue Value: " + String.valueOf(bluesl.getValue()));
        c.gridx = 1;
        c.gridy = 10;
        c.gridheight = 5;
        panel.add(bluesl, c);

        // Color Panel
        panel_2 = new JPanel();
        panel_2.setBackground(primary);
        c.gridx = 1;
        c.gridy = 15;
        c.gridwidth = 1;
        c.gridheight = 25;
        panel.add(panel_2, c);

        // RIGHT
        c.anchor = GridBagConstraints.LINE_START;
        c.ipady = 0;
        c.ipadx = 600;
        // Displaying Panel
        panel_1 = new JPanel();
        panel_1.setSize(new Dimension(1000, 1200));
        panel_1.setBackground(primary);
        c.gridx = 0;
        c.gridy = 40;
        c.gridwidth = 2;
        c.gridheight = 20;
        c.anchor = GridBagConstraints.LINE_END;
        panel.add(panel_1, c);

//        // x,y
//        pos = new JLabel("Test", SwingConstants.CENTER);
//        pos.setFont(new Font("Times New Roman", Font.BOLD, 20));
//        c.gridx = 7;
//        c.gridy = 7;
//        c.gridwidth = 1;
//        c.gridheight = 1;
//        c.ipadx = 30;
//        c.ipady = 30;
//        panel.add(pos, c);
    }

    public void mouseCkd(MouseEvent e) {
        if (e.getX() >= panel_1.getX() && e.getX() <= (panel_1.getX() + panel_1.getSize().getWidth())
                && e.getY() >= panel_1.getY() && e.getY() <= (panel_1.getY() + panel_1.getSize().getHeight())) {
            int x = e.getX();
            int y = e.getY();
            try {
                Robot robot = new Robot();
                Color color = robot.getPixelColor(x, y);
                panel_2.setBackground(color);
                redsl.setValue(color.getRed());
                bluesl.setValue(color.getBlue());
                greensl.setValue(color.getGreen());
                panel.removeMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                    }
                });
            } catch (AWTException awtException) {
                awtException.printStackTrace();
            }
        }
    }

    public void stateChanged(ChangeEvent e) {
        Integer r = redsl.getValue();
        Integer g = greensl.getValue();
        Integer b = bluesl.getValue();
        panel_2.setBackground(new Color(r, g, b));
    }

    public BufferedImage rescale(BufferedImage originalImage) {
        int x = originalImage.getWidth();
        int y = originalImage.getHeight();
        if ((x / 2) >= y) {
            while (x > 600) {
                x = x / 2;
                y = y / 2;
            }
        } else {
            while (y > 300) {
                x = x / 2;
                y = y / 2;
            }
        }
        BufferedImage resizedImage = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, x, y, null);
        g.dispose();
        return resizedImage;
    }

    public void setTarget(File reference) {
        try {
            targetFile = reference;
            targetImg = rescale(ImageIO.read(reference));
            currentImg = targetImg;

        } catch (IOException ex) {
            Logger.getLogger(ColorApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        panel_1.removeAll();
        panel_1.setLayout(new BorderLayout(0, 0));
        panel_1.add(new JLabel(new ImageIcon(targetImg)));
        setVisible(true);
    }

    public void jccButtonActionPerformed(ActionEvent e) {
        Color initialcolor = panel_2.getBackground();
        JColorChooser jcc = new JColorChooser();
        Color color = jcc.showDialog(this,
                "Select a color", initialcolor);
        redsl.setValue(color.getRed());
        bluesl.setValue(color.getBlue());
        greensl.setValue(color.getGreen());
        panel_2.setBackground(color);
    }

    private void browseButtonActionPerformed(ActionEvent evt) {
        JFileChooser fc = new JFileChooser(basePath);
        fc.setFileFilter(new JPEGImageFileFilter());
        int res = fc.showOpenDialog(null);
        try {
            if (res == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                currentfile = file;
                hasfile = true;
                setTarget(file);
            } else {
                JOptionPane.showMessageDialog(null,
                        "You must select one image to be the reference.", "Aborting...",
                        JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception iOException) {
        }

    }

    private void colclkButtonActionPerformed(ActionEvent evt, int num, MouseAdapter ma) {
        if (num % 2 == 1) {
            panel.addMouseListener(ma);
        }
        if (num % 2 == 0) {
            panel.removeMouseListener(ma);
        }
    }

    private void imgebtButtonActionPerformed(ActionEvent evt, BufferedImage currentImg) {
        if (hasfile) {

            int width = currentImg.getWidth();
            int height = currentImg.getHeight();
            double hstalp = 0.0;
            double hstred = 0.0;
            double hstgrn = 0.0;
            double hstble = 0.0;
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    Color color = new Color(currentImg.getRGB(x, y));
                    if (color.getAlpha() > hstalp) {
                        hstalp = color.getAlpha();
                    }
                    if (color.getRed() > hstred) {
                        hstred = color.getRed();
                    }
                    if (color.getBlue() > hstble) {
                        hstble = color.getBlue();
                    }
                    if (color.getGreen() > hstgrn) {
                        hstgrn = color.getGreen();
                    }
                }
            }

            double alpratio = 255.0 / hstalp;
            double redratio = 255.0 / hstred;
            double grnratio = 255.0 / hstgrn;
            double bleratio = 255.0 / hstble;

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int p = currentImg.getRGB(x, y);

                    int a = (p >> 24) & 0xff;
                    int r = (p >> 16) & 0xff;
                    int g = (p >> 8) & 0xff;
                    int b = (p >> 0) & 0xff;

                    a *= alpratio;
                    r *= redratio;
                    g *= grnratio;
                    b *= bleratio;

                    p = (a << 24) | (r << 16) | (g << 8) | (b << 0);
                    currentImg.setRGB(x, y, p);

                }
            }
            currentImg = rescale(currentImg);
            panel_1.removeAll();
            panel_1.setLayout(new BorderLayout(0, 0));
            panel_1.add(new JLabel(new ImageIcon(currentImg)));
            setVisible(true);
        }
    }

    private void imgeditButtonActionPerformed(ActionEvent evt, BufferedImage currentImg) {
        if (hasfile) {

            int width = currentImg.getWidth();
            int height = currentImg.getHeight();
            Color color_1 = panel_2.getBackground();
            double alpratio = color_1.getAlpha() / 255.0;
            double redratio = color_1.getRed() / 255.0;
            double grnratio = color_1.getGreen() / 255.0;
            double bleratio = color_1.getBlue() / 255.0;
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int p = currentImg.getRGB(x, y);
                    int a = (p >> 24) & 0xff;
                    int r = (p >> 16) & 0xff;
                    int g = (p >> 8) & 0xff;
                    int b = (p >> 0) & 0xff;
                    a *= alpratio;
                    r *= redratio;
                    g *= grnratio;
                    b *= bleratio;
                    p = (a << 24) | (r << 16) | (g << 8) | (b << 0);
                    currentImg.setRGB(x, y, p);
                }
            }
            currentImg = rescale(currentImg);
            panel_1.removeAll();
            panel_1.setLayout(new BorderLayout(0, 0));
            panel_1.add(new JLabel(new ImageIcon(currentImg)));
            setVisible(true);
        }
    }

    private void imgenmButtonActionPerformed(ActionEvent evt) {
        if (hasfile) {
            try {
                currentImg = ImageIO.read(currentfile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            int width = currentImg.getWidth();
            int height = currentImg.getHeight();
            Color color_1 = Color.white;
            double alpratio = color_1.getAlpha() / 255.0;
            double redratio = color_1.getRed() / 255.0;
            double grnratio = color_1.getGreen() / 255.0;
            double bleratio = color_1.getBlue() / 255.0;
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int p = currentImg.getRGB(x, y);
                    int a = (p >> 24) & 0xff;
                    int r = (p >> 16) & 0xff;
                    int g = (p >> 8) & 0xff;
                    int b = (p >> 0) & 0xff;
                    a *= alpratio;
                    r *= redratio;
                    g *= grnratio;
                    b *= bleratio;
                    p = (a << 24) | (r << 16) | (g << 8) | (b << 0);
                    currentImg.setRGB(x, y, p);
                }
            }
            currentImg = rescale(currentImg);
            panel_1.removeAll();
            panel_1.setLayout(new BorderLayout(0, 0));
            panel_1.add(new JLabel(new ImageIcon(currentImg)));
            setVisible(true);
        }
    }

    private void imgmkButtonActionPerformed(ActionEvent evt, BufferedImage currentImg) {
        if (hasfile) {
            int width = currentImg.getWidth();
            int height = currentImg.getHeight();
            double[][][] rgb = new double[3][width][height];
            double[][][] normalizedmatrix = new double[3][width][height];
            double red = 0;
            double grn = 0;
            double ble = 0;
            double hstalp = 0.0;
            double hstred = 0.0;
            double hstgrn = 0.0;
            double hstble = 0.0;

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    Color color = new Color(currentImg.getRGB(x, y));
                    rgb[0][x][y] = color.getRed();
                    rgb[1][x][y] = color.getGreen();
                    rgb[2][x][y] = color.getBlue();
                    red += color.getRed();
                    ble += color.getBlue();
                    grn += color.getGreen();
                }
            }

            double avgred = red / (height * width);
            double avggrn = grn / (height * width);
            double avgble = ble / (height * width);
            double a = panel_2.getBackground().getAlpha();
            double r = panel_2.getBackground().getRed();
            double g = panel_2.getBackground().getGreen();
            double b = panel_2.getBackground().getBlue();

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {

                    normalizedmatrix[0][x][y] = rgb[0][x][y] / avgred * r;
                    normalizedmatrix[1][x][y] = rgb[1][x][y] / avggrn * g;
                    normalizedmatrix[2][x][y] = rgb[2][x][y] / avgble * b;

                    if (normalizedmatrix[0][x][y] > hstred) {
                        hstred = normalizedmatrix[0][x][y];
                    }
                    if (normalizedmatrix[1][x][y] > hstble) {
                        hstble = normalizedmatrix[1][x][y];
                    }
                    if (normalizedmatrix[2][x][y] > hstgrn) {
                        hstgrn = normalizedmatrix[2][x][y];
                    }
                }
            }

            double redratio = 255.0 / hstred;
            double grnratio = 255.0 / hstgrn;
            double bleratio = 255.0 / hstble;
            int nwalp = 0;
            int nwred = 0;
            int nwgrn = 0;
            int nwble = 0;
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {

                    int p = 0;

                    nwalp = 255;
                    nwred = (int) (normalizedmatrix[0][x][y] * redratio);
                    nwgrn = (int) (normalizedmatrix[1][x][y] * grnratio);
                    nwble = (int) (normalizedmatrix[2][x][y] * bleratio);


                    p = (nwalp << 24) | (nwred << 16) | (nwgrn << 8) | (nwble << 0);
                    currentImg.setRGB(x, y, p);
                }
            }
            currentImg = rescale(currentImg);
            panel_1.removeAll();
            panel_1.setLayout(new BorderLayout(0, 0));
            panel_1.add(new JLabel(new ImageIcon(currentImg)));
            setVisible(true);
        }
    }
    private void getColAvgButtonActionPerformed(ActionEvent evt, BufferedImage currentImg) {
        if (hasfile) {

            int width = currentImg.getWidth();
            int height = currentImg.getHeight();

            double[][][] rgb = new double[3][width][height];
            double red = 0;
            double grn = 0;
            double ble = 0;

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    Color color = new Color(currentImg.getRGB(x, y));
                    rgb[0][x][y] = color.getRed();
                    rgb[1][x][y] = color.getGreen();
                    rgb[2][x][y] = color.getBlue();
                    red += color.getRed();
                    ble += color.getBlue();
                    grn += color.getGreen();
                }
            }
            int avgred = (int)(red / (height * width));
            int avggrn = (int)(grn / (height * width));
            int avgble = (int)(ble / (height * width));
            Color col = new Color(avgred, avggrn, avgble);
            redsl.setValue(col.getRed());
            bluesl.setValue(col.getBlue());
            greensl.setValue(col.getGreen());
            panel_2.setBackground(col);

        }
    }
    public static void wait(int ms)
    {
        try
        {
            Thread.sleep(ms);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }
}

