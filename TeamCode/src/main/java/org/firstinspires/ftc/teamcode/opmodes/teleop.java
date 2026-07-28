package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Telem;
import org.firstinspires.ftc.teamcode.robot.Robot;
import org.firstinspires.ftc.teamcode.subsystems.Launcher;
import org.firstinspires.ftc.teamcode.subsystems.intake;

@TeleOp(name = "TeleopMain")
public class teleop extends LinearOpMode {

    private static final double SLOW_FACTOR = 0.35;

    private Robot robot;

    @Override
    public void runOpMode() {
        Telem.init(telemetry);

        robot = new Robot(hardwareMap);


        telemetry.addLine("Initialized - ready");
        telemetry.update();

        waitForStart();
        if (isStopRequested()) return;

        while (opModeIsActive()) {

            // The SDK reports NEGATIVE left_stick_y when the stick is pushed
            // forward, so it has to be inverted before the mecanum math.
            double ly = -gamepad1.left_stick_y;
            double lx =  gamepad1.left_stick_x;
            double rx =  gamepad1.right_stick_x;

            double scale = gamepad1.a ? SLOW_FACTOR : 1.0;

            robot.drivetrain.drive(lx * scale, ly * scale, rx * scale);

            // Hold right bumper to run the intake.
            robot.intake.setState(
                    gamepad1.right_bumper ? intake.State.ON : intake.State.OFF);

            // Hold X for 4000 RPM, release to idle backwards at 100 RPM.
            robot.launcher.setState(
                    gamepad1.x ? Launcher.State.FIRING : Launcher.State.IDLE);

            robot.update();
        }

        robot.stop();
    }
}