package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Telem;

public class Drivetrain extends SubsystemBase {

    private static final double EPSILON = 1e-3;

    public enum State { DRIVING, STOPPED }

    private final DcMotor frontLeft;
    private final DcMotor frontRight;
    private final DcMotor backLeft;
    private final DcMotor backRight;

    private State state = State.STOPPED;

    public Drivetrain(DcMotor fl, DcMotor fr, DcMotor bl, DcMotor br) {
        frontLeft = fl;
        frontRight = fr;
        backLeft = bl;
        backRight = br;

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void drive(double lx, double ly, double rx) {
        double fl = ly + lx + rx;
        double fr = ly - lx - rx;
        double bl = ly - lx + rx;
        double br = ly + lx - rx;

        double max = Math.max(1.0,
                Math.max(Math.max(Math.abs(fl), Math.abs(fr)),
                        Math.max(Math.abs(bl), Math.abs(br))));

        fl /= max;
        fr /= max;
        bl /= max;
        br /= max;

        frontLeft.setPower(fl);
        frontRight.setPower(fr);
        backLeft.setPower(bl);
        backRight.setPower(br);

        boolean moving = Math.abs(fl) > EPSILON
                || Math.abs(fr) > EPSILON
                || Math.abs(bl) > EPSILON
                || Math.abs(br) > EPSILON;

        state = moving ? State.DRIVING : State.STOPPED;
    }

    public void stop() {
        drive(0, 0, 0);
    }

    public State getState() {
        return state;
    }

    @Override
    public void periodic() {
        Telem.addLine("Drivetrain");
        Telem.addData("State", state);
    }
}