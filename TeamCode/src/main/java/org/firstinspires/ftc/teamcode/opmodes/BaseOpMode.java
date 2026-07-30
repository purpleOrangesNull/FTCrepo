package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Telem;
import org.firstinspires.ftc.teamcode.robot.Robot;

public abstract class BaseOpMode extends CommandOpMode {

    protected Robot robot;

    private final ElapsedTime loopTimer = new ElapsedTime();
    private double loopTime = 0;

    protected abstract void configure();

    @Override
    public void initialize() {
        CommandScheduler.getInstance().reset();

        Telem.init(telemetry);

        robot = new Robot(hardwareMap);

        configure();

        telemetry.addLine("Initialized - ready");
        telemetry.update();

        loopTimer.reset();
    }

    @Override
    public void run() {
        robot.clearBulkCache();

        super.run();

        loopTime = loopTimer.milliseconds();
        loopTimer.reset();

        Telem.addLine("Loop");
        Telem.addData("Loop Time (ms)", loopTime);
        Telem.addData("Hz", loopTime > 0 ? 1000.0 / loopTime : 0);

        Telem.update();
    }

    @Override
    public void reset() {
        if (robot != null) {
            robot.stop();
        }
        super.reset();
    }

    protected double getLoopTime() {
        return loopTime;
    }
}