package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Telem;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Launcher;
import org.firstinspires.ftc.teamcode.subsystems.intake;

import java.util.List;

public class Robot {

    public final Drivetrain drivetrain;
    public final intake intake;
    public final Launcher launcher;

    private final List<LynxModule> hubs;
    private final ElapsedTime loopTimer = new ElapsedTime();

    private double loopTime = 0;

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

        loopTimer.reset();
    }

    /**
     * Call once per loop, at the END of the loop. The bulk cache clear has to
     * come before the telemetry reads so the displayed numbers are fresh.
     */
    public void update() {
        loopTime = loopTimer.milliseconds();
        loopTimer.reset();
        double hz = loopTime > 0 ? 1000.0 / loopTime : 0;

        for (LynxModule hub : hubs) {
            hub.clearBulkCache();
        }

        Telem.addLine("Loop");
        Telem.addData("Loop Time (ms)", loopTime);
        Telem.addData("Hz", hz);

        drivetrain.telemetry();
        intake.telemetry();
        launcher.telemetry();

        Telem.update();
    }

    public double getLoopTime() {
        return loopTime;
    }

    public void stop() {
        drivetrain.stop();
        intake.stop();
        launcher.stop();
    }
}