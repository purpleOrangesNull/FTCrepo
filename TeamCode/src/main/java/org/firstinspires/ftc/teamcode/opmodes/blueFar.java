package org.firstinspires.ftc.teamcode.opmodes;

import com.pedropathing.follower.Follower;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Telem;

@Autonomous(name = "Far Blue Auto", group = "Autonomous")
public class blueFar extends OpMode {
    private ElapsedTime timer;
    private Follower follower;
    private Paths paths;
    private int pathState;

    @Override
    public void init() {
        timer = new ElapsedTime();
        Telem.init(telemetry);
        follower = Constants.createFollower(hardwareMap);
        paths = new Paths(follower);
        pathState = 0;
    }

    @Override
    public void start() {
        timer.reset();

        follower.followPath(paths.MainChain);
        follower.refreshVoltage();
    }

    @Override
    public void loop() {
        follower.update();
        pathState = autonomousPathUpdate();

        Telem.addData("RegV", follower.getVoltage());
        Telem.addData("NormV: ", follower.getVoltageNormalized());

        Telem.addData("POSE", follower.getPose());
        Telem.addData("TIMER", timer.seconds());
        Telem.addData("Path State", pathState);
        Telem.update();
    }

    public int autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                if (!follower.isBusy()) {
                    follower.followPath(paths.Chain2);
                    return 1;
                }
                return 0;
            case 1:
                if (!follower.isBusy()) {
                    follower.followPath(paths.Chain3);
                    return 2;
                }
                return 1;
            case 2:
                if (!follower.isBusy()) {
                    follower.followPath(paths.Chain4);
                    return 3;
                }
                return 2;
            case 3:
                if (!follower.isBusy()) {
                    follower.followPath(paths.Chain5);
                    return 4;
                }
                return 3;
            case 4:
                if (!follower.isBusy()) {
                    follower.followPath(paths.Chain6);
                    return 5;
                }
                return 4;
            case 5:
                if (!follower.isBusy()) {
                    follower.followPath(paths.Chain7);
                    return 6;
                }
                return 5;
            case 6:
                if (!follower.isBusy()) {
                    follower.followPath(paths.Chain8);
                    return 7;
                }
                return 6;
            case 7:
                if (!follower.isBusy()) {
                    return -1;
                }
                return 7;
            default:
                return pathState;
        }
    }

    public static class Paths {
        public PathChain MainChain;
        public PathChain Chain2;
        public PathChain Chain3;
        public PathChain Chain4;
        public PathChain Chain5;
        public PathChain Chain6;
        public PathChain Chain7;
        public PathChain Chain8;

        public Paths(Follower follower) {
            follower.setStartingPose(farPoses.startPose);

            MainChain = follower.pathBuilder()
                    .addPath(new BezierLine(farPoses.mc1, farPoses.mc2))
                    .setLinearHeadingInterpolation(Math.toRadians(130), Math.toRadians(130))
                    .build();

            Chain2 = follower.pathBuilder()
                    .addPath(new BezierLine(farPoses.mc2, farPoses.mc3))
                    .setLinearHeadingInterpolation(Math.toRadians(130), Math.toRadians(170))
                    .build();

            Chain3 = follower.pathBuilder()
                    .addPath(new BezierLine(farPoses.mc3, farPoses.mc4))
                    .setLinearHeadingInterpolation(Math.toRadians(170), Math.toRadians(140))
                    .build();

            Chain4 = follower.pathBuilder()
                    .addPath(new BezierCurve(farPoses.mc4, farPoses.ctrl4, farPoses.mc5))
                    .setLinearHeadingInterpolation(Math.toRadians(140), Math.toRadians(170))
                    .build();

            Chain5 = follower.pathBuilder()
                    .addPath(new BezierLine(farPoses.mc5, farPoses.mc6))
                    .setLinearHeadingInterpolation(Math.toRadians(170), Math.toRadians(130))
                    .build();

            Chain6 = follower.pathBuilder()
                    .addPath(new BezierCurve(farPoses.mc6, farPoses.ctrl6, farPoses.mc7))
                    .setLinearHeadingInterpolation(Math.toRadians(130), Math.toRadians(170))
                    .build();

            Chain7 = follower.pathBuilder()
                    .addPath(new BezierLine(farPoses.mc7, farPoses.mc8))
                    .setLinearHeadingInterpolation(Math.toRadians(170), Math.toRadians(130))
                    .build();

            Chain8 = follower.pathBuilder()
                    .addPath(new BezierLine(farPoses.mc8, farPoses.mc9))
                    .setLinearHeadingInterpolation(Math.toRadians(130), Math.toRadians(130))
                    .build();
        }
    }
}