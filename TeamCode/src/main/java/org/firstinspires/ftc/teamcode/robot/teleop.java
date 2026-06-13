package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.robot.Robot;
import org.firstinspires.ftc.teamcode.Telem;
import org.firstinspires.ftc.teamcode.subsystems.intake;

@TeleOp(name = "TeleopMain")
public class teleop extends LinearOpMode {
    private Robot robot;
    @Override
    public void runOpMode() {
        Telem.init(telemetry);
        robot = new Robot(hardwareMap);
        //wait();
        waitForStart();

        while (opModeIsActive()) {
            robot.drivetrain.drive(gamepad1.left_stick_x,
                    gamepad1.left_stick_y,
                    gamepad1.right_stick_x);
            if (gamepad1.right_bumper){
                robot.intake.setState(intake.intakeState.ON);
            } else if (gamepad1.left_bumper) {
                robot.intake.setState(intake.intakeState.OFF);
                //add in reverse here later
            } else {
                robot.intake.setState(intake.intakeState.ON);

            robot.update(gamepad1.a);
        }
    }}