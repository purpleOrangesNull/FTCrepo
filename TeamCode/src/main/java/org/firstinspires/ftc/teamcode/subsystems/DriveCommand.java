package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class DriveCommand extends CommandBase {

    public static final double SLOW_FACTOR = 0.35;

    private final Drivetrain drivetrain;
    private final DoubleSupplier lx;
    private final DoubleSupplier ly;
    private final DoubleSupplier rx;
    private final BooleanSupplier slow;

    public DriveCommand(Drivetrain drivetrain,
                        DoubleSupplier lx,
                        DoubleSupplier ly,
                        DoubleSupplier rx,
                        BooleanSupplier slow) {
        this.drivetrain = drivetrain;
        this.lx = lx;
        this.ly = ly;
        this.rx = rx;
        this.slow = slow;

        addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        double scale = slow.getAsBoolean() ? SLOW_FACTOR : 1.0;

        drivetrain.drive(
                lx.getAsDouble() * scale,
                ly.getAsDouble() * scale,
                rx.getAsDouble() * scale
        );
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.stop();
    }
}