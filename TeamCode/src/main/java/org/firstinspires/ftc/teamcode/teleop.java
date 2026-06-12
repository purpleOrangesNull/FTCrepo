package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.robot.Robot;

@TeleOp(name = "TeleopMain")
public class teleop extends LinearOpMode {
    private Robot robot;
    @Override
    public void runOpMade(){
        Telem.init(telemetry);
        robot=new Robot(hardwareMap);
        //wait();
    waitForStart();

        while (opModeIsActive()) {
            // drivetrain
            robot.drivetrain.drive(
                    gamepad1.left_stick_x,
                    gamepad1.left_stick_y,
                    gamepad1.right_stick_x
            );

            // intake controls
            if (gamepad1.right_bumper) {
                robot.intake.intake();
            } else if (gamepad1.left_bumper) {
                robot.intake.outtake();
            } else {
                robot.intake.stop();
            }


        robot.update(gamepad1.a)}
}
