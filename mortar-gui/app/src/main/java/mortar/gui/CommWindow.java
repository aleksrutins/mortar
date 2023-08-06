package mortar.gui;

import java.io.IOException;

import javax.swing.JInternalFrame;
import javax.swing.JTextArea;

import com.fazecast.jSerialComm.SerialPort;

import mortar.gui.map.MapWindow;

public class CommWindow extends JInternalFrame {
    private SerialPort port;
    private JTextArea textArea;

    public CommWindow(SerialPort port) {
        super("Serial View");
        this.port = port;

        textArea = new JTextArea("Welcome!\n");
        textArea.setEditable(false);
        add(textArea);
    }

    void aim(MapWindow map) throws IOException {
        if(!map.mapView.isTargetValid()) {
            Dialogs.showError("Please select a target in front of the mortar.", map);
            return;
        }

        var aimInfo = map.getAim();
        var msg = "aim " + aimInfo.baseAngle() + " " + aimInfo.distance() + "\n";
        textArea.setText(textArea.getText() + msg);
        port.getOutputStream().write(msg.getBytes());
    }

    public void fire(MapWindow map) throws IOException {
        aim(map);
        textArea.setText(textArea.getText() + "fire\n");
        port.getOutputStream().write("fire\n".getBytes());
    }

    public boolean closePort() {
        return port.closePort();
    }
}
