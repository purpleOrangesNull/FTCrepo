package org.firstinspires.ftc.teamcode.subsystems;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Telem;

public class Drivetrain {
    private DcMotor frontLeft, frontRight, backLeft, backRight;

    public enum State { DRIVING, STOPPED }
    private State state = State.STOPPED;

    public Drivetrain(DcMotor fl, DcMotor fr, DcMotor bl, DcMotor br) {
        frontLeft  = fl;
        frontRight = fr;
        backLeft   = bl;
        backRight  = br;

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

        frontLeft.setPower(fl);
        frontRight.setPower(fr);
        backLeft.setPower(bl);
        backRight.setPower(br);

        state = (fl == 0 && fr == 0 && bl == 0 && br == 0) ? State.STOPPED : State.DRIVING;
    }

    public void stop() {
        drive(0, 0, 0);
    }

public void telemetry(){
        Telem.addLine("Drivetrain");
    Telem.addData("State", state);

}
}