package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.Telem;

public class Launcher {

    // ---- TUNE THESE ----------------------------------------------------

    // Encoder counts per revolution OF THE OUTPUT SHAFT.
    // Bare goBILDA 5202/5203 and bare REV HD Hex are both 28.
    // With a gearbox this is 28 * gearRatio.
    public static final double TICKS_PER_REV = 28.0;

    // Motor free speed, used only to compute the feedforward term.
    public static final double MAX_RPM = 6000.0;

    public static final double FIRE_RPM = 4000.0;
    public static final double IDLE_RPM = -100.0;

    // How close to target counts as ready to shoot.
    public static final double RPM_TOLERANCE = 150.0;

    // --------------------------------------------------------------------

    public enum State { FIRING, IDLE }

    private final DcMotorEx motor;
    private State state = State.IDLE;

    public Launcher(DcMotorEx motor) {
        this.motor = motor;

        this.motor.setDirection(DcMotorSimple.Direction.FORWARD);

        // A flywheel should coast. BRAKE would fight its own inertia.
        this.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        this.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Default velocity PIDF is tuned for slow geared motors and will
        // undershoot at 4000 RPM. F dominates for a flywheel: full output
        // (32767) divided by the maximum achievable ticks per second.
        this.motor.setVelocityPIDFCoefficients(
                10.0,                                   // P
                0.5,                                    // I
                0.0,                                    // D
                32767.0 / rpmToTicksPerSecond(MAX_RPM)  // F
        );

        setState(State.IDLE);
    }

    public static double rpmToTicksPerSecond(double rpm) {
        return rpm / 60.0 * TICKS_PER_REV;
    }

    public static double ticksPerSecondToRpm(double ticksPerSecond) {
        return ticksPerSecond / TICKS_PER_REV * 60.0;
    }

    /** Command any speed directly, in RPM. */
    public void setRpm(double rpm) {
        motor.setVelocity(rpmToTicksPerSecond(rpm));
    }

    public void setState(State state) {
        this.state = state;
        switch (state) {
            case FIRING:
                setRpm(FIRE_RPM);
                break;
            case IDLE:
                setRpm(IDLE_RPM);
                break;
        }
    }

    public State getState() {
        return state;
    }

    public double getRpm() {
        return ticksPerSecondToRpm(motor.getVelocity());
    }

    public double getTargetRpm() {
        return state == State.FIRING ? FIRE_RPM : IDLE_RPM;
    }

    /** True only while spun up and holding within tolerance. */
    public boolean atSpeed() {
        return state == State.FIRING
                && Math.abs(getRpm() - FIRE_RPM) < RPM_TOLERANCE;
    }

    /** Cut power entirely and let the wheel coast down. */
    public void stop() {
        state = State.IDLE;
        motor.setPower(0.0);
    }

    public void telemetry() {
        Telem.addLine("Launcher");
        Telem.addData("State", state);
        Telem.addData("Target RPM", getTargetRpm());
        Telem.addData("Actual RPM", getRpm());
        Telem.addData("At Speed", atSpeed());
        Telem.addData("Current (A)", motor.getCurrent(CurrentUnit.AMPS));
    }
}