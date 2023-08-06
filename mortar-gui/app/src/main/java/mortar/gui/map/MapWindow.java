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
        var distanceX = (mapView.targetPos.x - mapView.mortarPos.x) / mapView.pixelsPerFoot;
        var distanceY = (mapView.targetPos.y - mapView.mortarPos.y) / mapView.pixelsPerFoot;
        return new AimingInfo(
            (float)Math.atan2(distanceY, distanceX),
            (float)Math.sqrt(Math.pow(distanceX, 2.0) + Math.pow(distanceY, 2.0))
        );
    }
}
