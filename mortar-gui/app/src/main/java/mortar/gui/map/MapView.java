package mortar.gui.map;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.ImageObserver;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class MapView
    extends JPanel
    implements 
        MouseListener,
        MouseWheelListener,
        MouseMotionListener,
        KeyListener
{
    private Image image;
    double pixelsPerFoot = 0;

    private boolean placingMortar;
    private Point currentPos = new Point(0, 0);
    Point mortarPos = new Point(0, 0);
    Point targetPos = new Point(0, 0);
    double mortarOrientation = 0;

    private int dx = 0;
    private int dy = 0;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image, double pixelsPerFoot) {
        this.image = image;
        this.pixelsPerFoot = pixelsPerFoot;
        repaint();
    }

    public void placeMortar() {
        mortarPos = new Point(currentPos.x - dx, currentPos.y - dy);
    }

    public void placeTarget() {
        targetPos = new Point(currentPos.x - dx, currentPos.y - dy);
    }

    public MapView() {
        setFocusable(true);
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        addKeyListener(this);
    }

    public Dimension getPreferredSize() {
        return new Dimension(600, 600);
    }

    public void drawTargetIndicator(Graphics g, double x, double y) {
        var ix = (int)Math.round(x);
        var iy = (int)Math.round(y);
        var color = g.getColor();
        g.setColor(Color.RED);
        g.drawLine(ix - 5, iy - 5, ix + 5, iy + 5);
        g.drawLine(ix - 5, iy + 5, ix + 5, iy - 5);
        g.setColor(color);
    }

    public void drawMortarIndicator(Graphics g, double x, double y) {
        var color = g.getColor();
        g.setColor(Color.BLUE);

        var startX = (Math.cos(mortarOrientation + Math.PI) * 5) + x;
        var startY = (Math.sin(mortarOrientation + Math.PI) * 5) + y;
        var endX = (Math.cos(mortarOrientation) * 5) + x;
        var endY = (Math.sin(mortarOrientation) * 5) + y;

        g.drawLine((int)Math.round(startX), (int)Math.round(startY), (int)Math.round(endX), (int)Math.round(endY));
        g.drawRect((int)Math.round(startX), (int)Math.round(startY), 2, 2);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        ((Graphics2D)g).addRenderingHints((Map<?, ?>)Toolkit.getDefaultToolkit().getDesktopProperty("awt.font.desktophints"));

        var height = getHeight();
        var width = getWidth();
        var fontMetrics = g.getFontMetrics();

        if(this.image != null) {
            g.drawImage(image, dx, dy, image.getWidth(this), image.getHeight(this), this);
        }

        g.drawString("Click to place target, alt-click to place mortar, scroll to change mortar orientation", 10, height - (fontMetrics.getHeight() + 5));

        if(placingMortar) {
            drawMortarIndicator(g, currentPos.getX(), currentPos.getY());
        } else {
            drawTargetIndicator(g, currentPos.getX(), currentPos.getY());
        }

        drawMortarIndicator(g, mortarPos.getX() + dx, mortarPos.getY() + dy);
        drawTargetIndicator(g, targetPos.getX() + dx, targetPos.getY() + dy);
    }

    public boolean isTargetValid() {
        var angle = Math.atan2(targetPos.getY() - mortarPos.getY(), targetPos.getX() - mortarPos.getY());
        return (mortarOrientation - (Math.PI/2)) < angle && angle < (mortarOrientation + (Math.PI/2));
    }

    // Event listeners

    @Override
    public void mouseClicked(MouseEvent e) {
        if(placingMortar) placeMortar();
        else placeTarget();
        repaint();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if(e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
            mortarOrientation += e.getUnitsToScroll() * (Math.PI/60);
            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if((e.getModifiersEx() & InputEvent.ALT_DOWN_MASK) != 0) {
            placingMortar = true;
        } else {
            placingMortar = false;
        }
        currentPos = e.getPoint();
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W)
            dy -= 5;
        else if(e.getKeyCode() == KeyEvent.VK_S)
            dy += 5;
        else if(e.getKeyCode() == KeyEvent.VK_A)
            dx -= 5;
        else if(e.getKeyCode() == KeyEvent.VK_D)
            dx += 5;
        else if(e.getKeyCode() == KeyEvent.VK_ALT)
            placingMortar = true;
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ALT) {
            placingMortar = false;
            repaint();
        }
    }
}