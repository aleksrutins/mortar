package mortar.gui.map;

import javax.swing.JInternalFrame;

import mortar.gui.App;

public class MapWindow extends JInternalFrame {
    public record AimingInfo(float baseAngle, float distance) {}

    public MapView mapView;

    public MapWindow() {
        super("Map", true, true, true, true);
        setBounds(500, 500, 600, 600);
        mapView = new MapView();
        add(mapView);
    }

    public AimingInfo getAim() {
        return new AimingInfo(0f, 0f);
    }
}
