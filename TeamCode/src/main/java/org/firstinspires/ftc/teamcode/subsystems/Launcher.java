package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.Telem;

public class Launcher extends SubsystemBase {

    public static final double ticksPerRev = 28.0;
    public static final double maxRpm = 6000.0;
    public static final double fireRpm = 4000.0;
    public static final double idleRpm = -300.0;
    public static final double rpmTolerance = 150.0;

    public enum State { FIRING, IDLE }

    private final DcMotorEx motor;
    private State state = State.IDLE;

    public Launcher(DcMotorEx motor) {
        this.motor = motor;

        this.motor.setDirection(DcMotorSimple.Direction.FORWARD);
        this.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        this.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        this.motor.setVelocityPIDFCoefficients(
                10.0,
                0.5,
                0.0,
                32767.0 / rpmToTicksPerSecond(maxRpm)
        );

        setState(State.IDLE);
    }

    public static double rpmToTicksPerSecond(double rpm) {
        return rpm / 60.0 * ticksPerRev;
    }

    public static double ticksPerSecondToRpm(double ticksPerSecond) {
        return ticksPerSecond / ticksPerRev * 60.0;
    }

    public void setRpm(double rpm) {
        motor.setVelocity(rpmToTicksPerSecond(rpm));
    }

    public void setState(State state) {
        this.state = state;
        switch (state) {
            case FIRING:
                setRpm(fireRpm);
                break;
            case IDLE:
                setRpm(idleRpm);
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
        return state == State.FIRING ? fireRpm : idleRpm;
    }

    public boolean atSpeed() {
        return state == State.FIRING
                && Math.abs(getRpm() - fireRpm) < rpmTolerance;
    }

    public void stop() {
        state = State.IDLE;
        motor.setPower(0.0);
    }

    @Override
    public void periodic() {
        Telem.addLine("Launcher");
        Telem.addData("State", state);
        Telem.addData("Target RPM", getTargetRpm());
        Telem.addData("Actual RPM", getRpm());
        Telem.addData("At Speed", atSpeed());
        Telem.addData("Current (A)", motor.getCurrent(CurrentUnit.AMPS));
    }
}