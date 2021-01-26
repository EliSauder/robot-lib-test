package org.frcnomad.lib.inputdevices;

import java.util.HashMap;
import java.util.Map;

public class NomadInputMap {

    private InputMappingEnum map;

    private String name;

    private Map<Integer, NomadButton> buttons = new HashMap<>();

    private Map<Integer, NomadAxis> axes = new HashMap<>();

    public NomadInputMap(InputMappingEnum map, String name) {
        this.map = map;
        this.name = name;
    }

    public NomadInputMap withMap(InputMappingEnum map) {
        this.map = map;
        return this;
    }

    public NomadInputMap withName(String name) {
        this.name = name;
        return this;
    }

    public NomadInputMap withButton(NomadButton button) {
        this.buttons.put(button.getId(), button);
        return this;
    }

    public NomadInputMap withAxis(NomadAxis axis) {
        axes.put(axis.getId(), axis.withMap(this.map));
        return this;
    }

    public String getMappingString() {
        throw new UnsupportedOperationException("Not Implemented");
    }

    public NomadAxis getAxis(int id) {
        return axes.get(id);
    }

    public double getRawAxis(int id) {
        return getAxis(id).get();
    }

    public NomadButton getButton(int id) {
        return buttons.get(id);
    }

    public boolean getRawButton(int id) {
        return getButton(id).get();
    }

    public String getName() {
        return this.name;
    }

    public InputMappingEnum getMap() {
        return this.map;
    }
    
}
