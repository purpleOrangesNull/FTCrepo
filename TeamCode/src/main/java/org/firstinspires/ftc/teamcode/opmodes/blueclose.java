package org.firstinspires.ftc.teamcode.opmodes;

import com.pedropathing.follower.Follower;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Telem;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.Constants;

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
                    return -1;
                }
                return 0;
            default:
                return pathState;
        }
    }

    public static class Paths {
        public PathChain MainChain;

        public Paths(Follower follower) {
            follower.setStartingPose(Poses.startPose);

            MainChain = follower.pathBuilder()
                    .addPath(new BezierLine(Poses.mc1, Poses.mc2))
                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(135))
                    .addPath(new BezierCurve(Poses.mc2, Poses.mc3, Poses.mc4))
                    .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(180))
                    .addPath(new BezierLine(Poses.mc4, Poses.mc5))
                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(135))
                    .addPath(new BezierLine(Poses.mc5, Poses.mc6))
                    .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(180))
                    .addPath(new BezierLine(Poses.mc6, Poses.mc7))
                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                    .addPath(new BezierLine(Poses.mc7, Poses.mc8))
                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(135))
                    .addPath(new BezierLine(Poses.mc8, Poses.mc9))
                    .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(180))
                    .addPath(new BezierLine(Poses.mc9, Poses.mc10))
                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                    .addPath(new BezierLine(Poses.mc10, Poses.mc11))
                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(135))
                    .addPath(new BezierLine(Poses.mc11, Poses.mc12))
                    .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(135))
                    .addPath(new BezierLine(Poses.mc12, Poses.mc13))
                    .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(180))
                    .build();
        }
    }
}