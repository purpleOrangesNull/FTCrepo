package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

/**
 * Default command for the drivetrain. Suppliers rather than raw doubles so
 * the command reads live gamepad values every cycle instead of capturing a
 * single stale snapshot at construction time.
 *
 * isFinished() is not overridden, so it defaults to false and this runs
 * forever - which is exactly what a default command should do.
 */
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

        // Declares ownership. If any other command requiring the drivetrain
        // gets scheduled, the scheduler interrupts this one automatically
        // instead of letting both write motor powers in the same loop.
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