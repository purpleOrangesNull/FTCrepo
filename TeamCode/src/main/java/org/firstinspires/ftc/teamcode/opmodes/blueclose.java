package org.firstinspires.ftc.teamcode.OpModes;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Constants.Constants;
import org.firstinspires.ftc.teamcode.Telem;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Launcher;

@Autonomous(name = "Blue Close Auto", group = "Autonomous")
public class blueclose extends LinearOpMode {

    private static final long SPINUP_TIMEOUT_MS = 3000;
    private static final long FEED_TIME_MS = 2500;

    private Follower follower;
    private Launcher launcher;
    private Intake intake;

    private int pathState;
    private final ElapsedTime waitTimer = new ElapsedTime();

    public PathChain MainChain;
    public PathChain Chain2;
    public PathChain Chain3;
    public PathChain Chain4;
    public PathChain Chain5;
    public PathChain Chain6;
    public PathChain Chain7;
    public PathChain Chain8;
    public PathChain Chain9;

    @Override
    public void runOpMode() throws InterruptedException {
        Telem.init(telemetry);

        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(new Pose(15.08, 126.41, Math.toRadians(130)));

        DcMotorEx launcherLeader = hardwareMap.get(DcMotorEx.class, "launcherLeader");
        DcMotorEx launcherFollower = hardwareMap.get(DcMotorEx.class, "launcherFollower");
        launcher = new Launcher(launcherLeader, launcherFollower);

        DcMotor intakeMotor = hardwareMap.get(DcMotor.class, "intake");
        DcMotor transferMotor = hardwareMap.get(DcMotor.class, "transfer");
        intake = new Intake(intakeMotor, transferMotor);

        buildPaths();
        pathState = 0;

        waitForStart();

        while (opModeIsActive()) {
            follower.update();
            launcher.periodic();
            intake.periodic();

            switch (pathState) {
                case 0:
                    follower.followPath(MainChain, true);
                    pathState = 1;
                    break;
                case 1:
                    if (!follower.isBusy()) {
                        startSpinUp();
                        pathState = 2;
                    }
                    break;
                case 2:
                    if (launcher.atSpeed() || waitTimer.milliseconds() >= SPINUP_TIMEOUT_MS) {
                        startFeed();
                        pathState = 3;
                    }
                    break;
                case 3:
                    if (waitTimer.milliseconds() >= FEED_TIME_MS) {
                        stopShooting();
                        follower.followPath(Chain2, true);
                        intake.setState(Intake.State.ON);
                        pathState = 4;
                    }
                    break;
                case 4:
                    if (!follower.isBusy()) {
                        intake.setState(Intake.State.OFF);
                        follower.followPath(Chain3, true);
                        pathState = 5;
                    }
                    break;
                case 5:
                    if (!follower.isBusy()) {
                        startSpinUp();
                        pathState = 6;
                    }
                    break;
                case 6:
                    if (launcher.atSpeed() || waitTimer.milliseconds() >= SPINUP_TIMEOUT_MS) {
                        startFeed();
                        pathState = 7;
                    }
                    break;
                case 7:
                    if (waitTimer.milliseconds() >= FEED_TIME_MS) {
                        stopShooting();
                        follower.followPath(Chain4, true);
                        intake.setState(Intake.State.ON);
                        pathState = 8;
                    }
                    break;
                case 8:
                    if (!follower.isBusy()) {
                        intake.setState(Intake.State.OFF);
                        follower.followPath(Chain5, true);
                        pathState = 9;
                    }
                    break;
                case 9:
                    if (!follower.isBusy()) {
                        startSpinUp();
                        pathState = 10;
                    }
                    break;
                case 10:
                    if (launcher.atSpeed() || waitTimer.milliseconds() >= SPINUP_TIMEOUT_MS) {
                        startFeed();
                        pathState = 11;
                    }
                    break;
                case 11:
                    if (waitTimer.milliseconds() >= FEED_TIME_MS) {
                        stopShooting();
                        follower.followPath(Chain6, true);
                        intake.setState(Intake.State.ON);
                        pathState = 12;
                    }
                    break;
                case 12:
                    if (!follower.isBusy()) {
                        intake.setState(Intake.State.OFF);
                        follower.followPath(Chain7, true);
                        pathState = 13;
                    }
                    break;
                case 13:
                    if (!follower.isBusy()) {
                        startSpinUp();
                        pathState = 14;
                    }
                    break;
                case 14:
                    if (launcher.atSpeed() || waitTimer.milliseconds() >= SPINUP_TIMEOUT_MS) {
                        startFeed();
                        pathState = 15;
                    }
                    break;
                case 15:
                    if (waitTimer.milliseconds() >= FEED_TIME_MS) {
                        stopShooting();
                        follower.followPath(Chain8, true);
                        pathState = 16;
                    }
                    break;
                case 16:
                    if (!follower.isBusy()) {
                        follower.followPath(Chain9, true);
                        pathState = 17;
                    }
                    break;
                case 17:
                    if (!follower.isBusy()) {
                        pathState = -1;
                    }
                    break;
                default:
                    break;
            }

            Telem.addData("POSE", follower.getPose());
            Telem.addData("Path State", pathState);
            Telem.update();
        }
    }

