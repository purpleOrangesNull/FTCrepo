package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Telem;

public class intake extends SubsystemBase {

    public static final double INTAKE_POWER = 1.0;

    public enum State { ON, OFF }

    private final DcMotor motor;
    private State state = State.OFF;

    public intake(DcMotor motor) {
        this.motor = motor;
        this.motor.setDirection(DcMotorSimple.Direction.REVERSE);
        this.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        setState(State.OFF);
    }

    public void setState(State state) {
        this.state = state;
        switch (state) {
            case ON:
                motor.setPower(INTAKE_POWER);
                break;
            case OFF:
                motor.setPower(0.0);
                break;
        }
    }

    public State getState() {
        return state;
    }

    public void stop() {
        setState(State.OFF);
    }

    @Override
    public void periodic() {
        Telem.addLine("Intake");
        Telem.addData("State", state);
        Telem.addData("Power", motor.getPower());
    }
}