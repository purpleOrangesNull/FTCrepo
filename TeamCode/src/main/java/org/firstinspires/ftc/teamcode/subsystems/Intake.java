package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.FunctionalCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Telem;

public class Intake extends SubsystemBase {

    public static final double INTAKE_POWER = 1.0;
    public static final double TRANSFER_POWER = 1.0;

    public enum State { ON, OFF, REV }

    private final DcMotor motor;
    private final DcMotor transferMotor;

    private State state = State.OFF;
    private State transferState = State.OFF;

    public Intake(DcMotor motor, DcMotor transferMotor) {
        this.motor = motor;
        this.motor.setDirection(DcMotorSimple.Direction.REVERSE);
        this.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        this.transferMotor = transferMotor;
        this.transferMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        this.transferMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        setState(State.OFF);
        setTransferState(State.OFF);
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
            case REV:
                motor.setPower(0.8);
                break;
        }
    }

    public State getState() {
        return state;
    }

    public void setTransferState(State state) {
        this.transferState = state;
        switch (state) {
            case ON:
                transferMotor.setPower(TRANSFER_POWER);
                break;
            case OFF:
                transferMotor.setPower(0.0);
                break;
            case REV:
                transferMotor.setPower(0.8);
                break;
        }
    }

    public State getTransferState() {
        return transferState;
    }

    public void stop() {
        setState(State.OFF);
        setTransferState(State.OFF);
    }

    public Command runCommand(State runState) {
        return new FunctionalCommand(
                () -> setState(runState),
                () -> {},
                interrupted -> stop(),
                () -> false,
                this
        );
    }

    public Command runTransferCommand(State runState) {
        return new FunctionalCommand(
                () -> setTransferState(runState),
                () -> {},
                interrupted -> setTransferState(State.OFF),
                () -> false,
                this
        );
    }

    @Override
    public void periodic() {
        Telem.addLine("Intake");
        Telem.addData("State", state);
        Telem.addData("Power", motor.getPower());
        Telem.addLine("Transfer");
        Telem.addData("Transfer State", transferState);
        Telem.addData("Transfer Power", transferMotor.getPower());
    }
}