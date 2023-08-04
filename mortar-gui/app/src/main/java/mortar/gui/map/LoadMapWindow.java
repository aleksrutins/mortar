package mortar.gui.map;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.filechooser.FileFilter;
import javax.swing.plaf.basic.BasicBorders;

class ImageViewer
    extends JPanel
{
    private Image image;
    public ImageViewer() {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    @Override
    public Dimension getPreferredSize() {
        if(image != null) return new Dimension(image.getWidth(this), image.getHeight(this));
        return new Dimension(500, 500);
    }

    public void setImage(Image image) {
        this.image = image;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(image != null) g.drawImage(image, 0, 0, this);
    }
}

public class LoadMapWindow
    extends JInternalFrame
{
    Image image;
    ImageViewer imageViewer;
    
    public LoadMapWindow(MapView mapView) {
        super("Load Map");

        var contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        var loadImageBtn = new JButton("Browse...");
        loadImageBtn.addActionListener(this::loadImage);
        contentPane.add(loadImageBtn);

        imageViewer = new ImageViewer();
        contentPane.add(imageViewer);
    }

    public void loadImage(ActionEvent e) {
        var fileChooser = new JFileChooser();

        fileChooser.setFileFilter(new FileFilter() {

            @Override
            public boolean accept(File f) {
                var name = f.getName().toLowerCase();
                return
                    name.endsWith(".jpeg") ||
                    name.endsWith(".jpg") ||
                    name.endsWith(".png") ||
                    name.endsWith(".bmp");
            }

            @Override
            public String getDescription() {
                return "Image files";
            }
            
        });


    }
}
