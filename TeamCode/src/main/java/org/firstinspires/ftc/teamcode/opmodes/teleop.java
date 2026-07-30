package org.firstinspires.ftc.teamcode.opmodes;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.DriveCommand;
import org.firstinspires.ftc.teamcode.subsystems.Launcher;
import org.firstinspires.ftc.teamcode.subsystems.intake;

/**
 * No while loop. CommandOpMode owns it - see BaseOpMode.run(). Everything
 * here happens exactly once, at init. After that the scheduler polls these
 * bindings on its own.
 */
@TeleOp(name = "TeleopMain")
public class teleop extends org.firstinspires.ftc.teamcode.opmodes.BaseOpMode {

    private GamepadEx driver;

    @Override
    protected void configure() {
        driver = new GamepadEx(gamepad1);

        // Default command: runs whenever nothing else has claimed the
        // drivetrain. This replaces the drive lines from the old while loop.
        //
        // NOTE ON THE Y AXIS: GamepadEx.getLeftY() already negates the raw
        // SDK value, so there is deliberately no minus sign here. If forward
        // on the stick drives the robot backward on your first test, that is
        // the line to change - not anything inside Drivetrain.
        robot.drivetrain.setDefaultCommand(new DriveCommand(
                robot.drivetrain,
                driver::getLeftX,
                driver::getLeftY,
                driver::getRightX,
                () -> driver.getButton(GamepadKeys.Button.A)
        ));

        // Intake: hold right bumper.
        //
        // This is edge-triggered, not polled. The old code wrote a motor
        // power every single loop; this writes only on press and release.
        new GamepadButton(driver, GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(new InstantCommand(
                        () -> robot.intake.setState(intake.State.ON),
                        robot.intake))
                .whenReleased(new InstantCommand(
                        () -> robot.intake.setState(intake.State.OFF),
                        robot.intake));

        // Launcher: hold X for 4000 RPM, release to idle backwards at 100 RPM.
        new GamepadButton(driver, GamepadKeys.Button.X)
                .whenPressed(new InstantCommand(
                        () -> robot.launcher.setState(Launcher.State.FIRING),
                        robot.launcher))
                .whenReleased(new InstantCommand(
                        () -> robot.launcher.setState(Launcher.State.IDLE),
                        robot.launcher));
    }
}