package mortar.gui;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.*;
import com.fazecast.jSerialComm.*;

public class ConnectionDialog extends JInternalFrame {
    JComboBox<SerialPort> portSelect;
    private List<Consumer<SerialPort>> connectListeners = new ArrayList<>();

    public ConnectionDialog() {
        super("Connect to Serial Port");

        portSelect = new JComboBox<>(SerialPort.getCommPorts());

        var connectButton = new JButton("Connect");
        connectButton.addActionListener((event) -> {
            var port = (SerialPort)portSelect.getSelectedItem();
            try {
                port.openPort();
                port.getOutputStream().write("ready".getBytes());
                port.closePort();
                for (Consumer<SerialPort> listener : connectListeners) {
                    SwingUtilities.invokeLater(() -> { listener.accept(port); });
                }
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
        pack();
        setSize(300, getHeight());
    }

    public void addConnectListener(Consumer<SerialPort> fn) {
        connectListeners.add(fn);
    }

    public void removeConnectListener(Consumer<SerialPort> fn) {
        connectListeners.remove(fn);
    }
}
