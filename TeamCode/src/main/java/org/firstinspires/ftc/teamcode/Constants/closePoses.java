package org.firstinspires.ftc.teamcode.Constants;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.PathChain;

public class closePoses {

    public PathChain MainChain;
    public PathChain Chain2;
    public PathChain Chain3;
    public PathChain Chain4;
    public PathChain Chain5;
    public PathChain Chain6;
    public PathChain Chain7;
    public PathChain Chain8;
    public PathChain Chain9;

    public closePoses(Follower follower) {
        MainChain = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Pose(15.08, 126.41),
                                new Pose(53.49, 88.81)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(130), Math.toRadians(130))
                .build();

        Chain2 = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(53.49, 88.81),
                                new Pose(38.69, 80.77),
                                new Pose(10.86, 82.45)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(130), Math.toRadians(180))
                .build();

        Chain3 = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Pose(10.86, 82.45),
                                new Pose(54.60, 84.79)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(130))
                .build();

        Chain4 = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(54.60, 84.79),
                                new Pose(53.67, 56.74),
                                new Pose(11.13, 58.47)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(130), Math.toRadians(180))
                .build();

        Chain5 = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Pose(11.13, 58.47),
                                new Pose(56.37, 85.45)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(130))
                .build();

        Chain6 = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(56.37, 85.45),
                                new Pose(60.70, 38.20),
                                new Pose(44.44, 30.17),
                                new Pose(13.08, 35.98)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(130), Math.toRadians(180))
                .build();

        Chain7 = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Pose(13.08, 35.98),
                                new Pose(54.83, 87.92)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(130))
                .build();

        Chain8 = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Pose(54.83, 87.92),
                                new Pose(8.82, 70.92)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(130), Math.toRadians(90))
                .build();

        Chain9 = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Pose(8.82, 70.92),
                                new Pose(2.24, 3.07)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(270))
                .build();
    }
}