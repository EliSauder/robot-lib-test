package org.frcnomad.lib.inputdevices;

import java.util.function.DoubleSupplier;

public class NomadAxis {
    
    public static final NomadAxis NONE_NomadAxis = new NomadAxis(-1);

    private String name = null;

    private InputMappingEnum map = null;

    private int id;

    private DoubleSupplier behavior = null;

    public NomadAxis(int id) {
        this.id = id;
        this.name = "unnamed";
        this.map = InputMappingEnum.UNCATEGORIZED;
        this.behavior = () -> 0.0;
    }

    public NomadAxis(int id, String NomadAxisName) {
        this(id);
        this.name = NomadAxisName;
    }

    public NomadAxis(int id, String NomadAxisName, DoubleSupplier behavior) {
        this(id, NomadAxisName);
        this.behavior = behavior;
    }

    public final double get() {
        return !this.map.equals(InputMappingEnum.UNCATEGORIZED) ? this.behavior.getAsDouble() : 0.0;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public NomadAxis withMap(InputMappingEnum map) {
        this.map = map;
        return this;
    }

    public NomadAxis withBehavior(DoubleSupplier behavior) {
        this.behavior = behavior;
        return this;
    }

}