    private void startSpinUp() {
        launcher.setMode(Launcher.mode.close);
        launcher.setState(Launcher.State.FIRING);
        waitTimer.reset();
    }

    private void startFeed() {
        intake.setTransferState(Intake.State.ON);
        waitTimer.reset();
    }

    private void stopShooting() {
        intake.setTransferState(Intake.State.OFF);
        launcher.setState(Launcher.State.IDLE);
    }

    public void buildPaths() {
        MainChain = follower.pathBuilder()
                .addPath(new BezierLine(new Pose(15.08, 126.41), new Pose(53.49, 88.81)))
                .setLinearHeadingInterpolation(Math.toRadians(130), Math.toRadians(130))
                .build();

        Chain2 = follower.pathBuilder()
                .addPath(new BezierCurve(
                        new Pose(53.49, 88.81),
                        new Pose(38.69, 80.77),
                        new Pose(10.86, 82.45)))
                .setLinearHeadingInterpolation(Math.toRadians(130), Math.toRadians(180))
                .build();

        Chain3 = follower.pathBuilder()
                .addPath(new BezierLine(new Pose(10.86, 82.45), new Pose(54.60, 84.79)))
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(130))
                .build();

        Chain4 = follower.pathBuilder()
                .addPath(new BezierCurve(
                        new Pose(54.60, 84.79),
                        new Pose(53.67, 56.74),
                        new Pose(11.13, 58.47)))
                .setLinearHeadingInterpolation(Math.toRadians(130), Math.toRadians(180))
                .build();

        Chain5 = follower.pathBuilder()
                .addPath(new BezierLine(new Pose(11.13, 58.47), new Pose(56.37, 85.45)))
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(130))
                .build();

        Chain6 = follower.pathBuilder()
                .addPath(new BezierCurve(
                        new Pose(56.37, 85.45),
                        new Pose(60.70, 38.20),
                        new Pose(44.44, 30.17),
                        new Pose(13.08, 35.98)))
                .setLinearHeadingInterpolation(Math.toRadians(130), Math.toRadians(180))
                .build();

        Chain7 = follower.pathBuilder()
                .addPath(new BezierLine(new Pose(13.08, 35.98), new Pose(54.83, 87.92)))
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(130))
                .build();

        Chain8 = follower.pathBuilder()
                .addPath(new BezierLine(new Pose(54.83, 87.92), new Pose(8.82, 70.92)))
                .setLinearHeadingInterpolation(Math.toRadians(130), Math.toRadians(90))
                .build();

        Chain9 = follower.pathBuilder()
                .addPath(new BezierLine(new Pose(8.82, 70.92), new Pose(2.24, 3.07)))
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(270))
                .build();
    }
}