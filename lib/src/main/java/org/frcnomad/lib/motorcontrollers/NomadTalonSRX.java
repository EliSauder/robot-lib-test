package org.frcnomad.lib.motorcontrollers;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

/**
 * This class is an encapsulation of WPI_TalonSRX that add a couple constructors
 * for forcing common settings. It takes inspiration for the lazy feature from
 * FRC 254.
 */
public class NomadTalonSRX extends WPI_TalonSRX implements INomadBaseMotor {
    /** This decides if the talon should operate in lazy mode. */
    protected boolean lazy = false;
    protected INomadBaseMotor leader = null;
    protected double lastPower = Double.NaN;
    protected ControlMode lastMode = null;
    // for following
    protected boolean manualFollowing = false;
    protected double lastLeaderOutput = Double.NaN;
    protected double currentLeaderOutput = Double.NaN;

    /**
     * Constructs a TalonSRX, reverts it to factory default, and sets brake mode.
     * 
     * @param port The CAN ID of this Talon
     */
    public NomadTalonSRX(int port) {
        super(port);
        configFactoryDefault();
        setNeutralMode(NeutralMode.Brake);
        setSafetyEnabled(false);
    }

    /**
     * Constructs a TalonSRX, reverts it to factory default, and sets brake mode and
     * inversion status.
     * 
     * @param port     The CAN ID of this Talon.
     * @param inverted True for inverted, false if not.
     */
    public NomadTalonSRX(int port, boolean inverted) {
        this(port);
        setInverted(inverted);
    }

    /**
     * Constructs a TalonSRX, reverts it to factory default, sets brake mode and
     * inversion status, and slaves it to a specified NomadTalonSRX.
     * 
     * @param port     The CAN ID of this Talon.
     * @param inverted True for inverted, false if not.
     * @param leader   The NomadTalonSRX to follow.
     */
    public NomadTalonSRX(int port, boolean inverted, INomadBaseMotor leader) {
        this(port, inverted);
        setLeader(leader);
    }
    
    /**
     * Check if the motor controller is lazy
     * 
     * @return Whether the motor controller is lazy
     */
    public boolean isLazy() {
        return lazy;
    }

    /**
     * Set the lazy mode
     * 
     * @param isLazy A boolean for the lazy mode, where true is lazy on
     */
    public void setLazy(boolean isLazy) {
        lazy = isLazy;
    }

    /**
     * Sets the appropriate output on the talon, depending on the mode. If in lazy
     * mode, it will not call set unless the value or mode is different.
     * 
     * @param mode  The output mode to apply. In PercentOutput, the output is
     *              between -1.0 and 1.0, with 0.0 as stopped. In Current mode,
     *              output value is in amperes. In Velocity mode, output value is in
     *              position change / 100ms. In Position mode, output value is in
     *              encoder ticks or an analog value, depending on the sensor. In
     *              Follower mode, the output value is the integer device ID of the
     *              talon to duplicate.
     *
     * @param value The setpoint value, as described above.
     *
     *
     *              Standard Driving Example:
     *              _talonLeft.set(ControlMode.PercentOutput, leftJoy);
     *              _talonRght.set(ControlMode.PercentOutput, rghtJoy);
     */
    @Override
    public void set(ControlMode mode, double value) {
        if (lazy) {
            if (value != lastPower || mode != lastMode) {
                lastPower = value;
                lastMode = mode;
                super.set(mode, value);
            }
        } else {
            super.set(mode, value);
        }
    }

    public void updateFollower(){
        if(manualFollowing && leader != null) { //If we're following something that's not officially supported
            currentLeaderOutput = leader.getActualOutputPercent();
            if (lazy && currentLeaderOutput != lastLeaderOutput) { //jank it.
                lastLeaderOutput = currentLeaderOutput;
                set(currentLeaderOutput);
            }
        }
    }
    
    public INomadBaseMotor setLeader( INomadBaseMotor leader){
        manualFollowing = false;
        this.leader = leader;
        if (leader instanceof IMotorController) {
            follow((IMotorController) leader);
        }
        else{
            throw new IllegalArgumentException(getClass().toString() + getDeviceID() + " tried to follow an incompatible controller");
        }
        return this;
    }
    @Override
    public double getActualOutputPercent() {
        
        return get();

        /* if(RobotBase.isReal()) {
            return get();
        } else {
             return get();
        } */
    }
}