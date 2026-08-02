package org.firstinspires.ftc.teamcode.opmodes;

import com.pedropathing.follower.Follower;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Telem;

@Autonomous(name = "Close Blue Auto", group = "Autonomous")
public class blueclose extends OpMode {
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
    }

    @Override
    public void loop() {
        follower.update();
        pathState = autonomousPathUpdate();

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
                    return -1;
                }
                return 5;
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

        public Paths(Follower follower) {
            follower.setStartingPose(Poses.startPose);

            MainChain = follower.pathBuilder()
                    .addPath(new BezierLine(Poses.mc1, Poses.mc2))
                    .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(110))
                    .build();

            Chain2 = follower.pathBuilder()
                    .addPath(new BezierCurve(Poses.mc2, Poses.ctrl2, Poses.mc3))
                    .setLinearHeadingInterpolation(Math.toRadians(110), Math.toRadians(180))
                    .build();

            Chain3 = follower.pathBuilder()
                    .addPath(new BezierLine(Poses.mc3, Poses.mc4))
                    .setLinearHeadingInterpolation(Math.toRadians(110), Math.toRadians(110))
                    .build();

            Chain4 = follower.pathBuilder()
                    .addPath(new BezierLine(Poses.mc4, Poses.mc5))
                    .setLinearHeadingInterpolation(Math.toRadians(110), Math.toRadians(180))
                    .build();

            Chain5 = follower.pathBuilder()
                    .addPath(new BezierLine(Poses.mc5, Poses.mc6))
                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(110))
                    .build();

            Chain6 = follower.pathBuilder()
                    .addPath(new BezierLine(Poses.mc6, Poses.mc7))
                    .setTangentHeadingInterpolation()
                    .build();
        }
    }
}