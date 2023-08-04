package mortar.gui;

import javax.swing.JInternalFrame;
import javax.swing.JTextArea;

import com.fazecast.jSerialComm.SerialPort;

import mortar.gui.map.MapWindow;

public class CommWindow extends JInternalFrame {
    private SerialPort port;

    public CommWindow(SerialPort port) {
        super("Serial View");
        this.port = port;

        var textArea = new JTextArea("Welcome! Load a map, aim, and fire - it's that easy!");
    }

    public void fire(MapWindow.AimingInfo aim) {

    }
}
