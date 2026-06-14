package org.firstinspires.ftc.teamcode.pedroPathing.constants;

import com.pedropathing.localization.*;
import com.pedropathing.localization.constants.*;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;

public class LConstants {
    static {
        TwoWheelConstants.forwardTicksToInches = 0.00300116997590546;
        TwoWheelConstants.strafeTicksToInches = 0.0030179556753860133;
        TwoWheelConstants.forwardY = -4.5;
        TwoWheelConstants.strafeX = -5;
        TwoWheelConstants.forwardEncoder_HardwareMapName = "rightBack";
        TwoWheelConstants.strafeEncoder_HardwareMapName = "leftFront";
        TwoWheelConstants.forwardEncoderDirection = Encoder.REVERSE;
        TwoWheelConstants.strafeEncoderDirection = Encoder.FORWARD;
        TwoWheelConstants.IMU_HardwareMapName = "imu";
        TwoWheelConstants.IMU_Orientation = new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.RIGHT, RevHubOrientationOnRobot.UsbFacingDirection.UP);

    }
}




