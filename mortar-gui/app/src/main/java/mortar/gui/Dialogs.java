package mortar.gui;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

public class Dialogs {
    public static void showError(String error, JComponent parent) {
        JOptionPane.showInternalMessageDialog(parent, error, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void showUnlikelyError(String error, JComponent parent) {
        showError("This error should not happen. Please report it on GitHub at https://github.com/aleksrutins/mortar with the following info: " + error, parent);
    }
}
