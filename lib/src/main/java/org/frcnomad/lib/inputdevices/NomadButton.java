package org.frcnomad.lib.inputdevices;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.button.Button;

public class NomadButton extends Button {

    public static final NomadButton NONE_BUTTON = new NomadButton();
    
    private String name = null;

    private InputMappingEnum map = null;

    private int id;

    private BooleanSupplier behavior = null;

    public NomadButton() {
        this.name = "unnamed";
        this.id = -1;
        this.map = InputMappingEnum.UNCATEGORIZED;
        this.behavior = () -> false;
    }

    public NomadButton(int id) {
        super();
        this.id = id;
    }

    public NomadButton(int id, String name) {
        this(id);
        this.name = name;
    }

    @Override
    public boolean get() {
        return !map.equals(InputMappingEnum.UNCATEGORIZED) ? this.behavior.getAsBoolean() : false;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public NomadButton withMap(InputMappingEnum map) {
        this.map = map;
        return this;
    }

    public NomadButton withName(String name) {
        this.name = name;
        return this;
    }

    public NomadButton withBehavior(BooleanSupplier behavior) {
        this.behavior = behavior;
        return this;
    }

}
