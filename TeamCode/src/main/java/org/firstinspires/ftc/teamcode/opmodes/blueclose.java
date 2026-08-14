package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.FunctionalCommand;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Constants.Constants;
import org.firstinspires.ftc.teamcode.Constants.closePoses;
import org.firstinspires.ftc.teamcode.Telem;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Launcher;

@Autonomous(name = "Blue Close Auto", group = "Autonomous")
public class blueclose extends OpMode {

    private static final long SPINUP_TIMEOUT_MS = 3000;
    private static final long FEED_TIME_MS = 2500;

    private ElapsedTime timer;
    private Follower follower;
    private Launcher launcher;
    private Intake intake;

    private closePoses paths;
    private SequentialCommandGroup auto;

    @Override
    public void init() {
        CommandScheduler.getInstance().reset();

        timer = new ElapsedTime();
        Telem.init(telemetry);

        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(new Pose(15.08, 126.41, Math.toRadians(130)));

        DcMotorEx launcherMotor = hardwareMap.get(DcMotorEx.class, "launcher");
        DcMotorEx launcher2 = hardwareMap.get(DcMotorEx.class, "launcher2");
        launcher = new Launcher(launcherMotor, launcher2);

        DcMotorEx intakeMotor = hardwareMap.get(DcMotorEx.class, "intake");
        DcMotor transferMotor = hardwareMap.get(DcMotor.class, "transfer");
        intake = new Intake(intakeMotor, transferMotor);

        paths = new closePoses(follower);

        auto = buildAuto();
    }

    @Override
    public void start() {
        timer.reset();
        CommandScheduler.getInstance().schedule(auto);
    }

    @Override
    public void loop() {
        follower.update();
        CommandScheduler.getInstance().run();

        Telem.addData("Pose", follower.getPose());
        Telem.addData("Busy", follower.isBusy());
        Telem.addData("Timer", timer.seconds());
        Telem.update();
    }

    @Override
    public void stop() {
        CommandScheduler.getInstance().reset();
    }

    private Command followPathCommand(PathChain path) {
        return new FunctionalCommand(
                () -> follower.followPath(path, true),
                () -> {},
                interrupted -> {},
                () -> !follower.isBusy()
        );
    }

    private SequentialCommandGroup shootCycle(PathChain travelToPickup, PathChain travelToShoot) {
        return new SequentialCommandGroup(
                new ParallelCommandGroup(
                        followPathCommand(travelToPickup),
                        new InstantCommand(() -> intake.setState(Intake.State.ON))
                ),
                new InstantCommand(() -> intake.setState(Intake.State.REV)),
                followPathCommand(travelToShoot),
                new InstantCommand(() -> intake.setState(Intake.State.OFF)),
                launcher.spinUpCommand(Launcher.mode.close, SPINUP_TIMEOUT_MS),
                intake.feedCommand(launcher, FEED_TIME_MS),
                new InstantCommand(() -> intake.setState(Intake.State.REV))
        );
    }

    private SequentialCommandGroup buildAuto() {
        return new SequentialCommandGroup(
                new InstantCommand(() -> intake.setState(Intake.State.REV)),
                followPathCommand(paths.MainChain),
                new InstantCommand(() -> intake.setState(Intake.State.OFF)),
                launcher.spinUpCommand(Launcher.mode.close, SPINUP_TIMEOUT_MS),
                intake.feedCommand(launcher, FEED_TIME_MS),
                new InstantCommand(() -> intake.setState(Intake.State.REV)),

                shootCycle(paths.Chain2, paths.Chain3),
                shootCycle(paths.Chain4, paths.Chain5),
                shootCycle(paths.Chain6, paths.Chain7),

                followPathCommand(paths.Chain8),
                followPathCommand(paths.Chain9)
        );
    }
}