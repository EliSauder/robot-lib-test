package org.frcnomad.lib.inputdevices;

import edu.wpi.first.wpilibj.GenericHID;

public class NomadMappedGenericHID extends GenericHID {

    public static final NomadMappedGenericHID NONE_MAPPEDHID = new NomadMappedGenericHID(-1);

    private InputMappingEnum map = null;

    public NomadMappedGenericHID(int port) {
        super(port);
        this.map = InputMappingEnum.UNCATEGORIZED;
    }

    public NomadMappedGenericHID withMap(InputMappingEnum map) {
        this.map = map;
        return this;
    }

    public InputMappingEnum getMap() {
        return this.map;
    }

    @Override
    public double getX(Hand hand) {
        return 0;
    }

    @Override
    public double getY(Hand hand) {
        return 0;
    }
    
    @Override
    public double getRawAxis(int id){
        return NomadOperatorConsole.inputEnumMap.get(this.map).getRawAxis(id);
    }

    @Override
    public boolean getRawButton(int id){
        return NomadOperatorConsole.inputEnumMap.get(this.map).getRawButton(id);
    }

    public double getHIDRawAxis(int id) {
        return super.getRawAxis(id);
    }

    public boolean getHIDRawButton(int id) {
        return super.getRawButton(id);
    }
}
