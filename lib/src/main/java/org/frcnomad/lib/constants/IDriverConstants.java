package org.frcnomad.lib.constants;

import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.system.LinearSystem;
import edu.wpi.first.wpilibj.system.plant.DCMotor;
import edu.wpi.first.wpiutil.math.Vector;
import edu.wpi.first.wpiutil.math.numbers.N2;
import edu.wpi.first.wpiutil.math.numbers.N7;

interface IDriverConstants {

    int getDriveControllerFwdBackAxis();
    int getDriveControllerLeftRightAxis();

    int getCanIDLeftDriveMaster();
    boolean getLeftEncoderReversed();
    int[] getLeftEncoderPorts();
    int getCanIDLeftDriveFollower();

    int getCanIDRightDriveMaster();
    boolean getRightEncoderReversed();
    int[] getRightEncoderPorts();
    int getCanIDRightDriveFollower();

    boolean getGyroReversed();

    double getEncoderCountsPerEncoderRevolution();
    double getEncoderCountsPerWheelRevolution();
    double getEncoderRevolutionsPerWheelRevolution();
    double getEncoderDistancePerPulse();

    double getKsVolts();
    double getKvVoltSecondsPerMeter();
    double getKaVoltSecondsSquaredPerMeter();
    double getKvVoltSecondsPerRadian();
    double getKaVoltSecondsSquaredPerRadian();
    double getkWheelDiameter();

    double getkPDriveVel();
    double getkPDriveVelLeft();
    double getkPDriveVelRight();
    double getkTrackWidthMeters();

    DifferentialDriveKinematics getDifferentialDriveKinematics();
    LinearSystem<N2, N2, N2> getDrivetrainPlant();
    DCMotor getDriveGearbox();

    double getDriveGearingRatio();

    Vector<N7> getSimEncoderStdDev();

}