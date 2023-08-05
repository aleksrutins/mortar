package mortar.gui.map;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.filechooser.FileFilter;
import javax.swing.plaf.basic.BasicBorders;

import mortar.gui.Dialogs;

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
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        var contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        var loadImageBtn = new JButton("Browse...");
        loadImageBtn.addActionListener(this::loadImage);
        contentPane.add(loadImageBtn);

        imageViewer = new ImageViewer();
        contentPane.add(imageViewer);

        var pixelSpinner = new JSpinner(new SpinnerNumberModel());

        var scaleBox = new JPanel();
        scaleBox.setLayout(new BoxLayout(scaleBox, BoxLayout.X_AXIS));

        scaleBox.add(pixelSpinner);
        scaleBox.add(new JLabel("pixel(s) per foot"));
        
        contentPane.add(scaleBox);

        var buttonBox = new JPanel();
        buttonBox.setLayout(new BoxLayout(buttonBox, BoxLayout.X_AXIS));

        var cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            try {
                setClosed(true);
            } catch(Exception ex) {
                Dialogs.showUnlikelyError("Failed to close window: " + ex.getMessage(), this);
            }
        });

        var loadButton = new JButton("Load");
        loadButton.addActionListener(e -> {
            if(this.image == null) {
                Dialogs.showError("Please select an image, or click 'Cancel' to abort.", this);
                return;
            }
            mapView.setImage(image, (Integer)pixelSpinner.getValue());
            try {
                setClosed(true);
            } catch(Exception ex) {
                Dialogs.showUnlikelyError("Failed to close window: " + ex.getMessage(), this);
            }
        });

        buttonBox.add(cancelButton);
        buttonBox.add(loadButton);

        contentPane.add(buttonBox);
        
        pack();
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
                    name.endsWith(".bmp") ||
                    f.isDirectory();
            }

            @Override
            public String getDescription() {
                return "Image files";
            }
            
        });

        int result = fileChooser.showOpenDialog(this);
        if(result == JFileChooser.APPROVE_OPTION) {
            try {
                image = ImageIO.read(fileChooser.getSelectedFile());
                imageViewer.setImage(image);
                pack();
            } catch(IOException err) {
                Dialogs.showError("Failed to read image: " + err.getMessage(), this);
            }
        }
    }
}
