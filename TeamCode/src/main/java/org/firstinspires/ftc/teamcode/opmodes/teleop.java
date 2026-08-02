package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Constants.BaseOpMode;
import org.firstinspires.ftc.teamcode.subsystems.DriveCommand;
import org.firstinspires.ftc.teamcode.subsystems.Launcher;
import org.firstinspires.ftc.teamcode.subsystems.intake;

@TeleOp(name = "TeleopMain")
public class teleop extends BaseOpMode {

    private GamepadEx driver;

    @Override
    protected void configure() {
        driver = new GamepadEx(gamepad1);

        robot.drivetrain.setDefaultCommand(new DriveCommand(
                robot.drivetrain,
                driver::getLeftX,
                driver::getLeftY,
                driver::getRightX,
                () -> driver.getButton(GamepadKeys.Button.A)
        ));

        new GamepadButton(driver, GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(new InstantCommand(
                        () -> robot.intake.setState(intake.State.ON),

                        robot.intake))
                .whenReleased(new InstantCommand(
                        () -> robot.intake.setState(intake.State.OFF),
                        robot.intake));

        new GamepadButton(driver, GamepadKeys.Button.Y)
                .whenPressed(new InstantCommand(
                        () -> robot.intake.setState(intake.State.REV),

                        robot.intake))
                .whenReleased(new InstantCommand(
                        () -> robot.intake.setState(intake.State.OFF),
                        robot.intake));

        new GamepadButton(driver, GamepadKeys.Button.X)
                .whenPressed(new InstantCommand(
                        () -> {
                            robot.launcher.setMode(Launcher.mode.close);
                            robot.launcher.setState(Launcher.State.FIRING);
                        },
                        robot.launcher))
                .whenReleased(new InstantCommand(
                        () -> robot.launcher.setState(Launcher.State.IDLE),
                        robot.launcher));

        new GamepadButton(driver, GamepadKeys.Button.A)
                .whenPressed(new InstantCommand(
                        () -> {
                            robot.launcher.setMode(Launcher.mode.far);
                            robot.launcher.setState(Launcher.State.FIRING);
                        },
                        robot.launcher))
                .whenReleased(new InstantCommand(
                        () -> robot.launcher.setState(Launcher.State.IDLE),
                        robot.launcher));
    }
}