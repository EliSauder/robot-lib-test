package org.frcnomad.lib.inputdevices;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.logging.Logger;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.NetworkButton;

public class NomadOperatorConsole {

    public static final int MAX_SUPPORTED_POVS = 6;

    public static final int MAX_CONTROLLERS = 5;

    private static NetworkButton rescanButton;

    private static RunCommand rescanCommand;

    public static final Map<InputMappingEnum, NomadInputMap> INPUT_ENUM_MAP = new EnumMap<>(InputMappingEnum.class);

    private static Map<Integer, NomadMappedGenericHID> controllers = new HashMap<>();

    private NomadOperatorConsole() {}

    static {
        for (int i = 0; i < MAX_CONTROLLERS; i++) {
            NomadMappedGenericHID controller = new NomadMappedGenericHID(i);
            controllers.put(i, controller);
        }

        SmartDashboard.putBoolean("Controller Rescan", false);
        rescanButton = new NetworkButton("SmartDashboard/", "ControllerREscan");
        rescanCommand = new RunCommand(() -> {
            // scan controllers
            SmartDashboard.putBoolean("Controller Rescan", false);
            Logger.getGlobal().info("Rescan Controllers");
        });
        rescanButton.whenPressed(rescanCommand);
    }

    public static int getControllerPort(int id) {
        return id / 100;
    }

    public static int getInputPort(int id) {
        return id % 100;
    }

    public static boolean getRawButton(int id) {
        NomadMappedGenericHID hid = controllers.get(getControllerPort(id));
        return hid != null ? hid.getRawButton(getInputPort(id)) : false;
    }

    // TODO: Simplify this method/split it into multiple methods
    @SuppressWarnings("java:S3776")
    public static NomadInputMap populateMap(NomadInputMap map) {
        controllers.forEach((BiConsumer<Integer, NomadMappedGenericHID>)(port, controller) -> {

            for (int i = 0; i < controller.getAxisCount(); i++) {
                if (map.getAxis(i) == null) {

                    final int id = i;

                    map.withAxis(
                        new NomadAxis(i + 100 * port, Integer.toString(i))
                            .withBehavior(() -> controller.getHIDRawAxis(id)));
                }
            }

            for (int i = 0; i < controller.getButtonCount(); i++) {
                if (map.getButton(i) == null) {

                    final int id = i;

                    map.withButton(
                        new NomadButton(i + 100 * port, "" + i)
                            .withBehavior(() -> controller.getHIDRawButton(id)));
                }
            }

            for (int i = 0; i < Math.min(controller.getPOVCount(), MAX_SUPPORTED_POVS); i++) {
                for (int angle = 0; angle < 360; angle += 45) {
                    if (map.getButton(i) == null) {
                        final int id = i;
                        final int povAngle = angle;

                        // TODO: Add support for inbetween values (what happens if it is between 0-44, 45-98, etc.)
                        map.withButton(
                            new NomadButton( id * 8 + angle / 45 + 100 * port + 600, i + "-" + angle)
                                .withBehavior(() -> {
                                    return controller.getPOV(id) == povAngle;
                                }));
                        
                    }
                }
            }
        });

        INPUT_ENUM_MAP.put(map.getMap(), map);

        return map;
    }
    
}
