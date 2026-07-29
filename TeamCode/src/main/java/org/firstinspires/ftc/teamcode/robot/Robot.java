package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Launcher;
import org.firstinspires.ftc.teamcode.subsystems.intake;

import java.util.List;

/**
 * Hardware container. No update() method any more: constructing each
 * SubsystemBase registers it with the CommandScheduler, which then calls
 * every periodic() once per loop on its own.
 */
public class Robot {

    public final Drivetrain drivetrain;
    public final intake intake;
    public final Launcher launcher;

    private final List<LynxModule> hubs;

    public Robot(HardwareMap hm) {
        DcMotor frontLeft   = hm.get(DcMotor.class, "leftFront");
        DcMotor frontRight  = hm.get(DcMotor.class, "rightFront");
        DcMotor backLeft    = hm.get(DcMotor.class, "leftBack");
        DcMotor backRight   = hm.get(DcMotor.class, "rightBack");
        DcMotor intakeMotor = hm.get(DcMotor.class, "intake");

        // DcMotorEx, not DcMotor: setVelocity() and the PIDF coefficients
        // only exist on the Ex interface.
        DcMotorEx launcherMotor = hm.get(DcMotorEx.class, "launcher");

        drivetrain = new Drivetrain(frontLeft, frontRight, backLeft, backRight);
        intake = new intake(intakeMotor);
        launcher = new Launcher(launcherMotor);

        hubs = hm.getAll(LynxModule.class);
        for (LynxModule hub : hubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        }
    }

    /** Called by BaseOpMode at the top of every loop, before any reads. */
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