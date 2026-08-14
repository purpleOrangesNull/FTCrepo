package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Launcher;
import org.firstinspires.ftc.teamcode.subsystems.Intake;

import java.util.List;

public class Robot {

    public final Drivetrain drivetrain;
    public final Intake intake;
    public final Launcher launcher;

    private final List<LynxModule> hubs;

    public Robot(HardwareMap hm) {
        DcMotor frontLeft   = hm.get(DcMotor.class, "leftFront");
        DcMotor frontRight  = hm.get(DcMotor.class, "rightFront");
        DcMotor backLeft    = hm.get(DcMotor.class, "leftBack");
        DcMotor backRight   = hm.get(DcMotor.class, "rightBack");
        DcMotorEx intakeMotor = hm.get(DcMotorEx.class, "intake");
        DcMotor transferMotor = hm.get(DcMotor.class, "transfer");

        DcMotorEx launcherMotor = hm.get(DcMotorEx.class, "launcher");
        DcMotorEx launcher2 = hm.get(DcMotorEx.class, "launcher2");

        drivetrain = new Drivetrain(frontLeft, frontRight, backLeft, backRight);
        intake = new Intake(intakeMotor, transferMotor);
        launcher = new Launcher(launcherMotor, launcher2);

        hubs = hm.getAll(LynxModule.class);
        for (LynxModule hub : hubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        }
    }

    public void clearBulkCache() {
        for (LynxModule hub : hubs) {
            hub.clearBulkCache();
        }
    }

    public void stop() {
        drivetrain.stop();
        intake.stop();
        launcher.stop();
    }
}