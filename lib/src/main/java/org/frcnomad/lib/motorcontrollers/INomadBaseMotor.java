package org.frcnomad.lib.motorcontrollers;

import edu.wpi.first.wpilibj.SpeedController;

public interface INomadBaseMotor extends SpeedController {

    boolean isLazy();

    void setLazy(boolean islazy);

    INomadBaseMotor setLeader(INomadBaseMotor leader);

    void setInverted(boolean inverted);

    double getActualOutputPercent();
    
}
