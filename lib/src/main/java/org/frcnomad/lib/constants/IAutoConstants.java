package org.frcnomad.lib.constants;

import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.constraint.CentripetalAccelerationConstraint;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;

interface IAutoConstants {

    double getkMaxAccelerationMetersPerSecondSquared();

    double getkMaxSpeedMetersPerSecond();

    double getkRamseteB();

    double getkRamseteZeta();

    SimpleMotorFeedforward getTrajectoryFeedForward();

    DifferentialDriveVoltageConstraint getAutoVoltageConstraint();

    CentripetalAccelerationConstraint getAutoCentripetalConstraint();

    TrajectoryConfig getTrajectoryConfig();

    RamseteController getRamseteController();

}
