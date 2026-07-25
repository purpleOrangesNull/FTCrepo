package org.firstinspires.ftc.teamcode.pedroPathing.constants;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;

public class Constants {

    public static Follower createFollower(HardwareMap hardwareMap) {
        return new Follower(hardwareMap, FConstants.class, LConstants.class);
    }
}