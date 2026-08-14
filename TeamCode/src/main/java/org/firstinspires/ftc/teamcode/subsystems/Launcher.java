package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.FunctionalCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.Telem;

public class Launcher extends SubsystemBase {

    public static final double ticksPerRev = 28.0;
    public static final double closeRpm = 3200.0;
    public static final double farRpm = 4000.0;
    public static final double idleRpm = -400.0;
    public static final double rpmTolerance = 50.0;

    public enum State { FIRING, IDLE }
    public enum mode { close, far }

    private final DcMotorEx leader;
    private final DcMotorEx follower;

    private State shootState = State.IDLE;
    private mode shootMode = mode.far;
    private double setpointRpm = idleRpm;

    public Launcher(DcMotorEx leader, DcMotorEx follower) {
        this.leader = leader;
        this.follower = follower;

        this.leader.setDirection(DcMotorSimple.Direction.FORWARD);
        this.follower.setDirection(DcMotorSimple.Direction.REVERSE);

        for (DcMotorEx motor : new DcMotorEx[]{this.leader, this.follower}) {
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        setState(State.IDLE);
    }

    public static double rpmToTicksPerSecond(double rpm) {
        return rpm / 60.0 * ticksPerRev;
    }

    public static double ticksPerSecondToRpm(double ticksPerSecond) {
        return ticksPerSecond / ticksPerRev * 60.0;
    }

    private double bangBang(double measuredRpm, double setpointRpm) {
        return measuredRpm < setpointRpm - rpmTolerance ? 1.0 : 0.0;
    }

    public void setState(State state) {
        this.shootState = state;
    }

    public State getState() {
        return shootState;
    }

    public void setMode(mode mode) {
        this.shootMode = mode;
    }

    public mode getMode() {
        return shootMode;
    }

    public double getRpm() {
        return ticksPerSecondToRpm(leader.getVelocity());
    }

    public boolean atSpeed() {
        return shootState == State.FIRING
                && Math.abs(getRpm() - setpointRpm) < rpmTolerance;
    }

    public void stop() {
        shootState = State.IDLE;
        leader.setPower(0.0);
        follower.setPower(0.0);
    }

    public Command spinCommand(mode shootMode) {
        return new FunctionalCommand(
                () -> {
                    setMode(shootMode);
                    setState(State.FIRING);
                },
                () -> {},
                interrupted -> setState(State.IDLE),
                () -> false,
                this
        );
    }

    public Command spinUpCommand(mode mode, long timeoutMs) {
        ElapsedTime timer = new ElapsedTime();
        return new FunctionalCommand(
                () -> {
                    setMode(mode);
                    setState(State.FIRING);
                    timer.reset();
                },
                () -> {},
                interrupted -> {},
                () -> atSpeed() || timer.milliseconds() >= timeoutMs,
                this
        );
    }

    @Override
    public void periodic() {
        double targetRpm = (shootMode == mode.close) ? closeRpm : farRpm;

        setpointRpm = (shootState == State.FIRING) ? targetRpm : idleRpm;

        double measuredRpm = getRpm();
        double power = bangBang(measuredRpm, setpointRpm);

        leader.setPower(power);
        follower.setPower(power);

        Telem.addLine("Launcher");
        Telem.addData("Shoot State", shootState);
        Telem.addData("Mode State", shootMode);
        Telem.addData("Setpoint RPM", setpointRpm);
        Telem.addData("Actual RPM", measuredRpm);
        Telem.addData("At Speed", atSpeed());
        Telem.addData("Power", power);
        Telem.addData("Leader Current (A)", leader.getCurrent(CurrentUnit.AMPS));
        Telem.addData("Follower Current (A)", follower.getCurrent(CurrentUnit.AMPS));
    }
}