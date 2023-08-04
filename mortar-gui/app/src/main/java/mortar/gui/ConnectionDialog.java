package mortar.gui;

import java.awt.Window;
import java.awt.event.WindowEvent;
import java.beans.PropertyVetoException;
import java.io.IOException;

import javax.swing.*;
import com.fazecast.jSerialComm.*;

public class ConnectionDialog extends JInternalFrame {
    JComboBox<SerialPort> portSelect;

    public ConnectionDialog() {
        super("Connect to Serial Port");
        setBounds(50, 50, 400, 50);

        portSelect = new JComboBox<>(SerialPort.getCommPorts());

        var connectButton = new JButton("Connect");
        connectButton.addActionListener((event) -> {
            var port = (SerialPort)portSelect.getSelectedItem();
            try {
                port.openPort();
                port.getOutputStream().write("READY".getBytes());
                port.closePort();
                setClosed(true);
            } catch(IOException e) {
                Dialogs.showError("Failed to connect port " + port.toString(), this);
            } catch(PropertyVetoException e) {
                Dialogs.showUnlikelyError(e.getMessage(), this);
            } catch(Exception e) {
                Dialogs.showError(e.getMessage(), this);
            }
        });

        var contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
        contentPane.add(portSelect);
        contentPane.add(connectButton);
    }    
}
